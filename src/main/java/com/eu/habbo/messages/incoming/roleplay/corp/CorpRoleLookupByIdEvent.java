package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.corp.CorpRoleDataComposer;

public class CorpRoleLookupByIdEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        int roleId = this.packet.readInt();
        RoleplayCorpRole corpRole = RoleplayCorpRoleManager.getInstance().getCorpRoles().stream().filter(r -> r.getId() == roleId).findFirst().orElse(null);
        if (corpRole == null) {
            return;
        }
        this.client.sendResponse(new CorpRoleDataComposer(corpRole));
    }
}
