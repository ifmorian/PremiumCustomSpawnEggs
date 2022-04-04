package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.entities.pathfindergoals.WalkToLocationGoal;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class MovementAbility extends Ability {

    public double maxDistMob;
    public String goTo;
    public String entities;
    public String blocks;
    public float speed;
    public String velocity;

    public MovementAbility(CustomMob mob, String id, String name, int key, double maxDistClick, double range, String particle, int particleAmount, double maxDistMob, String goTo, String entities,
                           String blocks, double speed, String velocity) {
        super(mob, id, name, key, maxDistClick, range, particle, particleAmount);
        this.maxDistMob = maxDistMob;
        this.goTo = goTo;
        this. entities = entities;
        this.blocks = blocks;
        this.velocity = velocity;
        this.speed = (float) speed;
    }

    @Override
    public void execute(Player p) {
        String[] velocities = velocity.split(",");
        CraftEntity entity = mob.entity.getBukkitEntity();
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

        switch (goTo) {
            case "clicked" -> {
                Block b = p.getTargetBlockExact((int) maxDistClick + 1);
                if (b == null) return;
                Location bl = b.getLocation();
                if (mob.r != null) mob.r.cancel();
                mob.entity.goalSelector.removeAllGoals();
                mob.entity.goalSelector.addGoal(0, new WalkToLocationGoal(mob, bl, speed));
            }
            case "entity" -> {
                goToEntity(p.getUniqueId());
            }
            default -> Main.c.sendMessage(Main.PRE + "§cInvalid target §6" + goTo);
        }
    }

    public void goToEntity(UUID owner) {
        mob.entity.getBukkitEntity().getNearbyEntities(range, range, range).forEach(e -> {
            if (CustomMob.mobs.containsKey(e.getEntityId())) {
                if (CustomMob.mobs.get(e.getEntityId()).owner.equals(owner)) return;
            }

        });
    }

}
