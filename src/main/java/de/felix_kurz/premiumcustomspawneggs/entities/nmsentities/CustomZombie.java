package de.felix_kurz.premiumcustomspawneggs.entities.nmsentities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftLivingEntity;

public class CustomZombie extends Zombie {
    public CustomZombie(Location l) {
        super(EntityType.ZOMBIE, ((CraftWorld) l.getWorld()).getHandle());

    }
}
