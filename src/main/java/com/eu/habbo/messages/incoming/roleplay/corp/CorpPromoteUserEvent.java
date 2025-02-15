package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;


public class CorpPromoteUserEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corp_must_be_working"));
            return;
        }

        if (!this.client.getHabbo().getRoleplayCharacter().getCorpRole().canPromote()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        int userId = this.packet.readInt();
        Habbo targetHabbo = this.client.getHabbo().getRoomUnit().getRoom().getHabbo(userId);

        if (targetHabbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return;
        }

        targetHabbo.whisper(Emulator.getTexts()
                .getValue("rp.corp_you_were_promoted")
                .replace(":role", "")
        );
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp_corp_promote_success")
                .replace(":username", targetHabbo.getHabboInfo().getUsername())
                .replace(":role", "")
        );
    }
}
