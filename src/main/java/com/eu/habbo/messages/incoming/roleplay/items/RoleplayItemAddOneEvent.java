package com.eu.habbo.messages.incoming.roleplay.items;

import com.eu.habbo.habbohotel.roleplay.item.RoleplayItem;
import com.eu.habbo.habbohotel.roleplay.item.RoleplayItemManager;
import com.eu.habbo.habbohotel.roleplay.item.RoleplayItemRepository;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.items.RoleplayItemCreatedEvent;

public class RoleplayItemAddOneEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().hasPermission("acc_manage_roleplay")) {
            return;
        }

        String uniqueName = this.packet.readString();
        String displayName = this.packet.readString();
        String type = this.packet.readString();
        String effect = this.packet.readString();
        int accuracy = this.packet.readInt();
        int ammoSize = this.packet.readInt();
        int ammoCapacity = this.packet.readInt();
        String attackMessage = this.packet.readString();
        int cooldownSeconds = this.packet.readInt();
        int equipHandItemId = this.packet.readInt();
        int equipEffect = this.packet.readInt();
        String equipMessage = this.packet.readString();
        int maxDamage = this.packet.readInt();
        int minDamage = this.packet.readInt();
        int rangeInTiles = this.packet.readInt();
        String reloadMessage = this.packet.readString();
        int reloadTime = this.packet.readInt();
        String unequipMessage = this.packet.readString();
        int weight = this.packet.readInt();
        int value = this.packet.readInt();

        RoleplayItem item = RoleplayItemRepository.createOne(uniqueName, displayName, type, effect, accuracy, ammoSize, ammoCapacity, attackMessage, cooldownSeconds, equipHandItemId, equipEffect, equipMessage, maxDamage, minDamage, rangeInTiles, reloadMessage, reloadTime, unequipMessage, weight, value);
        RoleplayItemManager.getInstance().addItem(item);

        this.client.sendResponse(new RoleplayItemCreatedEvent(item));
    }
}