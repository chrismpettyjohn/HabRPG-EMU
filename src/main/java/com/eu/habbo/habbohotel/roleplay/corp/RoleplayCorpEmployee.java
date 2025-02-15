package com.eu.habbo.habbohotel.roleplay.corp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCorpEmployee {
    private final int id;
    private int characterId;
    private int corpId;
    private int corpRoleId;

    public RoleplayCorpEmployee(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.characterId = set.getInt("character_id");
        this.corpId = set.getInt("corp_id");
        this.corpRoleId = set.getInt("corp_role_id");
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

    public int getCorpId() {
        return this.corpId;
    }

    public void setCorpId(int corpId) {
        this.corpId = corpId;
    }

    public int getCorpRoleId() {
        return this.corpRoleId;
    }

    public void setCorpRoleId(int corpRoleId) {
        this.corpRoleId = corpRoleId;
    }

    public void save() {
        RoleplayCorpEmployeeRepository.updateByEmployee(this);
    }

    public void delete() {
        RoleplayCorpEmployeeRepository.deleteById(this.id);
    }
}
