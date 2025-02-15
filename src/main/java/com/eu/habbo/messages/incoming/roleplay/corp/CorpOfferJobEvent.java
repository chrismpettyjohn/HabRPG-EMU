package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;

public class CorpOfferJobEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.corp_must_be_working"));
            return;
        }

        if (!this.client.getHabbo().getRoleplayCharacter().getCorpRole().canHire()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        int userId = this.packet.readInt();
        Habbo targetHabbo = this.client.getHabbo().getRoomUnit().getRoom().getHabbo(userId);

        if (targetHabbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return;
        }

        if (targetHabbo.getRoleplayCharacter().getJobOfferCorpRoleId() != null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        RoleplayCorpRole startingRole = RoleplayCorpRoleManager.getInstance().getCorpRoles()
                .stream().filter(r -> r.getCorpId() == targetHabbo.getRoleplayCharacter().getCorpId() && r.getOrderId() == 1)
                .findFirst()
                .orElse(null);

        if (startingRole == null) {
            throw new Exception("Corp " + targetHabbo.getRoleplayCharacter().getCorpId() + " is missing starting role order id 1");
        }

        targetHabbo.getRoleplayCharacter().setJobOfferCorpRoleId(startingRole.getId());

        targetHabbo.whisper(Emulator.getTexts()
                .getValue("rp.you_were_offered_job")
                .replace(":corp", "")
                .replace(":role", startingRole.getName())
        );
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.offer_job_success")
                .replace(":username", targetHabbo.getHabboInfo().getUsername())
                .replace(":role", startingRole.getName())
        );
    }
}
