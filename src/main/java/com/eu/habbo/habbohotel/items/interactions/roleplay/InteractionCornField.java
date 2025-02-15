package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.inventory.AddHabboItemComposer;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserHandItemComposer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InteractionCornField extends InteractionDefault {

    public static final String INTERACTION_TYPE = "rp_corn_field";
    public static final String NEEDS_SEEDING = "0";
    public static final String NEEDS_WATERED = "1";
    public static final String NEEDS_HARVESTED = "2";
    public static final String IN_COOLDOWN = "3";

    public boolean isGrowing = false;

    public InteractionCornField(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionCornField(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {
        return false;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        boolean isCoolingDown = Objects.equals(this.getExtradata(), InteractionCornField.IN_COOLDOWN);

        if (isCoolingDown) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corn_field_cooldown"));
            return;
        }

        boolean isWithinOneTile = Math.abs(this.getX() - client.getHabbo().getRoomUnit().getX()) <= 1 && Math.abs(this.getY() - client.getHabbo().getRoomUnit().getY()) <= 1;

        if (!isWithinOneTile) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.too_far_away"));
            return;
        }

        boolean isNotPlanted = Objects.equals(this.getExtradata(), InteractionCornField.NEEDS_SEEDING);

        if (isNotPlanted) {
            if (client.getHabbo().getRoomUnit().getHandItem() != InteractionCornSeedBag.CORN_SEED_HAND_ITEM_ID) {
                client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corn_field_out_of_seeds"));
                return;
            }

            client.getHabbo().getRoomUnit().setHandItem(0);
            room.sendComposer(new RoomUserHandItemComposer(client.getHabbo().getRoomUnit()).compose());

            client.getHabbo().shout(Emulator.getTexts().getValue("rp.corn_field_plants_seeds"));

            this.setExtradata(InteractionCornField.NEEDS_WATERED);
            room.updateItemState(this);
            return;
        }

        if (this.isGrowing) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corn_field_is_still_growing"));
            return;
        }

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        boolean isNotWatered = Objects.equals(this.getExtradata(), InteractionCornField.NEEDS_WATERED);

        if (isNotWatered) {
            if (client.getHabbo().getRoomUnit().getHandItem() != InteractionWateringCan.WATERING_CAN_HAND_ITEM_ID) {
                client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corn_field_you_need_water"));
                return;
            }

            client.getHabbo().getRoomUnit().setHandItem(0);
            room.sendComposer(new RoomUserHandItemComposer(client.getHabbo().getRoomUnit()).compose());

            int growingSeconds = Emulator.getConfig().getInt("rp.corn_field_grow_seconds");

            client.getHabbo().shout(Emulator.getTexts()
                    .getValue("rp.corn_field_waters_field")
                    .replace(":secs", String.valueOf(growingSeconds))
            );

            this.isGrowing = true;

            scheduler.schedule(() -> {
                this.setExtradata(InteractionCornField.NEEDS_HARVESTED);
                room.updateItemState(this);
                this.isGrowing = false;
            }, growingSeconds, TimeUnit.SECONDS);
            return;
        }

        Item farmedCorn = Emulator.getGameEnvironment().getItemManager().getItemByInteractionType(InteractionCorn.class);
        HabboItem givenFarmedCorn = Emulator.getGameEnvironment().getItemManager().createItem(client.getHabbo().getHabboInfo().getId(), farmedCorn, 0, 0, "");
        client.getHabbo().getInventory().getItemsComponent().addItem(givenFarmedCorn);
        client.getHabbo().getClient().sendResponse(new AddHabboItemComposer(givenFarmedCorn));
        client.getHabbo().getClient().sendResponse(new InventoryRefreshComposer());
        client.getHabbo().shout(Emulator.getTexts().getValue("rp.corn_field_success"));
        this.setExtradata(InteractionCornField.IN_COOLDOWN);
        room.updateItemState(this);

        scheduler.schedule(() -> {
            this.setExtradata(InteractionCornField.NEEDS_SEEDING);
            room.updateItemState(this);
        }, Emulator.getConfig().getInt("rp.corn_field.replenish_seconds"), TimeUnit.SECONDS);
    }

}
