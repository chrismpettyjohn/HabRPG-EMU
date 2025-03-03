package com.eu.habbo.messages.incoming.roleplay.corp;

import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorp;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpManager;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.roleplay.corp.CorpRoleListByCorp;

public class CorpRoleListByCorpEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        int corpId = this.packet.readInt();
        RoleplayCorp corp = RoleplayCorpManager.getInstance().getCorps().stream().filter(c -> c.getId() == corpId).findFirst().orElse(null);

        if (corp == null) {
            return;
        }

        this.client.sendResponse(new CorpRoleListByCorp(corp));
    }
}
