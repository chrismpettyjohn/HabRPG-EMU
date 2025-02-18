package com.eu.habbo.messages.outgoing.roleplay.corp;

import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class CorpRoleDataComposer  extends MessageComposer {
    private final RoleplayCorpRole corpRole;

    public CorpRoleDataComposer(RoleplayCorpRole corpRole) {
        this.corpRole = corpRole;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.CorpRoleDataComposer);
        this.response.appendInt(this.corpRole.getId());
        this.response.appendInt(this.corpRole.getCorpId());
        this.response.appendInt(this.corpRole.getOrderId());
        this.response.appendString(this.corpRole.getName());
        this.response.appendBoolean(this.corpRole.canHire());
        this.response.appendBoolean(this.corpRole.canFire());
        this.response.appendBoolean(this.corpRole.canPromote());
        this.response.appendBoolean(this.corpRole.canDemote());
        return this.response;
    }
}
