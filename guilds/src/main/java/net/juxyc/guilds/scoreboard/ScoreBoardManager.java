package net.juxyc.guilds.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.data.User;
import net.juxyc.guilds.utils.ChatUtil;
import net.juxyc.guilds.utils.hexcolor.HexColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreBoardManager {
    public void create(Player player){
        FastBoard fastBoard = new FastBoard(player);
        fastBoard.updateTitle(ChatUtil.fixColor("&9Ice&fHard &f| &9Medium&fHc"));
        fastBoard.updateLines(
                ChatUtil.fixColor("&e"),
                ChatUtil.fixColor("&7Nick: &9Ladowanie..."),
                ChatUtil.fixColor("&7Ping: &9Ladowanie..."),
                ChatUtil.fixColor("&7Coinsy: &9Ladowanie..."),
                ChatUtil.fixColor("&7"),
                ChatUtil.fixColor("&7Punkty: &9Ladowanie..."),
                ChatUtil.fixColor("&7Zabojstwa: &9Ladowanie..."),
                ChatUtil.fixColor("&7Zgony: &9Ladowanie..."),
                ChatUtil.fixColor("&7K/D: &9Ladowanie..."),
                ChatUtil.fixColor("&7"),
                ChatUtil.fixColor("&7Zmien sektor: &9/ch")
        );

        this.boards.put(player.getUniqueId(), fastBoard);
    }

    public void delete(Player player){
        FastBoard fastBoard = this.boards.remove(player.getUniqueId());

        if(fastBoard != null){
            fastBoard.delete();
        }
    }

    public void update(FastBoard scoreboard){
        User user = BukkitPlugin.getInstance().getUserManager().getUser(scoreboard.getPlayer());
        double kdRatio = (((user.getKills() > 0) || (user.getDeaths() > 0)) ? (user.getKills() / user.getDeaths()) : 0);

        scoreboard.updateLines(
                ChatUtil.fixColor("&e"),
                ChatUtil.fixColor("&7Nick: &9" + user.getName()),
                ChatUtil.fixColor("&7Ping: &9" + ((CraftPlayer) scoreboard.getPlayer()).getHandle().ping),
                ChatUtil.fixColor("&7Coinsy: &9" + user.getCoins()),
                ChatUtil.fixColor("&7"),
                ChatUtil.fixColor("&7Punkty: &9" + user.getPoints()),
                ChatUtil.fixColor("&7Zabojstwa: &9" + user.getKills()),
                ChatUtil.fixColor("&7Zgony: &9" + user.getDeaths()),
                ChatUtil.fixColor("&7K/D: &9" + kdRatio),
                ChatUtil.fixColor("&7"),
                ChatUtil.fixColor("&7Zmien kanal: &9/ch")
        );
    }

    public final Map<UUID, FastBoard> boards = new HashMap<>();
}
