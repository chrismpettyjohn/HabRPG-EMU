package com.eu.habbo.habbohotel.roleplay.item;

import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
