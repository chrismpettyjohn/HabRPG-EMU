package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;

public class GangKickUserEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.gang_must_be_working"));
            return;
        }

        if (!this.client.getHabbo().getRoleplayCharacter().getGangRole().canKick()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        int userId = this.packet.readInt();
        Habbo targetHabbo = this.client.getHabbo().getRoomUnit().getRoom().getHabbo(userId);

        if (targetHabbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return;
        }

        if (this.client.getHabbo().getRoleplayCharacter().getGangRole().getOrderId() <= targetHabbo.getRoleplayCharacter().getGangRole().getOrderId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        targetHabbo.getRoleplayCharacter().setGangId(Emulator.getConfig().getInt("rp.default_gang_id"));
        targetHabbo.getRoleplayCharacter().setGangRoleId(Emulator.getConfig().getInt("rp.default_gang_role_id"));
        targetHabbo.whisper(Emulator.getTexts().getValue("rp.gang_you_were_fired"));
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp_gang_fire_success")
                .replace(":username", targetHabbo.getHabboInfo().getUsername())
        );
    }
}
