package com.eu.habbo.habbohotel.roleplay.character;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleplayCharacterItem {

    private final int id;
    private final int characterId;
    private final int itemId;

    public RoleplayCharacterItem(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.characterId = set.getInt("character_id");
        this.itemId = set.getInt("item_id");
    }

    public int getId() {
        return this.id;
    }

    public int getCharacterId() {
        return this.characterId;
    }

    public int getItemId() {
        return this.itemId;
    }

}
