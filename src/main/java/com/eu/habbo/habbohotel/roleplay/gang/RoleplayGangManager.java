package com.eu.habbo.habbohotel.roleplay.gang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RoleplayGangManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayGangManager.class);
    private static RoleplayGangManager instance;

    public static RoleplayGangManager getInstance() {
        if (instance == null) {
            synchronized (RoleplayGangManager.class) {
                if (instance == null) {
                    instance = new RoleplayGangManager();
                }
            }
        }
        return instance;
    }

    private List<RoleplayGang> gangs;

    private RoleplayGangManager() {
        this.gangs = RoleplayGangRepository.getAll();
        LOGGER.info("Loaded " + this.gangs.size() + " roleplay gangs");
    }

    public List<RoleplayGang> getGangs() {
        return gangs;
    }
    public void addItem(RoleplayGang gang) {
        this.gangs.add(gang);
    }

    public void updateItem(RoleplayGang gang) {
        this.gangs.set(gangs.indexOf(gang), gang);
    }

    public void removeItem(RoleplayGang gang) {
        RoleplayGangRepository.deleteById(gang.getId());
        this.gangs.remove(gang);
    }

    public void dispose() {
        for (RoleplayGang gang : this.gangs) {
            gang.save();
        }
        this.gangs = null;
    }
}
