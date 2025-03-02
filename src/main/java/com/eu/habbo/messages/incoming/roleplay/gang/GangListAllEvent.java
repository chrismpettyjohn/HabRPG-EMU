package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.gang.GangListAllComposer;

public class GangListAllEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        this.client.sendResponse(new GangListAllComposer());
    }
}
