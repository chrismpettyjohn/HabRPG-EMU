package com.eu.habbo.habbohotel.items.interactions.games.battlebanzai;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.rooms.RoomUnitStatus;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.threading.runnables.BanzaiRandomTeleport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionBattleBanzaiTeleporter extends HabboItem {
    public InteractionBattleBanzaiTeleporter(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
        this.setExtradata("0");
    }

    public InteractionBattleBanzaiTeleporter(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
        this.setExtradata("0");
    }

    @Override
    public void serializeExtradata(ServerMessage serverMessage) {
        serverMessage.appendInt((this.isLimited() ? 256 : 0));
        serverMessage.appendString(this.getExtradata());

        super.serializeExtradata(serverMessage);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {
        return true;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {

    }

    @Override
    public void onWalk(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {

    }

    @Override
    public void onWalkOn(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {
        super.onWalkOn(roomUnit, room, objects);
        HabboItem target = room.getRoomSpecialTypes().getRandomTeleporter(this.getBaseItem(), this);

        if (target == null) return;

        this.setExtradata("1");
        roomUnit.removeStatus(RoomUnitStatus.MOVE);
        target.setExtradata("1");
        room.updateItem(this);
        room.updateItem(target);
        roomUnit.setGoalLocation(room.getLayout().getTile(roomUnit.getX(), roomUnit.getY()));
        roomUnit.setCanWalk(false);
        Emulator.getThreading().run(new BanzaiRandomTeleport(this, target, roomUnit, room), 1000);
    }

    @Override
    public void onWalkOff(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {
        super.onWalkOff(roomUnit, room, objects);
    }
}
