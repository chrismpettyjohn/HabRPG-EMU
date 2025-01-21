package com.eu.habbo.habbohotel.roleplay.character.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.pets.PetVocal;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;

public class CharacterExhaustedAction {
    public CharacterExhaustedAction(RoleplayCharacter character) {
        if (character.getBot() != null) {
            character.getBot().shout(Emulator.getTexts().getValue("rp.exhausted"));
            return;
        }

        if (character.getHabbo() != null) {
            character.getHabbo().shout(Emulator.getTexts().getValue("rp.exhausted"));
        }

        if (character.getPet() != null) {
            character.getPet().say(new PetVocal(Emulator.getTexts().getValue("rp.exhausted")));
        }
    }
}
