package com.eu.habbo.habbohotel.roleplay.character.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.pets.PetVocal;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserStatusComposer;

public class CharacterHealedAction {

    public CharacterHealedAction(RoleplayCharacter character) {
        if (character.getBot() != null) {
            character.getBot().shout(Emulator.getTexts().getValue("rp.healed"));
            character.getBot().getRoomUnit().clearStatus();
            character.getBot().getRoom().sendComposer(new RoomUserStatusComposer(character.getBot().getRoomUnit()).compose());
            return;
        }

        if (character.getHabbo() != null) {
            character.getHabbo().shout(Emulator.getTexts().getValue("rp.healed"));
            character.getHabbo().getRoomUnit().clearStatus();
            character.getHabbo().getRoomUnit().getRoom().sendComposer(new RoomUserStatusComposer(character.getHabbo().getRoomUnit()).compose());
        }

        if (character.getPet() != null) {
            character.getPet().say(new PetVocal(Emulator.getTexts().getValue("rp.healed")));
            character.getPet().getRoomUnit().clearStatus();
            character.getPet().getRoom().sendComposer(new RoomUserStatusComposer(character.getPet().getRoomUnit()).compose());
        }
    }

}
