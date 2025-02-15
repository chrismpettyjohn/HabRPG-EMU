package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;

public class CorpFireUserEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corp_must_be_working"));
            return;
        }

        if (!this.client.getHabbo().getRoleplayCharacter().getCorpRole().canFire()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        int userId = this.packet.readInt();
        Habbo targetHabbo = this.client.getHabbo().getRoomUnit().getRoom().getHabbo(userId);

        if (targetHabbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return;
        }

        targetHabbo.getRoleplayCharacter().setCorpId(Emulator.getConfig().getInt("rp.default_corp_id"));
        targetHabbo.getRoleplayCharacter().setCorpRoleId(Emulator.getConfig().getInt("rp.default_corp_role_id"));
        targetHabbo.whisper(Emulator.getTexts().getValue("rp.corp_you_were_fired"));
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp_corp_fire_success")
                .replace(":username", targetHabbo.getHabboInfo().getUsername())
        );
    }
}
