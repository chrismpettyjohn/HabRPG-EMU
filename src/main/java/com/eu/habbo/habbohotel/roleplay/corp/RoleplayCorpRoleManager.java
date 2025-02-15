package com.eu.habbo.habbohotel.roleplay.corp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RoleplayCorpRoleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayCorpRoleManager.class);
    private static RoleplayCorpRoleManager instance;

    public static RoleplayCorpRoleManager getInstance() {
        if (instance == null) {
            synchronized (RoleplayCorpRoleManager.class) {
                if (instance == null) {
                    instance = new RoleplayCorpRoleManager();
                }
            }
        }
        return instance;
    }

    private List<RoleplayCorpRole> corpRoles;

    private RoleplayCorpRoleManager() {
        this.corpRoles = RoleplayCorpRoleRepository.getAll();
        LOGGER.info("Loaded " + this.corpRoles.size() + " roleplay corp roles");
    }

    public List<RoleplayCorpRole> getCorpRoles() {
        return corpRoles;
    }
    public void addItem(RoleplayCorpRole corpRole) {
        this.corpRoles.add(corpRole);
    }

    public void updateItem(RoleplayCorpRole corpRole) {
        this.corpRoles.set(corpRoles.indexOf(corpRole), corpRole);
    }

    public void removeItem(RoleplayCorpRole corpRole) {
        this.corpRoles.remove(corpRole);
    }

    public void dispose() {
        for (RoleplayCorpRole corpRole : this.corpRoles) {
            corpRole.save();
        }
        this.corpRoles = null;
    }
}
