package com.eu.habbo.habbohotel.items.interactions.wired.triggers;

import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionWiredTrigger;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.wired.WiredTriggerType;
import com.eu.habbo.messages.ClientMessage;
import com.eu.habbo.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredTriggerScoreAchieved extends InteractionWiredTrigger
{
    private static final WiredTriggerType type = WiredTriggerType.SCORE_ACHIEVED;
    private int score = 0;

    public WiredTriggerScoreAchieved(ResultSet set, Item baseItem) throws SQLException
    {
        super(set, baseItem);
    }

    public WiredTriggerScoreAchieved(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff)
    {
        if (stuff.length >= 2)
        {
            int points = (Integer)stuff[0];
            int amountAdded = (Integer)stuff[1];

            if (points - amountAdded < this.score && points >= this.score)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getWiredData()
    {
        return this.score + "";
    }

    @Override
    public void loadWiredData(ResultSet set, Room room) throws SQLException
    {
        try
        {
            this.score = Integer.valueOf(set.getString("wired_data"));
        }
        catch (Exception e){}
    }

    @Override
    public void onPickUp()
    {
        this.score = 0;
    }

    @Override
    public WiredTriggerType getType()
    {
        return type;
    }

    @Override
    public void serializeWiredData(ServerMessage message, Room room)
    {
        message.appendBoolean(false);
        message.appendInt(5);
        message.appendInt(0);
        message.appendInt(this.getBaseItem().getSpriteId());
        message.appendInt(this.getId());
        message.appendString("");
        message.appendInt(1);
        message.appendInt(this.score);
        message.appendInt(0);
        message.appendInt(this.getType().code);
        message.appendInt(0);
        message.appendInt(0);
    }

    @Override
    public boolean saveData(ClientMessage packet)
    {
        packet.readInt();
        this.score = packet.readInt();
        return true;
    }

    @Override
    public boolean isTriggeredByRoomUnit()
    {
        return true;
    }
}
