package com.eu.habbo.messages.incoming.roleplay.items;

import com.eu.habbo.habbohotel.roleplay.item.RoleplayItem;
import com.eu.habbo.habbohotel.roleplay.item.RoleplayItemManager;
import com.eu.habbo.habbohotel.roleplay.item.RoleplayItemRepository;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.items.RoleplayItemDeletedEvent;

public class RoleplayItemDeleteOneEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().hasPermission("acc_manage_roleplay")) {
            return;
        }

        int itemId = this.packet.readInt();

        RoleplayItem item = RoleplayItemManager.getInstance().getItems().stream().filter(i -> i.getId() == itemId).findFirst().orElse(null);

        if (item == null) {
            return;
        }

        RoleplayItemRepository.deleteOne(item);
        RoleplayItemManager.getInstance().removeItem(item);

        this.client.sendResponse(new RoleplayItemDeletedEvent(item));
    }
}
