package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionWater;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.inventory.AddHabboItemComposer;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class InteractionFishingSource extends InteractionWater {

    public static final String INTERACTION_TYPE = "rp_fishing_source";
    private static final Random random = new Random();
    private static final int FISHING_HAND_ITEM_BAIT = 1092;
    private static final int FISHING_HAND_ITEM_CAUGHT = 1090;

    public InteractionFishingSource(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionFishingSource(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
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
        Habbo habbo = client.getHabbo();
        RoomUnit roomUnit = habbo.getRoomUnit();
        RoomTile roomTile = room.getLayout().getTile(roomUnit.getX(), roomUnit.getY());

        boolean isWithinOneTile = Math.abs(this.getX() - roomUnit.getX()) <= 1 && Math.abs(this.getY() - roomUnit.getY()) <= 1;

        if (!isWithinOneTile) {
            habbo.whisper(Emulator.getTexts().getValue("rp.too_far_away"));
            return;
        }

        HabboItem fishingLine = habbo.getInventory().getItemsComponent().getItemsAsValueCollection().stream()
                .filter(item -> InteractionFishingLine.class.isAssignableFrom(item.getBaseItem().getInteractionType().getType()))
                .findFirst()
                .orElse(null);

        if (fishingLine == null) {
            habbo.whisper(Emulator.getTexts().getValue("rp.fishing_no_line"));
            return;
        }

        habbo.shout(Emulator.getTexts().getValue("rp.fishing_start"));
        habbo.getRoomUnit().setHandItem(FISHING_HAND_ITEM_BAIT);

        startFishing(habbo, roomTile);
    }

    private void startFishing(Habbo habbo, RoomTile roomTile) {
        Emulator.getThreading().run(new Runnable() {
            private int fishingCycles = 0;

            @Override
            public void run() {
                if (habbo.getRoomUnit().getPreviousLocation() != roomTile) {
                    habbo.whisper(Emulator.getTexts().getValue("rp.fishing_stop"));
                    return;
                }

                if (fishingCycles > 1) {
                    double baseRatio = Emulator.getConfig().getDouble("roleplay.fishing.catch_ratio");
                    if (random.nextDouble() < baseRatio) {
                        onFishingComplete(habbo);
                        return;
                    }
                }

                fishingCycles++;
                Emulator.getThreading().run(this, 1000);
            }
        }, 1000);
    }

    private void onFishingComplete(Habbo habbo) {
        habbo.getHabboInfo().run();
        habbo.shout(Emulator.getTexts().getValue("rp_fishing_success"));
        habbo.getRoomUnit().setHandItem(FISHING_HAND_ITEM_CAUGHT);

        Emulator.getThreading().run(() -> {
            habbo.getRoomUnit().setHandItem(0);
        }, 2000);

        Item fishBaseItem = Emulator.getGameEnvironment().getItemManager().getItemByInteractionType(InteractionCaughtFish.class);
        HabboItem fishRoomItem = Emulator.getGameEnvironment().getItemManager().createItem(habbo.getHabboInfo().getId(), fishBaseItem, 0, 0, "");
        habbo.getInventory().getItemsComponent().addItem(fishRoomItem);
        habbo.getClient().sendResponse(new AddHabboItemComposer(fishRoomItem));
        habbo.getClient().sendResponse(new InventoryRefreshComposer());
    }
}
