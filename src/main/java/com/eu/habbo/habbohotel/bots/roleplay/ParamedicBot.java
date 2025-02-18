package com.eu.habbo.habbohotel.bots.roleplay;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.bots.Bot;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

public class ParamedicBot extends Bot {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamedicBot.class);
    public static THashMap<THashSet<String>, Integer> serveItems = new THashMap<>();

    public ParamedicBot(ResultSet set) throws SQLException {
        super(set);
    }

    public ParamedicBot(Bot bot) {
        super(bot);
    }

    public static void initialise() {
        if (serveItems == null)
            serveItems = new THashMap<>();

        serveItems.clear();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement(); ResultSet set = statement.executeQuery("SELECT * FROM bot_serves")) {
            while (set.next()) {
                String[] keys = set.getString("keys").split(";");
                THashSet<String> ks = new THashSet<>();
                Collections.addAll(ks, keys);
                serveItems.put(ks, set.getInt("item"));
            }
        } catch (SQLException e) {
            LOGGER.error("Caught SQL exception", e);
        }
    }

    public static void dispose() {
        serveItems.clear();
    }

    @Override
    public void onPlace(Habbo habbo, Room room) {
        super.onPlace(habbo, room);
        this.shout("*Arrives with medical equipment*");
    }

    public void heal(Habbo habbo) {
        if (habbo != null && this.getRoom() != null && habbo.getHabboInfo().getCurrentRoom() == this.getRoom()) {
            if (habbo.getRoleplayCharacter() != null) {
                habbo.getRoleplayCharacter().addHealth(habbo.getRoleplayCharacter().getHealthMax(), this.getRoleplayCharacter());
                this.talk("*Heals " + habbo.getHabboInfo().getUsername() + "*");
                this.talk("You're all patched up now!");
            }
        }
    }
}