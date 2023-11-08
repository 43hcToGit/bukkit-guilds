package net.juxyc.guilds.managers.packet;

import java.util.HashMap;
import java.util.UUID;
import net.juxyc.guilds.data.packet.UserPacket;

public class UserPacketManager {
    public HashMap<UUID, UserPacket> getUsersPacket() {
        return this.usersPacket;
    }

    private final HashMap<UUID, UserPacket> usersPacket = new HashMap<>();

    public void create(UUID uuid, UserPacket user) {
        this.usersPacket.put(uuid, user);
    }

    public void delete(UUID uuid) {
        this.usersPacket.remove(uuid);
    }

    public UserPacket getUserPacket(UUID uuid) {
        return this.usersPacket.get(uuid);
    }
}

