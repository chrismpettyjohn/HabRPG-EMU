package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.pets.Pet;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacterRepository {

    public static RoleplayCharacter loadByBot(Bot bot) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters WHERE type = 'bot' AND bots_id = ?")) {
            statement.setInt(1, bot.getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return new RoleplayCharacter(set, bot, null, null);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return null;
    }

    public static RoleplayCharacter loadByHabbo(Habbo habbo) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters WHERE type = 'user' AND users_id = ?")) {
            statement.setInt(1, habbo.getHabboInfo().getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return new RoleplayCharacter(set, null, habbo, null);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return null;
    }

    public static RoleplayCharacter loadByPet(Pet pet) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters WHERE type = 'pet' AND pets_id = ?")) {
            statement.setInt(1, pet.getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return new RoleplayCharacter(set, null, null, pet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return null;
    }
}
