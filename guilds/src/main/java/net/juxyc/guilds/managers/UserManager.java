package net.juxyc.guilds.managers;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.data.User;
import net.juxyc.guilds.utils.KeyUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserManager {

    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    MongoClientURI clientURI = new MongoClientURI("mongodb://localhost:27017");
    MongoClient mongoClient = new MongoClient(clientURI);

    MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
    MongoCollection<Document> collection = mongoDatabase.getCollection(KeyUtil.PLATFORM_USERS);

    Block<Document> printBlock = document -> System.out.println(document.toJson());

    public User getUser(final UUID uuid) {
        for (User u : users.values()) {
            if (u.getUuid().equals(uuid))
                return u;
        }
        return null;
    }

    public User getUser(final Player p) {
        for (User u : users.values()) {
            if (u.getUuid().equals(p.getUniqueId()))
                return u;
        }
        return null;
    }

    public void create(final UUID uuid, final String name){
        final User user = new User(uuid, name);
        this.users.put(uuid, user);
    }

    public void delete(final User user){
        this.users.remove(user.getUuid(), user);
    }

    public void load() {
        this.printBlock = document -> {
            final User user = new User(document);
            this.create(user.getUuid(), user.getName());
        };
        collection = mongoDatabase.getCollection(KeyUtil.PLATFORM_USERS);
        collection.find().forEach(this.printBlock);
        BukkitPlugin.getInstance().getLogger().info("Loaded " + users.size() + " users!");
    }
}
