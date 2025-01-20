package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.roleplay.character.events.CharacterDiedEvent;
import com.eu.habbo.habbohotel.roleplay.character.events.CharacterExhaustedEvent;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.roleplay.character.CharacterDataComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacter {

    private final Bot bot;
    private final Habbo habbo;

    private int botId;
    private int userId;
    private int healthNow;
    private int healthMax;
    private int energyNow;
    private int energyMax;

    public RoleplayCharacter(ResultSet set, Bot bot, Habbo habbo) throws SQLException {
        this.bot = bot;
        this.habbo = habbo;
        this.botId = set.getInt("bots_id");
        this.userId = set.getInt("users_id");
        this.healthNow = set.getInt("health_now");
        this.healthMax = set.getInt("health_max");
        this.energyNow = set.getInt("energy_now");
        this.energyMax = set.getInt("energy_max");
    }
    public Bot getBot() {
        return this.bot;
    }

    public Habbo getHabbo() {
        return this.habbo;
    }

    public int getBotId() {
        return this.botId;
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

        if (this.isDead()) {
            new CharacterDiedEvent(this);
        }

        this.notifyRoom();
    }

    public int getEnergyNow() {
        return this.energyNow;
    }

    public int getEnergyMax() {
        return this.energyMax;
    }

    public void depleteEnergy(int points) {
        this.energyNow -= points;

        if (this.isExhausted()) {
            new CharacterExhaustedEvent(this);
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
