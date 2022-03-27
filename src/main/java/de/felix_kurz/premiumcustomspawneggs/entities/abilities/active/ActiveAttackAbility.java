package de.felix_kurz.premiumcustomspawneggs.entities.abilities.active;

import de.felix_kurz.premiumcustomspawneggs.entities.abilities.ActiveAbility;
import org.bukkit.Particle;

import java.util.List;

public class ActiveAttackAbility extends ActiveAbility {

    public int dmg;
    public boolean aoe;
    public List<String> targets;

    public ActiveAttackAbility(String id, double range, Particle particle, int key, int dmg, boolean aoe, String targets) {
        super(id, range, particle, key);
        this.dmg = dmg;
        this.aoe = aoe;
        for (String s : targets.split(",")) {
            this.targets.add(s);
        }
    }
}
