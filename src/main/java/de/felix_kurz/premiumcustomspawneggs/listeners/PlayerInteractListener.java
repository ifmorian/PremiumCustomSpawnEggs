package de.felix_kurz.premiumcustomspawneggs.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.getInventory().getItem(event.getHand()) == null) return;
        if (new NBTItem(p.getInventory().getItem(event.getHand())).getString("pcse_entity").equals("")) {

        }
    }

}
