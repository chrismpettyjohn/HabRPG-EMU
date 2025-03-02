package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangManager;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRoleManager;
import com.eu.habbo.messages.incoming.MessageHandler;

public class GangAcceptInviteEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        Integer offeredGangRoleId = this.client.getHabbo().getRoleplayCharacter().getGangOfferGangRoleId();
        if (offeredGangRoleId== null) {
            return;
        }

        RoleplayGangRole gangRole = RoleplayGangRoleManager.getInstance().getGangRoles()
                .stream().filter(r -> r.getId() == offeredGangRoleId)
                .findFirst()
                .orElse(null);

        RoleplayGang gang = RoleplayGangManager.getInstance().getGangs()
                .stream().filter(c -> c.getId() == gangRole.getGangId())
                .findFirst()
                .orElse(null);

        if (gangRole == null || gang == null) {
            throw new Exception("invalid gang role " + offeredGangRoleId + " was offered to user " + this.client.getHabbo().getHabboInfo().getUsername());
        }

        this.client.getHabbo().getRoleplayCharacter().setGangId(gangRole.getGangId());
        this.client.getHabbo().getRoleplayCharacter().setGangRoleId(gangRole.getId());
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.accept_job_offer")
                .replace(":gang", gang.getName())
                .replace("role", gangRole.getName())
        );
    }
}