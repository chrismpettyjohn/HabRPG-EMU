package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleplayCharacterKillsRepository {

    public static List<RoleplayCharacterKill> getKillsByCharacter(RoleplayCharacter character) {
        List<RoleplayCharacterKill> kills = new ArrayList<>();
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM rp_characters_kills WHERE attacker_character_id = ?")) {

            statement.setInt(1, character.getId());

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    kills.add(new RoleplayCharacterKill(set));
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return kills;
    }

    public static void create(RoleplayCharacter attackerCharacter, RoleplayCharacter targetCharacter) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO rp_characters_kills (attacker_character_id, victim_character_id) VALUES (?, ?)")) {

            statement.setInt(1, attackerCharacter.getId());
            statement.setInt(2, targetCharacter.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

}
