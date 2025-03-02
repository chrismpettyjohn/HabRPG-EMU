package com.eu.habbo.habbohotel.roleplay.gang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RoleplayGang {
    private final int id;
    private int userId;
    private String name;
    private int createdAt;
    private int updatedAt;

    public RoleplayGang(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.userId = set.getInt("users_id");
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

    public List<RoleplayGangRole> getRoles() {
        return RoleplayGangRoleManager.getInstance().getGangRoles().stream().filter(roleplayGangRole -> roleplayGangRole.getGangId() == this.id).collect(Collectors.toList());
    }

    public void save() {
        RoleplayGangRepository.updateByGang(this);

        for (RoleplayGangRole role : this.getRoles()) {
            role.save();
        }
    }

    public void delete() {
        for (RoleplayGangRole role :  this.getRoles()) {
            role.delete();
        }
        RoleplayGangRepository.deleteById(this.id);
    }
}
