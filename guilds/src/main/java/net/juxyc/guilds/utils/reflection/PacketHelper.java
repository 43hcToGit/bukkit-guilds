package net.juxyc.guilds.utils.reflection;

import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PacketHelper {

    private static final Class<?> CraftPlayerClass = ReflectionHelper.getOcbClass("entity.CraftPlayer");
    private static final Class<?> PlayerConnectionClass = ReflectionHelper.getNmsClass("PlayerConnection");

    private static final Method getHandle = ReflectionHelper.getMethod(CraftPlayerClass, "getHandle");
    private static final Method sendPacket = ReflectionHelper.getMethod(PlayerConnectionClass, "sendPacket");


    private final Player player;
    private List<Object> packets = new ArrayList<>();
    private Object entityPlayer;
    private Object playerConnection;

    public PacketHelper(Player player){
        this.player = player;
        this.entityPlayer = ReflectionHelper.invoke(getHandle, player);
        this.playerConnection = ReflectionHelper.getFieldValue(entityPlayer, "playerConnection");
    }

    public PacketHelper addPacket(Object packet){
        this.packets.add(packet);
        return this;
    }

    public PacketHelper addPackets(List<Object> packets){
        this.packets.addAll(packets);
        return this;
    }

    public PacketHelper addPackets(Object... packets){
        this.packets.addAll(Arrays.asList(packets));
        return this;
    }

    public void send(){
        if(packets.isEmpty()) return;
        packets.forEach(p -> ReflectionHelper.invoke(sendPacket, playerConnection, p));
        packets.clear();
    }

    public Player getPlayer() {
        return player;
    }

    public List<Object> getPackets() {
        return packets;
    }

    public void setPackets(List<Object> packets) {
        this.packets = packets;
    }

    public Object getEntityPlayer() {
        return entityPlayer;
    }

    public void setEntityPlayer(Object entityPlayer) {
        this.entityPlayer = entityPlayer;
    }

    public Object getPlayerConnection() {
        return playerConnection;
    }

    public void setPlayerConnection(Object playerConnection) {
        this.playerConnection = playerConnection;
    }
}