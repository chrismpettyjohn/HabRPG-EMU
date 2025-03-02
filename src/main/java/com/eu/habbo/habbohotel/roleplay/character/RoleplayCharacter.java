package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.pets.Pet;
import com.eu.habbo.habbohotel.roleplay.character.actions.CharacterDiedAction;
import com.eu.habbo.habbohotel.roleplay.character.actions.CharacterExhaustedAction;
import com.eu.habbo.habbohotel.roleplay.character.actions.CharacterHealedAction;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorp;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpManager;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangManager;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.roleplay.character.CharacterDataComposer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleplayCharacter {

    private final Bot bot;
    private final Habbo habbo;
    private final Pet pet;

    private final int id;
    private final String type;
    private final Integer botId;
    private final Integer userId;
    private final Integer petId;

    private int corpId;
    private int corpRoleId;

    private int gangId;
    private int gangRoleId;

    private int healthNow;
    private int healthMax;
    private int energyNow;
    private int energyMax;

    private boolean isWorking;

    private RoleplayCharacterSkills skills;
    private List<RoleplayCharacterItem> items;

    private Integer jobOfferCorpRoleId;
    private Integer gangOfferGangRoleId;

    public RoleplayCharacter(ResultSet set, Bot bot, Habbo habbo, Pet pet) throws SQLException {
        this.bot = bot;
        this.habbo = habbo;
        this.pet = pet;
        this.id = set.getInt("id");
        this.type = set.getString("type");
        this.botId = set.getInt("bots_id");
        this.userId = set.getInt("users_id");
        this.petId = set.getInt("pets_id");
        this.corpId = set.getInt("corp_id");
        this.corpRoleId = set.getInt("corp_role_id");
        this.gangId = set.getInt("gang_id");
        this.gangRoleId = set.getInt("gang_role_id");
        this.healthNow = set.getInt("health_now");
        this.healthMax = set.getInt("health_max");
        this.energyNow = set.getInt("energy_now");
        this.energyMax = set.getInt("energy_max");

        this.isWorking = false;
        this.jobOfferCorpRoleId = null;
        this.gangOfferGangRoleId = null;

        this.skills = RoleplayCharacterSkillsRepository.loadByCharacter(this);
        this.items = RoleplayCharacterItemRepository.loadAllByCharacter(this);
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

    public String getType() { return this.type; }

    public Integer getBotId() {
        return this.botId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public Integer getPetId() {
        return this.petId;
    }

    public RoleplayCorp getCorp() {
        return RoleplayCorpManager.getInstance().getCorps().stream().filter(c -> c.getId() == this.corpId).findFirst().orElse(null);
    }

    public Integer getCorpId() {
        return this.corpId;
    }

    public void setCorpId(int corpId) {
        this.corpId = corpId;

        this.notifyRoom();
    }

    public RoleplayCorpRole getCorpRole() {
        return this.getCorp().getRoles().stream().filter(c -> c.getId() == this.corpRoleId).findFirst().orElse(null);
    }

    public Integer getCorpRoleId() {
        return this.corpRoleId;
    }

    public void setCorpRoleId(int corpRoleId) {
        this.corpRoleId = corpRoleId;

        this.notifyRoom();
    }
    public RoleplayGang getGang() {
        return RoleplayGangManager.getInstance().getGangs().stream().filter(c -> c.getId() == this.gangId).findFirst().orElse(null);
    }

    public Integer getGangId() {
        return this.gangId;
    }

    public void setGangId(int gangId) {
        this.gangId = gangId;

        this.notifyRoom();
    }

    public RoleplayGangRole getGangRole() {
        return this.getGang().getRoles().stream().filter(c -> c.getId() == this.corpRoleId).findFirst().orElse(null);
    }

    public Integer getGangRoleId() {
        return this.gangRoleId;
    }

    public void setGangRoleId(int gangRoleId) {
        this.gangRoleId = gangRoleId;

        this.notifyRoom();
    }

    public int getHealthNow() {
        return this.healthNow;
    }

    public int getHealthMax() {
        return this.healthMax;
    }

    public void setHealthNow(int points, RoleplayCharacter affectedBy) {
        int healthBefore = this.healthNow;
        this.healthNow = Math.max(0, Math.min(points, this.healthMax));

        if (healthBefore <= 0 && this.healthNow > 0) {
            new CharacterHealedAction(this);
        }

        if (this.isDead()) {
            new CharacterDiedAction(this, affectedBy);
        }

        this.notifyRoom();
    }

    public void setHealthMax(int points) {
        this.healthMax = points;
        this.notifyRoom();
    }

    public void addHealth(int points, RoleplayCharacter healedBy) {
        this.setHealthNow(this.getHealthNow() + points, healedBy);
    }

    public void depleteHealth(int points, RoleplayCharacter attackedBy) {
        this.setHealthNow(this.getHealthNow() - points, attackedBy);
    }

    public int getEnergyNow() {
        return this.energyNow;
    }

    public int getEnergyMax() {
        return this.energyMax;
    }

    public void setEnergyNow(int points) {
        this.energyNow = Math.max(0, Math.min(points, this.energyMax));

        if (this.isExhausted()) {
            new CharacterExhaustedAction(this);
        }

        this.notifyRoom();
    }

    public void setEnergyMax(int points) {
        this.energyMax = points;
        this.notifyRoom();
    }

    public void addEnergy(int points) {
        this.setEnergyNow(this.getEnergyNow() + points);
    }

    public void depleteEnergy(int points) {
       this.setEnergyNow(this.getEnergyNow() - points);
    }

    public Integer getJobOfferCorpRoleId() {
        return this.jobOfferCorpRoleId;
    }

    public void setJobOfferCorpRoleId(int corpRoleId) {
        this.jobOfferCorpRoleId = corpRoleId;
    }

    public Integer getGangOfferGangRoleId() {
        return this.gangOfferGangRoleId;
    }

    public void setGangOfferGangRoleId(int gangRoleId) {
        this.gangOfferGangRoleId = gangRoleId;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void setIsWorking(boolean working) {
        this.isWorking = working;
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

    public List<RoleplayCharacterItem> getItems() {
        return this.items;
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

    public void dispose() {
        RoleplayCharacterRepository.updateByCharacter(this);
        RoleplayCharacterItemRepository.updateAll(this.items);
    }

    public void save() {
        RoleplayCharacterRepository.updateByCharacter(this);
    }

}
