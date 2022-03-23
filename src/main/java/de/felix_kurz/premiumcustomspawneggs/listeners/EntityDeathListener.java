package de.felix_kurz.premiumcustomspawneggs.listeners;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.items.CoreShard;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private ConfigurationManager cfgM = Main.getCfgM();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        for (String path : cfgM.getShardMobs().getKeys(true)) {
            if (path.equals(event.getEntityType().toString())) {
                event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), new CoreShard().getItem());
            }
        }
    }

}
