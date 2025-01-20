package com.eu.habbo.messages.outgoing.roleplay.character;

import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacterAttributes;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class CharacterAttributesDataComposer extends MessageComposer {
    private final RoleplayCharacterAttributes characterAttributes;

    public CharacterAttributesDataComposer(RoleplayCharacterAttributes characterAttributes) {
        this.characterAttributes = characterAttributes;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.CharacterAttributesDataComposer);
        this.response.appendInt(this.characterAttributes.getUserId());
        this.response.appendInt(this.characterAttributes.getStrength());
        this.response.appendInt(this.characterAttributes.getIntelligence());
        this.response.appendInt(this.characterAttributes.getDexterity());
        this.response.appendInt(this.characterAttributes.getCharisma());
        this.response.appendInt(this.characterAttributes.getPerception());
        this.response.appendInt(this.characterAttributes.getEndurance());
        this.response.appendInt(this.characterAttributes.getLuck());
        return this.response;
    }
}