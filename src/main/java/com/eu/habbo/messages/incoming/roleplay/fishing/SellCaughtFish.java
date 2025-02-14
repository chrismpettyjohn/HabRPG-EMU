package com.eu.habbo.messages.incoming.roleplay.fishing;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.items.interactions.roleplay.InteractionCaughtFish;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import com.eu.habbo.threading.runnables.QueryDeleteHabboItem;

public class SellCaughtFish extends MessageHandler {
    @Override
    public void handle() throws Exception {
        int botId = this.packet.readInt();
        Bot bot = this.client.getHabbo().getRoomUnit().getRoom().getBot(botId);

        if (bot == null) {
            return;
        }

        HabboItem caughtFishFromInventory = this.client.getHabbo().getInventory().getItemsComponent()
                .getItemsAsValueCollection().stream()
                .filter(item -> InteractionCaughtFish.class.isAssignableFrom(item.getBaseItem().getInteractionType().getType()))
                .findFirst()
                .orElse(null);

        if (caughtFishFromInventory == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.no_fish_to_sell"));
            return;
        }

        this.client.getHabbo().getInventory().getItemsComponent().removeHabboItem(caughtFishFromInventory);
        Emulator.getThreading().run(new QueryDeleteHabboItem(caughtFishFromInventory.getId()));
        this.client.sendResponse(new InventoryRefreshComposer());

        int creditsPerFish = Emulator.getConfig().getInt("rp.fish_merchant_credits_per_caught_fish");
        this.client.getHabbo().giveCredits(creditsPerFish);

        this.client.getHabbo().shout(Emulator.getTexts().getValue("rp.fish_merchant_sold_fish")
                .replace(":credits", String.valueOf(creditsPerFish))
        );
        bot.talk(Emulator.getTexts()
                .getValue("rp.fish_merchant_purchases_fish")
                .replace(":credits", String.valueOf(creditsPerFish))
                .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
        );
    }
}
