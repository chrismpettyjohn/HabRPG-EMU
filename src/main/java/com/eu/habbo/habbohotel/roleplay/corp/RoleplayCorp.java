package com.eu.habbo.habbohotel.roleplay.corp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleplayCorp {
    private final int id;
    private int characterId;
    private String key;
    private String name;
    private String description;
    private int createdAt;
    private int updatedAt;
    private List<RoleplayCorpRole> roles;
    private List<RoleplayCorpEmployee> employees;

    public RoleplayCorp(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.characterId = set.getInt("character_id");
        this.key = set.getString("key");
        this.name = set.getString("name");
        this.description = set.getString("description");
        this.createdAt = set.getInt("created_at");
        this.updatedAt = set.getInt("updated_at");

        // Load related roles and employees
        this.loadRelations();
    }

    private void loadRelations() {
        this.roles = RoleplayCorpRoleRepository.getByCorpId(this.id);
        this.employees = RoleplayCorpEmployeeRepository.getByCorpId(this.id);
    }

    public int getId() {
        return this.id;
    }

    public int getCharacterId() {
        return this.characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatedAt() {
        return this.createdAt;
    }

    public int getUpdatedAt() {
        return this.updatedAt;
    }

    public List<RoleplayCorpRole> getRoles() {
        return this.roles;
    }

    public List<RoleplayCorpEmployee> getEmployees() {
        return this.employees;
    }

    public void save() {
        RoleplayCorpRepository.updateByCorp(this);

        for (RoleplayCorpRole role : this.roles) {
            role.save();
        }
        for (RoleplayCorpEmployee employee : this.employees) {
            employee.save();
        }
    }

    public void delete() {
        for (RoleplayCorpRole role : this.roles) {
            role.delete();
        }

        for (RoleplayCorpEmployee employee : this.employees) {
            employee.delete();
        }

        RoleplayCorpRepository.deleteById(this.id);
    }
}
