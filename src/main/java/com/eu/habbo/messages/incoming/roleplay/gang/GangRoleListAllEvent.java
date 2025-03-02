package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.gang.GangRoleListAllComposer;

public class GangRoleListAllEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        this.client.sendResponse(new GangRoleListAllComposer());
    }
}
