package com.eu.habbo.messages.outgoing.roleplay.character;

import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacterItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class CharacterRoleplayItemsDataComposer extends MessageComposer {
    private final RoleplayCharacter character;

    public CharacterRoleplayItemsDataComposer(RoleplayCharacter character) {
        this.character = character;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.CharacterRoleplayItemsDataComposer);
        this.response.appendInt(this.character.getItems().size());

        for (RoleplayCharacterItem item : this.character.getItems()) {
            this.response.appendString(item.getId() + ";" + item.getCharacterId() + ";" + item.getItemId());
        }

        return this.response;
    }
}