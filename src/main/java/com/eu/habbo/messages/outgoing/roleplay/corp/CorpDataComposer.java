package com.eu.habbo.messages.outgoing.roleplay.corp;

import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorp;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class CorpDataComposer extends MessageComposer {
    private final RoleplayCorp corp;

    public CorpDataComposer(RoleplayCorp corp) {
        this.corp = corp;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.CorpDataComposer);
        this.response.appendInt(this.corp.getId());
        this.response.appendInt(this.corp.getRoomId());
        this.response.appendInt(this.corp.getUserId());
        this.response.appendString(this.corp.getName());
        this.response.appendString(this.corp.getDescription());
        this.response.appendString(this.corp.getBadgeCode());
        return this.response;
    }
}
