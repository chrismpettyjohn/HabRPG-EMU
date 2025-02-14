package com.eu.habbo.habbohotel.bots.roleplay;

import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FishMerchantBot extends Bot {

    public FishMerchantBot(ResultSet set) throws SQLException {
        super(set);
    }

    public FishMerchantBot(Bot bot) {
        super(bot);
    }


    @Override
    public void onUserSay(final RoomChatMessage message) {
        this.talk("ok then");
    }
}
