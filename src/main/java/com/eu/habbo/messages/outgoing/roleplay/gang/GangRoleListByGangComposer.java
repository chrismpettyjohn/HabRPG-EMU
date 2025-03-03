package com.eu.habbo.messages.outgoing.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;

public class GangRoleListByGangComposer extends MessageComposer {
    private final RoleplayGang gang;

    public GangRoleListByGangComposer(RoleplayGang gang) {
        this.gang = gang;
    }

    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayGangRole> gangRoles = this.gang.getRoles();
        this.response.init(Outgoing.GangRoleListByGangComposer);
        this.response.appendInt(gangRoles.size());
        for (RoleplayGangRole gangRole : gangRoles) {
            this.response.appendString(gangRole.getId() + ";" + gangRole.getGangId() + ";" + gangRole.getOrderId()  + ";" + gangRole.getName());
        }
        return this.response;
    }
}
