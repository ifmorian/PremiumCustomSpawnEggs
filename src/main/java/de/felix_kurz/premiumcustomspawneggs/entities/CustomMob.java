package de.felix_kurz.premiumcustomspawneggs.entities;

import de.felix_kurz.premiumcustomspawneggs.entities.nmsentities.CustomZombie;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;

import java.util.HashMap;

public class CustomMob {

    private String id;
    private String name;
    private EntityType type;
    private int health;

    private Entity entity;

    public static HashMap<String, CustomMob> mobs = new HashMap<>();

    public CustomMob(String id, String name, EntityType type, int health, Entity entity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.health = health;
        this.entity = entity;
    }

    public void spawnEntity(Location l) {
        Entity entity = l.getWorld().spawnEntity(l, type);

        entity.setCustomNameVisible(true);
        entity.setCustomName(name);
    }
}
