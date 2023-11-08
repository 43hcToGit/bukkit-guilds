package net.juxyc.guilds.commands.impl.normal;

import dev.rollczi.litecommands.annotations.*;
import lombok.RequiredArgsConstructor;
import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.commands.argument.NameArgument;
import net.juxyc.guilds.commands.argument.NickArgument;
import net.juxyc.guilds.commands.argument.TagArgument;
import net.juxyc.guilds.data.Guild;
import net.juxyc.guilds.data.impl.camera.inventory.GuildCameraInventory;
import net.juxyc.guilds.managers.GuildManager;
import net.juxyc.guilds.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@RequiredArgsConstructor
@Section(route = "g", aliases = { "gildie", " gildia" })
@Permission("m4creators.commands.guild")
public class GuildCommand {

    private BukkitPlugin bukkitPlugin = BukkitPlugin.getInstance();

    @Execute
    public void help(CommandSender sender) {
        ChatUtil.sendMessage(sender, "&8=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        ChatUtil.sendMessage(sender, "&8» &9/g zaloz <tag> <nazwa> &8- &7Zakladanie gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g usun &8- &7Usuwanie gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g zapros <nick> &8- &7Zapraszanie y do gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g dolacz <tag> &8- &7Dolaczanie do podanej gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g wyrzuc <nick> &8- &7Wyrzucanie danego a z gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g opusc &8- &7Opuszczanie gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g kamery &8- &7Odpala menu kamer gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g role &8- &7Odpala menu rang gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g lider <nick> &8- &7Zmienianie lidera gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g zastepca <nick> &8- &7Awansowanie podanego a na zastepce gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g degraduj <nick> &8- &7Degradowanie podanego a z zastepcy gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g sojusz <tag> &8- &7Zapraszanie do sojuszu podanej gildii.");
        ChatUtil.sendMessage(sender, "&8» &9/g zerwij <tag> &8- &7Zrywanie sojuszu z podana gildia.");
        ChatUtil.sendMessage(sender, "&8» &9/g info <tag> &8- &7Sprawdzanie informacji o podanej gildii.");
        ChatUtil.sendMessage(sender, "&8=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    @Execute(route = "zaloz", aliases = { "create", "stworz" })
    @MinArgs(2)
    public void create(Player player, @Arg(0) @Handler(TagArgument.class) String tag, @Arg(1) @Handler(NameArgument.class) String name) {
        if(this.bukkitPlugin.getGuildManager().getGuildByPlayer(player) != null){
            ChatUtil.sendMessage(player, "&cPosiadasz juz gildie!");
            return;
        }
        if (tag.length() < 2 || tag.length() > 5) {
            ChatUtil.sendMessage(player, "&cTag gildii musi zawierac od 2 do 5 znakow!");
            return;
        }
        if (name.length() < 4 || name.length() > 32) {
            ChatUtil.sendMessage(player, "&cNazwa gildii musi zawierac od 4 do 32 znakow!");
            return;
        }
        if (this.bukkitPlugin.getGuildManager().findByTag(tag) != null) {
            ChatUtil.sendMessage(player, "&cGildia o podanym tagu juz istnieje!");
            return;
        }
        if (this.bukkitPlugin.getGuildManager().findByName(name) != null) {
            ChatUtil.sendMessage(player, "&cGildia o podanej nazwie juz istnieje!");
            return;
        }
        final Guild guild = new Guild(tag, name, player.getUniqueId(), player.getDisplayName());

        this.bukkitPlugin.getGuildManager().create(guild);
        if(this.bukkitPlugin.getGuildManager().getGuilds().size() == 1){
            final Location location = new Location(player.getWorld(), guild.getGuildRegion().getX(), 50, guild.getGuildRegion().getZ());

            player.teleport(location);

            ChatUtil.sendTitle(player, "&9&lGILDIE", "&9GZ &f| Zalozyles gildie jako pierwszy na serwerze");
            Bukkit.getOnlinePlayers().forEach(player1 -> {
                ChatUtil.sendMessage(player1, "&fGracz: &9" + player.getName() + " &fzalozyl pierwszą gildie: &9" + tag + " &f| " + name);
            });
            return;
        }
        ChatUtil.sendTitle(player, "&9&lGILDIE", "&fZalozyles gildie: &9" + tag + " &f| " + name);
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            ChatUtil.sendMessage(player1, "&9" + player.getName() + " &fzalozyl gildie: &9" + tag + " &f| " + name);
        });
    }

    @Execute(route = "delete", aliases = { "usun" })
    @MinArgs(0)
    public void delete(Player player) {
        final Guild guild = this.bukkitPlugin.getGuildManager().getGuildByPlayer(player);

        if(guild == null){
            ChatUtil.sendMessage(player,"&cNie posiadasz gildii!");
            return;
        }
        if(!guild.getGuildOwner().equals(player.getUniqueId())){
            ChatUtil.sendTitle(player, "&9&lGILDIE", "&cNie jestes liderem gildii!");
            return;
        }
        this.bukkitPlugin.getGuildManager().delete(guild);
        ChatUtil.sendTitle(player, "&9&lGILDIE", "&cUsuneles gildie!");
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            ChatUtil.sendMessage(player1, "&9" + player.getName() + " &frozwiazal gildie: &9" + guild.getGuildTag() + " &f| " + guild.getGuildName());
        });
    }

    @Execute(route = "zapros", aliases = { "invite" })
    @MinArgs(1)
    public void invite(Player player, @Arg(0) @Handler(NickArgument.class) String nick) {
        final Guild guild = this.bukkitPlugin.getGuildManager().getGuildByPlayer(player);
        final Player playerFromInvite = Bukkit.getPlayer(nick);
        final Guild guildInvitePlayer = this.bukkitPlugin.getGuildManager().getGuildByPlayer(playerFromInvite);

        if(guild == null){
            ChatUtil.sendMessage(player,"&cNie posiadasz gildii!");
            return;
        }
        if(!guild.getGuildOwner().equals(player.getUniqueId()) && !guild.getGuildDeputys().equals(player)){
            ChatUtil.sendMessage(player,"&cAby zapraszac graczy do gildii musisz byc liderem/zastepca lub posiadac permisje!");
            return;
        }
        if(guild.getGuildMembers().size() >= 15){
            ChatUtil.sendMessage(player,"&cPosiadasz limit czlonkow w gildii!");
            return;
        }
        if(playerFromInvite == null && playerFromInvite.isOnline()){
            ChatUtil.sendMessage(player,"&cNie ma takiego gracza!");
            return;
        }
        if(guildInvitePlayer != null){
            ChatUtil.sendMessage(player,"&cTen gracz posiada gildie!");
            return;
        }
        if(guild.getGuildInvites().getIfPresent(playerFromInvite.getUniqueId()) != null){
            ChatUtil.sendMessage(player, "&cTen gracz zostal juz zaproszony do twojej gildii!");
            return;
        }
        guild.getGuildInvites().put(playerFromInvite.getUniqueId(), playerFromInvite.getName());
        ChatUtil.sendMessage(player, "&fZaprosiles gracza &9" + playerFromInvite.getName() + " &fdo swojej gildii!");
        ChatUtil.sendMessage(playerFromInvite, "&fOtrzymales zaproszenie do gildii &9" + guild.getGuildTag() + " " + guild.getGuildName());
    }

    @Execute(route = "info", aliases = { "" })
    @MinArgs(1)
    public void info(Player player, @Arg(0) @Handler(TagArgument.class) String tag) {
        final Guild guild = this.bukkitPlugin.getGuildManager().getGuildByName(tag);

        if(guild == null){
            ChatUtil.sendMessage(player,"&cNie ma takiej gildii!");
            return;
        }
        double kd = (double) guild.getGuildKills() / guild.getGuildDeaths();

        ChatUtil.sendMessage(player, "&7&m-------&8] &fGILDIA: &9{TAG} {DESC} &8[&7&m-------".replace("{TAG}", guild.getGuildTag()).replace("{DESC}", guild.getGuildName()));
        ChatUtil.sendMessage(player, " &fLider gildii: &9{OWNER}".replace("{OWNER}", guild.getGuildOwnerNickName()));
        ChatUtil.sendMessage(player, " &fPunkty gildii: &9" + guild.getGuildPoints());
        ChatUtil.sendMessage(player, " &fZabojstwa: &9" + guild.getGuildKills() + " &7| &fSmierci: &9" + guild.getGuildDeaths() + " &7| &fK/D: &9" +kd);
        ChatUtil.sendMessage(player, " &fOpis gildii: &9{DESC}".replace("{DESC}", guild.getGuildName()));
        ChatUtil.sendMessage(player, " &fOpis gildii: &9{DESC}".replace("{DESC}", guild.getGuildName()));
        ChatUtil.sendMessage(player, " &fOpis gildii: &9{DESC}".replace("{DESC}", guild.getGuildName()));
        ChatUtil.sendMessage(player, " &fOpis gildii: &9{DESC}".replace("{DESC}", guild.getGuildName()));
        ChatUtil.sendMessage(player, " &fOpis gildii: &9{DESC}".replace("{DESC}", guild.getGuildName()));
        ChatUtil.sendMessage(player, "&7&m-------&8] &fGILDIA: &9{TAG} {DESC} &8[&7&m-------".replace("{TAG}", guild.getGuildTag()).replace("{DESC}", guild.getGuildName()));
    }

    @Execute(route = "kamery", aliases = { "camers", "camer" })
    @MinArgs(1)
    public void camers(Player player, @Arg(0) @Handler(NameArgument.class) String ustaw){
        final Guild guild = this.bukkitPlugin.getGuildManager().getGuildByPlayer(player);

        if(guild == null){
            ChatUtil.sendMessage(player,"&cNie posiadasz gildii!");
            return;
        }

        //todo check region guild and rank user
        if(guild.getGuildRegion().isInCuboid(player.getLocation())){
            if(guild.getGuildOwner().equals(player.getUniqueId()) || guild.getGuildDeputys().containsKey(player.getUniqueId())){
                GuildCameraInventory.openCameraInventory(player, guild);
            }else{
                ChatUtil.sendMessage(player, "&cNie jestes zalozycielem/zastepca lub nie posiadasz permisji do ogladania/edytowania kamer");
            }
        }else{
            ChatUtil.sendMessage(player, "&cNie jestes na terenie swojej gildii");
        }

    }
}
