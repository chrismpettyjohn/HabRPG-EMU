package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleplayCharacterItemRepository {

    public static List<RoleplayCharacterItem> loadAllByCharacter(RoleplayCharacter character) {
        List<RoleplayCharacterItem> items = new ArrayList<>();
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters_items WHERE characters_id = ?")) {

            statement.setInt(1, character.getId());

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    items.add(new RoleplayCharacterItem(set));
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return items;
    }

    public static void updateAll(List<RoleplayCharacterItem> items) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE rp_items SET characters_id = ?, items_id = ? WHERE id = ?")) {

            for (RoleplayCharacterItem item : items) {
                statement.setInt(1, item.getCharacterId());
                statement.setInt(2, item.getItemId());
                statement.setInt(3, item.getId());
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

}
