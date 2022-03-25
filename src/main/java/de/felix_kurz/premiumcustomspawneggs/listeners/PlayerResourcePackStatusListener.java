package de.felix_kurz.premiumcustomspawneggs.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class PlayerResourcePackStatusListener implements Listener {

    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            event.getPlayer().kickPlayer("§cYou have to enable the resource pack to play on this server.");
        } else if (event.getStatus() == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            event.getPlayer().kickPlayer("§cSomething went wrong while downloading the resource pack. Please try again.");
        }
    }

}
