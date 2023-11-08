package net.juxyc.guilds.listeners;

import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.data.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        Guild guild = BukkitPlugin.getInstance().getGuildManager().getGuildByPlayer(player);

        if(guild != null){
        }
    }
}
