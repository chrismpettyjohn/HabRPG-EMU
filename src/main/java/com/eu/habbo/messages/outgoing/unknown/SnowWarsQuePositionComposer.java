package com.eu.habbo.messages.outgoing.unknown;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;

public class SnowWarsQuePositionComposer extends MessageComposer
{
    @Override
    public ServerMessage compose()
    {
        this.response.init(2077);
        this.response.appendInt(1);
        return this.response;
    }
}
