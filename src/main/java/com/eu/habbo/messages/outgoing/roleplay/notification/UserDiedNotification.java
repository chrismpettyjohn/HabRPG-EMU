package com.eu.habbo.messages.outgoing.roleplay.notification;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class UserDiedNotification extends MessageComposer {
    private final Habbo victim;
    private final Habbo attacker;

    public UserDiedNotification(Habbo victim, Habbo attacker) {
        this.victim = victim;
        this.attacker = attacker;
    }

    @Override
    protected ServerMessage composeInternal() {
        this.response.init(Outgoing.UsedDiedComposer);
        this.response.appendInt(this.victim.getHabboInfo().getId());
        this.response.appendString(this.attacker.getHabboInfo().getUsername());
        this.response.appendString(this.attacker.getHabboInfo().getLook());
        this.response.appendInt(this.attacker.getHabboInfo().getId());
        this.response.appendString(this.attacker.getHabboInfo().getUsername());
        this.response.appendString(this.attacker.getHabboInfo().getLook());
        return this.response;
    }

}