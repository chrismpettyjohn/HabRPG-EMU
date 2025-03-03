package com.eu.habbo.habbohotel.roleplay.character;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
