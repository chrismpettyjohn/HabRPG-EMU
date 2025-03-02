package com.eu.habbo.messages.outgoing.roleplay.gang;

import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRole;
import com.eu.habbo.habbohotel.roleplay.gang.RoleplayGangRoleManager;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class GangInviteComposer extends MessageComposer {
    private final Habbo habbo;

    public GangInviteComposer(Habbo habbo) {
        this.habbo = habbo;
    }

    @Override
    protected ServerMessage composeInternal() {
        RoleplayGangRole role = this.habbo.getRoleplayCharacter().getGangOfferGangRoleId() != null
                ? RoleplayGangRoleManager.getInstance().getGangRoles().stream().filter(r -> r.getId() == this.habbo.getRoleplayCharacter().getGangOfferGangRoleId()).findFirst().orElse(null) : null;

        this.response.init(Outgoing.GangInviteComposer);
        this.response.appendBoolean(role != null);
        this.response.appendInt(role != null ? role.getGangId() : 0);
        this.response.appendInt(role != null ? role.getId() : 0);

        return this.response;
    }
}
