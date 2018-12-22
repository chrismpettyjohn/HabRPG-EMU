package com.eu.habbo.messages.incoming.handshake;

import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.handshake.MachineIDComposer;

public class MachineIDEvent extends MessageHandler {

    @Override
    public void handle() throws Exception {
        String unknown = this.packet.readString();
        this.client.setMachineId(this.packet.readString());
        this.client.sendResponse(new MachineIDComposer(this.client));
    }
}
