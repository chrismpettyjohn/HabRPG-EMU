package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;

public class CorpStartWorkEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corp_already_working"));
            return;
        }

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.corp_start_work_success")
                .replace(":role", "")
        );
    }
}
