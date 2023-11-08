package net.juxyc.guilds.tasks;

import fr.mrmicky.fastboard.FastBoard;
import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.scoreboard.ScoreBoardManager;
import org.bukkit.Bukkit;

public class ScoreBoardUpdateTask implements Runnable {

    private final BukkitPlugin plugin;

    public ScoreBoardUpdateTask(BukkitPlugin plugin) {
        this.plugin = plugin;
    }


    private final ScoreBoardManager scoreBoardManager = BukkitPlugin.getInstance().getScoreBoardManager();

    public void start(){
        Bukkit.getScheduler().runTaskTimer(this.plugin, this, 20L, 20L);
    }

    @Override
    public void run() {
        for (FastBoard fastBoard : scoreBoardManager.boards.values()) {
            scoreBoardManager.update(fastBoard);
        }
    }
}
