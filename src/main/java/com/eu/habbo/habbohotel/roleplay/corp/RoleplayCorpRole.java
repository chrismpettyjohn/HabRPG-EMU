package com.eu.habbo.habbohotel.roleplay.corp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCorpRole {
    private final int id;
    private int corpId;
    private String name;
    private String description;
    private boolean canHire;
    private boolean canFire;
    private boolean canPromote;
    private boolean canDemote;
    private boolean canEdit;

    public RoleplayCorpRole(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.corpId = set.getInt("corp_id");
        this.name = set.getString("name");
        this.description = set.getString("description");
        this.canHire = set.getString("can_hire").equalsIgnoreCase("yes");
        this.canFire = set.getString("can_fire").equalsIgnoreCase("yes");
        this.canPromote = set.getString("can_promote").equalsIgnoreCase("yes");
        this.canDemote = set.getString("can_demote").equalsIgnoreCase("yes");
        this.canEdit = set.getString("can_edit").equalsIgnoreCase("yes");
    }

    public int getId() {
        return this.id;
    }

    public int getCorpId() {
        return this.corpId;
    }

    public void setCorpId(int corpId) {
        this.corpId = corpId;
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

    public boolean canHire() {
        return this.canHire;
    }

    public void setCanHire(boolean canHire) {
        this.canHire = canHire;
    }

    public boolean canFire() {
        return this.canFire;
    }

    public void setCanFire(boolean canFire) {
        this.canFire = canFire;
    }

    public boolean canPromote() {
        return this.canPromote;
    }

    public void setCanPromote(boolean canPromote) {
        this.canPromote = canPromote;
    }

    public boolean canDemote() {
        return this.canDemote;
    }

    public void setCanDemote(boolean canDemote) {
        this.canDemote = canDemote;
    }

    public boolean canEdit() {
        return this.canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public void save() {
        RoleplayCorpRoleRepository.updateByRole(this);
    }

    public void delete() {
        RoleplayCorpRoleRepository.deleteById(this.id);
    }
}
