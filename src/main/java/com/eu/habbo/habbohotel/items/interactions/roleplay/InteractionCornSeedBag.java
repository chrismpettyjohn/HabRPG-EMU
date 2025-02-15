package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserHandItemComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionCornSeedBag extends InteractionDefault {

    public static final String INTERACTION_TYPE = "rp_corn_seed_bag";

    public static final int CORN_SEED_HAND_ITEM_ID = 69;

    public InteractionCornSeedBag(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionCornSeedBag(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {

        if (client.getHabbo().getRoomUnit().getHandItem() == InteractionCornSeedBag.CORN_SEED_HAND_ITEM_ID) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corn_seed_bag_inventory_full"));
            return;
        }


        client.getHabbo().getRoomUnit().setHandItem(InteractionCornSeedBag.CORN_SEED_HAND_ITEM_ID);
        room.sendComposer(new RoomUserHandItemComposer(client.getHabbo().getRoomUnit()).compose());
        client.getHabbo().shout(Emulator.getTexts().getValue("rp.corn_seed_bag_success"));
    }

}
