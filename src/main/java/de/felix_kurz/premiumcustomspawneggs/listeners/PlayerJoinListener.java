package de.felix_kurz.premiumcustomspawneggs.listeners;

import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record PlayerJoinListener(Main plugin) implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setResourcePack(plugin.getCfgM().getResourcePackLink());
        event.getPlayer().getInventory().addItem(new FullCore());
    }

}
