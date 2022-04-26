package de.felix_kurz.premiumcustomspawneggs.entities.pathfindergoals;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.bukkit.Location;

public class WalkToLocationGoal extends Goal {

    private final float speed;
    private final CustomMob mob;
    private final Location l;
    private final PathNavigation nav;

    public WalkToLocationGoal(CustomMob mob, Location l, float speed) {
        this.mob = mob;
        this.l = l;
        this.speed = speed;
        this.nav = mob.entity.getNavigation();
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.nav.isDone() && mob.entity.isAlive();
    }

    @Override
    public void stop() {
        mob.startBehavior();
    }

    @Override
    public void start() {
        this.nav.moveTo(l.getX(), l.getY(), l.getZ(), speed);
    }

}
