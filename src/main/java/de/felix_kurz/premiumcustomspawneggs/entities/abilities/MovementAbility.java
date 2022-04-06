package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.entities.pathfindergoals.WalkToLocationGoal;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MovementAbility extends Ability {

    public double maxDistClick;
    public String goTo;
    public List<String> entities;
    public String blocks;
    public float speed;

    public MovementAbility(CustomMob mob, String id, String name, double maxDistClick, double range, String particle, int particleAmount, int cd, double maxDistMob, String goTo, String entities,
                           String blocks, double speed, long delay, String velocity) {
        super(mob, id, name, maxDistMob, range, particle, particleAmount, cd, delay, velocity);
        this.maxDistClick = maxDistClick;
        this.goTo = goTo;
        this. entities = new ArrayList<>(Arrays.asList(entities.toUpperCase().split(",")));
        this.blocks = blocks;
        this.speed = (float) speed;
    }

    @Override
    public void execute(Player p) {
        if (checkNotReady() || p.getLocation().distance(mob.entity.getBukkitEntity().getLocation()) > maxDistMob) return;
        spawnParticles();
        setVelocity();

        if (!goTo.equals("none")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    switch (goTo) {
                        case "clicked" -> {
                            Block b = p.getTargetBlockExact((int) maxDistClick + 1);
                            if (b == null) return;
                            Location bl = b.getLocation();
                            if (mob.r != null) mob.r.cancel();
                            mob.entity.goalSelector.removeAllGoals();
                            mob.entity.goalSelector.addGoal(0, new WalkToLocationGoal(mob, bl, speed));
                        }
                        case "entity" -> goToEntity(p.getUniqueId());
                        default -> Main.c.sendMessage(Main.PRE + "§cInvalid target §6" + goTo);
                    }
                }
            }.runTaskLater(Main.getPlugin(), delay);
        }

    }

    public void goToEntity(UUID owner) {
        final double[] maxDistance = {range + 1};
        mob.entity.getBukkitEntity().getNearbyEntities(range, range, range).forEach(e -> {
            if (CustomMob.mobs.containsKey(e.getEntityId())) {
                if (CustomMob.mobs.get(e.getEntityId()).owner.equals(owner)) return;
            }
            if (entities.contains(e.getType().toString()) || entities.contains("ALL")) {
                double distance = mob.entity.getBukkitEntity().getLocation().distance(e.getLocation());
                if (distance > maxDistance[0]) return;
                maxDistance[0] = distance;
                mob.entity.getNavigation().moveTo(((CraftEntity) e).getHandle(), speed);
            }
        });
    }

}
