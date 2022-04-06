package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttackAbility extends Ability {

    public List<String> entities;
    public int damage;
    public int fire;
    public boolean multi;
    public double knockback;

    public AttackAbility(CustomMob mob, String id, String name, double maxDistMob, double range, String particle, int particleAmount, int cd, long delay, String velocity,
                         String entities, int damage, int fire, boolean multi, double knockback) {
        super(mob, id, name, maxDistMob, range, particle, particleAmount, cd, delay, velocity);
        this.entities = new ArrayList<>(Arrays.asList(entities.toUpperCase().split(",")));
        this.damage = damage;
        this.fire = fire;
        this.multi = multi;
        this.knockback = knockback;
    }

    @Override
    public void execute(Player p) {
        if (checkNotReady() || p.getLocation().distance(mob.entity.getBukkitEntity().getLocation()) > maxDistMob) return;
        setVelocity();

        if (!entities.contains("NONE")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    spawnParticles();
                    double[] maxDistance = {range + 0.1};
                    mob.entity.getBukkitEntity().getNearbyEntities(range, range, range).forEach(e -> {
                        if (!(e instanceof LivingEntity le)) return;
                        CustomMob leMob = CustomMob.mobs.get(le.getEntityId());
                        if (leMob != null) {
                            if (leMob.owner.equals(mob.owner)) return;}

                        if ((entities.contains(le.getType().toString()) || entities.contains("ALL")) && !le.getUniqueId().equals(mob.owner)) {
                            double distance = le.getLocation().distance(mob.entity.getBukkitEntity().getLocation());
                            if (multi || distance < maxDistance[0]) {
                                maxDistance[0] = distance;
                                le.damage(damage, mob.entity.getBukkitEntity());
                                le.setFireTicks(fire);
                                Location l = mob.entity.getBukkitEntity().getLocation();
                                le.setVelocity(new Vector(le.getLocation().getX() - l.getX(), le.getLocation().getY() - l.getY() + 0.001, le.getLocation().getZ() - l.getZ()).normalize().multiply(knockback).add(new Vector(0,0.2 * knockback,0)));
                            }
                        }
                    });
                }
            }.runTaskLater(Main.getPlugin(), delay);
        }
    }

}
