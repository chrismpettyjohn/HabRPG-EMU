package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;

public class CorpStartWorkEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().getRoleplayCharacter().setIsWorking(false);
            this.client.getHabbo().shout(Emulator.getTexts().getValue("rp.corp_stop_work_success"));
            return;
        }

        this.client.getHabbo().getRoleplayCharacter().setIsWorking(true);
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.corp_start_work_success")
                .replace(":role", this.client.getHabbo().getRoleplayCharacter().getCorpRole().getName())
        );
    }
}
