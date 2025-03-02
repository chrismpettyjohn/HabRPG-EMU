package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;

public class GangLeaveEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.gang_already_working"));
            return;
        }

        String oldGangRoleName =  this.client.getHabbo().getRoleplayCharacter().getGangRole().getName();

        this.client.getHabbo().getRoleplayCharacter().setGangId(Emulator.getConfig().getInt("rp.default_gang_id"));
        this.client.getHabbo().getRoleplayCharacter().setGangRoleId(Emulator.getConfig().getInt("rp.default_gang_role_id"));

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.gang_quit_job_success")
                .replace(":role", oldGangRoleName)
        );
    }
}
