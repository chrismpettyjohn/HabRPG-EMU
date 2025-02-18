package com.eu.habbo.habbohotel.roleplay.character.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.pets.PetVocal;
import com.eu.habbo.habbohotel.roleplay.RoleplayHelper;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.habbohotel.roleplay.paramedic.actions.CallParamedicAction;
import com.eu.habbo.habbohotel.rooms.RoomUnitStatus;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.roleplay.character.CharacterDataComposer;
import com.eu.habbo.messages.outgoing.roleplay.notification.UserDiedNotification;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserStatusComposer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CharacterDiedAction {

    public CharacterDiedAction(RoleplayCharacter character, RoleplayCharacter killedBy) {
        if (character.getBot() != null) {
            character.getBot().shout(Emulator.getTexts().getValue("rp.died"));
            character.getBot().getRoomUnit().setStatus(RoomUnitStatus.LAY, "0.5");
            character.getBot().getRoom().sendComposer(new RoomUserStatusComposer(character.getBot().getRoomUnit()).compose());
            return;
        }

        if (character.getHabbo() != null) {
            character.getHabbo().shout(Emulator.getTexts().getValue("rp.died"));
            character.getHabbo().getRoomUnit().setStatus(RoomUnitStatus.LAY, "0.5");
            character.getHabbo().getRoomUnit().getRoom().sendComposer(new RoomUserStatusComposer(character.getHabbo().getRoomUnit()).compose());


            if (killedBy.getHabbo() != null) {
                RoleplayHelper.notifyOnline(new UserDiedNotification(character.getHabbo(), killedBy.getHabbo()));
            }

            startRevivalCheck(character);
        }

        if (character.getPet() != null) {
            character.getPet().say(new PetVocal(Emulator.getTexts().getValue("rp.died")));
            character.getPet().getRoomUnit().setStatus(RoomUnitStatus.LAY, "0.5");
            character.getPet().getRoom().sendComposer(new RoomUserStatusComposer(character.getPet().getRoomUnit()).compose());
        }
    }

    private void startRevivalCheck(RoleplayCharacter character) {
        if (character.getHabbo() == null) {
            return;
        }

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        final int[] elapsed = {0};

        int waitUntilParamedicIsFree = Emulator.getConfig().getInt("rp.paramedic_wait_secs");

        character.getHabbo().whisper(Emulator.getTexts()
                .getValue("rp.paramedic_free_dispatch")
                .replace(":secs", String.valueOf(waitUntilParamedicIsFree - elapsed[0]))
        );

        scheduler.scheduleAtFixedRate(() -> {
            elapsed[0] += 10;

            if (!character.isDead()) {
                scheduler.shutdown();
                return;
            }

            if (elapsed[0] >= waitUntilParamedicIsFree) {
                new CallParamedicAction(character.getHabbo()).execute();
                scheduler.shutdown();
                return;
            }
            character.getHabbo().whisper(Emulator.getTexts()
                    .getValue("rp.paramedic_free_wait")
                    .replace(":secs", String.valueOf(waitUntilParamedicIsFree - elapsed[0]))
            );
        }, 10, 10, TimeUnit.SECONDS);
    }

}
