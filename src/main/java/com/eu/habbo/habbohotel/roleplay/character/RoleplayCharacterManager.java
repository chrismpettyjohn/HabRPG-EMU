package com.eu.habbo.habbohotel.roleplay.character;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.pets.Pet;
import com.eu.habbo.habbohotel.users.Habbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class RoleplayCharacterManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayCharacterManager.class);
    private static RoleplayCharacterManager instance;

    public static RoleplayCharacterManager getInstance() {
        if (instance == null) {
            synchronized (RoleplayCharacterManager.class) {
                if (instance == null) {
                    instance = new RoleplayCharacterManager();
                }
            }
        }
        return instance;
    }

    private List<RoleplayCharacter> characters;

    private RoleplayCharacterManager() {
        this.characters = RoleplayCharacterRepository.getAll();
        LOGGER.info("Loaded " + this.characters.size() + " roleplay characters");
    }

    public List<RoleplayCharacter> getCharacters() {
        return characters;
    }

    public RoleplayCharacter getByBot(int botId) {
        RoleplayCharacter character = this.characters.stream()
                .filter(c -> c.getBotId() == botId)
                .findFirst()
                .orElse(null);

        if (character != null) {
            return character;
        }

        character = RoleplayCharacterRepository.createDefaultCharacter("bot", botId);
        this.characters.add(character);
        return character;
    }

    public RoleplayCharacter getByHabbo(int habboId) {
        RoleplayCharacter character = this.characters.stream()
                .filter(c -> c.getUserId() == habboId)
                .findFirst()
                .orElse(null);

        if (character != null) {
            return character;
        }

        character = RoleplayCharacterRepository.createDefaultCharacter("user", habboId);
        this.characters.add(character);
        return character;
    }

    public RoleplayCharacter getByPet(int petId) {
        RoleplayCharacter character = this.characters.stream()
                .filter(c -> c.getBotId() == petId)
                .findFirst()
                .orElse(null);

        if (character != null) {
            return character;
        }

        character = RoleplayCharacterRepository.createDefaultCharacter("pet", petId);
        this.characters.add(character);
        return character;
    }



    public void addItem(RoleplayCharacter character) {
        this.characters.add(character);
    }

    public void updateItem(RoleplayCharacter character) {
        this.characters.set(characters.indexOf(character), character);
    }

    public void removeItem(RoleplayCharacter character) {
        RoleplayCharacterRepository.deleteById(character.getId());
        this.characters.remove(character);
    }

    public void dispose() {
        for (RoleplayCharacter character : this.characters) {
            character.save();
        }
        this.characters = null;
    }
}
