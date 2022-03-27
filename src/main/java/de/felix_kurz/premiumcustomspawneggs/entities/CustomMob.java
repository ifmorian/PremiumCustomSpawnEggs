package de.felix_kurz.premiumcustomspawneggs.entities;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.C;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Supplier;

public class CustomMob {

    public static int idd;
    public static LivingEntity e;

    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public String id;
    public String name;
    public String type;
    public double health;
    public double speed;

    public static HashMap<String, CustomMob> mobs = new HashMap<>();

    public CustomMob(String id, String name, String type, double health, double speed) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.health = health;
        this.speed = speed;
    }

    public void spawnEntity(Location l, UUID player) {
        Level level = ((CraftWorld) l.getWorld()).getHandle();

        LivingEntity entity;

        BukkitTask r = new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskTimer(Main.getPlugin(), 10, 10);
        CompoundTag tag = new CompoundTag();
        tag.putUUID("pcse_control", player);
        tag.putInt("pcse_runnable", r.getTaskId());

        switch(type.toLowerCase()) {
            case "zombie" -> {
                entity = new Zombie(EntityType.ZOMBIE, level);
                entity.setPos(l.getX(), l.getY(), l.getZ());
            }
            default -> entity = new Zombie(EntityType.ZOMBIE, level);
        }
        entity.setPos(l.getX(), l.getY(), l.getZ());
        idd = entity.getId();
        e = entity;
        level.addFreshEntity(entity);
    }

}
