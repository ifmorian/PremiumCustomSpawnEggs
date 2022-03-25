package de.felix_kurz.premiumcustomspawneggs.entities;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Location;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;

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
        //ServerLevel world = ((CraftWorld) l.getWorld()).getHandle();

        //LivingEntity nmsEntity = new Zombie(world);

        org.bukkit.entity.LivingEntity entity = (org.bukkit.entity.LivingEntity) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);

        entity.setCustomName(name);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(300D);
        entity.setHealth(300D);
        Zombie nmsEntity = (Zombie) ((CraftEntity) entity).getHandle();
        nmsEntity.setHealth(1);
        nmsEntity.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(nmsEntity, Skeleton.class, true, false));
    }
}
