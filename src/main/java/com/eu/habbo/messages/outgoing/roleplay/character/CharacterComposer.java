package com.eu.habbo.messages.outgoing.roleplay.character;

import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class CharacterComposer extends MessageComposer {
    private final RoleplayCharacter character;

    public CharacterComposer(RoleplayCharacter character) {
        this.character = character;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.RoleplayCharacterComposer);
        this.response.appendInt(this.character.getUserId());
        this.response.appendInt(this.character.getHealthNow());
        this.response.appendInt(this.character.getHealthMax());
        this.response.appendInt(this.character.getEnergyNow());
        this.response.appendInt(this.character.getEnergyMax());
        return this.response;
    }
}