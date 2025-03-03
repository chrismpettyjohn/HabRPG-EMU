package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.pets.Pet;
import com.eu.habbo.habbohotel.users.Habbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleplayCharacterRepository {

    public static List<RoleplayCharacter> getAll() {
        List<RoleplayCharacter> characters = new ArrayList<>();
        String query = "SELECT * FROM rp_characters";
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                characters.add(new RoleplayCharacter(set));
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return characters;
    }

    public static RoleplayCharacter loadByBot(Bot bot) {
        RoleplayCharacter character = null;
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters WHERE type = 'bot' AND bots_id = ?")) {
            statement.setInt(1, bot.getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    character = new RoleplayCharacter(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        if (character == null) {
            createDefaultCharacter("bot", bot.getId());
            character = loadByBot(bot); // Reload after creation
        }
        return character;
    }

    public static RoleplayCharacter loadByHabbo(Habbo habbo) {
        RoleplayCharacter character = null;
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters WHERE type = 'user' AND users_id = ?")) {
            statement.setInt(1, habbo.getHabboInfo().getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    character = new RoleplayCharacter(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        if (character == null) {
            createDefaultCharacter("user", habbo.getHabboInfo().getId());
            character = loadByHabbo(habbo); // Reload after creation
        }
        return character;
    }

    public static RoleplayCharacter loadByPet(Pet pet) {
        RoleplayCharacter character = null;
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters WHERE type = 'pet' AND pets_id = ?")) {
            statement.setInt(1, pet.getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    character = new RoleplayCharacter(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        if (character == null) {
            createDefaultCharacter("pet", pet.getId());
            character = loadByPet(pet); // Reload after creation
        }
        return character;
    }

    private static void createDefaultCharacter(String type, int id) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO rp_characters (type, bots_id, users_id, pets_id) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, type);
            statement.setInt(2, "bot".equals(type) ? id : 0);
            statement.setInt(3, "user".equals(type) ? id : 0);
            statement.setInt(4, "pet".equals(type) ? id : 0);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

    public static void updateByCharacter(RoleplayCharacter character) {
        String query = """
        UPDATE rp_characters SET 
            type = ?, 
            bots_id = ?, 
            users_id = ?, 
            pets_id = ?, 
            health_now = ?, 
            health_max = ?, 
            energy_now = ?, 
            energy_max = ?
        WHERE id = ?
    """;

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, character.getType());
            statement.setInt(2, character.getBotId());
            statement.setInt(3, character.getUserId());
            statement.setInt(4, character.getPetId());
            statement.setInt(5, character.getHealthNow());
            statement.setInt(6, character.getHealthMax());
            statement.setInt(7, character.getEnergyNow());
            statement.setInt(8, character.getEnergyMax());
            statement.setInt(9, character.getId());
            statement.addBatch();

            statement.executeBatch();

        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

    public static void deleteById(int id) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM rp_characters WHERE id = ? LIMIT 1")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

}
