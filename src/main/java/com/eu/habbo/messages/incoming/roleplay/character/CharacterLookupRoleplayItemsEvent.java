package com.eu.habbo.messages.incoming.roleplay.character;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.character.CharacterRoleplayItemsDataComposer;

public class CharacterLookupRoleplayItemsEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        int userId = this.packet.readInt();
        Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(userId);

        if (habbo == null) {
            return;
        }

        this.client.sendResponse(new CharacterRoleplayItemsDataComposer(habbo.getRoleplayCharacter()));
    }
}
