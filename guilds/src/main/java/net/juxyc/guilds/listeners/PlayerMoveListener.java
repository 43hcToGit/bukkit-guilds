package net.juxyc.guilds.listeners;

import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.data.Guild;
import net.juxyc.guilds.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {

        if (event.isCancelled())
            return;

        final Player player = event.getPlayer();

        final Location to = event.getTo();
        final Location from = event.getFrom();

        int xTo = to.getBlockX();
        int yTo = to.getBlockY();
        int zTo = to.getBlockZ();

        int xFrom = from.getBlockX();
        int yFrom = from.getBlockY();
        int zFrom = from.getBlockZ();

        Guild toGuild = BukkitPlugin.getInstance().getGuildManager().getGuildByLocation(to);
        Guild fromGuild = BukkitPlugin.getInstance().getGuildManager().getGuildByLocation(from);
        if ((xTo != xFrom || yTo != yFrom || zTo != zFrom) && fromGuild == null && toGuild != null) {
            if (toGuild.getGuildMembers().containsKey(player.getUniqueId())) {
                ChatUtil.sendTitle(player, "&9GILDIA", "&cWkroczyles na teren gildi: &4" + toGuild.getGuildTag() + " " + toGuild.getGuildName());
               } else {
                ChatUtil.sendTitle(player, "&9GILDIA", "&cWkroczyles na teren gildi: &4" + toGuild.getGuildTag() + " " + toGuild.getGuildName());
            }
            if (!toGuild.getGuildMembers().containsKey(player.getUniqueId()) && !player.isOp()) {
                toGuild.sendMessage("&cGracz &4" + player.getName() + " &cwkroczyl na teren twojej gildi!");
            }
        } else if ((xTo != xFrom || yTo != yFrom || zTo != zFrom) && toGuild == null && fromGuild != null) {
            if (fromGuild.getGuildMembers().containsKey(player.getUniqueId())) {
                ChatUtil.sendTitle(player, "&9GILDIE", "&cOpuszczono teren gildii &4" + fromGuild.getGuildTag() + " " + fromGuild.getGuildName());
            } else {
                ChatUtil.sendTitle(player, "&9GILDIE", "&cOpuszczono teren gildii &4" + fromGuild.getGuildTag() + " " + fromGuild.getGuildName());
            }
            if (!fromGuild.getGuildMembers().containsKey(player.getUniqueId()) && !player.isOp()) {
                fromGuild.sendMessage("&cGrasz &4" + player.getName() + " &copuscil teren twojej gildi!");
            }
        }
    }
}

