package net.juxyc.guilds.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChatUtil extends HexColors {

    public static String fixColor(String s) {
        return fixHexColor(ChatColor.translateAlternateColorCodes('&', s).replace(">>", "").replace("<<", "").replace("{O}", ""));
    }

    public static List<String> fixColor(List<String> list) {
        List<String> fixed = new ArrayList<>();
        if (list == null || list.isEmpty())
            return fixed;
        for (String s : list)
            fixed.add(fixColor(s));
        return fixed;
    }

    public static boolean sendMessage(CommandSender sender, String message, String permission) {
        if (sender instanceof org.bukkit.command.ConsoleCommandSender)
            sendMessage(sender, message);
        return (permission != null && permission != "" && sender.hasPermission(permission) && sendMessage(sender, message));
    }

    public static boolean sendMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            if (message != null || message != "")
                sender.sendMessage(fixColor(message));
        } else {
            sender.sendMessage(ChatColor.stripColor(fixColor(message)));
        }
        return true;
    }

    public static boolean sendMessage(Collection<? extends CommandSender> collection, String message) {
        for (CommandSender cs : collection)
            sendMessage(cs, message);
        return true;
    }

    public static boolean sendMessage(Collection<? extends CommandSender> collection, String message, String permission) {
        for (CommandSender cs : collection)
            sendMessage(cs, message, permission);
        return true;
    }

    public static void sendTitle(Player p, String title, String subtitle) {
        p.sendTitle(fixColor(title), fixColor(subtitle));
    }
}
