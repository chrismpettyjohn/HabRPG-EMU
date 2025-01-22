package com.eu.habbo.habbohotel.roleplay.item;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayItem {

    private final int id;
    private String uniqueName;
    private String displayName;
    private String type;
    private String effect;
    private int accuracy;
    private int ammoSize;
    private int ammoCapacity;
    private String attackMessage;
    private int cooldownSeconds;
    private int equipHandItem;
    private int equipEffect;
    private String equipMessage;
    private int maxDamage;
    private int minDamage;
    private int rangeInTiles;
    private String reloadMessage;
    private int reloadTime;
    private String unequipMessage;
    private int weight;
    private int value;

    public RoleplayItem(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.uniqueName = set.getString("unique_name");
        this.displayName = set.getString("display_name");
        this.type = set.getString("type");
        this.effect = set.getString("effect");
        this.accuracy = set.getInt("accuracy");
        this.ammoSize = set.getInt("ammo_size");
        this.ammoCapacity = set.getInt("ammo_capacity");
        this.attackMessage = set.getString("attack_message");
        this.cooldownSeconds = set.getInt("cooldown_seconds");
        this.equipHandItem = set.getInt("equip_handitem");
        this.equipEffect = set.getInt("equip_effect");
        this.equipMessage = set.getString("equip_message");
        this.maxDamage = set.getInt("max_damage");
        this.minDamage = set.getInt("min_damage");
        this.rangeInTiles = set.getInt("range_in_tiles");
        this.reloadMessage = set.getString("reload_message");
        this.reloadTime = set.getInt("reload_time");
        this.unequipMessage = set.getString("unequip_message");
        this.weight = set.getInt("weight");
        this.value = set.getInt("value");
    }

    public int getId() {
        return this.id;
    }

    public String getUniqueName() {
        return this.uniqueName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getType() {
        return this.type;
    }

    public String getEffect() {
        return this.effect;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public int getAmmoSize() {
        return this.ammoSize;
    }

    public int getAmmoCapacity() {
        return this.ammoCapacity;
    }

    public String getAttackMessage() {
        return this.attackMessage;
    }

    public int getCooldownSeconds() {
        return this.cooldownSeconds;
    }

    public int getEquipHandItem() {
        return this.equipHandItem;
    }

    public int getEquipEffect() {
        return this.equipEffect;
    }

    public String getEquipMessage() {
        return this.equipMessage;
    }

    public int getMaxDamage() {
        return this.maxDamage;
    }

    public int getMinDamage() {
        return this.minDamage;
    }

    public int getRangeInTiles() {
        return this.rangeInTiles;
    }

    public String getReloadMessage() {
        return this.reloadMessage;
    }

    public int getReloadTime() {
        return this.reloadTime;
    }

    public String getUnequipMessage() {
        return this.unequipMessage;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getValue() {
        return this.value;
    }
}
