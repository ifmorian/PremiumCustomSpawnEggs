package de.felix_kurz.premiumcustomspawneggs.entities.abilities;

import org.bukkit.Location;
import org.bukkit.Particle;

public abstract class Ability {

    public String id;
    public double range;
    public Particle particle;
    public Location l;

    public Ability(String id, double range, Particle particle, Location l) {
        this.id = id;
        this.range = range;
        this.particle = particle;
        this.l = l;
    }
}
