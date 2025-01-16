package com.eu.habbo.habbohotel.rooms;

import java.util.HashMap;
import java.util.Map;

public class RoomChatMessageBubbles {
    private static final Map<Integer, RoomChatMessageBubbles> BUBBLES = new HashMap<>();

    public static final RoomChatMessageBubbles NORMAL = new RoomChatMessageBubbles(0, "NORMAL", "", true, true);
    public static final RoomChatMessageBubbles ALERT = new RoomChatMessageBubbles(1, "ALERT", "", true, true);
    public static final RoomChatMessageBubbles BOT = new RoomChatMessageBubbles(2, "BOT", "", true, true);
    public static final RoomChatMessageBubbles RED = new RoomChatMessageBubbles(3, "RED", "", true, true);
    public static final RoomChatMessageBubbles BLUE = new RoomChatMessageBubbles(4, "BLUE", "", true, true);
    public static final RoomChatMessageBubbles YELLOW = new RoomChatMessageBubbles(5, "YELLOW", "", true, true);
    public static final RoomChatMessageBubbles GREEN = new RoomChatMessageBubbles(6, "GREEN", "", true, true);
    public static final RoomChatMessageBubbles BLACK = new RoomChatMessageBubbles(7, "BLACK", "", true, true);
    public static final RoomChatMessageBubbles FORTUNE_TELLER = new RoomChatMessageBubbles(8, "FORTUNE_TELLER", "", false, false);
    public static final RoomChatMessageBubbles ZOMBIE_ARM = new RoomChatMessageBubbles(9, "ZOMBIE_ARM", "", true, false);
    public static final RoomChatMessageBubbles SKELETON = new RoomChatMessageBubbles(10, "SKELETON", "", true, false);
    public static final RoomChatMessageBubbles LIGHT_BLUE = new RoomChatMessageBubbles(11, "LIGHT_BLUE", "", true, true);
    public static final RoomChatMessageBubbles PINK = new RoomChatMessageBubbles(12, "PINK", "", true, true);
    public static final RoomChatMessageBubbles PURPLE = new RoomChatMessageBubbles(13, "PURPLE", "", true, true);
    public static final RoomChatMessageBubbles DARK_YELLOW = new RoomChatMessageBubbles(14, "DARK_YELLOW", "", true, true);
    public static final RoomChatMessageBubbles DARK_BLUE = new RoomChatMessageBubbles(15, "DARK_BLUE", "", true, true);
    public static final RoomChatMessageBubbles HEARTS = new RoomChatMessageBubbles(16, "HEARTS", "", true, true);
    public static final RoomChatMessageBubbles ROSES = new RoomChatMessageBubbles(17, "ROSES", "", true, true);
    public static final RoomChatMessageBubbles UNUSED = new RoomChatMessageBubbles(18, "UNUSED", "", true, true);
    public static final RoomChatMessageBubbles PIG = new RoomChatMessageBubbles(19, "PIG", "", true, true);
    public static final RoomChatMessageBubbles DOG = new RoomChatMessageBubbles(20, "DOG", "", true, true);
    public static final RoomChatMessageBubbles BLAZE_IT = new RoomChatMessageBubbles(21, "BLAZE_IT", "", true, true);
    public static final RoomChatMessageBubbles DRAGON = new RoomChatMessageBubbles(22, "DRAGON", "", true, true);
    public static final RoomChatMessageBubbles STAFF = new RoomChatMessageBubbles(23, "STAFF", "", false, true);
    public static final RoomChatMessageBubbles BATS = new RoomChatMessageBubbles(24, "BATS", "", true, false);
    public static final RoomChatMessageBubbles MESSENGER = new RoomChatMessageBubbles(25, "MESSENGER", "", true, false);
    public static final RoomChatMessageBubbles STEAMPUNK = new RoomChatMessageBubbles(26, "STEAMPUNK", "", true, false);
    public static final RoomChatMessageBubbles THUNDER = new RoomChatMessageBubbles(27, "THUNDER", "", true, true);
    public static final RoomChatMessageBubbles PARROT = new RoomChatMessageBubbles(28, "PARROT", "", false, false);
    public static final RoomChatMessageBubbles PIRATE = new RoomChatMessageBubbles(29, "PIRATE", "", false, false);
    public static final RoomChatMessageBubbles BOT_GUIDE = new RoomChatMessageBubbles(30, "BOT_GUIDE", "", true, true);
    public static final RoomChatMessageBubbles BOT_RENTABLE = new RoomChatMessageBubbles(31, "BOT_RENTABLE", "", true, true);
    public static final RoomChatMessageBubbles SCARY_THING = new RoomChatMessageBubbles(32, "SCARY_THING", "", true, false);
    public static final RoomChatMessageBubbles FRANK = new RoomChatMessageBubbles(33, "FRANK", "", true, false);
    public static final RoomChatMessageBubbles WIRED = new RoomChatMessageBubbles(34, "WIRED", "", false, true);
    public static final RoomChatMessageBubbles GOAT = new RoomChatMessageBubbles(35, "GOAT", "", true, false);
    public static final RoomChatMessageBubbles SANTA = new RoomChatMessageBubbles(36, "SANTA", "", true, false);
    public static final RoomChatMessageBubbles AMBASSADOR = new RoomChatMessageBubbles(37, "AMBASSADOR", "acc_ambassador", false, true);
    public static final RoomChatMessageBubbles RADIO = new RoomChatMessageBubbles(38, "RADIO", "", true, false);
    public static final RoomChatMessageBubbles UNKNOWN_39 = new RoomChatMessageBubbles(39, "UNKNOWN_39", "", true, false);
    public static final RoomChatMessageBubbles UNKNOWN_40 = new RoomChatMessageBubbles(40, "UNKNOWN_40", "", true, false);
    public static final RoomChatMessageBubbles UNKNOWN_41 = new RoomChatMessageBubbles(41, "UNKNOWN_41", "", true, false);
    public static final RoomChatMessageBubbles UNKNOWN_42 = new RoomChatMessageBubbles(42, "UNKNOWN_42", "", true, false);
    public static final RoomChatMessageBubbles UNKNOWN_43 = new RoomChatMessageBubbles(43, "UNKNOWN_43", "", true, false);
    public static final RoomChatMessageBubbles UNKNOWN_44 = new RoomChatMessageBubbles(44, "UNKNOWN_44", "", true, false);
    public static final RoomChatMessageBubbles UNKNOWN_45 = new RoomChatMessageBubbles(45, "UNKNOWN_45", "", true, false);

    static {
        registerBubble(NORMAL);
        registerBubble(ALERT);
        registerBubble(BOT);
        registerBubble(RED);
        registerBubble(BLUE);
        registerBubble(YELLOW);
        registerBubble(GREEN);
        registerBubble(BLACK);
        registerBubble(FORTUNE_TELLER);
        registerBubble(ZOMBIE_ARM);
        registerBubble(SKELETON);
        registerBubble(LIGHT_BLUE);
        registerBubble(PINK);
        registerBubble(PURPLE);
        registerBubble(DARK_YELLOW);
        registerBubble(DARK_BLUE);
        registerBubble(HEARTS);
        registerBubble(ROSES);
        registerBubble(UNUSED);
        registerBubble(PIG);
        registerBubble(DOG);
        registerBubble(BLAZE_IT);
        registerBubble(DRAGON);
        registerBubble(STAFF);
        registerBubble(BATS);
        registerBubble(MESSENGER);
        registerBubble(STEAMPUNK);
        registerBubble(THUNDER);
        registerBubble(PARROT);
        registerBubble(PIRATE);
        registerBubble(BOT_GUIDE);
        registerBubble(BOT_RENTABLE);
        registerBubble(SCARY_THING);
        registerBubble(FRANK);
        registerBubble(WIRED);
        registerBubble(GOAT);
        registerBubble(SANTA);
        registerBubble(AMBASSADOR);
        registerBubble(RADIO);
        registerBubble(UNKNOWN_39);
        registerBubble(UNKNOWN_40);
        registerBubble(UNKNOWN_41);
        registerBubble(UNKNOWN_42);
        registerBubble(UNKNOWN_43);
        registerBubble(UNKNOWN_44);
        registerBubble(UNKNOWN_45);
    }

    private final int type;
    private final String name;
    private final String permission;
    private final boolean overridable;
    private final boolean triggersTalkingFurniture;

    private RoomChatMessageBubbles(int type, String name, String permission, boolean overridable, boolean triggersTalkingFurniture) {
        this.type = type;
        this.name = name;
        this.permission = permission;
        this.overridable = overridable;
        this.triggersTalkingFurniture = triggersTalkingFurniture;
    }

    public static RoomChatMessageBubbles getBubble(int id) {
        return BUBBLES.getOrDefault(id, NORMAL);
    }

    private static void registerBubble(RoomChatMessageBubbles bubble) {
        BUBBLES.put(bubble.getType(), bubble);
    }

    public int getType() {
        return type;
    }

    public String name() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isOverridable() {
        return overridable;
    }

    public boolean triggersTalkingFurniture() {
        return triggersTalkingFurniture;
    }

    public static void addDynamicBubble(int type, String name, String permission, boolean overridable, boolean triggersTalkingFurniture) {
        registerBubble(new RoomChatMessageBubbles(type, name, permission, overridable, triggersTalkingFurniture));
    }

    public static void removeDynamicBubbles() {
        synchronized (BUBBLES) {
            BUBBLES.entrySet().removeIf(entry -> entry.getKey() > 45);
        }
    }

    public static RoomChatMessageBubbles[] values() {
        return BUBBLES.values().toArray(new RoomChatMessageBubbles[0]);
    }
}