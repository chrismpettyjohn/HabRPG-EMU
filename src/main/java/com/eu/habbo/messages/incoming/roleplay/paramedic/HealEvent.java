package com.eu.habbo.messages.incoming.roleplay.paramedic;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.roleplay.InteractionFirstAidKit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import com.eu.habbo.threading.runnables.QueryDeleteHabboItem;

public class HealEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (this.client.getHabbo().getHabboInfo().getCurrentRoom() == null) {
            return;
        }

        int targetId = this.packet.readInt();
        Habbo targetHabbo = this.client.getHabbo().getRoomUnit().getRoom().getHabbo(targetId);

        if (targetHabbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.not_possible"));
            return;
        }

        if (!this.client.getHabbo().getRoleplayCharacter().canInteract()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.not_possible"));
            return;
        }

        if (targetHabbo.getRoleplayCharacter().getHealthNow() >= targetHabbo.getRoleplayCharacter().getHealthMax()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.not_possible"));
            return;
        }

        HabboItem firstAidKit = this.client.getHabbo().getInventory().getItemsComponent().getItems().valueCollection().stream()
                .filter(i -> i instanceof InteractionFirstAidKit && i.getRoomId() == 0)
                .findFirst()
                .orElse(null);

        if (firstAidKit == null) {
            this.client.getHabbo().whisper(Emulator.getTexts()
                    .getValue("rp.ran_out")
                    .replace(":item", "first aid kits")
            );
            return;
        }

        this.client.getHabbo().getInventory().getItemsComponent().removeHabboItem(firstAidKit);
        Emulator.getThreading().run(new QueryDeleteHabboItem(firstAidKit.getId()));
        this.client.sendResponse(new InventoryRefreshComposer());

        targetHabbo.getRoleplayCharacter().addHealth(Emulator.getConfig().getInt("rp.first_aid_kit_health"));

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.first_aid_kit_used")
                .replace(":username", targetHabbo.getHabboInfo().getUsername())
        );
        targetHabbo.shout(Emulator.getTexts()
                .getValue("rp_.health_received")
                .replace(":healthNow", String.valueOf(targetHabbo.getRoleplayCharacter().getHealthNow()))
                .replace(":healthMax", String.valueOf(targetHabbo.getRoleplayCharacter().getHealthMax()))
        );
    }
}
