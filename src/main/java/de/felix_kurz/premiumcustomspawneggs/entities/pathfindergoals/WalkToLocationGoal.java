package de.felix_kurz.premiumcustomspawneggs.entities.pathfindergoals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import org.bukkit.Location;

public class WalkToLocationGoal extends Goal {

    private float speed;
    private Mob entity;
    private Location l;
    private PathNavigation nav;

    public WalkToLocationGoal(Entity entity, Location l, float speed) {
        this.entity = (Mob) entity;
        this.l = l;
        this.speed = speed;
        this.nav = this.entity.getNavigation();
    }

    @Override
    public boolean canUse() {
        return true;
    }

    public boolean canContinueToUse() {
        return !this.nav.isDone() && this.entity.isAlive();
    }

    public void start() {
        Path path = this.nav.createPath(l.getX(), l.getY(), l.getZ(), 0);
        this.nav.moveTo(l.getX(), l.getY(), l.getZ(), speed);
    }

}
