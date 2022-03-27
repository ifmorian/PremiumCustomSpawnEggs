package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import org.bukkit.Particle;

public abstract class ActiveAbility extends Ability {

    public int key;

    public ActiveAbility(String id, double range, Particle particle, int key) {
        super(id, range, particle);
        this.key = key;
    }
}
