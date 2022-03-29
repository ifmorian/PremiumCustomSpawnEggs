package de.felix_kurz.premiumcustomspawneggs.entities;

import com.comphenix.packetwrapper.WrapperPlayServerExplosion;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.felix_kurz.premiumcustomspawneggs.entities.pathfindergoals.WalkToLocationGoal;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class CustomMob {

    public static int idd;
    public static LivingEntity e;

    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public LivingEntity entity;

    public UUID owner;

    public String id;
    public String name;
    public String type;
    public int health;
    public float speed;
    public boolean multiRemote;
    public boolean dropOnDeath;
    public boolean dropOnExplosion;
    public int explosionRadius;
    public int explosionDamage;
    public double exlosionPower;
    public double explosionBreakBlockChance;
    public double explosionDropBlockChance;
    public int explosionTimer;

    public static HashMap<Integer, CustomMob> mobs = new HashMap<>();

    public CustomMob(UUID owner, String id, String name, String type, int health, float speed, boolean multiRemote, boolean dropOnDeath, boolean dropOnExplosion, int explosionRadius, int explosionDamage, double explosionPower, double explosionBreakBlocksChance, double explosionDropBlockChance, int explosionTimer) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        this.type = type;
        this.health = health;
        this.speed = speed;
        this.multiRemote = multiRemote;
        this.dropOnDeath = dropOnDeath;
        this.dropOnExplosion = dropOnExplosion;
        this.explosionRadius = explosionRadius;
        this.explosionDamage = explosionDamage;
        this.exlosionPower = explosionPower;
        this.explosionBreakBlockChance = explosionBreakBlocksChance;
        this.explosionDropBlockChance = explosionDropBlockChance;
        this.explosionTimer = explosionTimer;
    }

    public void spawnEntity(Location l) {
        Level level = ((CraftWorld) l.getWorld()).getHandle();

        BukkitTask r = new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(Main.getPlugin(), 5, 5);

        entity = new PathfinderMob(EntTypes.valueOf(type.toUpperCase()).type, level) {
            @Override
            public float getWalkTargetValue(BlockPos blockposition) {
                return super.getWalkTargetValue(blockposition);
            }
        };
        entity.setPos(l.getX(), l.getY(), l.getZ());

        CompoundTag tag = new CompoundTag();
        tag.putUUID("pcse_control", owner);
        tag.putInt("pcse_runnable", r.getTaskId());

        entity.addTag(String.valueOf(tag));

        idd = entity.getId();
        e = entity;
        level.addFreshEntity(entity);
        ((PathfinderMob) entity).goalSelector.removeAllGoals();
        ((PathfinderMob) entity).goalSelector.addGoal(0, new WalkToLocationGoal(entity, l.add(new Vector(10, 0, 0)), speed));

        entity.getBukkitEntity().setCustomName(name);
        ((org.bukkit.entity.LivingEntity) entity.getBukkitEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        entity.setHealth(health);

        if (!entity.getBukkitEntity().getNearbyEntities(0.01, 0.01, 0.01).isEmpty()) entity.push(Math.random() / 10 - 0.05, 0.01, Math.random() / 10 - 0.05);


        new BukkitRunnable() {
            @Override
            public void run() {
                explode();
            }
        }.runTaskLater(Main.getPlugin(), explosionTimer);

    }

    private void explode() {
        if (entity.isAlive()) {
            Location l = entity.getBukkitEntity().getLocation();
            WrapperPlayServerExplosion packet = new WrapperPlayServerExplosion();
            packet.setRadius(explosionRadius);
            packet.setX(l.getX());
            packet.setY(l.getY());
            packet.setZ(l.getZ());
            packet.broadcastPacket();
            entity.getBukkitEntity().getNearbyEntities(explosionRadius, explosionRadius, explosionRadius).forEach(ent -> {
                if (ent instanceof org.bukkit.entity.LivingEntity ent1) {
                    if (ent1.getUniqueId().equals(owner)) return;
                    ent1.damage(explosionDamage, Bukkit.getPlayer(owner));
                    ent1.setVelocity(new Vector(ent1.getLocation().getX() - l.getX(), ent1.getLocation().getY() - l.getY() + 0.001, ent1.getLocation().getZ() - l.getZ()).normalize().multiply(exlosionPower).add(new Vector(0,0.2 * exlosionPower,0)));
                }
            });
            //From Synapz (https://www.spigotmc.org/members/synapz.93056/)
            int bx = l.getBlockX();
            int by = l.getBlockY();
            int bz = l.getBlockZ();
            for(int x = bx - explosionRadius; x <= bx + explosionRadius; x++) {
                for(int y = by + explosionRadius; y >= by - explosionRadius; y--) {
                    for(int z = bz - explosionRadius; z <= bz + explosionRadius; z++) {
                        double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                        if(distance < explosionRadius * explosionRadius) {
                            if (explosionBreakBlockChance <= Math.random()) continue;
                            if (explosionDropBlockChance > Math.random()) {
                                l.getWorld().getBlockAt(x, y, z).breakNaturally();
                            } else {
                                l.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                            }
                        }
                    }
                }
            }
            entity.kill();
        }
    }

}
