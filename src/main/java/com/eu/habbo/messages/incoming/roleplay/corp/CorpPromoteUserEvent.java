package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
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

        if (this.client.getHabbo().getRoleplayCharacter().getCorpRole().getOrderId() <= targetHabbo.getRoleplayCharacter().getCorpRole().getOrderId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        RoleplayCorpRole promotionCorpRole = RoleplayCorpRoleManager.getInstance().getCorpRoles()
                .stream().filter(r -> r.getCorpId() == this.client.getHabbo().getRoleplayCharacter().getCorpId() && r.getOrderId() == targetHabbo.getRoleplayCharacter().getCorpRole().getOrderId() + 1)
                        .findFirst()
                                .orElse(null);

        if (promotionCorpRole == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        targetHabbo.getRoleplayCharacter().setCorpRoleId(promotionCorpRole.getId());

        targetHabbo.whisper(Emulator.getTexts()
                .getValue("rp.corp_you_were_promoted")
                .replace(":role", promotionCorpRole.getName())
        );
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp_corp_promote_success")
                .replace(":username", targetHabbo.getHabboInfo().getUsername())
                .replace(":role", promotionCorpRole.getName())
        );
    }
}
