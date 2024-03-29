package net.juxyc.guilds.utils;

import net.juxyc.guilds.BukkitPlugin;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public final class Logger
{
    public static void info(final String... logs) {
        for (final String s : logs) {
            log(Level.INFO, s);
        }
    }

    public static void warning(final String... logs) {
        for (final String s : logs) {
            log(Level.WARNING, s);
        }
    }

    public static void exception(final Throwable cause) {
        cause.printStackTrace();
    }

    public static void log(final Level level, final String log) {
        BukkitPlugin.getInstance().getLogger().log(level, log);
    }
}

