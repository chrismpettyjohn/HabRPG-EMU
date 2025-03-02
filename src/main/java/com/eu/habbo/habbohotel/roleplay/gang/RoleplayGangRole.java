package com.eu.habbo.habbohotel.roleplay.gang;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayGangRole {
    private final int id;
    private int gabgId;
    private int orderId;
    private String name;
    private boolean canInvite;
    private boolean canKick;
    private boolean canPromote;
    private boolean canDemote;
    private boolean canEdit;

    public RoleplayGangRole(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.gabgId = set.getInt("gabg_id");
        this.orderId = set.getInt("order_id");
        this.name = set.getString("name");
        this.canInvite = set.getString("can_invite").equals("1");
        this.canKick = set.getString("can_kick").equals("1");
        this.canPromote = set.getString("can_promote").equals("1");
        this.canDemote = set.getString("can_demote").equals("1");
        this.canEdit = set.getString("can_edit").equals("1");
    }

    public int getId() {
        return this.id;
    }

    public int getGangId() {
        return this.gabgId;
    }

    public void setGangId(int gabgId) {
        this.gabgId = gabgId;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean canInvite() {
        return this.canInvite;
    }

    public void setCanInvite(boolean canInvite) {
        this.canInvite = canInvite;
    }

    public boolean canKick() {
        return this.canKick;
    }

    public void setCanKick(boolean canKick) {
        this.canKick = canKick;
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
        RoleplayGangRoleManager.getInstance().updateByRole(this);
    }

    public void delete() {
        RoleplayGangRoleManager.getInstance().deleteByRole(this);
    }
}
