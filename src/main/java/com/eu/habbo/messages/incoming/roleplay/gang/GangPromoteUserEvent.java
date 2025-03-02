package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRoleManager;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;


public class GangPromoteUserEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.gang_must_be_working"));
            return;
        }

        if (!this.client.getHabbo().getRoleplayCharacter().getGangRole().canPromote()) {
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

        RoleplayGangRole promotionGangRole = RoleplayGangRoleManager.getInstance().getGangRoles()
                .stream().filter(r -> r.getGangId() == this.client.getHabbo().getRoleplayCharacter().getGangId() && r.getOrderId() == targetHabbo.getRoleplayCharacter().getGangRole().getOrderId() + 1)
                        .findFirst()
                                .orElse(null);

        if (promotionGangRole == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        targetHabbo.getRoleplayCharacter().setGangRoleId(promotionGangRole.getId());

        targetHabbo.whisper(Emulator.getTexts()
                .getValue("rp.gang_you_were_promoted")
                .replace(":role", promotionGangRole.getName())
        );
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp_gang_promote_success")
                .replace(":username", targetHabbo.getHabboInfo().getUsername())
                .replace(":role", promotionGangRole.getName())
        );
    }
}
