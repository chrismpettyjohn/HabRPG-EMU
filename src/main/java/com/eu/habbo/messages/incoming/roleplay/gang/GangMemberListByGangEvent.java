package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.gang.GangMemberListByGangComposer;

public class GangMemberListByGangEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        int gangId = this.packet.readInt();
        RoleplayGang gang = RoleplayGangManager.getInstance().getGangs().stream().filter(g -> g.getId() == gangId).findFirst().orElse(null);

        if (gang == null) {
            return;
        }

        this.client.sendResponse(new GangMemberListByGangComposer(gang));
    }
}
