package net.juxyc.guilds.data.impl.camera.inventory;

import net.juxyc.guilds.data.Guild;
import net.juxyc.guilds.utils.ChatUtil;
import net.juxyc.guilds.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuildCameraInventory {

    public static void openCameraInventory(Player player, Guild guild){
        World world = Bukkit.getWorld("world");

        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, ChatUtil.fixColor("&fKamery &9gildyjne!"));

        ItemStack camera1 = new ItemBuilder(Material.EMERALD_BLOCK, "&fObejrzyj &9kamere gildyjna 1").create();
        ItemStack camera2 = new ItemBuilder(Material.EMERALD_BLOCK, "&fObejrzyj &9kamere gildyjna 2").create();
        ItemStack camera3 = new ItemBuilder(Material.EMERALD_BLOCK, "&fObejrzyj &9kamere gildyjna 3").create();

        inventory.setItem(0, camera1);
        inventory.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE, 1));
        inventory.setItem(2, camera2);
        inventory.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE, 1));
        inventory.setItem(4, camera3);

        player.openInventory(inventory);
    }

    public static void closeCameraInventory(Player player){
        player.closeInventory();
    }
}
