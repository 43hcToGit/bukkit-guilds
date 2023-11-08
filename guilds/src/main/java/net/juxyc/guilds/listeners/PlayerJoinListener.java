package net.juxyc.guilds.listeners;

import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.data.User;
import net.juxyc.guilds.data.packet.UserPacket;
import net.juxyc.guilds.scoreboard.ScoreBoardManager;
import net.juxyc.guilds.tablist.TablistHelper;
import net.juxyc.guilds.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    private final ScoreBoardManager scoreBoardManager = BukkitPlugin.getInstance().getScoreBoardManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user = BukkitPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        scoreBoardManager.create(player);

        if(user == null){
            BukkitPlugin.getInstance().getUserManager().create(player.getUniqueId(), player.getName());
            //player.kickPlayer(ChatUtil.fixColor("&cPomyślnie stworzono konto!"));
        }

        BukkitPlugin.getInstance().getUserPacketManager().create(player.getUniqueId(), new UserPacket(player));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        User user = BukkitPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
        scoreBoardManager.delete(player);

        BukkitPlugin.getInstance().getUserPacketManager().delete(player.getUniqueId());
    }
}
