package com.eu.habbo.messages.incoming.roleplay.combat;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;

import java.util.List;

public class AttackEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        if (this.client.getHabbo().getHabboInfo().getCurrentRoom() == null) {
            return;
        }

        int x = this.packet.readInt();
        int y = this.packet.readInt();

        int damage = 4;
        int energy = 2;

        if (this.client.getHabbo().getRoleplayCharacter().getEnergyNow() < energy) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.out_of_energy"));
            return;
        }

        this.client.getHabbo().getRoleplayCharacter().depleteEnergy(energy);

        RoomTile tile = this.client.getHabbo().getRoomUnit().getRoom().getLayout().getTile((short) x, (short) y);

        if (tile == null) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("rp.not_possible"));
            return;
        }

        List<Habbo> habbosAtTile = this.client.getHabbo().getRoomUnit().getRoom().getHabbosAt(tile).stream().toList();

        if (!habbosAtTile.isEmpty()) {
            for (Habbo habbo : habbosAtTile) {
                habbo.getRoleplayCharacter().depleteHealth(damage);
                habbo.shout(
                        Emulator.getTexts().getValue("rp.damage_received")
                                .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
                                .replace(":healthNow", String.valueOf(habbo.getRoleplayCharacter().getHealthNow()))
                                .replace(":healthMax", String.valueOf(habbo.getRoleplayCharacter().getHealthMax()))
                );
            }
        }

        List<Bot> botsAtTile = this.client.getHabbo().getRoomUnit().getRoom().getBotsAt(tile).stream().toList();

        if (!botsAtTile.isEmpty()) {
            for (Bot bot : botsAtTile) {
                bot.getRoleplayCharacter().depleteHealth(damage);
                bot.shout(
                        Emulator.getTexts().getValue("rp.damage_received")
                                .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
                                .replace(":healthNow", String.valueOf(bot.getRoleplayCharacter().getHealthNow()))
                                .replace(":healthMax", String.valueOf(bot.getRoleplayCharacter().getHealthMax()))

                );
            }
        }

    }
}
