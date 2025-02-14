package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionCornSeed extends InteractionDefault {

    public static final String INTERACTION_TYPE = "rp_corn_seed";

    public InteractionCornSeed(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionCornSeed(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }
}
