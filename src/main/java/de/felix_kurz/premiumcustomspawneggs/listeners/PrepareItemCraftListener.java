package de.felix_kurz.premiumcustomspawneggs.listeners;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.items.CorePiece;
import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraftListener implements Listener {

    private ConfigurationManager cfgM = Main.getCfgM();

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        int shards = 0;
        int pieces = 0;
        int otherItems = 0;
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item == null) continue;
            if (cfgM.isCore(item, "shard")) shards++;
            else if (cfgM.isCore(item, "piece")) pieces++;
            else otherItems++;
        }
        if (shards == cfgM.getCoreRecipe("piece") && pieces == 0 && otherItems == 0) event.getInventory().setResult(new CorePiece());
        else if (pieces == cfgM.getCoreRecipe("fullcore") && shards == 0 && otherItems == 0) event.getInventory().setResult(new FullCore());
        else if (pieces != 0 || shards != 0) event.getInventory().setResult(null);
    }

}