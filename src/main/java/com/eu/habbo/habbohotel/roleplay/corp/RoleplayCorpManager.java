package com.eu.habbo.habbohotel.roleplay.corp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RoleplayCorpManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleplayCorpManager.class);
    private static RoleplayCorpManager instance;

    public static RoleplayCorpManager getInstance() {
        if (instance == null) {
            synchronized (RoleplayCorpManager.class) {
                if (instance == null) {
                    instance = new RoleplayCorpManager();
                }
            }
        }
        return instance;
    }

    private List<RoleplayCorp> corps;

    private RoleplayCorpManager() {
        this.corps = RoleplayCorpRepository.getAll();
        LOGGER.info("Loaded " + this.corps.size() + " roleplay corps");
    }

    public List<RoleplayCorp> getCorps() {
        return corps;
    }
    public void addItem(RoleplayCorp corp) {
        this.corps.add(corp);
    }

    public void updateItem(RoleplayCorp corp) {
        this.corps.set(corps.indexOf(corp), corp);
    }

    public void removeItem(RoleplayCorp corp) {
        this.corps.remove(corp);
    }

    public void dispose() {
        for (RoleplayCorp corp : this.corps) {
            corp.save();
        }
        this.corps = null;
    }
}
