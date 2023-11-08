package net.juxyc.guilds.tablist;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.juxyc.guilds.tablist.type.SendType;
import net.juxyc.guilds.utils.reflection.PacketHelper;
import net.juxyc.guilds.utils.reflection.ReflectionHelper;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@Setter
public class TablistHelper {

    protected static final String UUID_PATTERN = "00000000-0000-%s-0000-000000000000";

    private static final Class<?> IChatBaseComponentClass = ReflectionHelper.getNmsClass("IChatBaseComponent");
    private static final Class<?> ChatSerializerClass = IChatBaseComponentClass.getDeclaredClasses()[0];
    private static final Class<?> PacketPlayOutPlayerListHeaderFooterClass = ReflectionHelper.getNmsClass("PacketPlayOutPlayerListHeaderFooter");
    private static final Class<?> PacketPlayOutPlayerInfoClass = ReflectionHelper.getNmsClass("PacketPlayOutPlayerInfo");
    private static final Class<?> PlayerInfoDataClass = ReflectionHelper.getNmsClass("PacketPlayOutPlayerInfo$PlayerInfoData");
    private static final Class<?> EnumPlayerInfoActionClass = ReflectionHelper.getNmsClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
    private static final Class<?> EnumGamemodeClass = ReflectionHelper.getNmsClass("WorldSettings$EnumGamemode");

    private static final Enum<?> ADD_PLAYER = (Enum<?>) EnumPlayerInfoActionClass.getEnumConstants()[0];
    private static final Enum<?> UPDATE_PLAYER = (Enum<?>) EnumPlayerInfoActionClass.getEnumConstants()[3];
    private static final Enum<?> REMOVE_PLAYER = (Enum<?>) EnumPlayerInfoActionClass.getEnumConstants()[4];
    private static final Enum<?> ENUMGAMEMODE = (Enum<?>) EnumGamemodeClass.getEnumConstants()[2];

    private static Constructor<?> PlayerInfoDataConstructor = PlayerInfoDataClass.getDeclaredConstructors()[0];

    private static final Method serializeMethod = ReflectionHelper.getMethod(ChatSerializerClass, "a", String.class);

    private final Player player;
    private final PacketHelper packetHelper;

    private GameProfile[] gameProfiles = new GameProfile[80];

    private List<String> header = new ArrayList<>(),
            footer = new ArrayList<>(),
            cells = new ArrayList<>(),
            safed = new ArrayList<>();
//eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjVhNzcwNGI3MzIxMjk0NmY0OGYyMWRmZGQ2ZmNhNjhlM2M2ODBlOTI4Mzk0OTg1MGQ0ZGFjZjEyYTFlYzk4ZiJ9fX0=
    private String signature = "vU6MFoqrtLNqz3f9uonF/D5JwAZr6ZQb6TgvFc4imh8lXAt6fOpuwg4AXSurxPvsmctD2egDYBCbjJnmnGHzrUnf4unQwbOZGg9ZgkBSO3pQ+FKUyaiYnKI9ArK3MOxC994YH0LLc+0PfVo6tqajUlnVM7PtY3kMxyqeQG34oa5y0eCSNyBc7+g3hUSweeIXwbr9xTsJ5SvkFYmDTA2jDsZSJwiWAi90e8qjZvoklz7MRcbm4ibZDz+2uPGqmoklD7Uhg2rA8Vj+D6W9JN3WBAON3fs1pYS0PCHCl3XANCaZobovby3MPpt3KIFs9qORB1+R0x/St245eU10g+5D5hqly2sDcJNUzGlrLTKGa3LIVI1X2PeJ5zK573SSDR31TA+CCndWa+Vle/c3Lvn7/FopS4/zfPhO6G/XwP1khgTFRc5qoFtVJ6qNztCmTlTTBbdnFegv6WMljF+x9b163OhSoQd1/qZgd7+q2dXnEM1uxME249Cmv2/XpTS2XRkQREPGKPokB59NophFSHbyNJKGs0qMjYvqF/E85jmvWHfBWzK4yM3jxryNMuYJzTwBXNmZ2YY6MaZorWAk5Z+yuR5vFTZbc/MTQzV1Si7B/xt6RlHY8C+YDoPMvKVwAkqbqFgCfRwfJM4VaCcL2RbWT48OXJwuUy/BTyLosRQobas=",
            texture = "ewogICJ0aW1lc3RhbXAiIDogMTYzNTc5NzE2MjQ3MiwKICAicHJvZmlsZUlkIiA6ICJiNzQ3OWJhZTI5YzQ0YjIzYmE1NjI4MzM3OGYwZTNjNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTeWxlZXgiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODYxYWJkMDEzZmRjOTBkMGViM2RiMmI4MTc4NmNjMjhiNzEzOGJmYmI4ZjkxZDZlMmRiMmI1NTdmZGNjMGQwNiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9";
    private int ping;

    private boolean first = true;

    public TablistHelper(Player player, PacketHelper packetHelper) {
        this.player = player;

        if (packetHelper != null) this.packetHelper = packetHelper;
        else this.packetHelper = new PacketHelper(player);
    }

    public TablistHelper apply(Consumer<TablistHelper> consumer) {
        consumer.accept(this);
        return this;
    }

    @SneakyThrows
    public void send(SendType sendType) {
        if (sendType.equals(SendType.UPDATE)) {

            safed.clear();

            Object packetPlayOutPlayerListHeaderFooter = PacketPlayOutPlayerListHeaderFooterClass.newInstance();

            StringBuilder text = new StringBuilder();
            for (String s : header) {
                text.append(ChatColor.RESET).append("\n").append(s);
            }

            Object component = ReflectionHelper.invoke(serializeMethod, null ,"{\"text\":\"" + text + "\"}");

            Field header = PacketPlayOutPlayerListHeaderFooterClass.getDeclaredField("a");
            header.setAccessible(true);
            header.set(packetPlayOutPlayerListHeaderFooter, component);

            text = new StringBuilder();

            for (String s : footer) {
                text.append(ChatColor.RESET).append("\n").append(s);
            }

            component = ReflectionHelper.invoke(serializeMethod, null ,"{\"text\":\"" + text + "\"}");

            ReflectionHelper.setFieldValue(packetPlayOutPlayerListHeaderFooter, "b", component);

            Field footer = PacketPlayOutPlayerListHeaderFooterClass.getDeclaredField("b");
            footer.setAccessible(true);
            footer.set(packetPlayOutPlayerListHeaderFooter, component);

            Object packetPlayOutPlayerInfo = ReflectionHelper.newInstance(PacketPlayOutPlayerInfoClass);

            List<Object> playerInfoDataList = new ArrayList<>();

            for (int i = 0; i < 80; i++) {
                GameProfile gameProfile = gameProfiles[i];
                if(gameProfile == null){
                    gameProfile = new GameProfile(UUID.fromString(String.format(UUID_PATTERN, StringUtils.leftPad(String.valueOf(i), 2, '0'))), "");
                    if(!signature.isEmpty() && !texture.isEmpty()){
                        gameProfile.getProperties().removeAll("textures");
                        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
                    }
                    gameProfiles[i] = gameProfile;
                }

                String textLine = getTextLineByCells(i);
                safed.add(textLine);

                component = ReflectionHelper.invoke(serializeMethod, null ,"{\"text\":\"" + textLine + "\"}");
                playerInfoDataList.add(ReflectionHelper.newInstance(PlayerInfoDataConstructor, packetPlayOutPlayerInfo, gameProfile, ping, ENUMGAMEMODE, component));
            }

            if (first) {
                ReflectionHelper.setFieldValue(packetPlayOutPlayerInfo, "a", ADD_PLAYER);
                first = false;
            } else ReflectionHelper.setFieldValue(packetPlayOutPlayerInfo, "a", UPDATE_PLAYER);

            Field field = PacketPlayOutPlayerInfoClass.getDeclaredField("b");
            field.setAccessible(true);
            field.set(packetPlayOutPlayerInfo, playerInfoDataList);
            field.setAccessible(false);

            packetHelper.addPackets(packetPlayOutPlayerListHeaderFooter, packetPlayOutPlayerInfo);
        } else {
            if (first) return;

            Object packetPlayOutPlayerListHeaderFooter = ReflectionHelper.newInstance(PacketPlayOutPlayerListHeaderFooterClass);
            Object component = ReflectionHelper.invoke(serializeMethod, null, "{\"text\":\"\"}");

            ReflectionHelper.setFieldValue(packetPlayOutPlayerListHeaderFooter, "header", component);
            ReflectionHelper.setFieldValue(packetPlayOutPlayerListHeaderFooter, "footer", component);

            packetHelper.addPacket(packetPlayOutPlayerListHeaderFooter);

            Object packetPlayOutPlayerInfo = ReflectionHelper.newInstance(PacketPlayOutPlayerInfoClass);
            List<Object> playerInfoDataList = new ArrayList<>();

            for (int i = 0; i < 80; i++) {
                component = ReflectionHelper.invoke(serializeMethod, null, "{\"text\":\"" + safed.get(i) + "\"}");
                playerInfoDataList.add(ReflectionHelper.newInstance(PlayerInfoDataConstructor, packetPlayOutPlayerInfo, gameProfiles[i], ping, ENUMGAMEMODE, component));
            }

            ReflectionHelper.setFieldValue(packetPlayOutPlayerInfo, "a", REMOVE_PLAYER);
            ReflectionHelper.setFieldValue(packetPlayOutPlayerInfo, "b", playerInfoDataList);

            safed.clear();

            packetHelper.addPacket(packetPlayOutPlayerInfo);
        }
        packetHelper.send();
    }


    private String getTextLineByCells(int id) {
        if(cells.isEmpty()) return " ";
        return cells.size() <= id ? " " : cells.get(id);
    }
}
