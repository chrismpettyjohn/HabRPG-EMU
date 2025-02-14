package com.eu.habbo.habbohotel.roleplay.character.actions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.pets.PetVocal;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacter;
import com.eu.habbo.habbohotel.rooms.RoomUnitStatus;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserStatusComposer;

import java.util.Timer;
import java.util.TimerTask;

public class CharacterDiedAction {

    public CharacterDiedAction(RoleplayCharacter character) {
        if (character.getBot() != null) {
            character.getBot().shout(Emulator.getTexts().getValue("rp.died"));
            character.getBot().getRoomUnit().setStatus(RoomUnitStatus.LAY, "0.5");
            character.getBot().getRoom().sendComposer(new RoomUserStatusComposer(character.getBot().getRoomUnit()).compose());
            startRevivalCheck(character);
            return;
        }

        if (character.getHabbo() != null) {
            character.getHabbo().shout(Emulator.getTexts().getValue("rp.died"));
            character.getHabbo().getRoomUnit().setStatus(RoomUnitStatus.LAY, "0.5");
            character.getHabbo().getRoomUnit().getRoom().sendComposer(new RoomUserStatusComposer(character.getHabbo().getRoomUnit()).compose());
            startRevivalCheck(character);
        }

        if (character.getPet() != null) {
            character.getPet().say(new PetVocal(Emulator.getTexts().getValue("rp.died")));
            character.getPet().getRoomUnit().setStatus(RoomUnitStatus.LAY, "0.5");
            character.getPet().getRoom().sendComposer(new RoomUserStatusComposer(character.getPet().getRoomUnit()).compose());
            startRevivalCheck(character);
        }
    }

    private void startRevivalCheck(RoleplayCharacter character) {
        Timer timer = new Timer();
        final int[] elapsed = {0};

        int waitUntilParamedicIsFree = Emulator.getConfig().getInt("rp.paramedic_wait_secs");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!character.isDead()) {
                    timer.cancel();
                } else if (elapsed[0] >= waitUntilParamedicIsFree) {
                    System.out.println("Character " + character.getHabbo().getHabboInfo().getUsername() + " has been dead for " + waitUntilParamedicIsFree + " seconds.");
                    timer.cancel();
                }
                elapsed[0] += 10;
            }
        }, 10000, 10000);
    }
}
