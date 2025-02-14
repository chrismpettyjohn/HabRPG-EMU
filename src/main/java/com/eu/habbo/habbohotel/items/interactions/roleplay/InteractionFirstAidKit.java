package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionFirstAidKit extends BaseConsumableInteraction {

    public static final String INTERACTION_TYPE = "rp_first_aid_kit";

    public InteractionFirstAidKit(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionFirstAidKit(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canConsume() {
        Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(this.getUserId());
        return habbo.getRoleplayCharacter().getHealthNow() < habbo.getRoleplayCharacter().getHealthMax();
    }

    @Override
    public void onConsume(GameClient client)  {
        int healthGained = Emulator.getConfig().getInt("rp.first_aid_kit.health_points", 0);
        client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.first_aid_kit_used")
                .replace(":points", String.valueOf(healthGained))
        );
        client.getHabbo().getRoleplayCharacter().addHealth(healthGained);
    }

}
