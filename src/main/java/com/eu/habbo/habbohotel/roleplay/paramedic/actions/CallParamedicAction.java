package com.eu.habbo.habbohotel.roleplay.paramedic.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.bots.ParamedicBot;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.threading.runnables.RoomUnitWalkToRoomUnit;

import java.util.ArrayList;
import java.util.List;

public class CallParamedicAction {
    private final Habbo habbo;
    
    public CallParamedicAction(Habbo habbo) {
        this.habbo = habbo;
    }
    
    public void execute() {
        try {
            HabboInfo systemUser = Emulator.getGameEnvironment().getHabboManager().getHabboInfo(Emulator.getConfig().getInt("rp.system_user_id"));
            Bot baseBot = new Bot(0, "Paramedic", "[Working] Paramedic", Emulator.getConfig().getValue("rp.paramedic_figure"), HabboGender.M, systemUser.getId(), systemUser.getUsername());
            baseBot.setOwnerId(systemUser.getId()); // System user

            ParamedicBot paramedicBot = new ParamedicBot(baseBot);

            RoomTile targetTile = this.habbo.getRoomUnit().getCurrentLocation();
            RoomTile spawnTile = this.habbo.getRoomUnit().getRoom().getLayout().getTileInFront(targetTile, this.habbo.getRoomUnit().getBodyRotation().getValue());

            if (spawnTile == null || spawnTile.isWalkable()) {
                this.habbo.whisper(Emulator.getTexts().getValue("rp.paramedic_cant_reach_you"));
                this.habbo.getRoleplayCharacter().addHealth(5);
                return;
            }

            paramedicBot.setRoomUnit(new RoomUnit());
            paramedicBot.getRoomUnit().setLocation(spawnTile);
            paramedicBot.getRoomUnit().setZ(spawnTile.getStackHeight());
            paramedicBot.getRoomUnit().setRotation(habbo.getRoomUnit().getBodyRotation());

            RoomTile nearestTile = this.habbo.getRoomUnit().getClosestAdjacentTile(this.habbo.getRoomUnit().getX(), this.habbo.getRoomUnit().getY(), true);

            Emulator.getGameEnvironment().getBotManager().placeBot(
                    paramedicBot,
                    this.habbo,
                    this.habbo.getHabboInfo().getCurrentRoom(),
                    nearestTile
            );

            List<Runnable> tasks = new ArrayList<>();
            tasks.add(() -> {
                paramedicBot.heal(this.habbo);

                Emulator.getThreading().run(() -> {
                    this.habbo.getRoomUnit().getRoom().removeBot(paramedicBot);
                }, 5000);
            });

            // Walk to target if needed
            if (spawnTile.distance(targetTile) > 1) {
                Emulator.getThreading().run(
                        new RoomUnitWalkToRoomUnit(
                                paramedicBot.getRoomUnit(),
                                this.habbo.getRoomUnit(),
                                this.habbo.getRoomUnit().getRoom(),
                                tasks,
                                null,
                                1
                        )
                );
            } else {
                tasks.forEach(Runnable::run);
            }
        } catch (Exception e) {
            Emulator.getLogging().logErrorLine(e);
        }
    }
}
