package de.felix_kurz.premiumcustomspawneggs.listeners;

import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!Main.getCfgM().getUseRessourcepack()) return;
        event.getPlayer().setResourcePack(Main.getCfgM().getResourcePackLink());
    }

}
