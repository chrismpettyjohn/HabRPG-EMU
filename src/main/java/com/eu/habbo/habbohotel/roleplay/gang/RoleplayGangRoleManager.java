package com.eu.habbo.habbohotel.roleplay.gang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleplayGangRoleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayGangRoleManager.class);

    private static final class InstanceHolder {
        private static final RoleplayGangRoleManager instance = new RoleplayGangRoleManager();
    }

    public static RoleplayGangRoleManager getInstance() {
        return InstanceHolder.instance;
    }

    private Map<Integer, RoleplayGangRole> gangRolesMap;

    private RoleplayGangRoleManager() {
        List<RoleplayGangRole> gangRoles = RoleplayGangRoleRepository.getAll();
        this.gangRolesMap = new HashMap<>();
        for (RoleplayGangRole role : gangRoles) {
            this.gangRolesMap.put(role.getId(), role);
        }
        LOGGER.info("Loaded {} roleplay gang roles", this.gangRolesMap.size());
    }

    public RoleplayGangRole create(int gangId, int orderId, String name, String description, boolean canInvite, boolean canKick, boolean canPromote, boolean canDemote, boolean canEdit) {
        RoleplayGangRole gangRole = RoleplayGangRoleRepository.create(gangId, orderId, name, description, canInvite, canKick, canPromote, canDemote, canEdit);
        this.gangRolesMap.put(gangRole.getId(), gangRole);
        return gangRole;
    }

    public List<RoleplayGangRole> getAll() {
        return List.copyOf(gangRolesMap.values());
    }

    public RoleplayGangRole getById(int gangRoleId) {
        return this.gangRolesMap.get(gangRoleId);
    }

    public void updateByRole(RoleplayGangRole gangRole) {
        RoleplayGangRoleRepository.updateByRole(gangRole);
        this.gangRolesMap.put(gangRole.getId(), gangRole);
    }

    public void deleteByRole(RoleplayGangRole gangRole) {
        RoleplayGangRoleRepository.deleteById(gangRole.getId());
        this.gangRolesMap.remove(gangRole.getId());
    }

    public void dispose() {
        for (RoleplayGangRole gangRole : this.gangRolesMap.values()) {
            gangRole.save();
        }
        this.gangRolesMap.clear();
    }
}
