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
    
    public CallParamedicAction(Habbo habbo) {
        try {
            HabboInfo systemUser = Emulator.getGameEnvironment().getHabboManager().getHabboInfo(Emulator.getConfig().getInt("rp.system_user_id"));
            Bot baseBot = new Bot(0, "Paramedic", "[Working] Paramedic", Emulator.getConfig().getValue("rp.paramedic_figure"), HabboGender.M, systemUser.getId(), systemUser.getUsername());
            baseBot.setOwnerId(1); // System user

            ParamedicBot paramedicBot = new ParamedicBot(baseBot);

            RoomTile targetTile = habbo.getRoomUnit().getCurrentLocation();
            RoomTile spawnTile = habbo.getRoomUnit().getRoom().getLayout().getTileInFront(targetTile, habbo.getRoomUnit().getBodyRotation().getValue());

            if (spawnTile != null && spawnTile.isWalkable()) {
                paramedicBot.setRoomUnit(new RoomUnit());
                paramedicBot.getRoomUnit().setLocation(spawnTile);
                paramedicBot.getRoomUnit().setZ(spawnTile.getStackHeight());
                paramedicBot.getRoomUnit().setRotation(habbo.getRoomUnit().getBodyRotation());

                RoomTile nearestTile = habbo.getRoomUnit().getClosestAdjacentTile(habbo.getRoomUnit().getX(), habbo.getRoomUnit().getY(), true);

                Emulator.getGameEnvironment().getBotManager().placeBot(
                        paramedicBot,
                        habbo,
                        habbo.getHabboInfo().getCurrentRoom(),
                        nearestTile
                );

                List<Runnable> tasks = new ArrayList<>();
                tasks.add(() -> {
                    paramedicBot.heal(habbo);

                    Emulator.getThreading().run(() -> {
                        habbo.getRoomUnit().getRoom().removeBot(paramedicBot);
                    }, 5000);
                });

                // Walk to target if needed
                if (spawnTile.distance(targetTile) > 1) {
                    Emulator.getThreading().run(
                            new RoomUnitWalkToRoomUnit(
                                    paramedicBot.getRoomUnit(),
                                    habbo.getRoomUnit(),
                                    habbo.getRoomUnit().getRoom(),
                                    tasks,
                                    null,
                                    1
                            )
                    );
                } else {
                    tasks.forEach(Runnable::run);
                }
            }
        } catch (Exception e) {
            Emulator.getLogging().logErrorLine(e);
        }
    }
}
