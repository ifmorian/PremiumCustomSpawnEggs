package de.felix_kurz.premiumcustomspawneggs.configuration;

import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
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

    public String getCoreName(String core) {
        return cfg.getString("cores." + core + ".name");
    }

    public boolean isCoreEnchanted(String core) {
        return cfg.getBoolean("cores." + core + ".enchanted");
    }

    public Material getCoreMaterial(String core) {
        return Material.getMaterial(cfg.getString("cores." + core + ".item").toUpperCase());
    }

    public int getCoreCustomDataModel(String core) {
        return cfg.getInt("cores." + core + ".customModelData");
    }

    public int getCoreRecipe(String core) {
        return cfg.getInt("cores." + core + ".crafting");
    }

    public ConfigurationSection getShardMobs() {
        return (ConfigurationSection) cfg.get("cores.shard.mobDroprate");
    }

    public ConfigurationSection getEgg(String egg) {
        return (ConfigurationSection) cfg.get("eggs." + egg);
    }

}
