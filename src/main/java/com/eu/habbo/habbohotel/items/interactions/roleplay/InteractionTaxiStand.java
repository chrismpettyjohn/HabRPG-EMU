package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.base.BaseConsumableInteraction;
import com.eu.habbo.habbohotel.rooms.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionTaxiStand extends BaseConsumableInteraction {

    public static final String INTERACTION_TYPE = "rp_taxi_stand";

    public InteractionTaxiStand(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionTaxiStand(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        boolean isWithinOneTile = Math.abs(this.getX() - client.getHabbo().getRoomUnit().getX()) <= 1 && Math.abs(this.getY() - client.getHabbo().getRoomUnit().getY()) <= 1;

        if (!isWithinOneTile) {
            client.getHabbo().whisper(Emulator.getTexts().getValue("rp.too_far_away"));
            return;
        }

        client.getHabbo().shout("taxi!");
    }

    @Override
    public String getExtradata() {
        return "2";
    }
}
