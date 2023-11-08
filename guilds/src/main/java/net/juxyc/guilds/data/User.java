package net.juxyc.guilds.data;

import lombok.Getter;
import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.database.DatabaseModel;
import net.juxyc.guilds.group.GroupType;
import net.juxyc.guilds.tablist.TablistHelper;
import net.juxyc.guilds.utils.KeyUtil;
import net.juxyc.guilds.utils.reflection.PacketHelper;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class User extends DatabaseModel {

    private final BukkitPlugin bukkitPlugin = BukkitPlugin.getInstance();

    private UUID uuid;

    private String name;

    private GroupType group;

    private int points, coins, kills, deaths;

    public User(final UUID uuid, final String name){
        this.uuid = uuid;
        this.name = name;
        this.group = GroupType.GRACZ;

        this.coins = 0;
        this.points = 500;

        this.kills = 0;
        this.deaths = 0;

        insert();
    }

    public User(final Document document){
        this.uuid = UUID.fromString(document.getString("uuid"));
        this.name = document.getString("name");
        this.group = GroupType.valueOf(document.getString("group"));

        this.coins = document.getInteger("coins");
        this.points = document.getInteger("points");

        this.kills = document.getInteger("kills");
        this.deaths = document.getInteger("deaths");
    }

    @Override
    protected Document buildDocument() {
        return new Document()
                .append("uuid", this.getUuid().toString())
                .append("name", this.getName())
                .append("group", this.getGroup().toString())

                .append("coins", this.getCoins())

                .append("points", this.getPoints())
                .append("kills", this.getKills())
                .append("deaths", this.getDeaths());
    }

    @Override
    protected void insert() {
        this.bukkitPlugin.getMongoManager().insert(KeyUtil.PLATFORM_USERS, this.buildDocument());
    }

    @Override
    public void update() {
        this.bukkitPlugin.getMongoManager().update(KeyUtil.PLATFORM_USERS, this.buildDocument(), "uuid", this.getUuid(), true);
    }

    @Override
    public void delete() {
        this.bukkitPlugin.getMongoManager().remove(KeyUtil.PLATFORM_USERS, this.buildDocument(), true);
    }
}
