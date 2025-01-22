package com.eu.habbo.habbohotel.roleplay.item;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleplayItemRepository {

    public static List<RoleplayItem> loadAll() {
        List<RoleplayItem> items = new ArrayList<>();
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_items");
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                items.add(new RoleplayItem(set));
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return items;
    }

    public static RoleplayItem createOne(String uniqueName,
                                              String displayName, String type, String effect,
                                              int accuracy, int ammoSize, int ammoCapacity, String attackMessage,
                                              int cooldownSeconds, int equipHandItem, int equipEffect, String equipMessage,
                                              int maxDamage, int minDamage, int rangeInTiles, String reloadMessage,
                                              int reloadTime, String unequipMessage, int weight, int value) {
        String query = "INSERT INTO rp_items (unique_name, display_name, type, effect, accuracy, ammo_size, " +
                "ammo_capacity, attack_message, cooldown_seconds, equip_hand_item, equip_effect, equip_message, " +
                "max_damage, min_damage, range_in_tiles, reload_message, reload_time, unequip_message, weight, value) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, uniqueName);
            statement.setString(2, displayName);
            statement.setString(3, type);
            statement.setString(4, effect);
            statement.setInt(5, accuracy);
            statement.setInt(6, ammoSize);
            statement.setInt(7, ammoCapacity);
            statement.setString(8, attackMessage);
            statement.setInt(9, cooldownSeconds);
            statement.setInt(10, equipHandItem);
            statement.setInt(11, equipEffect);
            statement.setString(12, equipMessage);
            statement.setInt(13, maxDamage);
            statement.setInt(14, minDamage);
            statement.setInt(15, rangeInTiles);
            statement.setString(16, reloadMessage);
            statement.setInt(17, reloadTime);
            statement.setString(18, unequipMessage);
            statement.setInt(19, weight);
            statement.setInt(20, value);

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);

                    try (PreparedStatement selectStatement = connection.prepareStatement(
                            "SELECT * FROM rp_items WHERE id = ?")) {
                        selectStatement.setInt(1, generatedId);
                        try (ResultSet resultSet = selectStatement.executeQuery()) {
                            if (resultSet.next()) {
                                return new RoleplayItem(resultSet);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return null;
    }




    public static void updateAll(List<RoleplayItem> items) {
        String query = """
        UPDATE rp_items SET 
            uniqueName = ?, 
            displayName = ?, 
            type = ?, 
            effect = ?, 
            accuracy = ?, 
            ammoSize = ?, 
            ammoCapacity = ?, 
            attackMessage = ?, 
            cooldownSeconds = ?, 
            equipHandItem = ?, 
            equipEffect = ?, 
            equipMessage = ?, 
            maxDamage = ?, 
            minDamage = ?, 
            rangeInTiles = ?, 
            reloadMessage = ?, 
            reloadTime = ?, 
            unequipMessage = ?, 
            weight = ?, 
            value = ? 
        WHERE id = ?
    """;

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (RoleplayItem item : items) {
                statement.setString(1, item.getUniqueName());
                statement.setString(2, item.getDisplayName());
                statement.setString(3, item.getType());
                statement.setString(4, item.getEffect());
                statement.setInt(5, item.getAccuracy());
                statement.setInt(6, item.getAmmoSize());
                statement.setInt(7, item.getAmmoCapacity());
                statement.setString(8, item.getAttackMessage());
                statement.setInt(9, item.getCooldownSeconds());
                statement.setInt(10, item.getEquipHandItem());
                statement.setInt(11, item.getEquipEffect());
                statement.setString(12, item.getEquipMessage());
                statement.setInt(13, item.getMaxDamage());
                statement.setInt(14, item.getMinDamage());
                statement.setInt(15, item.getRangeInTiles());
                statement.setString(16, item.getReloadMessage());
                statement.setInt(17, item.getReloadTime());
                statement.setString(18, item.getUnequipMessage());
                statement.setInt(19, item.getWeight());
                statement.setInt(20, item.getValue());
                statement.setInt(21, item.getId());
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

    public static void updateOne(RoleplayItem item) {
        String query = """
        UPDATE rp_items SET 
            uniqueName = ?, 
            displayName = ?, 
            type = ?, 
            effect = ?, 
            accuracy = ?, 
            ammoSize = ?, 
            ammoCapacity = ?, 
            attackMessage = ?, 
            cooldownSeconds = ?, 
            equipHandItem = ?, 
            equipEffect = ?, 
            equipMessage = ?, 
            maxDamage = ?, 
            minDamage = ?, 
            rangeInTiles = ?, 
            reloadMessage = ?, 
            reloadTime = ?, 
            unequipMessage = ?, 
            weight = ?, 
            value = ? 
        WHERE id = ?
    """;

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, item.getUniqueName());
            statement.setString(2, item.getDisplayName());
            statement.setString(3, item.getType());
            statement.setString(4, item.getEffect());
            statement.setInt(5, item.getAccuracy());
            statement.setInt(6, item.getAmmoSize());
            statement.setInt(7, item.getAmmoCapacity());
            statement.setString(8, item.getAttackMessage());
            statement.setInt(9, item.getCooldownSeconds());
            statement.setInt(10, item.getEquipHandItem());
            statement.setInt(11, item.getEquipEffect());
            statement.setString(12, item.getEquipMessage());
            statement.setInt(13, item.getMaxDamage());
            statement.setInt(14, item.getMinDamage());
            statement.setInt(15, item.getRangeInTiles());
            statement.setString(16, item.getReloadMessage());
            statement.setInt(17, item.getReloadTime());
            statement.setString(18, item.getUnequipMessage());
            statement.setInt(19, item.getWeight());
            statement.setInt(20, item.getValue());
            statement.setInt(21, item.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

    public static void deleteOne(RoleplayItem item) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM rp_items WHERE id = ?")) {
            statement.setInt(1, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }


}
