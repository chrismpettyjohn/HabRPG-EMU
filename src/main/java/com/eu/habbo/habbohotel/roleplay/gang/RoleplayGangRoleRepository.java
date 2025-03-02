package com.eu.habbo.habbohotel.roleplay.gang;

import com.eu.habbo.Emulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleplayGangRoleRepository {
    public static RoleplayGangRole create(int gangId, int orderId, String name, String description, boolean canHire, boolean canFire, boolean canPromote, boolean canDemote, boolean canEdit) {
        RoleplayGangRole role = null;
        String query = "INSERT INTO rp_gang_roles (gang_id, prder_id, name, description, can_hire, can_fire, can_promote, can_demote, can_edit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, gangId);
            statement.setInt(2, orderId);
            statement.setString(3, name);
            statement.setString(4, description);
            statement.setString(5, canHire ? "yes" : "no");
            statement.setString(6, canFire ? "yes" : "no");
            statement.setString(7, canPromote ? "yes" : "no");
            statement.setString(8, canDemote ? "yes" : "no");
            statement.setString(9, canEdit ? "yes" : "no");

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role = getById(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return role;
    }

    public static List<RoleplayGangRole> getByGangId(int gangId) {
        List<RoleplayGangRole> roles = new ArrayList<>();
        String query = "SELECT * FROM rp_gang_roles WHERE gang_id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, gangId);

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    roles.add(new RoleplayGangRole(set));
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return roles;
    }

    public static List<RoleplayGangRole> getAll() {
        List<RoleplayGangRole> roles = new ArrayList<>();
        String query = "SELECT * FROM rp_gang_roles";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                roles.add(new RoleplayGangRole(set));
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return roles;
    }

    public static RoleplayGangRole getById(int roleId) {
        RoleplayGangRole role = null;
        String query = "SELECT * FROM rp_gang_roles WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roleId);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    role = new RoleplayGangRole(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return role;
    }

    public static void updateByRole(RoleplayGangRole role) {
        String query = "UPDATE rp_gang_roles SET gang_id = ?, order_id = ?, name = ?, description = ?, can_hire = ?, can_fire = ?, can_promote = ?, can_demote = ?, can_edit = ? WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, role.getGangId());
            statement.setInt(2, role.getOrderId());
            statement.setString(3, role.getName());
            statement.setString(4, role.canInvite() ? "yes" : "no");
            statement.setString(5, role.canKick() ? "yes" : "no");
            statement.setString(6, role.canPromote() ? "yes" : "no");
            statement.setString(7, role.canDemote() ? "yes" : "no");
            statement.setString(8, role.canEdit() ? "yes" : "no");
            statement.setInt(9, role.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

    public static void deleteById(int roleId) {
        String query = "DELETE FROM rp_gang_roles WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }
}
