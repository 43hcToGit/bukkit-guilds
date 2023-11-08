package net.juxyc.guilds.tasks;

import net.juxyc.guilds.BukkitPlugin;
import net.juxyc.guilds.data.Guild;
import net.juxyc.guilds.data.User;
import net.juxyc.guilds.rank.StatsType;
import net.juxyc.guilds.tablist.type.SendType;
import net.juxyc.guilds.utils.ChatUtil;
import net.juxyc.guilds.utils.DateUtil;
import net.juxyc.guilds.utils.hexcolor.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateTask implements Runnable {

    private final BukkitPlugin plugin;

    public UpdateTask(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    public void start(){
        Bukkit.getScheduler().runTaskTimer(this.plugin, this, 20L, 20L);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            this.plugin.getUserPacketManager().getUserPacket(player.getUniqueId()).getTablistHelper().apply(tablistHelper -> {

                tablistHelper.setTexture("ewogICJ0aW1lc3RhbXAiIDogMTY4MTU2OTQ5OTU0MiwKICAicHJvZmlsZUlkIiA6ICI3ZGVhOWMzNjRlYjI0YWQxOWY4ZDAwMzEwM2E5MTc3MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJib256YWkxNiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NjJiMTM1Njg4MGM1YTk4OGFhOTNkZDljZDc2MTQyNjYzYzc2ZGE4NjEyNDk0YTZkYWVlOTlmODFlOWQ3M2NkIgogICAgfQogIH0KfQ==");
                tablistHelper.setSignature("A8E25VrLGdazniUovw/zP00YhvfmY+9SizC+yg2yMs5/nFcMypUN6ZDO6LyGhd29xRDoEF/jJN9tSCjG3td0QdLRQqKM+L/W65ojUsHkHsugxMCNhn9GDtmEvj80FoUH3OKaQCCYceNtK+4/O9OLtjBnryzLNciZ8TuVNcgwJCX3g13FO48sQptLHJZHHMMD+7nV0L6aPebiNGMCAF7SrITHoibKTIRcrzU/oLarv6Q1TMntgm17XLGdoFvNOs3XbjrhAHgv1P+0XhloraEnsxsGmHZhPe99cfJtlb1g/Xe0VWBz+JhbcC4YpbOsYPuxbkodZ9QjmZ8pNY0tEknZywJf9cwdS34AvlUUBNZcAz7gef9twP+XrCE2JTf85lCVxUA74Z+JJ0yaU8p7cuk0CiPe1+HUMm0PBt3LuynIGPzXAALIj5HxTITRZYiqBz9B8NOcwCCrBkZN36+ujyKd7Nu8oTexvyuYoxhaCJOZZPic+BtQ1zoW+/Sda1yxFbrs0i4uUJlvoDRA9PvYc/U0x/zKUtGGP1J2XsCOgepOsCeyKdz9xl/S7KJEwv/TcZvDjbuxW6idxsq1DlhRh6oqJE0lT92Ua81zegPmRDIRtJfXif0H+ANxHAbYUYrQ08nKlDORra7SjM0LrKCfwOwfPhwA2zkh8JJn3NgUIFDsm2I=");

                tablistHelper.setHeader(ChatUtil.fixColor(Arrays.asList("", HexColor.HEADER_I.getAsHex() + "&lI" + HexColor.HEADER_C.getAsHex() + "c" + HexColor.HEADER_E.getAsHex() + "e" + HexColor.HEADER_H.getAsHex() + "H" + HexColor.HEADER_A.getAsHex() + "a" + HexColor.HEADER_R.getAsHex() + "r" + HexColor.HEADER_D.getAsHex() + "d" + HexColor.HEADER_P.getAsHex() + ".p" + HexColor.HEADER_L.getAsHex() + "l", " &fOnline: &9{ONLINE_COUNT}", "")).stream().map(s -> {
                    s = s.replace("{ONLINE_COUNT}", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    return s;
                }).collect(Collectors.toList()));

                tablistHelper.setFooter(ChatUtil.fixColor(Arrays.asList("", "&6✶ &fŻyczymy Miłej Gry &6✶", " &fdiscord.gg/&9icehardpl", "")).stream().map(s -> {
                    s = s.replace("{ONLINE_COUNT}", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    return s;
                }).collect(Collectors.toList()));

                List<String> tabList = ChatUtil.fixColor(Stream.of(
                        "",
                        "    &9&lINFORMACJE",
                        "",
                        " &fNick: &9{NICKNAME}",
                        " &fRanga: &9{GROUP}",
                        " &fSektor: &9sektor_01",
                        " &fProxy: &9proxy_01",
                        " &fPing &9{PING}",
                        " &fGodzina: &9{HOUR}",
                        " &fIlosc gildii: &9{GUILDS_INTALL}",
                        "",
                        "    &9&lSTATYSTYKI",
                        " ",
                        " &fPunkty: &9{POINTS}",
                        " &fPozycja: &9{POSSITION}",
                        " &fZabojstwa: &9{KILLS}",
                        " &fSmierci: &9{DEATHS}",
                        " &fAsysty: &9{ASSISTS}",
                        " &fPoziom: &9{LEVEL}",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "    &9&lTWOJA GILDIA",
                        "",
                        " &fTag: &9{GUILD_NAME}",
                        " &fPunkty: &9{GUILD_RANK}",
                        " &fPozycja: &9",
                        " &fZabójstwa: &9{GUILD_KILLS}",
                        " &fŚmierci: &9{GUILD_DEATHS}",
                        " &fCzłonków: &9{GUILD_ONLINE}",
                        " &fŻycia gildi: &9",
                        "",
                        "",
                        "    &9&lPORADNIK",
                        "",
                        " &fListe komend znajdziesz: &9/pomoc",
                        " &fZarzadzanie chatem: &9/cc",
                        " &fKomendy gildyjne: &9/g",
                        " &fDrop ze stone: &9/drop",
                        " &fUstawienia serwerowe: &9/ustawienia",
                        ""
                        ).collect(Collectors.toList()));

                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

                Guild guild = this.plugin.getGuildManager().getGuildByPlayer(player);
                User user = this.plugin.getUserManager().getUser(player.getUniqueId());

                tablistHelper.setCells(ChatUtil.fixColor(tabList.stream().map(s -> {
                    s = s.replace("{NICKNAME}", player.getName());
                    s = s.replace("{PING}", String.valueOf(((CraftPlayer) player).getHandle().ping));
                    s = s.replace("{LEVEL}", String.valueOf(player.getLevel()));
                    s = s.replace("{POINTS}", String.valueOf(user.getPoints()));
                    s = s.replace("{KILLS}", String.valueOf(user.getKills()));
                    s = s.replace("{DEATHS}", String.valueOf(user.getDeaths()));
                    s = s.replace("{GROUP}", user.getGroup().getFullName());
                    s = s.replace("{HOUR}", DateUtil.getCurrentHour());
                    s = s.replace("{KD}", String.valueOf(00.00));
                    s = s.replace("{GUILD_NAME}", (guild == null ? "&fBrak" : guild.getGuildTag().toUpperCase()));
                    s = s.replace("{GUILD_OWNER}", (guild == null ? "&fBrak" : guild.getGuildOwnerNickName()));
                    s = s.replace("{GUILD_FULLNAME}", (guild == null ? "&fBrak" : guild.getGuildName()));
                    s = s.replace("{GUILD_KD}", (guild == null ? "&fBrak" : String.valueOf(0)));
                    s = s.replace("{GUILD_RANK}", (guild == null ? "&fBrak" : String.valueOf(guild.getGuildPoints())));
                    s = s.replace("{GUILD_KILLS}", (guild == null ? "&fBrak" : String.valueOf(guild.getGuildKills())));
                    s = s.replace("{GUILD_DEATHS}", (guild == null ? "&fBrak" : String.valueOf(guild.getGuildDeaths())));
                    s = s.replace("{GUILD_ONLINE}", (guild == null ? "&fBrak" : String.valueOf(guild.getOnlineMembers().size())));
                    s = s.replace("{GUILDS_INTALL}", (guild == null ? "&fBrak gildii" : String.valueOf(this.plugin.getGuildManager().getGuilds().size())));
//                            s = s.replace("{INCOGNITO}", (user.isIncognito() ? "&awłączone" : "&cwyłączone"));

                    // users
                    for (int i = 1; i <= 65; i++) {
                        String userPlaceholder = "{USER_" + i + "}";
                        if (i <= players.size()) {
                            Player player1 = players.get(i - 1);

                            s = s.replace(userPlaceholder, (guild == null ? "" : "&8[&b" + guild.getGuildTag() + "&8]" + " &f" + player1.getName()));
                        } else {
                            s = s.replace(userPlaceholder, "");
                        }
                    }

//                    for (int i = 1; i <= 30; i++) {
//                                String userPlaceholder = "{USER_" + i + "}";
//                                if (i <= BukkitPlugin.getInstance().getUserManager().getUsers().size()) {
//                                    User topUser = users.get(i - 1);
//                                    s = s.replace(userPlaceholder, "&3[" + topUser.getNick() + " &7(&8" + topUser.getStatisticValue(StatsType.POINTS) + "&7)");
//                                } else {
//                                    s = s.replace(userPlaceholder, "");
//                                }
//                            }


                    return s;
                }).collect(Collectors.toList())));


            }).send(SendType.UPDATE);
        });
    }
}
