package com.eu.habbo.habbohotel.roleplay.character;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacter {

    private int userId;
    private int healthNow;
    private int healthMax;
    private int energyNow;
    private int energyMax;

    public RoleplayCharacter(ResultSet set) throws SQLException {
        this.userId = set.getInt("users_id");
        this.healthNow = set.getInt("health_now");
        this.healthMax = set.getInt("health_max");
        this.energyNow = set.getInt("energy_now");
        this.energyMax = set.getInt("energy_max");
    }

    public boolean isDead() {
        return this.healthNow <= 0;
    }

    public boolean canInteract() {
        return !this.isDead();
    }

    public boolean canMove() {
        return !this.isDead();
    }

    public int getUserId() {
        return this.userId;
    }

    public int getHealthNow() {
        return this.healthNow;
    }

    public int getHealthMax() {
        return this.healthMax;
    }

    public void depleteHealth(int damage) {
        this.healthNow -= damage;
    }

    public int getEnergyNow() {
        return this.energyNow;
    }

    public int getEnergyMax() {
        return this.energyMax;
    }

    public void depleteEnergy(int points) {
        this.energyNow -= points;
    }
}
