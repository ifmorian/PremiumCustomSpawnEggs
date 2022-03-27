package de.felix_kurz.premiumcustomspawneggs.listeners;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.items.CorePiece;
import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraftListener implements Listener {

    private final ConfigurationManager cfgM = Main.getCfgM();

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        int shards = 0;
        int pieces = 0;
        int fullcore = 0;
        int otherItems = 0;
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item == null) continue;
            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            if(!nmsItem.hasTag()) return;
            CompoundTag tag = nmsItem.getTag();
            if (tag.getString("pcse_core").equals("shard")) shards++;
            else if (tag.getString("pcse_core").equals("piece")) pieces++;
            else if (tag.getString("pcse_core").equals("fullcore")) fullcore++;
            else otherItems++;
        }
        if (shards == cfgM.getCoreRecipe("piece") && pieces == 0 && otherItems == 0) event.getInventory().setResult(new CorePiece().getItem());
        else if (pieces == cfgM.getCoreRecipe("fullcore") && shards == 0 && otherItems == 0) event.getInventory().setResult(new FullCore().getItem());
        else if (pieces != 0 || shards != 0) event.getInventory().setResult(null);
        else if (fullcore == 2 && event.getInventory().getResult().equals(new ItemStack(cfgM.getCoreMaterial("fullcore")))) event.getInventory().setResult(null);
    }

}
