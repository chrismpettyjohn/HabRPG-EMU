package com.eu.habbo.messages.outgoing.roleplay.corp;

import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;

public class CorpRoleListAllComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayCorpRole> corpRoles = RoleplayCorpRoleManager.getInstance().getCorpRoles();
        this.response.init(Outgoing.CorpRoleListAllComposer);
        this.response.appendInt(corpRoles.size());
        for (RoleplayCorpRole corpRole : corpRoles) {
            this.response.appendString(corpRole.getId() + ";" + corpRole.getName());
        }
        return this.response;
    }
}
