package de.felix_kurz.premiumcustomspawneggs.entities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class CustomMob {

    public static int idd;
    public static LivingEntity e;

    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public LivingEntity entity;

    public String id;
    public String name;
    public String type;
    public int health;
    public double speed;
    public boolean dropOnDeath;
    public boolean dropOnExplosion;
    public double explosionRadius;
    public int explosionDamage;
    public double explosionBreakBlockChance;
    public double explosionDropBlockChance;
    public int explosionTimer;

    public static HashMap<String, CustomMob> mobs = new HashMap<>();

    public CustomMob(String id, String name, String type, int health, double speed, boolean dropOnDeath, boolean dropOnExplosion, double explosionRadius, int explosionDamage, double explosionBreakBlocksChance, double explosionDropBlockChance, int explosionTimer) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.health = health;
        this.speed = speed;
        this.dropOnDeath = dropOnDeath;
        this.dropOnExplosion = dropOnExplosion;
        this.explosionRadius = explosionRadius;
        this.explosionDamage = explosionDamage;
        this.explosionBreakBlockChance = explosionBreakBlocksChance;
        this.explosionDropBlockChance = explosionDropBlockChance;
        this.explosionTimer = explosionTimer;
    }

    public void spawnEntity(Location l, UUID player) {
        Level level = ((CraftWorld) l.getWorld()).getHandle();

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
                explode();
            }
        }.runTaskLater(Main.getPlugin(), explosionTimer);

    }

    private void explode() {
        if (entity.isAlive()) {
            Location l = entity.getBukkitEntity().getLocation();
            PacketContainer packet1 = manager.createPacket(PacketType.Play.Server.EXPLOSION);
            packet1.getDoubles().write(0, l.getX());
            packet1.getDoubles().write(1, l.getY());
            packet1.getDoubles().write(2, l.getZ());
            packet1.getFloat().write(0, (float) explosionRadius);
            manager.broadcastServerPacket(packet1);
            entity.getBukkitEntity().getNearbyEntities(explosionRadius, explosionRadius, explosionRadius).forEach(ent -> {
                if (ent instanceof org.bukkit.entity.LivingEntity ent1) {
                    ent1.damage(explosionDamage);
                }
            });
            if (explosionBreakBlocks) {
                for (int x = (int) (l.getX() - explosionRadius); x <= l.getX() + explosionRadius; x++) {
                    for (int y = (int) (l.getY() - explosionRadius); y <= l.getY() + explosionRadius; y++) {
                        for (int z = (int) (l.getZ() - explosionRadius); z <= l.getZ() + explosionRadius; z++) {
                            if (explosionBreakBlockChance > Math.random()) {
                                if (explosionDropBlockChance > Math.random()) {
                                    l.getWorld().getBlockAt(x, y, z).breakNaturally();
                                } else {
                                    PacketContainer packet2 = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK);
                                    packet2.getIntegers().write(0, entity.getId());
                                    packet2.getIntegers().write(1,((x & 0x3FFFFFF) << 38) | ((z & 0x3FFFFFF) << 12) | (y & 0xFFF));
                                    packet2.getBytes().write(0, (byte) 10);
                                    manager.broadcastServerPacket(packet2);
                                }
                            }
                        }
                    }
                }
            }
            entity.kill();
        }
    }

}
