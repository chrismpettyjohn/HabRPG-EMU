package com.eu.habbo.messages.incoming.catalog.marketplace;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.catalog.marketplace.MarketPlace;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.catalog.AlertPurchaseFailedComposer;
import com.eu.habbo.messages.outgoing.catalog.marketplace.MarketplaceItemPostedComposer;
import com.eu.habbo.messages.outgoing.catalog.marketplace.MarketplaceSellItemComposer;

public class SellItemEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {

        if(!MarketPlace.MARKETPLACE_ENABLED)
        {
            this.client.sendResponse(new MarketplaceSellItemComposer(3, 0, 0));
            return;
        }

        int credits = this.packet.readInt();

        int unknown = this.packet.readInt();
        int itemId = this.packet.readInt();

        HabboItem item = this.client.getHabbo().getInventory().getItemsComponent().getHabboItem(itemId);
        if(item != null)
        {
            if (!item.getBaseItem().allowMarketplace())
            {
                String message = Emulator.getTexts().getValue("scripter.warning.marketplace.forbidden").replace("%username%", client.getHabbo().getHabboInfo().getUsername()).replace("%itemname%", item.getBaseItem().getName()).replace("%credits%", credits + "");
                Emulator.getGameEnvironment().getModToolManager().quickTicket(this.client.getHabbo(), "Scripter", message);
                Emulator.getLogging().logUserLine(message);
                this.client.sendResponse(new AlertPurchaseFailedComposer(AlertPurchaseFailedComposer.SERVER_ERROR));
                return;
            }

            if(credits < 0)
            {
                String message = Emulator.getTexts().getValue("scripter.warning.marketplace.negative").replace("%username%", client.getHabbo().getHabboInfo().getUsername()).replace("%itemname%", item.getBaseItem().getName()).replace("%credits%", credits + "");
                Emulator.getGameEnvironment().getModToolManager().quickTicket(this.client.getHabbo(), "Scripter", message);
                Emulator.getLogging().logUserLine(message);
                this.client.sendResponse(new AlertPurchaseFailedComposer(AlertPurchaseFailedComposer.SERVER_ERROR));
                return;
            }

            if(MarketPlace.sellItem(this.client, item, credits))
            {
                this.client.sendResponse(new MarketplaceItemPostedComposer(MarketplaceItemPostedComposer.POST_SUCCESS));
            }
            else
            {
                this.client.sendResponse(new MarketplaceItemPostedComposer(MarketplaceItemPostedComposer.FAILED_TECHNICAL_ERROR));
            }
        }
    }
}
