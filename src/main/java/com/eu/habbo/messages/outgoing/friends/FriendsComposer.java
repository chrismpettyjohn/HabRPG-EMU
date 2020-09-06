package com.eu.habbo.messages.outgoing.friends;

import com.eu.habbo.habbohotel.messenger.Messenger;
import com.eu.habbo.habbohotel.messenger.MessengerBuddy;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FriendsComposer extends MessageComposer {
    private static final Logger LOGGER = LoggerFactory.getLogger(FriendsComposer.class);

    private final Habbo habbo;

    public FriendsComposer(Habbo habbo) {
        this.habbo = habbo;
    }

    @Override
    protected ServerMessage composeInternal() {
        try {
            this.response.init(Outgoing.FriendsComposer);

            //this.response.appendInt(300);
            //this.response.appendInt(300);
            //this.response.appendInt(3); //Club level
            this.response.appendInt(this.habbo.hasPermission("acc_infinite_friends") ? Integer.MAX_VALUE : Messenger.MAXIMUM_FRIENDS);
            this.response.appendInt(this.habbo.hasPermission("acc_infinite_friends") ? Integer.MAX_VALUE : Messenger.MAXIMUM_FRIENDS);
            this.response.appendInt(this.habbo.getMessenger().getFriends().size()/* + (this.habbo.hasPermission("acc_staff_chat") ? 1 : 0)*/);

            for (Map.Entry<Integer, MessengerBuddy> row : this.habbo.getMessenger().getFriends().entrySet()) {
                this.response.appendInt(row.getKey());
                this.response.appendString(row.getValue().getUsername());
                this.response.appendInt(row.getValue().getGender().equals(HabboGender.M) ? 0 : 1);
                this.response.appendBoolean(row.getValue().getOnline() == 1);
                this.response.appendBoolean(row.getValue().inRoom()); //IN ROOM
                this.response.appendString(row.getValue().getOnline() == 1 ? row.getValue().getLook() : "");
                this.response.appendInt(0);
                this.response.appendString(row.getValue().getMotto());
                this.response.appendString("");
                this.response.appendString("");
                this.response.appendBoolean(false); //Offline messaging.
                this.response.appendBoolean(false);
                this.response.appendBoolean(false);
                this.response.appendShort(row.getValue().getRelation());
            }

            /*if(this.habbo.hasPermission("acc_staff_chat"))
            {
                this.response.appendInt(-1);
                this.response.appendString("Staff Chat");
                this.response.appendInt(this.habbo.getHabboInfo().getGender().equals(HabboGender.M) ? 0 : 1);
                this.response.appendBoolean(true);
                this.response.appendBoolean(false); //IN ROOM
                this.response.appendString("ADM");
                this.response.appendInt(0);
                this.response.appendString("");
                this.response.appendString("");
                this.response.appendString("");
                this.response.appendBoolean(true); //Offline messaging.
                this.response.appendBoolean(false);
                this.response.appendBoolean(false);
                this.response.appendShort(0);
            }*/

            return this.response;
        } catch (Exception e) {
            LOGGER.error("Caught exception", e);
        }
        return null;
    }
}