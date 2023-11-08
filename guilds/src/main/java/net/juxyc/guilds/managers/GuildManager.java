package net.juxyc.guilds.managers;

import java.util.HashMap;
import java.util.UUID;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.data.Guild;
import net.juxyc.guilds.utils.KeyUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GuildManager {

    private final HashMap<String, Guild> guilds;

    public GuildManager(){
        this.guilds = new HashMap<>();
    }

    MongoClientURI clientURI = new MongoClientURI("mongodb://localhost:27017");
    MongoClient mongoClient = new MongoClient(clientURI);

    MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
    MongoCollection<Document> collection = mongoDatabase.getCollection(KeyUtil.PLATFORM_GUILDS);

    Block<Document> printBlock = document -> System.out.println(document.toJson());

    public void create(final Guild guild){
        this.guilds.put(guild.getGuildTag().toUpperCase(), guild);
    }

    public void delete(final Guild guild){

        this.guilds.remove(guild.getGuildTag());
        guild.delete();
    }

    public Guild getGuildByName(final String name){
        return this.guilds.values().stream().filter(guild -> guild.getGuildName().equalsIgnoreCase(name) || guild.getGuildTag().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Guild getGuildByPlayer(final Player player){
        return this.guilds.values().stream().filter(guild -> guild.getGuildMembers().containsKey(player.getUniqueId())).findFirst().orElse(null);
    }

    public Guild getPlayerUUID(final UUID uuid){
        return this.guilds.values().stream().filter(guild -> guild.getGuildMembers().containsKey(uuid)).findFirst().orElse(null);
    }

    public Guild findByTag(final String tag){
        return this.guilds.values().stream().filter(guild -> guild.getGuildTag().equals(tag)).findFirst().orElse(null);
    }

    public Guild findByName(final String name){
        return this.guilds.values().stream().filter(guild -> guild.getGuildName().equals(name)).findFirst().orElse(null);
    }

    public Guild getGuildByLocation(final Location location) {
        for (final Guild guild : this.guilds.values()) {
            if (guild.getGuildRegion().isInCuboid(location)) {
                return guild;
            }
        }
        return null;

    }

    public HashMap<String, Guild> getGuilds() {
        return guilds;


    }
    public void load() {
        this.printBlock = document -> {
            final Guild guild = new Guild(document);
            this.create(guild);
        };
        collection = mongoDatabase.getCollection(KeyUtil.PLATFORM_GUILDS);
        collection.find().forEach(this.printBlock);
        BukkitPlugin.getInstance().getLogger().info("Loaded " + guilds.size() + " guilds!");
    }
}
