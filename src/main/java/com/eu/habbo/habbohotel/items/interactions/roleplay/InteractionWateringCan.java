package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserHandItemComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionWateringCan extends InteractionDefault {

    public static final String INTERACTION_TYPE = "rp_watering_can";

    public static final int WATERING_CAN_HAND_ITEM_ID = 1081;

    public InteractionWateringCan(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionWateringCan(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        boolean isWithinOneTile = Math.abs(this.getX() - client.getHabbo().getRoomUnit().getX()) <= 1 && Math.abs(this.getY() - client.getHabbo().getRoomUnit().getY()) <= 1;

        if (!isWithinOneTile) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.too_far_away"));
            return;
        }

        if (client.getHabbo().getRoomUnit().getHandItem() == InteractionWateringCan.WATERING_CAN_HAND_ITEM_ID) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.watering_can_inventory_full"));
            return;
        }


        client.getHabbo().getRoomUnit().setHandItem(InteractionWateringCan.WATERING_CAN_HAND_ITEM_ID);
        room.sendComposer(new RoomUserHandItemComposer(client.getHabbo().getRoomUnit()).compose());
        client.getHabbo().shout(Emulator.getTexts().getValue("rp.watering_can_success"));
    }
}
