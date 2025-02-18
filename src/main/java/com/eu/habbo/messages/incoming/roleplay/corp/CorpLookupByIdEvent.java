package com.eu.habbo.messages.incoming.roleplay.corp;


import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorp;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.corp.CorpDataComposer;

public class CorpLookupByIdEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        RoleplayCorp corp = RoleplayCorpManager.getInstance().getCorps().get(this.packet.readInt());
        if (corp == null) {
            return;
        }
        this.client.sendResponse(new CorpDataComposer(corp));
    }
}
