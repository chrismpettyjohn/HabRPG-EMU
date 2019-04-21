package com.eu.habbo.networking.rconserver;

import com.eu.habbo.Emulator;
import com.eu.habbo.core.Logging;
import com.eu.habbo.messages.rcon.*;
import com.eu.habbo.networking.Server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gnu.trove.map.hash.THashMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RCONServer extends Server
{
    List<String> allowedAdresses = new ArrayList<>();

    private final THashMap<String, Class<? extends RCONMessage>> messages;

    private final GsonBuilder gsonBuilder;

    public RCONServer(String host, int port) throws Exception
    {
        super("RCON Server", host, port, 1, 2);

        this.messages = new THashMap<>();

        this.gsonBuilder = new GsonBuilder();
        this.gsonBuilder.registerTypeAdapter(RCONMessage.class, new RCONMessage.RCONMessageSerializer());

        this.addRCONMessage("alertuser",            AlertUser.class);
        this.addRCONMessage("disconnect",           DisconnectUser.class);
        this.addRCONMessage("forwarduser",          ForwardUser.class);
        this.addRCONMessage("givebadge",            GiveBadge.class);
        this.addRCONMessage("givecredits",          GiveCredits.class);
        this.addRCONMessage("givepixels",           GivePixels.class);
        this.addRCONMessage("givepoints",           GivePoints.class);
        this.addRCONMessage("hotelalert",           HotelAlert.class);
        this.addRCONMessage("sendgift",             SendGift.class);
        this.addRCONMessage("sendroombundle",       SendRoomBundle.class);
        this.addRCONMessage("setrank",              SetRank.class);
        this.addRCONMessage("updatewordfilter",     UpdateWordfilter.class);
        this.addRCONMessage("updatecatalog",        UpdateCatalog.class);
        this.addRCONMessage("executecommand",       ExecuteCommand.class);
        this.addRCONMessage("progressachievement",  ProgressAchievement.class);
        this.addRCONMessage("updateuser",           UpdateUser.class);
        this.addRCONMessage("friendrequest",        FriendRequest.class);
        this.addRCONMessage("imagehotelalert",      ImageHotelAlert.class);
        this.addRCONMessage("imagealertuser",       ImageAlertUser.class);
        this.addRCONMessage("stalkuser",            StalkUser.class);
        this.addRCONMessage("staffalert",           StaffAlert.class);
        this.addRCONMessage("modticket",            CreateModToolTicket.class);
        this.addRCONMessage("talkuser",             TalkUser.class);
        this.addRCONMessage("changeroomowner",      ChangeRoomOwner.class);
        this.addRCONMessage("muteuser",             MuteUser.class);
        this.addRCONMessage("giverespect",          GiveRespect.class);
        this.addRCONMessage("ignoreuser",           IgnoreUser.class);
        this.addRCONMessage("setmotto",             SetMotto.class);

        Collections.addAll(this.allowedAdresses, Emulator.getConfig().getValue("rcon.allowed", "127.0.0.1").split(";"));
    }

    @Override
    public void initializePipeline()
    {
        super.initializePipeline();

        this.serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>()
        {
            @Override
            public void initChannel(SocketChannel ch) throws Exception
            {
                ch.pipeline().addLast(new RCONServerHandler());
            }
        });
    }


    public void addRCONMessage(String key, Class<? extends RCONMessage> clazz)
    {
        this.messages.put(key, clazz);
    }

    public String handle(ChannelHandlerContext ctx, String key, String body) throws Exception
    {
        Class<? extends RCONMessage> message = this.messages.get(key.replace("_", "").toLowerCase());

        String result;
        if(message != null)
        {
            try
            {
                RCONMessage rcon = message.getDeclaredConstructor().newInstance();
                Gson gson = this.gsonBuilder.create();
                rcon.handle(gson, gson.fromJson(body, rcon.type));
                System.out.print("[" + Logging.ANSI_BLUE + "RCON" + Logging.ANSI_RESET + "] Handled RCON Message: " + message.getSimpleName());
                result = gson.toJson(rcon, RCONMessage.class);

                if (Emulator.debugging)
                {
                    System.out.print(" [" + Logging.ANSI_BLUE + "DATA" + Logging.ANSI_RESET + "]" + body + "[" + Logging.ANSI_BLUE + "RESULT" + Logging.ANSI_RESET + "]" + result);
                }
                System.out.println("");

                return result;
            }
            catch (Exception ex)
            {
                Emulator.getLogging().logErrorLine(ex);
                Emulator.getLogging().logPacketError("[RCON] Failed to handle RCONMessage: " + message.getName() + ex.getMessage() + " by: " + ctx.channel().remoteAddress());
            }
        }
        else
        {
            Emulator.getLogging().logPacketError("[RCON] Couldn't find: " + key);
        }

        throw new ArrayIndexOutOfBoundsException("Unhandled RCON Message");
    }

    public List<String> getCommands()
    {
        return new ArrayList<>(this.messages.keySet());
    }
}
