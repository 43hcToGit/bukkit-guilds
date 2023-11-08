package net.juxyc.guilds.utils;

import net.juxyc.guilds.utils.hexcolor.HexColor;
import org.bukkit.ChatColor;

public abstract class HexColors {
  public String colorText(String text, HexColor color) {
    return color.getAsHex() + text + ChatColor.RESET;
  }
  
  public static String getHexAsColor(HexColor color) {
    return color.getAsHex();
  }

  public static String getHexAsColor(int color) {
    String hex = String.valueOf(color).replaceAll("0x", "");
    return "§#" + hex;
  }

  public static String getHexAsColor(String hexColor) {
    if (hexColor.contains("#"))
      return "§" + hexColor;
    return "§#" + hexColor;
  }
  
  public static String fixHexColor(String text) {
    return text.replaceAll("&#", "§");
  }
}