package de.felix_kurz.premiumcustomspawneggs.entities.nmsentities;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;

public class CustomZombie extends Zombie {
    public CustomZombie(Location l) {
        super(EntityType.ZOMBIE, ((CraftWorld) l.getWorld()).getHandle());
        this.setPos(l.getX(), l.getY(), l.getZ());
    }
}
