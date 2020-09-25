package com.eu.habbo.messages.outgoing.rooms.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.interactions.InteractionRoller;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomUnitOnRollerComposer extends MessageComposer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomUnitOnRollerComposer.class);
    private final RoomUnit roomUnit;
    private final HabboItem roller;
    private final RoomTile oldLocation;
    private final double oldZ;
    private final RoomTile newLocation;
    private final double newZ;
    private final Room room;
    private int x;
    private int y;

    public RoomUnitOnRollerComposer(RoomUnit roomUnit, HabboItem roller, RoomTile oldLocation, double oldZ, RoomTile newLocation, double newZ, Room room) {
        this.roomUnit = roomUnit;
        this.roller = roller;
        this.oldLocation = oldLocation;
        this.oldZ = oldZ;
        this.newLocation = newLocation;
        this.newZ = newZ;
        this.room = room;
    }

    public RoomUnitOnRollerComposer(RoomUnit roomUnit, RoomTile newLocation, Room room) {
        this.roomUnit = roomUnit;
        this.roller = null;
        this.oldLocation = this.roomUnit.getCurrentLocation();
        this.oldZ = this.roomUnit.getZ();
        this.newLocation = newLocation;
        this.newZ = this.newLocation.getStackHeight();
        this.room = room;
    }

    @Override
    protected ServerMessage composeInternal() {
        if (!this.room.isLoaded())
            return null;

        this.response.init(Outgoing.ObjectOnRollerComposer);
        this.response.appendInt(this.oldLocation.x);
        this.response.appendInt(this.oldLocation.y);
        this.response.appendInt(this.newLocation.x);
        this.response.appendInt(this.newLocation.y);
        this.response.appendInt(0);
        this.response.appendInt(this.roller == null ? 0 : this.roller.getId());
        this.response.appendInt(2);
        this.response.appendInt(this.roomUnit.getId());
        this.response.appendString(this.oldZ + "");
        this.response.appendString(this.newZ + "");

        if (this.roller != null && room.getLayout() != null) {
            RoomTile rollerTile = room.getLayout().getTile(this.roller.getX(), this.roller.getY());
            HabboItem topItem = this.room.getTopItemAt(this.roomUnit.getCurrentLocation().x, this.roomUnit.getCurrentLocation().y);
            if (topItem != null) {
                try {
                    topItem.onWalkOff(this.roomUnit, this.room, new Object[]{this});
                } catch (Exception e) {
                    LOGGER.error("Caught exception", e);
                }
            }
            Emulator.getThreading().run(() -> {
                if (RoomUnitOnRollerComposer.this.oldLocation == rollerTile && RoomUnitOnRollerComposer.this.roomUnit.getGoal() == rollerTile) {
                    RoomUnitOnRollerComposer.this.roomUnit.setLocation(room.getLayout().getTile(newLocation.x, newLocation.y));
                    RoomUnitOnRollerComposer.this.roomUnit.setPreviousLocationZ(RoomUnitOnRollerComposer.this.newLocation.getStackHeight());
                    RoomUnitOnRollerComposer.this.roomUnit.setZ(RoomUnitOnRollerComposer.this.newLocation.getStackHeight());
                    RoomUnitOnRollerComposer.this.roomUnit.sitUpdate = true;

                }

            }, this.room.getRollerSpeed() == 0 ? 250 : InteractionRoller.DELAY);
        } else {
            this.roomUnit.setLocation(this.newLocation);
            this.roomUnit.setZ(this.newZ);
        }

        return this.response;
    }
}
