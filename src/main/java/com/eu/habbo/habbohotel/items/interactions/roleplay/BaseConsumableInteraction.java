package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseConsumableInteraction extends InteractionDefault {

    public BaseConsumableInteraction(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public BaseConsumableInteraction(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    public boolean canConsume() {
        return false;
    }

    public void onConsume(GameClient client) {
    }
}
