package de.felix_kurz.premiumcustomspawneggs.configuration;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

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

    public double getDropProbability(String s) {
        return cfg.getDouble("cores.shard.mobDroprate." + s);
    }

    public ConfigurationSection getEggs() {
        return cfg.getConfigurationSection("eggs");
    }

    public ConfigurationSection getEgg(String egg) {
        return (ConfigurationSection) cfg.get("eggs." + egg);
    }

    public CustomMob getMob(String mob, UUID owner) {
        return new CustomMob(
                owner,
                mob,
                cfg.getString("mobs." + mob + ".name"),
                cfg.getString("mobs." + mob + ".type"),
                cfg.getInt("mobs." + mob + ".health"),
                (float) cfg.getDouble("mobs." + mob + ".speed"),
                cfg.getBoolean("mobs." + mob + ".multiRemote"),
                cfg.getBoolean("mobs." + mob + ".dropsOnDeath"),
                cfg.getBoolean("mobs." + mob + ".dropsOnExplosion"),
                cfg.getInt("mobs." + mob + ".explosionRadius"),
                cfg.getInt("mobs." + mob + ".explosionDamage"),
                cfg.getString("mobs." + mob + ".explosionPotion"),
                cfg.getInt("mobs." + mob + ".explosionPotionDuration"),
                cfg.getInt("mobs." + mob + ".explosionPotionAmplifier"),
                cfg.getDouble("mobs." + mob + ".explosionPower"),
                cfg.getInt("mobs." + mob + ".lavaRadius"),
                cfg.getDouble("mobs." + mob + ".explosionBreakBlockChance"),
                cfg.getDouble("mobs." + mob + ".explosionDropBlockChance"),
                cfg.getInt("mobs." + mob + ".explosionTimer"),
                cfg.getBoolean("mobs." + mob + ".randomStroll"),
                (float) cfg.getDouble("mobs." + mob + ".strollSpeed"),
                cfg.getString("mobs." + mob + ".attackEntities"),
                cfg.getInt("mobs." + mob + ".attackDamage"),
                cfg.getInt("mobs." + mob + ".attackSpeed"),
                (float) cfg.getDouble("mobs." + mob + ".walkToTargetSpeed"),
                cfg.getInt("mobs." + mob + ".attackTriggerRange"),
                cfg.getBoolean("mobs." + mob + ".multiAttack"),
                cfg.getString("mobs." + mob + ".breakBlocks"),
                cfg.getInt("mobs." + mob + ".breakDamage"),
                cfg.getInt("mobs." + mob + ".breakSpeed"),
                (float) cfg.getDouble("mobs." + mob + ".walkToBlockSpeed"),
                cfg.getInt("mobs." + mob + ".breakTriggerRange"),
                cfg.getBoolean("mobs." + mob + ".multiBreak"),
                cfg.getBoolean("mobs." + mob + ".prioritizeBlocks")
        );
    }

}
