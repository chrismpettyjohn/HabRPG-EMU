package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacter {

    private int userId;
    private int healthNow;
    private int healthMax;
    private int energyNow;
    private int energyMax;

    private RoleplayCharacter(ResultSet set) throws SQLException {
        this.userId = set.getInt("users_id");
        this.healthNow = set.getInt("health_now");
        this.healthMax = set.getInt("health_max");
        this.energyNow = set.getInt("energy_now");
        this.energyMax = set.getInt("energy_max");
    }

    public static RoleplayCharacter loadByHabbo(Habbo habbo) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters WHERE users_id = ?")) {
            statement.setInt(1, habbo.getHabboInfo().getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return new RoleplayCharacter(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return null;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getHealthNow() {
        return this.healthNow;
    }

    public int getHealthMax() {
        return this.healthMax;
    }

    public int getEnergyNow() {
        return this.energyNow;
    }

    public int getEnergyMax() {
        return this.energyMax;
    }
}
