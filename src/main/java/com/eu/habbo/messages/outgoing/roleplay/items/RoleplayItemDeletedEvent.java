package com.eu.habbo.messages.outgoing.roleplay.items;

import com.eu.habbo.habbohotel.roleplay.item.RoleplayItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class RoleplayItemDeletedEvent extends MessageComposer {
    private final RoleplayItem item;

    public RoleplayItemDeletedEvent(RoleplayItem item) {
        this.item = item;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.RoleplayItemDeleted);
        this.response.appendInt(this.item.getId());
        return this.response;
    }
}