package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;

public class GangDisbandEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (this.client.getHabbo().getRoleplayCharacter().getGang() == null) {
            return;
        }

        if (this.client.getHabbo().getRoleplayCharacter().getGang().getUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.not_allowed"));
            return;
        }

        // Update all users to no gang
        // Delete gang roles
        // Delete gang
    }
}