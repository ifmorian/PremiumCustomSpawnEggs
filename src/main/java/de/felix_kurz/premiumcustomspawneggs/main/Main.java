package de.felix_kurz.premiumcustomspawneggs.main;

import de.felix_kurz.premiumcustomspawneggs.commands.GiveEggCommand;
import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.listeners.*;
import de.felix_kurz.premiumcustomspawneggs.recipes.CustomEgg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin {

    private static ConfigurationManager cfgM;
    private static Main plugin;
    private static Scoreboard sb;

    public static final ConsoleCommandSender c = Bukkit.getConsoleSender();
    public static final String PRE = "§f[§dPremiumCustomSpawnEggs§f] ";

    public void onEnable() {
        plugin = this;
        cfgM = new ConfigurationManager(this);

        CustomEgg.setupEggs();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerResourcePackStatusListener(), this);
        Bukkit.getPluginManager().registerEvents(new PrepareItemCraftListener(), this);

        getCommand("giveegg").setExecutor(new GiveEggCommand());

        sb = this.getServer().getScoreboardManager().getMainScoreboard();
        for (ChatColor color : ChatColor.values()) {
            Team team = sb.registerNewTeam(color.asBungee().getName());
            team.setColor(color);
        }
    }

    public static Scoreboard getScoreboard() {
        return sb;
    }

    public void onDisable() {
        Bukkit.clearRecipes();
    }

    public static ConfigurationManager getCfgM() {
        return cfgM;
    }
    public static Main getPlugin() {
        return plugin;
    }

}
