package com.eu.habbo.messages.incoming.roleplay.items;

import com.eu.habbo.habbohotel.roleplay.item.RoleplayItem;
import com.eu.habbo.habbohotel.roleplay.item.RoleplayItemManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.items.RoleplayItemsListEvent;

import java.util.List;

public class RoleplayItemLookupByTypeEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().hasPermission("acc_manage_roleplay")) {
            return;
        }

        String type = this.packet.readString();

        List<RoleplayItem> items = RoleplayItemManager.getInstance().getItems().stream().filter(item -> item.getType() == type).toList();
        this.client.sendResponse(new RoleplayItemsListEvent(items));
    }
}
