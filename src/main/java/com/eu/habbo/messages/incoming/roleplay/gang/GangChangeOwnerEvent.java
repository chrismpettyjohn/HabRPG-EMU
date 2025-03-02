package com.eu.habbo.messages.incoming.roleplay.gang;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;

public class GangChangeOwnerEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (this.client.getHabbo().getRoleplayCharacter().getGang() == null) {
            return;
        }

        if (this.client.getHabbo().getRoleplayCharacter().getGang().getUserId() != this.client.getHabbo().getHabboInfo().getId()) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.not_allowed"));
            return;
        }

        Habbo targetHabbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(this.packet.readInt());

        if (targetHabbo == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("generic.user_not_found"));
            return;
        }

        if (targetHabbo.getRoleplayCharacter().getGang() != this.client.getHabbo().getRoleplayCharacter().getGang()) {
            this.client.getHabbo().whisper(Emulator.getTexts()
                    .getValue("rp.gang_change_owner.must_be_same_gang")
                    .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
            );
            return;
        }


        this.client.getHabbo().getRoleplayCharacter().getGang().setUserId(targetHabbo.getHabboInfo().getId());
        this.client.getHabbo().getRoleplayCharacter().getGang().save();

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.gang_change_owner_given_up")
                .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
        );
        targetHabbo.shout(Emulator.getTexts()
                .getValue("rp.gang_change_owner_new_owner")
                .replace(":gangName", this.client.getHabbo().getRoleplayCharacter().getGang().getName())
        );
    }
}