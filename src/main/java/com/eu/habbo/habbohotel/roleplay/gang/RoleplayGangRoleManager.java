package com.eu.habbo.habbohotel.roleplay.gang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RoleplayGangRoleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayGangRoleManager.class);
    private static RoleplayGangRoleManager instance;

    public static RoleplayGangRoleManager getInstance() {
        if (instance == null) {
            synchronized (RoleplayGangRoleManager.class) {
                if (instance == null) {
                    instance = new RoleplayGangRoleManager();
                }
            }
        }
        return instance;
    }

    private List<RoleplayGangRole> gangRoles;

    private RoleplayGangRoleManager() {
        this.gangRoles = RoleplayGangRoleRepository.getAll();
        LOGGER.info("Loaded " + this.gangRoles.size() + " roleplay gang roles");
    }

    public List<RoleplayGangRole> getGangRoles() {
        return gangRoles;
    }
    public void addItem(RoleplayGangRole gangRole) {
        this.gangRoles.add(gangRole);
    }

    public void updateItem(RoleplayGangRole gangRole) {
        this.gangRoles.set(gangRoles.indexOf(gangRole), gangRole);
    }

    public void removeItem(RoleplayGangRole gangRole) {
        RoleplayGangRoleRepository.deleteById(gangRole.getId());
        this.gangRoles.remove(gangRole);
    }

    public void dispose() {
        for (RoleplayGangRole gangRole : this.gangRoles) {
            gangRole.save();
        }
        this.gangRoles = null;
    }
}
