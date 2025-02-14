package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.inventory.AddHabboItemComposer;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionCornSeedBag extends InteractionDefault {

    public static final String INTERACTION_TYPE = "rp_corn_seed_bag";

    public InteractionCornSeedBag(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionCornSeedBag(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        boolean isWithinOneTile = Math.abs(this.getX() - client.getHabbo().getRoomUnit().getX()) <= 1 && Math.abs(this.getY() - client.getHabbo().getRoomUnit().getY()) <= 1;

        if (!isWithinOneTile) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.too_far_away"));
            return;
        }

        HabboItem ownedCornSeed = client.getHabbo().getInventory().getItemsComponent().getItemsAsValueCollection().stream()
                .filter(item -> InteractionCornSeed.class.isAssignableFrom(item.getBaseItem().getInteractionType().getType()))
                .findFirst()
                .orElse(null);

        if (ownedCornSeed != null) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corn_seed_bag_inventory_full"));
            return;
        }

        Item cornSeed = Emulator.getGameEnvironment().getItemManager().getItemByInteractionType(InteractionCornSeed.class);
        HabboItem givenCornSeed = Emulator.getGameEnvironment().getItemManager().createItem(client.getHabbo().getHabboInfo().getId(), cornSeed, 0, 0, "");
        client.getHabbo().getInventory().getItemsComponent().addItem(givenCornSeed);
        client.getHabbo().getClient().sendResponse(new AddHabboItemComposer(givenCornSeed));
        client.getHabbo().getClient().sendResponse(new InventoryRefreshComposer());
        client.getHabbo().shout(Emulator.getTexts().getValue("rp.corn_seed_bag_success"));
    }

}
