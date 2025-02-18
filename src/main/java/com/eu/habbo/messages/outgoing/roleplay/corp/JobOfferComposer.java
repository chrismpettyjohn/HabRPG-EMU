package com.eu.habbo.messages.outgoing.roleplay.corp;

import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRole;
import com.eu.habbo.habbohotel.roleplay.corp.RoleplayCorpRoleManager;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class JobOfferComposer extends MessageComposer {
    private final Habbo habbo;

    public JobOfferComposer(Habbo habbo) {
        this.habbo = habbo;
    }

    @Override
    protected ServerMessage composeInternal() {
        RoleplayCorpRole role = this.habbo.getRoleplayCharacter().getJobOfferCorpRoleId() != null
                ? RoleplayCorpRoleManager.getInstance().getCorpRoles().stream().filter(r -> r.getId() == this.habbo.getRoleplayCharacter().getJobOfferCorpRoleId()).findFirst().orElse(null) : null;

        this.response.init(Outgoing.JobOfferComposer);
        this.response.appendBoolean(this.habbo.getRoleplayCharacter().getJobOfferCorpRoleId() != null);
        this.response.appendInt(role != null ? role.getCorpId() : 0);
        this.response.appendInt(role != null ? role.getId() : 0);

        return this.response;
    }
}
