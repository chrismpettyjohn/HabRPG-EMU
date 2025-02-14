package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnergyJuiceInteraction extends BaseConsumableInteraction {

    public static final String INTERACTION_TYPE = "rp_energy_drink";

    public EnergyJuiceInteraction(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public EnergyJuiceInteraction(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onConsume(GameClient client) throws Exception {
        client.getHabbo().shout("i will consume");
    }

}
