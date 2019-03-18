package com.eu.habbo.messages.outgoing.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class UserCurrencyComposer extends MessageComposer
{
    private final Habbo habbo;

    public UserCurrencyComposer(Habbo habbo)
    {
        this.habbo = habbo;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.UserCurrencyComposer);
        String[] pointsTypes = Emulator.getConfig().getValue("seasonal.types").split(";");
        this.response.appendInt(pointsTypes.length);
        for(String s : pointsTypes)
        {
            int type;
            try
            {
                type = Integer.valueOf(s);
            }
            catch (Exception e){
                Emulator.getLogging().logErrorLine(e);
                return null;
            }

            this.response.appendInt(type);
            this.response.appendInt(this.habbo.getHabboInfo().getCurrencyAmount(type));
        }
        return this.response;
    }
}
