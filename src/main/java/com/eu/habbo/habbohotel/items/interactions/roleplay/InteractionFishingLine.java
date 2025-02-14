package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionFishingLine extends InteractionDefault {

    public static final String INTERACTION_TYPE = "rp_fishing_line";

    public InteractionFishingLine(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionFishingLine(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

}
