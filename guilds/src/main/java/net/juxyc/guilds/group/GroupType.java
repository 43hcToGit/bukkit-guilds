package net.juxyc.guilds.group;

import lombok.Getter;

public enum GroupType {

    GRACZ("Gracz", "&7GRACZ", "&7", "&f", "&f ", "", 5),
    VIP("VIP", "&6VIP", "&7", "&f", "&6VIP &f", "&6VIP", 10),
    SVIP("SVIP", "&eSVIP", "&7", "&f", "&eSVIP &f", "&eSVIP", 15),
    TESTHELPER("THELPER", "&bTHELPER", "&7", "&f", "&bTHELPER &f", "&bTHELPER", 40),
    HELPER("HELPER", "&3HELPER", "&7", "&f", "&3HELPER &f", "&3HELPER", 50),
    MODERATOR("MODERATOR", "&2MODERATOR", "&7", "&f", "&2MODERATOR &f", "&2MODERATOR", 60),
    ADMIN("ADMIN", "&cADMIN", "&7", "&f", "&cADMIN &f", "&cADMIN", 70),
    HEADADMIN("HEADADMIN", "&4H@", "&7", "&f", "&4H@ &c", "&4H@", 80),
    ROOT("ROOT", "&4ROOT", "&7", "&c", "&4R &c", "&4ROOT", 99);

    @Getter
    private final String name;

    @Getter
    private final String fullName;

    @Getter
    private final String prefix;

    @Getter
    private final String suffix;

    @Getter
    private final String tag;

    @Getter
    private final String chat;

    @Getter
    private final int power;

    GroupType(String name, String fullName, String prefix, String suffix, String tag, String chat, int power) {
        this.name = name;
        this.fullName = fullName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.tag = tag;
        this.chat = chat;
        this.power = power;
    }

    public boolean can(final GroupType type) {
        return this.power >= type.getPower();
    }

}
