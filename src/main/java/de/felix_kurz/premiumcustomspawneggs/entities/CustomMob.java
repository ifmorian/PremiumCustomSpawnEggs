package de.felix_kurz.premiumcustomspawneggs.entities;

import de.felix_kurz.premiumcustomspawneggs.entities.nmsentities.CustomZombie;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;

import java.util.HashMap;

public class CustomMob {

    public String id;
    public String name;
    public String type;
    public int health;

    public static HashMap<String, CustomMob> mobs = new HashMap<>();

    public CustomMob(String id, String name, String type, int health) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.health = health;
    }

    public void spawnEntity(Location l) {
        ServerLevel world = ((CraftWorld) l.getWorld()).getHandle();

        Entity nmsEntity = new Zombie(world);

        nmsEntity.setCustomName(Component.nullToEmpty(name));
        ((LivingEntity) nmsEntity).setHealth(200);
        nmsEntity.setPos(l.getX(), l.getY(), l.getZ());
        world.tryAddFreshEntityWithPassengers(nmsEntity);
    }
}
