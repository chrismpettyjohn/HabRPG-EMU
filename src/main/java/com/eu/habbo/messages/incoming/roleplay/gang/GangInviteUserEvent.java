package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRoleManager;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.gang.GangInviteComposer;

public class GangInviteUserEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().getRoleplayCharacter().isWorking()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.gang_must_be_working"));
            return;
        }

        if (!this.client.getHabbo().getRoleplayCharacter().getGangRole().canInvite()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        int userId = this.packet.readInt();
        Habbo targetHabbo = this.client.getHabbo().getRoomUnit().getRoom().getHabbo(userId);

        if (targetHabbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return;
        }

        if (targetHabbo.getRoleplayCharacter().getGangOfferGangRoleId() != null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.cannot_do_that"));
            return;
        }

        RoleplayGangRole startingRole = RoleplayGangRoleManager.getInstance().getGangRoles()
                .stream().filter(r -> r.getGangId() == targetHabbo.getRoleplayCharacter().getGangId() && r.getOrderId() == 1)
                .findFirst()
                .orElse(null);

        if (startingRole == null) {
            throw new Exception("Gang " + targetHabbo.getRoleplayCharacter().getGangId() + " is missing starting role order id 1");
        }

        targetHabbo.getRoleplayCharacter().setGangOfferGangRoleId(startingRole.getId());

        targetHabbo.whisper(Emulator.getTexts()
                .getValue("rp.you_were_offered_job")
                .replace(":gang", "")
                .replace(":role", startingRole.getName())
        );
        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.offer_job_success")
                .replace(":username", targetHabbo.getHabboInfo().getUsername())
                .replace(":role", startingRole.getName())
        );

        targetHabbo.getClient().sendResponse(new GangInviteComposer(targetHabbo));
    }
}
