package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionDefault;
import com.eu.habbo.habbohotel.items.interactions.base.BaseConsumableInteraction;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionCaughtFish extends BaseConsumableInteraction {

    public static final String INTERACTION_TYPE = "rp_caught_fish";

    public InteractionCaughtFish(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionCaughtFish(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canConsume() {
        Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(this.getUserId());
        boolean lowEnergy = habbo.getRoleplayCharacter().getEnergyNow() < habbo.getRoleplayCharacter().getEnergyMax();
        boolean lowHealth = habbo.getRoleplayCharacter().getHealthNow() < habbo.getRoleplayCharacter().getHealthMax();
        return lowEnergy || lowHealth;
    }

    @Override
    public void onConsume(GameClient client) {
        int healthPoints = Emulator.getConfig().getInt("rp.health_from_fish");
        int energyPoints = Emulator.getConfig().getInt("rp.energy_from_fish");

        client.getHabbo().getRoleplayCharacter().addHealth(healthPoints);
        client.getHabbo().getRoleplayCharacter().addEnergy(energyPoints);

        client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.eats_food")
                .replace(":food", this.getBaseItem().getName())
                .replace(":health", String.valueOf(healthPoints))
                .replace(":energy", String.valueOf(energyPoints))
        );
    }
}
