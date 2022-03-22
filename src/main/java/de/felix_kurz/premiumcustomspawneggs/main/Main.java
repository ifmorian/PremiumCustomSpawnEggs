package de.felix_kurz.premiumcustomspawneggs.main;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.listeners.PrepareItemCraftListener;
import de.felix_kurz.premiumcustomspawneggs.listeners.EntityDeathListener;
import de.felix_kurz.premiumcustomspawneggs.listeners.PlayerJoinListener;
import de.felix_kurz.premiumcustomspawneggs.listeners.PlayerResourcePackStatusListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static ConfigurationManager cfgM;
    private static Main plugin;

    public void onEnable() {
        plugin = this;
        cfgM = new ConfigurationManager(this);

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerResourcePackStatusListener(), this);
        Bukkit.getPluginManager().registerEvents(new PrepareItemCraftListener(), this);
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
