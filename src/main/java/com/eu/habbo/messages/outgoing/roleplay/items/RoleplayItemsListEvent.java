package com.eu.habbo.messages.outgoing.roleplay.items;

import com.eu.habbo.habbohotel.roleplay.item.RoleplayItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;

public class RoleplayItemsListEvent extends MessageComposer {
    private final List<RoleplayItem> items;

    public RoleplayItemsListEvent(List<RoleplayItem> items) {
        this.items = items;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.RoleplayItemsListEvent);
        this.response.appendInt(this.items.size());

        for (RoleplayItem item : this.items) {
            this.response.appendString(item.getId() + ";" + item.getWeight());
        }

        return this.response;
    }
}