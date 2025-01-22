package com.eu.habbo.messages.incoming.roleplay.items;

import com.eu.habbo.habbohotel.roleplay.item.RoleplayItem;
import com.eu.habbo.habbohotel.roleplay.item.RoleplayItemManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.items.RoleplayItemsDataEvent;

public class RoleplayItemUpdateOneEvent extends MessageHandler {
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

        item.setUniqueName(this.packet.readString());
        item.setDisplayName(this.packet.readString());
        item.setType(this.packet.readString());
        item.setEffect(this.packet.readString());
        item.setAccuracy(this.packet.readInt());
        item.setAmmoSize(this.packet.readInt());
        item.setAmmoCapacity(this.packet.readInt());
        item.setAttackMessage(this.packet.readString());
        item.setCooldownSeconds(this.packet.readInt());
        item.setEquipHandItem(this.packet.readInt());
        item.setEquipEffect(this.packet.readInt());
        item.setEquipMessage(this.packet.readString());
        item.setMaxDamage(this.packet.readInt());
        item.setMinDamage(this.packet.readInt());
        item.setRangeInTiles(this.packet.readInt());
        item.setReloadMessage(this.packet.readString());
        item.setReloadTime(this.packet.readInt());
        item.setUnequipMessage(this.packet.readString());
        item.setWeight(this.packet.readInt());
        item.setValue(this.packet.readInt());

        RoleplayItemManager.getInstance().updateItem(item);

        this.client.sendResponse(new RoleplayItemsDataEvent(item));

    }
}
