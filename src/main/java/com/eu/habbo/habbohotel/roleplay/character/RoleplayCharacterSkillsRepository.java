package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacterSkillsRepository {

    public static RoleplayCharacterSkills loadByCharacter(RoleplayCharacter character) {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rp_characters_skills WHERE characters_id = ?")) {
            statement.setInt(1, character.getId());

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return new RoleplayCharacterSkills(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
        return null;
    }

}
