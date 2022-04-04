package de.felix_kurz.premiumcustomspawneggs.listeners;

import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftProjectile;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ProjectileHitListener implements Listener {

    private List<Projectile> hits = new ArrayList<>();

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        String mobID = projectile.getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "pcse_entity"), PersistentDataType.STRING);
        if (mobID != null) {
            if (hits.contains(projectile)) {
                hits.remove(projectile);
                event.setCancelled(true);
                return;
            }
            projectile.remove();
            hits.add(projectile);
            Main.getCfgM().getMob(mobID, ((CraftProjectile)projectile).getHandle().getOwner().getUUID()).spawnEntity(projectile.getLocation());
            event.setCancelled(true);
            return;
        }
        Integer fire = projectile.getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "pcse_fire"), PersistentDataType.INTEGER);
        if (fire != null) {
            event.getHitEntity().setFireTicks(fire);
        }
    }

}
