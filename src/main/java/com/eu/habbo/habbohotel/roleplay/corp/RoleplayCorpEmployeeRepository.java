package com.eu.habbo.habbohotel.roleplay.corp;

import com.eu.habbo.Emulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleplayCorpEmployeeRepository {
    public static RoleplayCorpEmployee create(int characterId, int corpId, int corpRoleId) {
        RoleplayCorpEmployee employee = null;
        String query = "INSERT INTO rp_corp_employees (character_id, corp_id, corp_role_id) VALUES (?, ?, ?)";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, characterId);
            statement.setInt(2, corpId);
            statement.setInt(3, corpRoleId);

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee = getById(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return employee;
    }

    public static List<RoleplayCorpEmployee> getAll() {
        List<RoleplayCorpEmployee> employees = new ArrayList<>();
        String query = "SELECT * FROM rp_corp_employees";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                employees.add(new RoleplayCorpEmployee(set));
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return employees;
    }

    public static RoleplayCorpEmployee getById(int employeeId) {
        RoleplayCorpEmployee employee = null;
        String query = "SELECT * FROM rp_corp_employees WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employeeId);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    employee = new RoleplayCorpEmployee(set);
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return employee;
    }

    public static List<RoleplayCorpEmployee> getByCorpId(int corpId) {
        List<RoleplayCorpEmployee> employees = new ArrayList<>();
        String query = "SELECT * FROM rp_corp_employees WHERE corp_id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, corpId);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    employees.add(new RoleplayCorpEmployee(set));
                }
            }
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }

        return employees;
    }

    public static void updateByEmployee(RoleplayCorpEmployee employee) {
        String query = "UPDATE rp_corp_employees SET character_id = ?, corp_id = ?, corp_role_id = ? WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employee.getCharacterId());
            statement.setInt(2, employee.getCorpId());
            statement.setInt(3, employee.getCorpRoleId());
            statement.setInt(4, employee.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }

    public static void deleteById(int employeeId) {
        String query = "DELETE FROM rp_corp_employees WHERE id = ?";

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Caught SQL exception: " + e.getMessage());
        }
    }
}
