package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRoleManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.gang.GangRoleDataComposer;

public class GangRoleLookupByIdEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        int roleId = this.packet.readInt();
        RoleplayGangRole gangRole = RoleplayGangRoleManager.getInstance().getGangRoles().stream().filter(g -> g.getId() == roleId).findFirst().orElse(null);
        if (gangRole == null) {
            return;
        }
        this.client.sendResponse(new GangRoleDataComposer(gangRole));
    }
}
