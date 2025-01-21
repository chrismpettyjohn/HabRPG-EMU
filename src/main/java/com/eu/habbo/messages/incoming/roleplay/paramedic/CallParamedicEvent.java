package com.eu.habbo.messages.incoming.roleplay.paramedic;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.bots.ParamedicBot;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.users.UserCreditsComposer;
import com.eu.habbo.threading.runnables.RoomUnitWalkToRoomUnit;

import java.util.ArrayList;
import java.util.List;

public class CallParamedicEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (!this.client.getHabbo().getRoleplayCharacter().isDead()) {
            return;
        }

        int paramedicCost = Emulator.getConfig().getInt("rp.paramedic_cost");
        int paramedicWaitSecs = Emulator.getConfig().getInt("rp.paramedic_wait_secs");

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
                .replace(":secs", String.valueOf(paramedicWaitSecs))
        );

        try {
            HabboInfo systemUser = Emulator.getGameEnvironment().getHabboManager().getHabboInfo(Emulator.getConfig().getInt("rp.system_user_id"));
            Bot baseBot = new Bot(0, "Paramedic", "[Working] Paramedic", Emulator.getConfig().getValue("rp.paramedic_figure"), HabboGender.M, systemUser.getId(), systemUser.getUsername());
            baseBot.setOwnerId(1); // System user

            ParamedicBot paramedicBot = new ParamedicBot(baseBot);

            RoomTile targetTile = this.client.getHabbo().getRoomUnit().getCurrentLocation();
            RoomTile spawnTile = this.client.getHabbo().getRoomUnit().getRoom().getLayout().getTileInFront(targetTile, this.client.getHabbo().getRoomUnit().getBodyRotation().getValue());

            if (spawnTile != null && spawnTile.isWalkable()) {
                paramedicBot.setRoomUnit(new RoomUnit());
                paramedicBot.getRoomUnit().setLocation(spawnTile);
                paramedicBot.getRoomUnit().setZ(spawnTile.getStackHeight());
                paramedicBot.getRoomUnit().setRotation(this.client.getHabbo().getRoomUnit().getBodyRotation());

                RoomTile nearestTile = this.client.getHabbo().getRoomUnit().getClosestAdjacentTile(this.client.getHabbo().getRoomUnit().getX(), this.client.getHabbo().getRoomUnit().getY(), true);

                Emulator.getGameEnvironment().getBotManager().placeBot(
                        paramedicBot,
                        this.client.getHabbo(),
                        this.client.getHabbo().getHabboInfo().getCurrentRoom(),
                        nearestTile
                );

                List<Runnable> tasks = new ArrayList<>();
                tasks.add(() -> {
                    paramedicBot.heal(this.client.getHabbo());

                    Emulator.getThreading().run(() -> {
                        this.client.getHabbo().getRoomUnit().getRoom().removeBot(paramedicBot);
                    }, 5000);
                });

                // Walk to target if needed
                if (spawnTile.distance(targetTile) > 1) {
                    Emulator.getThreading().run(
                            new RoomUnitWalkToRoomUnit(
                                    paramedicBot.getRoomUnit(),
                                    this.client.getHabbo().getRoomUnit(),
                                    this.client.getHabbo().getRoomUnit().getRoom(),
                                    tasks,
                                    null,
                                    1
                            )
                    );
                } else {
                    // Execute tasks immediately if already close enough
                    tasks.forEach(Runnable::run);
                }
            }
        } catch (Exception e) {
            Emulator.getLogging().logErrorLine(e);
        }
    }
}
