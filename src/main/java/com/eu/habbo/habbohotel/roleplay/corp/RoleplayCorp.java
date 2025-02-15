package com.eu.habbo.habbohotel.roleplay.corp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCorp {
    private final int id;
    private int characterId;
    private String key;
    private String name;
    private String description;
    private int createdAt;
    private int updatedAt;

    public RoleplayCorp(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.characterId = set.getInt("character_id");
        this.key = set.getString("key");
        this.name = set.getString("name");
        this.description = set.getString("description");
        this.createdAt = set.getInt("created_at");
        this.updatedAt = set.getInt("updated_at");
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

    public void save() {
        RoleplayCorpRepository.updateByCorp(this);
    }

    public void delete() {
        RoleplayCorpRepository.deleteById(this.id);
    }
}
