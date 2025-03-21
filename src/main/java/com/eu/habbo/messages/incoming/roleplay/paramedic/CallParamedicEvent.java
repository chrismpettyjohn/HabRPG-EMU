package com.eu.habbo.messages.incoming.roleplay.paramedic;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.roleplay.paramedic.actions.CallParamedicAction;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.users.UserCreditsComposer;

public class CallParamedicEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().getRoleplayCharacter().isDead()) {
            return;
        }

        int paramedicCost = Emulator.getConfig().getInt("rp.paramedic_cost");

        if (this.client.getHabbo().getHabboInfo().getCredits() < paramedicCost) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.cant_afford"));
            return;
        }

        this.client.getHabbo().getHabboInfo().depleteCredits(paramedicCost);
        this.client.sendResponse(new UserCreditsComposer(this.client.getHabbo()));

        this.client.getHabbo().shout(Emulator.getTexts()
                .getValue("rp.payment_made")
                .replace(":credits", String.valueOf(paramedicCost))
                .replace(":service", "a paramedic")
        );

        this.client.getHabbo().whisper(Emulator.getTexts()
                .getValue("rp.paramedic_called")
                .replace(":secs", String.valueOf(1))
        );

        new CallParamedicAction(this.client.getHabbo()).execute();
    }
}
