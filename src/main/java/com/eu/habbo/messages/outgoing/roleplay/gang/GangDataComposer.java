package com.eu.habbo.messages.outgoing.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class GangDataComposer extends MessageComposer {
    private final RoleplayGang gang;

    public GangDataComposer(RoleplayGang gang) {
        this.gang = gang;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.GangDataComposer);
        this.response.appendInt(this.gang.getId());
        this.response.appendInt(this.gang.getRoomId());
        this.response.appendInt(this.gang.getUserId());
        this.response.appendString(this.gang.getName());
        this.response.appendString(this.gang.getDescription());
        this.response.appendString(this.gang.getBadgeCode());
        return this.response;
    }
}
