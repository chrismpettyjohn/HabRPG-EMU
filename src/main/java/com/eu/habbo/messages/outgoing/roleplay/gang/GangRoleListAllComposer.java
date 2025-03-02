package com.eu.habbo.messages.outgoing.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRoleManager;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;

public class GangRoleListAllComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayGangRole> gangRoles = RoleplayGangRoleManager.getInstance().getGangRoles();
        this.response.init(Outgoing.GangRoleListAllComposer);
        this.response.appendInt(gangRoles.size());
        for (RoleplayGangRole gangRole : gangRoles) {
            this.response.appendString(gangRole.getId() + ";" + gangRole.getGangId() + ";" + gangRole.getName());
        }
        return this.response;
    }
}
