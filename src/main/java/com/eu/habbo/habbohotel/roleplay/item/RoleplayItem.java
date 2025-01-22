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

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEffect() {
        return this.effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getAmmoSize() {
        return this.ammoSize;
    }

    public void setAmmoSize(int ammoSize) {
        this.ammoSize = ammoSize;
    }

    public int getAmmoCapacity() {
        return this.ammoCapacity;
    }

    public void setAmmoCapacity(int ammoCapacity) {
        this.ammoCapacity = ammoCapacity;
    }

    public String getAttackMessage() {
        return this.attackMessage;
    }

    public void setAttackMessage(String attackMessage) {
        this.attackMessage = attackMessage;
    }

    public int getCooldownSeconds() {
        return this.cooldownSeconds;
    }

    public void setCooldownSeconds(int cooldownSeconds) {
        this.cooldownSeconds = cooldownSeconds;
    }

    public int getEquipHandItem() {
        return this.equipHandItem;
    }

    public void setEquipHandItem(int equipHandItem) {
        this.equipHandItem = equipHandItem;
    }

    public int getEquipEffect() {
        return this.equipEffect;
    }

    public void setEquipEffect(int equipEffect) {
        this.equipEffect = equipEffect;
    }

    public String getEquipMessage() {
        return this.equipMessage;
    }

    public void setEquipMessage(String equipMessage) {
        this.equipMessage = equipMessage;
    }

    public int getMaxDamage() {
        return this.maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getMinDamage() {
        return this.minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getRangeInTiles() {
        return this.rangeInTiles;
    }

    public void setRangeInTiles(int rangeInTiles) {
        this.rangeInTiles = rangeInTiles;
    }

    public String getReloadMessage() {
        return this.reloadMessage;
    }

    public void setReloadMessage(String reloadMessage) {
        this.reloadMessage = reloadMessage;
    }

    public int getReloadTime() {
        return this.reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public String getUnequipMessage() {
        return this.unequipMessage;
    }

    public void setUnequipMessage(String unequipMessage) {
        this.unequipMessage = unequipMessage;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
