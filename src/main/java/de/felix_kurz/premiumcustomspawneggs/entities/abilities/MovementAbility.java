package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.entities.pathfindergoals.WalkToLocationGoal;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.world.level.pathfinder.Path;
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

    private final double maxDistClick;
    private final String goTo;
    private final List<String> entities;
    private final List<String> blocks;
    private final float speed;

    public MovementAbility(CustomMob mob, String name, double maxDistClick, double range, String particle, int particleAmount, int cd, double maxDistMob, String goTo, String entities,
                           String blocks, double speed, long delay, String velocity) {
        super(mob, name, maxDistMob, range, particle, particleAmount, cd, delay, velocity);
        this.maxDistClick = maxDistClick;
        this.goTo = goTo;
        this. entities = new ArrayList<>(Arrays.asList(entities.toUpperCase().split(",")));
        this.blocks =  new ArrayList<>(Arrays.asList(blocks.toUpperCase().split(",")));
        this.speed = (float) speed;
    }

    @Override
    public void execute(Player p) {
        if (checkNotReady() || p.getLocation().distance(mob.entity.getBukkitEntity().getLocation()) > maxDistMob) return;
        setVelocity();
        spawnParticles();

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
                        case "block" -> goToBlock();
                        default -> Main.c.sendMessage(Main.PRE + "§cInvalid target §6" + goTo);
                    }
                }
            }.runTaskLater(Main.getPlugin(), delay);
        }

    }

    private void goToEntity(UUID owner) {
        final double[] maxDistance = {range + 0.1};
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

    private void goToBlock() {
        double maxDistance = range + 0.1;
        double prio = blocks.size();

        Location l = mob.entity.getBukkitEntity().getLocation();
        int bx = l.getBlockX();
        int by = l.getBlockY();
        int bz = l.getBlockZ();

        for (int x = (int) (bx - range); x <= bx + range; x++) {
            for (int y = (int) (by + range); y >= by - range; y--) {
                for (int z = (int) (bz - range); z <= bz + range; z++) {
                    double distance = l.distance(new Location(l.getWorld(), x, y, z));

                    if (distance < range) {
                        Block block = l.getWorld().getBlockAt(x, y, z);

                        if (!blocks.contains(block.getType().toString())) continue;

                        Path path = mob.entity.getNavigation().createPath(x + ((x - bx) > 0 ? 1 : -1), y, z + ((z - bz) > 0 ? 1 : -1), 1);

                        if (path != null && path.canReach()) {
                            if (distance < maxDistance || blocks.indexOf(block.getType().toString()) < prio) {
                                mob.entity.getNavigation().moveTo(path, speed);
                                prio = blocks.indexOf(block.getType().toString());
                                maxDistance = distance;
                            }
                        }
                    }
                }
            }
        }
    }

}
