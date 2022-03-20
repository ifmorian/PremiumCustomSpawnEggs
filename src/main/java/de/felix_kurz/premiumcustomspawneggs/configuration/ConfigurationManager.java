package de.felix_kurz.premiumcustomspawneggs.configuration;

import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationManager {

    private Main plugin;
    private FileConfiguration cfg;

    public ConfigurationManager(Main plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        cfg = plugin.getConfig();
    }

    public String getResourcePackLink() {
        return cfg.getString("resourcepack");
    }

}
