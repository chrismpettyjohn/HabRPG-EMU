package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;

public class CorpQuitJobEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corp_already_working"));
            return;
        }

        this.client.getHabbo().getRoleplayCharacter().setCorpId(Emulator.getConfig().getInt("rp.default_corp_id"));
        this.client.getHabbo().getRoleplayCharacter().setCorpRoleId(Emulator.getConfig().getInt("rp.default_corp_role_id"));

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.corp_quit_job_success")
                .replace(":role", "")
        );
    }
}
