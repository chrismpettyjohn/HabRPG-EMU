package com.eu.habbo.messages.outgoing.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGang;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangManager;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.List;

public class GangListAllComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayGang> gangs = RoleplayGangManager.getInstance().getGangs();
        this.response.init(Outgoing.GangListAllComposer);
        this.response.appendInt(gangs.size());
        for (RoleplayGang gang : gangs) {
            this.response.appendString(gang.getId() + ";" + gang.getName() + ";" + gang.getBadgeCode());
        }
        return this.response;
    }
}
