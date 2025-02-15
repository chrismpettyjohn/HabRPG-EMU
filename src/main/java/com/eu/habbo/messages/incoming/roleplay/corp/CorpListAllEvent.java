package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.corp.CorpListAllComposer;

public class CorpListAllEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        this.client.sendResponse(new CorpListAllComposer());
    }
}
