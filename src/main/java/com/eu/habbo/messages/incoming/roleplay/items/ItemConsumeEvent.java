package com.eu.habbo.messages.incoming.roleplay.items;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.roleplay.BaseConsumableInteraction;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;

public class ItemConsumeEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        HabboItem item = this.client.getHabbo().getInventory().getItemsComponent().getHabboItem(this.packet.readInt());

        if (item == null) {
            return;
        }

        if (!item.getBaseItem().allowConsumption()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.not_consumable_item"));
            return;
        }

        BaseConsumableInteraction consumableInteraction = (BaseConsumableInteraction) item;
        consumableInteraction.onConsume(this.client);

        this.client.getHabbo().getInventory().getItemsComponent().removeHabboItem(item);
        this.client.sendResponse(new InventoryRefreshComposer());
    }
}