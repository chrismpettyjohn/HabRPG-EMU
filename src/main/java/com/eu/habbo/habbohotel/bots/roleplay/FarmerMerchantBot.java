package com.eu.habbo.habbohotel.bots.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FarmerMerchantBot extends Bot {
    public FarmerMerchantBot(ResultSet set) throws SQLException {
        super(set);
    }

    public FarmerMerchantBot(Bot bot) {
        super(bot);
    }

    @Override
    public void onUserSay(final RoomChatMessage message) {
        if (!message.getMessage().equalsIgnoreCase(Emulator.getTexts().getValue("generic.yes"))) {
            return;
        }
    }

}
