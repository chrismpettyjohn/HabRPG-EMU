package com.eu.habbo.messages.incoming.roleplay.character;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.character.CharacterDataComposer;

public class CharacterUpdateByIdEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().hasPermission("acc_manage_roleplay")) {
            return;
        }

        int userId = this.packet.readInt();
        Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(userId);

        if (habbo == null) {
            return;
        }

        habbo.getRoleplayCharacter().setHealthNow(this.packet.readInt());
        habbo.getRoleplayCharacter().setHealthMax(this.packet.readInt());
        habbo.getRoleplayCharacter().setEnergyNow(this.packet.readInt());
        habbo.getRoleplayCharacter().setEnergyMax(this.packet.readInt());
        habbo.getRoleplayCharacter().save();

        this.client.sendResponse(new CharacterDataComposer(habbo.getRoleplayCharacter()));
    }
}
