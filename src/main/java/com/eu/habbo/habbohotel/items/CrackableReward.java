package com.eu.habbo.habbohotel.items;

import com.eu.habbo.Emulator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CrackableReward
{
    public final int itemId;
    public final int count;
    public final Map<Integer, Map.Entry<Integer, Integer>> prizes;
    public int totalChance;
    public final String achievementTick;
    public final String achievementCracked;
    public final int requiredEffect;
    public final int subscriptionDuration;
    public final RedeemableSubscriptionType subscriptionType;

    public CrackableReward(ResultSet set) throws SQLException
    {
        this.itemId = set.getInt("item_id");
        this.count = set.getInt("count");
        this.achievementTick = set.getString("achievement_tick");
        this.achievementCracked = set.getString("achievement_cracked");
        this.requiredEffect = set.getInt("required_effect");
        this.subscriptionDuration = set.getInt("subscription_duration");
        this.subscriptionType = RedeemableSubscriptionType.fromString(set.getString("subscription_type"));


        String[] prizes = set.getString("prizes").split(";");
        this.prizes = new HashMap<>();

        if (set.getString("prizes").isEmpty()) return;

        this.totalChance = 0;
        for (String prize : prizes)
        {
            try
            {
                int itemId = 0;
                int chance = 100;

                if (prize.contains(":") && prize.split(":").length == 2)
                {
                    itemId = Integer.valueOf(prize.split(":")[0]);
                    chance = Integer.valueOf(prize.split(":")[1]);
                }
                else
                {
                    itemId = Integer.valueOf(prize.replace(":", ""));
                }

                this.prizes.put(itemId, new AbstractMap.SimpleEntry<>(this.totalChance, this.totalChance + chance));
                this.totalChance += chance;
            }
            catch (Exception e)
            {
                Emulator.getLogging().logErrorLine(e);
            }
        }
    }

    public int getRandomReward()
    {
        if (this.prizes.size() == 0) return 0;

        int random = Emulator.getRandom().nextInt(this.totalChance);

        int notFound = 0;
        for (Map.Entry<Integer, Map.Entry<Integer, Integer>> set : this.prizes.entrySet())
        {
            notFound = set.getKey();
            if (random >= set.getValue().getKey() && random < set.getValue().getValue())
            {
                return set.getKey();
            }
        }

        return notFound;
    }
}
