package de.felix_kurz.premiumcustomspawneggs.entities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class CustomMob {

    public static int idd;
    public static LivingEntity e;

    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public String id;
    public String name;
    public String type;
    public int health;
    public double speed;
    public double explosionRadius;
    public int explosionDamage;
    public boolean explosionBreakBlocks;
    public int explosionTimer;

    public static HashMap<String, CustomMob> mobs = new HashMap<>();

    public CustomMob(String id, String name, String type, int health, double speed, double explosionRadius, int explosionDamage, boolean explosionBreakBlocks, int explosionTimer) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.health = health;
        this.speed = speed;
        this.explosionRadius = explosionRadius;
        this.explosionDamage = explosionDamage;
        this.explosionBreakBlocks = explosionBreakBlocks;
        this.explosionTimer = explosionTimer;
    }

    public void spawnEntity(Location l, UUID player) {
        Level level = ((CraftWorld) l.getWorld()).getHandle();

        LivingEntity entity;

        BukkitTask r = new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(Main.getPlugin(), 5, 5);
        CompoundTag tag = new CompoundTag();
        tag.putUUID("pcse_control", player);
        tag.putInt("pcse_runnable", r.getTaskId());

        switch(type.toLowerCase()) {
            case "zombie" -> {
                entity = new Zombie(EntityType.ZOMBIE, level);
                ((Zombie) entity).goalSelector.removeAllGoals();
            }
            default -> {
                entity = new Zombie(EntityType.ZOMBIE, level);
                ((Zombie) entity).goalSelector.removeAllGoals();
            }
        }
        entity.setPos(l.getX(), l.getY(), l.getZ());
        idd = entity.getId();
        e = entity;
        level.addFreshEntity(entity);

        new BukkitRunnable() {
            @Override
            public void run() {
                explode(entity, explosionRadius, explosionDamage, explosionBreakBlocks);
            }
        }.runTaskLater(Main.getPlugin(), explosionTimer);

    }

    private void explode(LivingEntity entity, double explosionRadius, int explosionDamage, boolean explosionBreakBlocks) {
        if (entity.isAlive()) {
            Location l = entity.getBukkitEntity().getLocation();
            PacketContainer packet = manager.createPacket(PacketType.Play.Server.EXPLOSION);
            packet.getDoubles().write(0, l.getX());
            packet.getDoubles().write(1, l.getY());
            packet.getDoubles().write(2, l.getZ());
            packet.getFloat().write(0, (float) explosionRadius);
            manager.broadcastServerPacket(packet);
            entity.getBukkitEntity().getNearbyEntities(explosionRadius, explosionRadius, explosionRadius).forEach(ent -> {
                if (ent instanceof org.bukkit.entity.LivingEntity ent1) {
                    ent1.damage(explosionDamage);
                }
            });
            entity.kill();
        }
    }

}
