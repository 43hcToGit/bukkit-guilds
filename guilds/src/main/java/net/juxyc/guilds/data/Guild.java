package net.juxyc.guilds.data;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import lombok.Setter;
import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.data.impl.camera.GuildCamera;
import net.juxyc.guilds.data.impl.others.GuildDeputys;
import net.juxyc.guilds.data.impl.member.GuildMember;
import net.juxyc.guilds.data.impl.region.Region;
import net.juxyc.guilds.data.impl.role.GuildRole;
import net.juxyc.guilds.database.DatabaseModel;
import net.juxyc.guilds.utils.ChatUtil;
import net.juxyc.guilds.utils.KeyUtil;
import net.juxyc.guilds.utils.TimeUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Guild extends DatabaseModel {

    private final BukkitPlugin main = BukkitPlugin.getInstance();

    private final String guildTag;
    private final String guildName;

    private final UUID guildOwner;
    private final String guildOwnerNickName;

    private final Region guildRegion;
    private final int x,z,size;

    private final HashMap<UUID, GuildDeputys> guildDeputys;
    private final HashMap<UUID, GuildMember> guildMembers;
    private final Cache<UUID, String> guildInvites;
    private final HashMap<String, Guild> guildAllies;
    private final HashMap<String, GuildCamera> guildCamers;

    private final HashMap<String, GuildRole> guildRoles;

    private final int guildKills;
    private final int guildDeaths;
    private final int guildPoints;

    private final boolean friendlyFireStatus;
    private final long guildValidate;
    private final long guildProtection;

    public Guild(final String guildTag, final String guildName, final UUID guildOwner, final String guildOwnerNickName){
        final Player player = Bukkit.getPlayer(guildOwner);

        this.guildTag = guildTag;
        this.guildName = guildName;

        this.guildOwner = guildOwner;
        this.guildOwnerNickName = guildOwnerNickName;

        this.x = player.getLocation().getBlockX();
        this.z = player.getLocation().getBlockZ();
        this.size = 25;
        this.guildRegion = new Region(x,z,size);

        this.guildDeputys = new HashMap<>();

        this.guildMembers = new HashMap<>();
        this.guildMembers.put(guildOwner, new GuildMember(guildOwner, guildOwnerNickName, "MEMBER"));

        this.guildInvites = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.SECONDS).build();

        this.guildAllies = new HashMap<>();

        this.guildCamers = new HashMap<>();

        this.guildRoles = new HashMap<>();

        this.guildKills = 0;
        this.guildDeaths = 0;
        this.guildPoints = 500;

        this.friendlyFireStatus = true;

        this.guildValidate = System.currentTimeMillis() + TimeUtil.SECOND.getTime(15);
        this.guildProtection = System.currentTimeMillis() + TimeUtil.DAY.getTime(1);

        insert();
    }

    public Guild(final Document document){
        this.guildTag = document.getString("guildTag");
        this.guildName = document.getString("guildName");

        this.guildOwner = UUID.fromString(document.getString("guildOwner"));
        this.guildOwnerNickName = document.getString("guildOwnerNickName");

        this.x = document.getInteger("x");
        this.z = document.getInteger("z");
        this.size = document.getInteger("size");
        this.guildRegion = new Region(document.getInteger("x"),document.getInteger("z"),document.getInteger("size"));

        HashMap<UUID, GuildDeputys> deputys = new HashMap<>();
        List<Document> deputysList = (List<Document>) document.get("guildDeputys");
        deputysList.forEach(document1 -> {
            deputys.put(UUID.fromString(document1.getString("uuid")), new GuildDeputys(UUID.fromString(document1.getString("uuid")), document1.getString("nickName")));
        });
        this.guildDeputys = deputys;

        HashMap<UUID, GuildMember> members = new HashMap<>();
        List<Document> membersList = (List<Document>) document.get("guildMembers");
        membersList.forEach(document1 -> {
            members.put(UUID.fromString(document1.getString("uuid")), new GuildMember(UUID.fromString(document1.getString("uuid")), document1.getString("nickName"), document1.getString("role")));
        });
        this.guildMembers = members;

        this.guildInvites = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.SECONDS).build();

        HashMap<String, Guild> allies = new HashMap<>();
        this.guildAllies = allies;

        HashMap<String, GuildCamera> camers = new HashMap<>();
        this.guildCamers = camers;

        HashMap<String, GuildRole> roles = new HashMap<>();
        this.guildRoles = roles;

        this.guildKills = document.getInteger("guildKills");
        this.guildDeaths = document.getInteger("guildDeaths");
        this.guildPoints = document.getInteger("guildPoints");

        this.friendlyFireStatus = document.getBoolean("friendlyFireStatus");

        this.guildValidate = document.getLong("guildValidate");
        this.guildProtection = document.getLong("guildProtection");
    }
    @Override
    protected Document buildDocument() {

        ArrayList<Document> deputys = new ArrayList<>();
        for(GuildDeputys guildDeputys : this.guildDeputys.values()) {
            deputys.add((new Document("uuid", guildDeputys.getUuid().toString())).append("nickName", guildDeputys.getNick()));
        }

        ArrayList<Document> members = new ArrayList<>();
        for(GuildMember guildMember : this.guildMembers.values()) {
            members.add((new Document("uuid", guildMember.getUuid().toString())).append("nickName", guildMember.getNick()));
        }
        return new Document()
                .append("guildTag", this.getGuildTag())
                .append("guildName", this.getGuildName())

                .append("guildOwner", this.getGuildOwner().toString())
                .append("guildOwnerNickName", this.getGuildOwnerNickName())

                .append("x", this.getGuildRegion().getX())
                .append("z", this.getGuildRegion().getZ())
                .append("size", this.getGuildRegion().getSize())

                .append("guildDeputys", deputys)
                .append("guildMembers", members)
                .append("guildAllies", this.getGuildAllies())
                .append("guildCamers", this.getGuildCamers())

                .append("guildRoles", this.getGuildRoles())

                .append("guildKills", this.getGuildKills())
                .append("guildDeaths", this.getGuildDeaths())
                .append("guildPoints", this.getGuildPoints())

                .append("friendlyFireStatus", this.isFriendlyFireStatus())

                .append("guildValidate", this.getGuildValidate())
                .append("guildProtection", this.getGuildProtection());
    }

    public List<Player> getOnlineMembers() {
        final List<Player> onlineMembers = new ArrayList<>();

        this.getGuildMembers().forEach((uuid, guildMember) -> {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.isOnline()) {
                onlineMembers.add(offlinePlayer.getPlayer());
            }
        });
        return onlineMembers;
    }


    public void sendMessage(final String message) {
        this.getOnlineMembers().forEach(player -> ChatUtil.sendMessage(player, message));
    }

    @Override
    protected void insert() {
        this.main.getMongoManager().insert(KeyUtil.PLATFORM_GUILDS, this.buildDocument());
    }

    @Override
    public void update() {
        this.main.getMongoManager().update(KeyUtil.PLATFORM_GUILDS, this.buildDocument(), "guildTag" , this.getGuildTag(), true);
    }

    @Override
    public void delete() {
        this.main.getMongoManager().remove(KeyUtil.PLATFORM_GUILDS, this.buildDocument(), true);
    }
}

