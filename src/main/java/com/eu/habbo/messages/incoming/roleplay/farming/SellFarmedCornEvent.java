package com.eu.habbo.messages.incoming.roleplay.farming;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.items.interactions.roleplay.InteractionCorn;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import com.eu.habbo.threading.runnables.QueryDeleteHabboItem;

public class SellFarmedCornEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        int botId = this.packet.readInt();
        Bot bot = this.client.getHabbo().getRoomUnit().getRoom().getBot(botId);

        if (bot == null) {
            return;
        }

        HabboItem farmedCornFromInventory = this.client.getHabbo().getInventory().getItemsComponent()
                .getItemsAsValueCollection().stream()
                .filter(item -> InteractionCorn.class.isAssignableFrom(item.getBaseItem().getInteractionType().getType()))
                .findFirst()
                .orElse(null);

        if (farmedCornFromInventory == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.no_corn_to_sell"));
            return;
        }

        this.client.getHabbo().getInventory().getItemsComponent().removeHabboItem(farmedCornFromInventory);
        Emulator.getThreading().run(new QueryDeleteHabboItem(farmedCornFromInventory.getId()));
        this.client.sendResponse(new InventoryRefreshComposer());

        int creditsPerCorn = Emulator.getConfig().getInt("rp.farm_merchant_credits_per_caught_corn");
        this.client.getHabbo().giveCredits(creditsPerCorn);

        this.client.getHabbo().shout(Emulator.getTexts().getValue("rp.farm_merchant_sold_corn")
                .replace(":credits", String.valueOf(creditsPerCorn))
        );
        bot.talk(Emulator.getTexts()
                .getValue("rp.farm_merchant_purchases_corn")
                .replace(":credits", String.valueOf(creditsPerCorn))
                .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
        );
    }
}
