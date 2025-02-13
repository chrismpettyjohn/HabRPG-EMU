package com.eu.habbo.messages.outgoing.roleplay.character;

import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class CharacterDataComposer extends MessageComposer {
    private final RoleplayCharacter character;

    public CharacterDataComposer(RoleplayCharacter character) {
        this.character = character;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.CharacterDataComposer);
        this.response.appendInt(this.character.getId());
        this.response.appendInt(this.character.getBotId() == null ? -1 : this.character.getBotId());
        this.response.appendInt(this.character.getUserId() == null ? -1 : this.character.getUserId());
        this.response.appendInt(this.character.getPetId() == null ? -1 : this.character.getPetId());
        this.response.appendBoolean(this.character.isDead());
        this.response.appendBoolean(this.character.isExhausted());
        this.response.appendInt(this.character.getHealthNow());
        this.response.appendInt(this.character.getHealthMax());
        this.response.appendInt(this.character.getEnergyNow());
        this.response.appendInt(this.character.getEnergyMax());
        return this.response;
    }
}
