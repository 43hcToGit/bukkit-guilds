package net.juxyc.guilds.data.packet;

import net.juxyc.guilds.tablist.TablistHelper;
import net.juxyc.guilds.utils.reflection.PacketHelper;

import org.bukkit.entity.Player;

public class UserPacket {
    private final PacketHelper packetHelper;

    private final TablistHelper tablistHelper;

    public PacketHelper getPacketHelper() {
        return this.packetHelper;
    }

    public TablistHelper getTablistHelper() {
        return this.tablistHelper;
    }

    public UserPacket(Player player) {
        this.packetHelper = new PacketHelper(player);
        this.tablistHelper = new TablistHelper(player, this.packetHelper);
    }
}
