package com.eu.habbo.habbohotel.roleplay.gang;

import com.eu.habbo.Emulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleplayGangRepository {
    public static RoleplayGang create(int characterId, String name) {
        RoleplayGang gang = null;
        String query = "INSERT INTO rp_gangs (character_id, name, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            int timestamp = (int) (System.currentTimeMillis() / 1000);
            statement.setInt(1, characterId);
            statement.setString(2, name);
            statement.setInt(3, timestamp);
            statement.setInt(4, timestamp);

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    gang = getById(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return gang;
    }

    public static List<RoleplayGang> getAll() {
        List<RoleplayGang> gangs = new ArrayList<>();
        String query = "SELECT * FROM rp_gangs";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                gangs.add(new RoleplayGang(set));
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return gangs;
    }

    public static RoleplayGang getById(int gangId) {
        RoleplayGang gang = null;
        String query = "SELECT * FROM rp_gangs WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, gangId);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    gang = new RoleplayGang(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return gang;
    }

    public static void updateByGang(RoleplayGang gang) {
        String query = "UPDATE rp_gangs SET character_id = ?, rooms_id = ?, name = ?, description = ?, badge_code = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, gang.getUserId());
            statement.setInt(2, gang.getRoomId());
            statement.setString(3, gang.getName());
            statement.setString(4, gang.getDescription());
            statement.setString(5, gang.getBadgeCode());
            statement.setInt(6, (int) (System.currentTimeMillis() / 1000));
            statement.setInt(7, gang.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

    public static void deleteById(int gangId) {
        String query = "DELETE FROM rp_gangs WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, gangId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }
}
