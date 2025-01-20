package com.eu.habbo.habbohotel.roleplay.character.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;

public class CharacterExhaustedEvent {
    public CharacterExhaustedEvent(RoleplayCharacter character) {
        if (character.getBot() != null) {
            character.getBot().shout(Emulator.getTexts().getValue("rp.exhausted"));
            return;
        }

        if (character.getHabbo() != null) {
            character.getHabbo().shout(Emulator.getTexts().getValue("rp.exhausted"));
        }
    }
}
