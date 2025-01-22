package com.eu.habbo.habbohotel.roleplay.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RoleplayItemManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayItemManager.class);
    private static RoleplayItemManager instance;

    public static RoleplayItemManager getInstance() {
        if (instance == null) {
            synchronized (RoleplayItemManager.class) {
                if (instance == null) {
                    instance = new RoleplayItemManager();
                }
            }
        }
        return instance;
    }

    private List<RoleplayItem> items;

    private RoleplayItemManager() {
        this.items = RoleplayItemRepository.loadAll();
        LOGGER.info("Loaded " + this.items.size() + " roleplay items");
    }

    public List<RoleplayItem> getItems() {
        return items;
    }

    public void addItem(RoleplayItem item) {
        this.items.add(item);
    }

    public void updateItem(RoleplayItem item) {
        this.items.set(items.indexOf(item), item);
    }

    public void removeItem(RoleplayItem item) {
        this.items.remove(item);
    }

    public void dispose() {
        RoleplayItemRepository.updateAll(this.items);
        this.items = null;
    }
}
