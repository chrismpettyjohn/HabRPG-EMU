package com.eu.habbo.messages.outgoing.unknown;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class UnknownTradeComposer extends MessageComposer
{
    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.UnknownTradeComposer);
        //Empty Body
        return this.response;
    }
}