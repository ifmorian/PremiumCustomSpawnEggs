package de.felix_kurz.premiumcustomspawneggs.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;

import java.util.Optional;

public class CustomEntity {

    public static Entity setEntityData(Entity e, String name) {
        e.setCustomName(Component.nullToEmpty(name));
        return e;
    }

}
