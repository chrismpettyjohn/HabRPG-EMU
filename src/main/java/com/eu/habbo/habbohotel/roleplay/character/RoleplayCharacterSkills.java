package com.eu.habbo.habbohotel.roleplay.character;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacterSkills {

    private int characterId;
    private int strengthLevel;
    private int strengthExperience;
    private int staminaLevel;
    private int staminaExperience;
    private int agilityLevel;
    private int agilityExperience;
    private int resilienceLevel;
    private int resilienceExperience;
    private int meleeLevel;
    private int meleeExperience;
    private int rangedLevel;
    private int rangedExperience;
    private int defenseLevel;
    private int defenseExperience;

    public RoleplayCharacterSkills(ResultSet set) throws SQLException {
        this.characterId = set.getInt("characters_id");
        this.strengthLevel = set.getInt("strength_level");
        this.strengthExperience = set.getInt("strength_experience");
        this.staminaLevel = set.getInt("stamina_level");
        this.staminaExperience = set.getInt("stamina_experience");
        this.agilityLevel = set.getInt("agility_level");
        this.agilityExperience = set.getInt("agility_experience");
        this.resilienceLevel = set.getInt("resilience_level");
        this.resilienceExperience = set.getInt("resilience_experience");
        this.meleeLevel = set.getInt("melee_level");
        this.meleeExperience = set.getInt("melee_experience");
        this.rangedLevel = set.getInt("ranged_level");
        this.rangedExperience = set.getInt("ranged_experience");
        this.defenseLevel = set.getInt("defense_level");
        this.defenseExperience = set.getInt("defense_experience");
    }

    public int getCharacterId() {
        return this.characterId;
    }

    public int getStrengthLevel() {
        return this.strengthLevel;
    }

    public int getStrengthExperience() {
        return this.strengthExperience;
    }

    public int getStaminaLevel() {
        return this.staminaLevel;
    }

    public int getStaminaExperience() {
        return this.staminaExperience;
    }

    public int getAgilityLevel() {
        return this.agilityLevel;
    }

    public int getAgilityExperience() {
        return this.agilityExperience;
    }

    public int getResilienceLevel() {
        return this.resilienceLevel;
    }

    public int getResilienceExperience() {
        return this.resilienceExperience;
    }

    public int getMeleeLevel() {
        return this.meleeLevel;
    }

    public int getMeleeExperience() {
        return this.meleeExperience;
    }

    public int getRangedLevel() {
        return this.rangedLevel;
    }

    public int getRangedExperience() {
        return this.rangedExperience;
    }

    public int getDefenseLevel() {
        return this.defenseLevel;
    }

    public int getDefenseExperience() {
        return this.defenseExperience;
    }
}
