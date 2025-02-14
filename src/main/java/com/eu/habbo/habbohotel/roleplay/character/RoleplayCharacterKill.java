package com.eu.habbo.habbohotel.roleplay.character;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacterKill {

    private final int id;
    private final int attackerCharacterId;
    private final int victimCharacterId;

    public RoleplayCharacterKill(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.attackerCharacterId = set.getInt("attacker_character_id");
        this.victimCharacterId = set.getInt("victim_character_id");
    }

    public int getId() {
        return this.id;
    }

    public int getAttackerCharacterId() {
        return this.attackerCharacterId;
    }

    public int getVictimCharacterId() {
        return this.victimCharacterId;
    }

}
