package de.felix_kurz.premiumcustomspawneggs.main;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.listeners.EntityDeathListener;
import de.felix_kurz.premiumcustomspawneggs.listeners.PlayerJoinListener;
import de.felix_kurz.premiumcustomspawneggs.recipes.CorePieceRecipe;
import de.felix_kurz.premiumcustomspawneggs.recipes.FullCoreRecipe;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static ConfigurationManager cfgM;

    public void onEnable() {
        cfgM = new ConfigurationManager(this);

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);

        Bukkit.addRecipe(new FullCoreRecipe(this).getRecipe());
        Bukkit.addRecipe(new CorePieceRecipe(this).getRecipe());
    }

    public void onDisable() {

    }

    public static ConfigurationManager getCfgM() {
        return cfgM;
    }
}
