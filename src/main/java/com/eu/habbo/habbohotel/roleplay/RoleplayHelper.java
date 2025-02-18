package com.eu.habbo.habbohotel.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.MessageComposer;

public class RoleplayHelper {

    public static void notifyRoom(Room room, MessageComposer message) {
        for (Habbo habbo : room.getHabbos()) {
            habbo.getClient().sendResponse(message);
        }
    }
    public static void notifyOnline(MessageComposer message) {
        for (Habbo habbo : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values()) {
            habbo.getClient().sendResponse(message);
        }
    }
}
