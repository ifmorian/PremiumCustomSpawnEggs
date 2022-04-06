package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class Ability {

    public CustomMob mob;

    public String id;
    public String name;
    public double maxDistMob;
    public double range;
    public String particle;
    public int particleAmount;
    public int cd;
    public boolean ready;
    public long delay;
    public String velocity;

    public Ability(CustomMob mob, String id, String name, double maxDistMob, double range, String particle, int particleAmount, int cd, long delay, String velocity) {
        this.mob = mob;
        this.id = id;
        this.name = name;
        this.maxDistMob = maxDistMob;
        this.range = range;
        this.particle = particle;
        this.particleAmount = particleAmount;
        this.cd = cd;
        this.ready = true;
        this.delay = delay;
        this.velocity = velocity;
    }

    public abstract void execute(Player p);

    public void spawnParticles() {
        Location l = mob.entity.getBukkitEntity().getLocation();
        l.setY(l.getY() + mob.entity.getBbHeight());
        try {
            l.getWorld().spawnParticle(Particle.valueOf(particle), l, particleAmount);
        } catch (IllegalArgumentException e) {
            Main.c.sendMessage(Main.PRE + "§cInvalid particle §6" + particle);
        }
    }

    public boolean checkNotReady() {
        if (ready) {
            coolDown();
            return false;
        }
        else {
            Location l = mob.entity.getBukkitEntity().getLocation();
            l.getWorld().playSound(l, Sound.ITEM_SHIELD_BLOCK, 1f, 0.1f);
            return true;
        }
    }

    public void coolDown() {
        ready = false;
        new BukkitRunnable() {
            @Override
            public void run() {
                ready = true;
            }
        }.runTaskLater(Main.getPlugin(), cd);
    }

    public void setVelocity() {
        String[] velocities = velocity.split(",");
        CraftEntity entity = mob.entity.getBukkitEntity();
        if (!velocity.equals("0,0,0")) {
            try {
                double x;
                if (velocities[0].contains("~")) {
                    x = Double.parseDouble(velocities[0].substring(0, velocities[0].length() - 2)) * entity.getLocation().getDirection().getX();
                } else if (velocities[0].contains("*")) {
                    x = Double.parseDouble(velocities[0].substring(0, velocities[0].length() - 2)) * entity.getVelocity().getX();
                } else {
                    x = Double.parseDouble(velocities[0]);
                }
                double y;
                if (velocities[1].contains("~")) {
                    y = Double.parseDouble(velocities[1].substring(0, velocities[1].length() - 2)) * entity.getLocation().getDirection().getY();
                } else if (velocities[1].contains("*")) {
                    y = Double.parseDouble(velocities[1].substring(0, velocities[1].length() - 2)) * entity.getVelocity().getY();
                } else {
                    y = Double.parseDouble(velocities[1]);
                }
                double z;
                if (velocities[2].contains("~")) {
                    z = Double.parseDouble(velocities[2].substring(0, velocities[2].length() - 2)) * entity.getLocation().getDirection().getZ();
                } else if (velocities[2].contains("*")) {
                    z = Double.parseDouble(velocities[2].substring(0, velocities[2].length() - 2)) * entity.getVelocity().getZ();
                } else {
                    z = Double.parseDouble(velocities[2]);
                }
                entity.setVelocity(new Vector(x, y, z));
            } catch (Exception ignore) {
                Main.c.sendMessage(Main.PRE + "§cInvalid velocity §6" + velocity);
            }
        }
    }

}
