package com.eu.habbo.habbohotel.roleplay.corp;

import com.eu.habbo.Emulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleplayCorpRepository {
    public static RoleplayCorp create(int characterId, String name) {
        RoleplayCorp corp = null;
        String query = "INSERT INTO rp_corps (character_id, name, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";

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
                    corp = getById(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return corp;
    }

    public static List<RoleplayCorp> getAll() {
        List<RoleplayCorp> corps = new ArrayList<>();
        String query = "SELECT * FROM rp_corps";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                corps.add(new RoleplayCorp(set));
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return corps;
    }

    public static RoleplayCorp getById(int corpId) {
        RoleplayCorp corp = null;
        String query = "SELECT * FROM rp_corps WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, corpId);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    corp = new RoleplayCorp(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return corp;
    }

    public static void updateByCorp(RoleplayCorp corp) {
        String query = "UPDATE rp_corps SET character_id = ?,name = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, corp.getCharacterId());
            statement.setString(2, corp.getName());
            statement.setInt(3, (int) (System.currentTimeMillis() / 1000));
            statement.setInt(4, corp.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

    public static void deleteById(int corpId) {
        String query = "DELETE FROM rp_corps WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, corpId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }
}
