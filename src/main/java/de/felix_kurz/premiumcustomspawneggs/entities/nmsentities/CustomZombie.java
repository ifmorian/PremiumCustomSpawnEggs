package de.felix_kurz.premiumcustomspawneggs.entities.nmsentities;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.bukkit.Location;

public class CustomZombie extends Zombie {
    public CustomZombie(Level world, Location l) {
        super(world);
        this.spawnAtLocation((ItemLike) l);
    }
}
