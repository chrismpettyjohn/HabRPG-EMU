package com.eu.habbo.messages.outgoing.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class GangRoleDataComposer extends MessageComposer {
    private final RoleplayGangRole gangRole;

    public GangRoleDataComposer(RoleplayGangRole gangRole) {
        this.gangRole = gangRole;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.GangRoleDataComposer);
        this.response.appendInt(this.gangRole.getId());
        this.response.appendInt(this.gangRole.getGangId());
        this.response.appendInt(this.gangRole.getOrderId());
        this.response.appendString(this.gangRole.getName());
        this.response.appendBoolean(this.gangRole.canInvite());
        this.response.appendBoolean(this.gangRole.canKick());
        this.response.appendBoolean(this.gangRole.canPromote());
        this.response.appendBoolean(this.gangRole.canDemote());
        return this.response;
    }
}
