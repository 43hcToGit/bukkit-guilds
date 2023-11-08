package net.juxyc.guilds;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.valid.ValidationInfo;
import dev.rollczi.litecommands.valid.messages.UseSchemeFormatting;
import lombok.Getter;
import net.juxyc.guilds.commands.argument.IntegerArgument;
import net.juxyc.guilds.commands.argument.NameArgument;
import net.juxyc.guilds.commands.argument.NickArgument;
import net.juxyc.guilds.commands.argument.TagArgument;
import net.juxyc.guilds.commands.impl.normal.GuildCommand;
import net.juxyc.guilds.database.MongoManager;
import net.juxyc.guilds.listeners.PlayerDeathListener;
import net.juxyc.guilds.listeners.PlayerJoinListener;
import net.juxyc.guilds.listeners.PlayerMoveListener;
import net.juxyc.guilds.managers.GuildManager;
import net.juxyc.guilds.managers.UserManager;
import net.juxyc.guilds.managers.packet.UserPacketManager;
import net.juxyc.guilds.scoreboard.ScoreBoardManager;
import net.juxyc.guilds.tasks.ScoreBoardUpdateTask;
import net.juxyc.guilds.tasks.UpdateTask;
import net.juxyc.guilds.utils.reflection.ReflectionHelper;
import org.bukkit.Server;
import org.bukkit.scoreboard.Score;

@Getter
public final class BukkitPlugin extends BukkitPluginDelegator {

    private LiteCommands liteCommands;

    private MongoManager mongoManager;

    private GuildManager guildManager;

    private UserManager userManager;

    private ScoreBoardManager scoreBoardManager;

    private UserPacketManager userPacketManager;


    @Override
    public void load() {
    }

    @Override
    public void enable() {
        new ReflectionHelper().initialize();
        this.mongoManager = new MongoManager("mongodb://localhost:27017", "admin");
        this.guildManager = new GuildManager();
        this.guildManager.load();
        this.userManager = new UserManager();
        this.userManager.load();
        this.scoreBoardManager = new ScoreBoardManager();
        this.userPacketManager = new UserPacketManager();

        this.registerCommands();

        this.registerListeners(
                new PlayerDeathListener(),
                new PlayerMoveListener(),
                new PlayerJoinListener()
        );

        new UpdateTask(this).start();
        new ScoreBoardUpdateTask(this).start();
    }

    public void registerCommands() {
        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "guild_main")
                .typeBind(BukkitPlugin.class, getPlugin(BukkitPlugin.class))
                .typeBind(Server.class, this.getServer())
                .argument(String.class, new TagArgument())
                .argument(String.class, new NameArgument())
                .argument(String.class, new NickArgument())
                .argument(Integer.class, new IntegerArgument())
                .formattingUseScheme(UseSchemeFormatting.NORMAL)
                //.message(ValidationInfo.NO_PERMISSION, new PermissionMessage())
                //.message(ValidationInfo.INVALID_USE, new UsageMessage())
                .command(GuildCommand.class)
                .register();
    }

    @Override
    public void disable() {
        super.disable();
    }

    public static BukkitPlugin getInstance(){
        return getPlugin(BukkitPlugin.class);
    }
}
