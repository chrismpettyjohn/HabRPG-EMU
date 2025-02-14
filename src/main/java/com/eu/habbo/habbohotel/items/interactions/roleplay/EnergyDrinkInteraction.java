package com.eu.habbo.habbohotel.items.interactions.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnergyDrinkInteraction extends BaseConsumableInteraction {

    public static final String INTERACTION_TYPE = "rp_energy_drink";

    public EnergyDrinkInteraction(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public EnergyDrinkInteraction(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canConsume() {
        Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(this.getUserId());
        return habbo.getRoleplayCharacter().getEnergyNow() < habbo.getRoleplayCharacter().getEnergyMax();
    }

    @Override
    public void onConsume(GameClient client) {
        int energyGained = Emulator.getConfig().getInt("rp.energy_drink.health_points", 0);
        client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.energy_drink_used")
                .replace(":points", String.valueOf(energyGained))
        );
        client.getHabbo().getRoleplayCharacter().addEnergy(energyGained);
    }

}
