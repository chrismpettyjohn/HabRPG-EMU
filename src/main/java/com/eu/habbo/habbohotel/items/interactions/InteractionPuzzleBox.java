package com.eu.habbo.habbohotel.items.interactions;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.rooms.*;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.rooms.items.FloorItemOnRollerComposer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class InteractionPuzzleBox extends HabboItem {
    public InteractionPuzzleBox(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionPuzzleBox(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        if (client.getHabbo().getRoomUnit().hasStatus(RoomUnitStatus.MOVE))
            return;

        RoomTile boxLocation = room.getLayout().getTile(this.getX(), this.getY());
        RoomUserRotation rotation = null;

        if (this.getX() == client.getHabbo().getRoomUnit().getX()) {
            if (this.getY() == client.getHabbo().getRoomUnit().getY() + 1) {
                rotation = RoomUserRotation.SOUTH;
            } else if (this.getY() == client.getHabbo().getRoomUnit().getY() - 1) {
                rotation = RoomUserRotation.NORTH;
            }
        } else if (this.getY() == client.getHabbo().getRoomUnit().getY()) {
            if (this.getX() == client.getHabbo().getRoomUnit().getX() + 1) {
                rotation = RoomUserRotation.EAST;
            } else if (this.getX() == client.getHabbo().getRoomUnit().getX() - 1) {
                rotation = RoomUserRotation.WEST;
            }
        }

        if (rotation == null) {
            Optional<RoomTile> nearestTile = Arrays.stream(
                    new RoomTile[]{
                            room.getLayout().getTileInFront(room.getLayout().getTile(this.getX(), this.getY()), RoomUserRotation.SOUTH.getValue()),
                            room.getLayout().getTileInFront(room.getLayout().getTile(this.getX(), this.getY()), RoomUserRotation.NORTH.getValue()),
                            room.getLayout().getTileInFront(room.getLayout().getTile(this.getX(), this.getY()), RoomUserRotation.EAST.getValue()),
                            room.getLayout().getTileInFront(room.getLayout().getTile(this.getX(), this.getY()), RoomUserRotation.WEST.getValue())
                    }
            )
                    .filter(t -> t.isWalkable() && !room.hasHabbosAt(t.x, t.y))
                    .min(Comparator.comparingDouble(a -> a.distance(client.getHabbo().getRoomUnit().getCurrentLocation())));

            nearestTile.ifPresent(roomTile -> client.getHabbo().getRoomUnit().setGoalLocation(roomTile));
            return;
        }

        RoomTile tile = room.getLayout().getTileInFront(room.getLayout().getTile(this.getX(), this.getY()), rotation.getValue());

        if (tile == null || room.hasHabbosAt(tile.x, tile.y)) {
            return;
        }

        if (!boxLocation.equals(room.getLayout().getTileInFront(client.getHabbo().getRoomUnit().getCurrentLocation(), rotation.getValue())))
            return;

        HabboItem item = room.getTopItemAt(tile.x, tile.y);

        if (item != null && !room.getTopItemAt(tile.x, tile.y).getBaseItem().allowStack()) return;

        this.setZ(room.getStackHeight(tile.x, tile.y, false));
        this.needsUpdate(true);
        room.updateItem(this);

        room.scheduledComposers.add(new FloorItemOnRollerComposer(this, null, tile, 0, room).compose());
        room.scheduledTasks.add(() -> client.getHabbo().getRoomUnit().setGoalLocation(boxLocation));
        this.needsUpdate(true);

        super.onClick(client, room, new Object[]{"TOGGLE_OVERRIDE"});
    }

    @Override
    public void serializeExtradata(ServerMessage serverMessage) {
        serverMessage.appendInt((this.isLimited() ? 256 : 0));
        serverMessage.appendString(this.getExtradata());

        super.serializeExtradata(serverMessage);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {
        return false;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void onWalk(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {

    }
}
