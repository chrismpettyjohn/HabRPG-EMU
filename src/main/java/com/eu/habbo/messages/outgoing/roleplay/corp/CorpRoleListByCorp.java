package com.eu.habbo.messages.outgoing.roleplay.corp;

import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorp;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;

public class CorpRoleListByCorp extends MessageComposer {
    private final RoleplayCorp corp;

    public CorpRoleListByCorp(RoleplayCorp corp) {
        this.corp = corp;
    }
    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayCorpRole> corpRoles = this.corp.getRoles();
        this.response.init(Outgoing.CorpRoleListAllComposer);
        this.response.appendInt(corpRoles.size());
        for (RoleplayCorpRole corpRole : corpRoles) {
            this.response.appendString(corpRole.getId() + ";" + corpRole.getCorpId() + ";" + corpRole.getName());
        }
        return this.response;
    }
}
