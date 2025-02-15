package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorp;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpManager;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
import com.eu.habbo.messages.incoming.MessageHandler;

public class CorpAcceptJobOfferEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        Integer offeredCorpRoleId = this.client.getHabbo().getRoleplayCharacter().getJobOfferCorpRoleId();
        if (offeredCorpRoleId== null) {
            return;
        }

        RoleplayCorpRole corpRole = RoleplayCorpRoleManager.getInstance().getCorpRoles()
                .stream().filter(r -> r.getId() == offeredCorpRoleId)
                .findFirst()
                .orElse(null);

        RoleplayCorp corp = RoleplayCorpManager.getInstance().getCorps()
                .stream().filter(c -> c.getId() == corpRole.getCorpId())
                .findFirst()
                .orElse(null);

        if (corpRole == null || corp == null) {
            throw new Exception("invalid corp role " + offeredCorpRoleId + " was offered to user " + this.client.getHabbo().getHabboInfo().getUsername());
        }

        this.client.getHabbo().getRoleplayCharacter().setCorpId(corpRole.getCorpId());
        this.client.getHabbo().getRoleplayCharacter().setCorpRoleId(corpRole.getId());
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.accept_job_offer")
                .replace(":corp", corp.getName())
                .replace("role", corpRole.getName())
        );
    }
}