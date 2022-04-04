package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.world.entity.Entity;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public abstract class Ability {

    public CustomMob mob;

    public String id;
    public String name;
    public int key;
    public double maxDistClick;
    public double range;
    public String particle;
    public int particleAmount;

    public Ability(CustomMob mob, String id, String name, int key, double maxDistClick, double range, String particle, int particleAmount) {
        this.mob = mob;
        this.id = id;
        this.name = name;
        this.key = key;
        this.maxDistClick = maxDistClick;
        this.range = range;
        this.particle = particle;
        this.particleAmount = particleAmount;
    }

    public abstract void execute(Player p);

    public void spawnParticles(Entity entity) {
        Location l = entity.getBukkitEntity().getLocation();
        l.setY(l.getY() + entity.getBbHeight());
        try {
            l.getWorld().spawnParticle(Particle.valueOf(particle), l, particleAmount);
        } catch (IllegalArgumentException e) {
            Main.c.sendMessage(Main.PRE + "§cInvalid particle §6" + particle);
        }
    }
}
