package net.juxyc.guilds.utils;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material){ item = new ItemStack(material); }

    public ItemBuilder(Material material, String name){ item = new ItemBuilder(material).setName(name).create() ; }

    public ItemBuilder(Material material, Integer amount){ item = new ItemStack(material, amount); }

    public ItemBuilder(ItemStack item){ this.item = item.clone(); }

    public ItemStack create(){ return item; }

    public Material getMaterial(){ return item.getType(); }

    public int getAmount(){ return item.getAmount(); }

    public String getName(){ return item.getItemMeta().getDisplayName(); }

    public List<String> getLore(){ return  item.getItemMeta().getLore(); }

    public short getDurability(){ return  item.getDurability(); }

    public boolean isUnbreakable(){ return item.getDurability() == Short.MAX_VALUE; }

    public ItemBuilder setType(Material material){
        item.setType(material);

        return this;
    }

    public ItemBuilder setName(String name){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatUtil.fixColor(name));
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setAmount(Integer amount){
        item.setAmount(amount);

        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setLore(int line, String newLine){
        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.getLore();
        lore.set(line, ChatUtil.fixColor(newLine));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchant, int lvl){
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchant, lvl, true);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setDurability(int durability){
        item.setDurability((short) durability);

        return this;
    }

    public ItemBuilder setDurabilityDes(int durability){
        item.setDurability((short) (getMaterial().getMaxDurability() - durability));

        return this;
    }

    public ItemBuilder setInfDurability(){
        ItemMeta meta = item.getItemMeta();
        item.setDurability(Short.MAX_VALUE);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setFlag(ItemFlag flag){
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flag);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setSkullOwner(String owner){
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(owner);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setBaseColor(DyeColor dyeColor){
        BannerMeta meta = (BannerMeta) item.getItemMeta();
        meta.setBaseColor(dyeColor);
        item.setItemMeta(meta);

        return this;
    }
}