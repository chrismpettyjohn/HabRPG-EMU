package com.eu.habbo.messages.outgoing.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;

public class GangMemberListByGangComposer extends MessageComposer {
    private final RoleplayGang gang;

    public GangMemberListByGangComposer(RoleplayGang gang) {
        this.gang = gang;
    }

    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayCharacter> gangMembers = this.gang.getMembers();
        this.response.init(Outgoing.GangMemberListByGangComposer);
        this.response.appendInt(gangMembers.size());
        for (RoleplayCharacter character : gangMembers) {
            this.response.appendString(character.getId() + ";" + character.getGangRoleId() + ";" + character.getHabbo().getHabboInfo().getUsername() + ";" + character.getHabbo().getHabboInfo().getLook());
        }
        return this.response;
    }
}
