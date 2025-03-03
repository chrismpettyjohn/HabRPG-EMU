package com.eu.habbo.habbohotel.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacterManager;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacterRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RoleplayGang {
    private final int id;
    private int roomId;
    private int userId;
    private String name;
    private String description;
    private String badgeCode;
    private int createdAt;
    private int updatedAt;

    public RoleplayGang(ResultSet set) throws SQLException {
        this.id = set.getInt("id");
        this.roomId = set.getInt("rooms_id");
        this.userId = set.getInt("users_id");
        this.name = set.getString("name");
        this.description = set.getString("description");
        this.badgeCode = set.getString("badge_code");
        this.createdAt = set.getInt("created_at");
        this.updatedAt = set.getInt("updated_at");
    }

    public int getId() {
        return this.id;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int characterId) {
        this.userId = characterId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBadgeCode() {
        return this.badgeCode;
    }

    public void setBadgeCode(String badgeCode) {
        this.badgeCode = badgeCode;
    }

    public int getCreatedAt() {
        return this.createdAt;
    }

    public int getUpdatedAt() {
        return this.updatedAt;
    }

    public List<RoleplayGangRole> getRoles() {
        return RoleplayGangRoleManager.getInstance().getGangRoles().stream().filter(roleplayGangRole -> roleplayGangRole.getGangId() == this.id).collect(Collectors.toList());
    }
    public List<RoleplayCharacter> getMembers() {
        return RoleplayCharacterManager.getInstance().getCharacters().stream().filter(roleplayCharacter -> roleplayCharacter.getGangId() == this.id).collect(Collectors.toList());
    }

    public void save() {
        RoleplayGangRepository.updateByGang(this);

        for (RoleplayGangRole role : this.getRoles()) {
            role.save();
        }
    }

    public void delete() {
        for (RoleplayGangRole role :  this.getRoles()) {
            role.delete();
        }
        RoleplayGangRepository.deleteById(this.id);
    }
}
