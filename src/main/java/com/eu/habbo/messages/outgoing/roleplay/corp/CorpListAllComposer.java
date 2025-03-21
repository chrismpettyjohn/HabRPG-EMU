package com.eu.habbo.messages.outgoing.roleplay.corp;

import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorp;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpManager;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import java.util.List;

public class CorpListAllComposer extends MessageComposer {
    @Override
    protected ServerMessage composeInternal() {
        List<RoleplayCorp> corps = RoleplayCorpManager.getInstance().getCorps();
        this.response.init(Outgoing.CorpListAllComposer);
        this.response.appendInt(corps.size());
        for (RoleplayCorp corp : corps) {
            this.response.appendString(corp.getId() + ";" + corp.getName() + ";" + corp.getBadgeCode());
        }
        return this.response;
    }
}
