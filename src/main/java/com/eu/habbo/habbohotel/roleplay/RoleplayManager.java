package com.eu.habbo.habbohotel.roleplay;

import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpManager;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
import com.eu.habbo.habbohotel.roleplay.item.RoleplayItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleplayManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayManager.class);
    private static RoleplayManager instance;

    public static RoleplayManager getInstance() {
        if (instance == null) {
            synchronized (RoleplayManager.class) {
                if (instance == null) {
                    instance = new RoleplayManager();
                }
            }
        }
        return instance;
    }

    private final RoleplayItemManager roleplayItemManager;
    private final RoleplayCorpManager roleplayCorpManager;
    private final RoleplayCorpRoleManager roleplayCorpRoleManager;


    private RoleplayManager() {
        LOGGER.info("Roleplay -> loading");
        long millis = System.currentTimeMillis();
        this.roleplayItemManager = RoleplayItemManager.getInstance();
        this.roleplayCorpManager = RoleplayCorpManager.getInstance();
        this.roleplayCorpRoleManager = RoleplayCorpRoleManager.getInstance();
        LOGGER.info("Roleplay -> loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public RoleplayItemManager getRoleplayItemManager() {
        return this.roleplayItemManager;
    }

    public RoleplayCorpManager getRoleplayCorpManager() {
        return this.roleplayCorpManager;
    }
    public RoleplayCorpRoleManager getRoleplayCorpRoleManager() {
        return this.roleplayCorpRoleManager;
    }

    public void dispose() {
        this.roleplayItemManager.dispose();
        this.roleplayCorpManager.dispose();
        this.roleplayCorpRoleManager.dispose();
    }
}
