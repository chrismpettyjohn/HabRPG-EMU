package com.eu.habbo.messages.outgoing.roleplay.corp;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorp;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;
import java.util.Objects;

public class CorpMemberListByCorp extends MessageComposer {
    private final RoleplayCorp corp;

    public CorpMemberListByCorp(RoleplayCorp corp) {
        this.corp = corp;
    }
    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayCharacter> corpMembers = this.corp.getMembers().stream().filter(m -> Objects.equals(m.getType(), "user")).toList();
        this.response.init(Outgoing.CorpMemberListByCorpComposer);
        this.response.appendInt(this.corp.getId());
        this.response.appendInt(corpMembers.size());
        for (RoleplayCharacter character : corpMembers) {
            HabboInfo info = character.getHabbo() != null ? character.getHabbo().getHabboInfo() : Emulator.getGameEnvironment().getHabboManager().getHabboInfo(character.getUserId());
            this.response.appendString(character.getId() + ";" + character.getCorpRoleId() + ";" + info.getUsername() + ";" + info.getLook());
        }
        return this.response;
    }
}
