package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionFirstAidKit extends InteractionDefault {

    public static final String INTERACTION_TYPE ="rp_first_aid_kit";

    public InteractionFirstAidKit(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionFirstAidKit(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

}
