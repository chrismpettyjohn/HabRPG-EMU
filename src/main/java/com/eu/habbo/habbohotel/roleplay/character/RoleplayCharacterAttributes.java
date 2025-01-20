package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacterAttributes {

    private int userId;
    private int strength;
    private int intelligence;
    private int dexterity;
    private int charisma;
    private int perception;
    private int endurance;
    private int luck;

    private RoleplayCharacterAttributes(ResultSet set) throws SQLException {
        this.userId = set.getInt("users_id");
        this.strength = set.getInt("strength");
        this.intelligence = set.getInt("intelligence");
        this.dexterity = set.getInt("dexterity");
        this.charisma = set.getInt("charisma");
        this.perception = set.getInt("perception");
        this.endurance = set.getInt("endurance");
        this.luck = set.getInt("luck");
    }

    public static RoleplayCharacterAttributes loadByHabbo(Habbo habbo) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters_attributes WHERE users_id = ?")) {
            statement.setInt(1, habbo.getHabboInfo().getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return new RoleplayCharacterAttributes(set);
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

    public int getStrength() {
        return this.strength;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public int getCharisma() {
        return this.charisma;
    }

    public int getPerception() {
        return this.perception;
    }

    public int getEndurance() {
        return this.endurance;
    }

    public int getLuck() {
        return this.luck;
    }
}
