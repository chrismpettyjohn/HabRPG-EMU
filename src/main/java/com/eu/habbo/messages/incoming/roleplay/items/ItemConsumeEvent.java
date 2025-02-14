package com.eu.habbo.messages.incoming.roleplay.items;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.base.BaseConsumableInteraction;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import com.eu.habbo.threading.runnables.QueryDeleteHabboItem;

public class ItemConsumeEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        HabboItem item = this.client.getHabbo().getInventory().getItemsComponent().getHabboItem(this.packet.readInt());

        if (item == null) {
            return;
        }

        BaseConsumableInteraction consumableInteraction = (BaseConsumableInteraction) item;

        if (!consumableInteraction.canConsume()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.not_consumable_item"));
            return;
        }

        this.client.getHabbo().getInventory().getItemsComponent().removeHabboItem(item);
        Emulator.getThreading().run(new QueryDeleteHabboItem(item.getId()));
        consumableInteraction.onConsume(this.client);
        this.client.sendResponse(new InventoryRefreshComposer());
    }
}