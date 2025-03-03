package com.eu.habbo.habbohotel.roleplay;

import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacterManager;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpManager;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangManager;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRoleManager;
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

    private final RoleplayCharacterManager roleplayCharacterManager;
    private final RoleplayItemManager roleplayItemManager;
    private final RoleplayCorpManager roleplayCorpManager;
    private final RoleplayCorpRoleManager roleplayCorpRoleManager;
    private final RoleplayGangManager roleplayGangManager;
    private final RoleplayGangRoleManager rOleplayGangRoleManager;


    private RoleplayManager() {
        LOGGER.info("Roleplay -> loading");
        long millis = System.currentTimeMillis();
        this.roleplayCharacterManager = RoleplayCharacterManager.getInstance();
        this.roleplayItemManager = RoleplayItemManager.getInstance();
        this.roleplayCorpManager = RoleplayCorpManager.getInstance();
        this.roleplayCorpRoleManager = RoleplayCorpRoleManager.getInstance();
        this.roleplayGangManager = RoleplayGangManager.getInstance();
        this.rOleplayGangRoleManager = RoleplayGangRoleManager.getInstance();
        LOGGER.info("Roleplay -> loaded! (" + (System.currentTimeMillis() - millis) + " MS)");
    }

    public void dispose() {
        this.roleplayCharacterManager.dispose();
        this.roleplayItemManager.dispose();
        this.roleplayCorpManager.dispose();
        this.roleplayCorpRoleManager.dispose();
        this.roleplayGangManager.dispose();
        this.rOleplayGangRoleManager.dispose();
    }
}
