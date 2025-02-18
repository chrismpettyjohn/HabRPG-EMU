package com.eu.habbo.messages.incoming.roleplay.combat;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.pets.Pet;
import com.eu.habbo.habbohotel.pets.PetVocal;
import com.eu.habbo.habbohotel.roleplay.character.RoleplayCharacterKillsRepository;
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

        if (Math.sqrt(Math.pow(this.client.getHabbo().getRoomUnit().getCurrentLocation().x - tile.x, 2) + Math.pow(this.client.getHabbo().getRoomUnit().getCurrentLocation().y - tile.y, 2)) > 1.5) {
            this.client.getHabbo().whisper(Emulator.getTexts().getValue("roleplay.attack_too_far_away"));
            return;
        }

        List<Habbo> habbosAtTile = this.client.getHabbo().getRoomUnit().getRoom().getHabbosAt(tile).stream().toList();

        if (!habbosAtTile.isEmpty()) {
            for (Habbo habbo : habbosAtTile) {
                if (habbo.getRoleplayCharacter().isDead()) {
                    this.client.getHabbo().whisper( Emulator.getTexts()
                            .getValue("rp.cant_attack")
                            .replace(":username", habbo.getHabboInfo().getUsername())
                    );
                    continue;
                }
                habbo.getRoleplayCharacter().depleteHealth(damage, this.client.getHabbo().getRoleplayCharacter());
                if (habbo.getRoleplayCharacter().isDead()) {
                    RoleplayCharacterKillsRepository.create(this.client.getHabbo().getRoleplayCharacter(), habbo.getRoleplayCharacter());
                }
                if (!habbo.getRoleplayCharacter().isDead()) {
                    habbo.shout(
                            Emulator.getTexts().getValue("rp.damage_received")
                                    .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
                                    .replace(":healthNow", String.valueOf(habbo.getRoleplayCharacter().getHealthNow()))
                                    .replace(":healthMax", String.valueOf(habbo.getRoleplayCharacter().getHealthMax()))
                    );
                }
            }
        }

        List<Bot> botsAtTile = this.client.getHabbo().getRoomUnit().getRoom().getBotsAt(tile).stream().toList();

        if (!botsAtTile.isEmpty()) {
            for (Bot bot : botsAtTile) {
                if (bot.getRoleplayCharacter().isDead()) {
                    this.client.getHabbo().whisper( Emulator.getTexts()
                            .getValue("rp.cant_attack")
                            .replace(":username", bot.getName())
                    );
                    continue;
                }
                bot.getRoleplayCharacter().depleteHealth(damage, this.client.getHabbo().getRoleplayCharacter());
                if (bot.getRoleplayCharacter().isDead()) {
                    RoleplayCharacterKillsRepository.create(this.client.getHabbo().getRoleplayCharacter(), bot.getRoleplayCharacter());
                }
                if (!bot.getRoleplayCharacter().isDead()) {
                    bot.shout(
                            Emulator.getTexts().getValue("rp.damage_received")
                                    .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
                                    .replace(":healthNow", String.valueOf(bot.getRoleplayCharacter().getHealthNow()))
                                    .replace(":healthMax", String.valueOf(bot.getRoleplayCharacter().getHealthMax()))
                    );
                }
            }
        }

        List<Pet> petsAtTile = this.client.getHabbo().getRoomUnit().getRoom().getPetsAt(tile).stream().toList();

        if (!petsAtTile.isEmpty()) {
            for (Pet pet : petsAtTile) {
                if (pet.getRoleplayCharacter().isDead()) {
                    RoleplayCharacterKillsRepository.create(this.client.getHabbo().getRoleplayCharacter(), pet.getRoleplayCharacter());
                }
                if (pet.getRoleplayCharacter().isDead()) {
                    this.client.getHabbo().whisper( Emulator.getTexts()
                            .getValue("rp.cant_attack")
                            .replace(":username", pet.getName())
                    );
                    continue;
                }
                pet.getRoleplayCharacter().depleteHealth(damage, this.client.getHabbo().getRoleplayCharacter());
                if (!pet.getRoleplayCharacter().isDead()) {
                    pet.say(new PetVocal(
                            Emulator.getTexts().getValue("rp.damage_received")
                                    .replace(":username", this.client.getHabbo().getHabboInfo().getUsername())
                                    .replace(":healthNow", String.valueOf(pet.getRoleplayCharacter().getHealthNow()))
                                    .replace(":healthMax", String.valueOf(pet.getRoleplayCharacter().getHealthMax()))
                            )
                    );
                }
            }
        }

    }
}
