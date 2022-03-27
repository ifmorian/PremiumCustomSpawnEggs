package de.felix_kurz.premiumcustomspawneggs.listeners;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.items.CoreShard;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private final ConfigurationManager cfgM = Main.getCfgM();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        for (String path : cfgM.getShardMobs().getKeys(false)) {
            if (path.equals(event.getEntityType().toString())) {
                if (cfgM.getDropProbability(path) > Math.random()) {
                    if (event.getEntityType() == EntityType.SLIME) {
                        Slime slime = (Slime) event.getEntity();
                        if (slime.getSize() != 1) return;
                    }
                    event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), new CoreShard().getItem());
                }
            }
        }
    }

}
