package com.eu.habbo.messages.outgoing.roleplay.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;
import java.util.Objects;

public class GangMemberListByGangComposer extends MessageComposer {
    private final RoleplayGang gang;

    public GangMemberListByGangComposer(RoleplayGang gang) {
        this.gang = gang;
    }

    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayCharacter> gangMembers = this.gang.getMembers().stream().filter(m -> Objects.equals(m.getType(), "user")).toList();
        this.response.init(Outgoing.GangMemberListByGangComposer);
        this.response.appendInt(this.gang.getId());
        this.response.appendInt(gangMembers.size());
        for (RoleplayCharacter character : gangMembers) {
            HabboInfo info = character.getHabbo() != null ? character.getHabbo().getHabboInfo() : Emulator.getGameEnvironment().getHabboManager().getHabboInfo(character.getUserId());
            this.response.appendString(character.getId() + ";" + character.getGangRoleId() + ";" +info.getUsername() + ";" + info.getLook());
        }
        return this.response;
    }
}
