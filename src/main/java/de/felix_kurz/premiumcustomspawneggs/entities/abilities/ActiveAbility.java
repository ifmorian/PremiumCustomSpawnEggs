package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import org.bukkit.Location;
import org.bukkit.Particle;

public abstract class ActiveAbility extends Ability {

    public int key;

    public ActiveAbility(String id, double range, Particle particle, int key, Location l) {
        super(id, range, particle, l);
        this.key = key;
    }
}
