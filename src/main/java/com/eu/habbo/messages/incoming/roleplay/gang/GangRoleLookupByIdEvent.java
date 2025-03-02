package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRoleManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.gang.GangRoleDataComposer;

public class GangRoleLookupByIdEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        RoleplayGangRole gangRole = RoleplayGangRoleManager.getInstance().getGangRoles().get(this.packet.readInt());
        if (gangRole == null) {
            return;
        }
        this.client.sendResponse(new GangRoleDataComposer(gangRole));
    }
}
