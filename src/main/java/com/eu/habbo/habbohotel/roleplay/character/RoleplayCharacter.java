package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.pets.Pet;
import com.eu.habbo.habbohotel.roleplay.character.actions.CharacterDiedAction;
import com.eu.habbo.habbohotel.roleplay.character.actions.CharacterExhaustedAction;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.roleplay.character.CharacterDataComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacter {

    private final Bot bot;
    private final Habbo habbo;
    private final Pet pet;

    private final int id;
    private final Integer botId;
    private final Integer userId;
    private final Integer petId;
    private int healthNow;
    private int healthMax;
    private int energyNow;
    private int energyMax;

    private RoleplayCharacterSkills skills;

    public RoleplayCharacter(ResultSet set, Bot bot, Habbo habbo, Pet pet) throws SQLException {
        this.bot = bot;
        this.habbo = habbo;
        this.pet = pet;
        this.id = set.getInt("id");
        this.botId = set.getInt("bots_id");
        this.userId = set.getInt("users_id");
        this.petId = set.getInt("pets_id");
        this.healthNow = set.getInt("health_now");
        this.healthMax = set.getInt("health_max");
        this.energyNow = set.getInt("energy_now");
        this.energyMax = set.getInt("energy_max");

        this.skills = RoleplayCharacterSkillsRepository.loadByCharacter(this);
    }
    public Bot getBot() {
        return this.bot;
    }

    public Habbo getHabbo() {
        return this.habbo;
    }
    public Pet getPet() {
        return this.pet;
    }

    public int getId() {
        return this.id;
    }

    public Integer getBotId() {
        return this.botId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public Integer getPetId() {
        return this.petId;
    }

    public int getHealthNow() {
        return this.healthNow;
    }

    public int getHealthMax() {
        return this.healthMax;
    }

    public void addHealth(int points) {
        this.healthNow += points;
        this.notifyRoom();
    }

    public void depleteHealth(int points) {
        this.healthNow -= points;

        if (this.isDead()) {
            new CharacterDiedAction(this);
        }

        this.notifyRoom();
    }

    public int getEnergyNow() {
        return this.energyNow;
    }

    public int getEnergyMax() {
        return this.energyMax;
    }

    public void addEnergy(int points) {
        this.energyNow += points;
        this.notifyRoom();
    }

    public void depleteEnergy(int points) {
        this.energyNow -= points;

        if (this.isExhausted()) {
            new CharacterExhaustedAction(this);
        }

        this.notifyRoom();
    }

    public boolean isDead() {
        return this.healthNow <= 0;
    }

    public boolean isExhausted() {
        return this.energyNow <= 0;
    }

    public boolean canInteract() {
        return !this.isDead();
    }

    public boolean canMove() {
        return !this.isDead();
    }

    public RoleplayCharacterSkills getSkills() {
        return this.skills;
    }

    private void notifyRoom() {
        if (this.bot != null) {
            this.bot.getRoom().sendResponse(new CharacterDataComposer(this));
            return;
        }

        if (this.habbo != null) {
            this.habbo.getRoomUnit().getRoom().sendResponse(new CharacterDataComposer(this));
        }
    }

}
