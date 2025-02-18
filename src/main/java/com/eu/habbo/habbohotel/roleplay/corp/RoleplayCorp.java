package com.eu.habbo.habbohotel.roleplay.corp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RoleplayCorp {
    private final int id;
    private int userId;
    private String name;
    private int createdAt;
    private int updatedAt;

    public RoleplayCorp(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.userId = set.getInt("user_id");
        this.name = set.getString("name");
        this.createdAt = set.getInt("created_at");
        this.updatedAt = set.getInt("updated_at");
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int characterId) {
        this.userId = characterId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreatedAt() {
        return this.createdAt;
    }

    public int getUpdatedAt() {
        return this.updatedAt;
    }

    public List<RoleplayCorpRole> getRoles() {
        return RoleplayCorpRoleManager.getInstance().getCorpRoles().stream().filter(roleplayCorpRole -> roleplayCorpRole.getCorpId() == this.id).collect(Collectors.toList());
    }

    public void save() {
        RoleplayCorpRepository.updateByCorp(this);

        for (RoleplayCorpRole role : this.getRoles()) {
            role.save();
        }
    }

    public void delete() {
        for (RoleplayCorpRole role :  this.getRoles()) {
            role.delete();
        }
        RoleplayCorpRepository.deleteById(this.id);
    }
}
