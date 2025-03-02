package com.eu.habbo.messages.incoming.roleplay.gang;


import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.gang.GangDataComposer;

public class GangLookupByIdEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        RoleplayGang gang = RoleplayGangManager.getInstance().getGangs().get(this.packet.readInt());
        if (gang == null) {
            return;
        }
        this.client.sendResponse(new GangDataComposer(gang));
    }
}
