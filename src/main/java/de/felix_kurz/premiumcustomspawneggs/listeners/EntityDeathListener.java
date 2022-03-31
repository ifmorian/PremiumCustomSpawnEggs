package de.felix_kurz.premiumcustomspawneggs.listeners;

import com.google.common.primitives.Ints;
import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.items.CoreShard;
import de.felix_kurz.premiumcustomspawneggs.items.remote.MobRemote;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

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

        int id = event.getEntity().getEntityId();
        CustomMob mob = CustomMob.mobs.get(id);
        if (mob != null) {
            if (mob.r != null) mob.r.cancel();
            Inventory inv = Bukkit.getPlayer(mob.owner).getInventory();
            int slot = MobRemote.getRemoteSlot(inv, mob.id);
            if (slot != -1) {
                ItemStack nmsItem = CraftItemStack.asNMSCopy(inv.getItem(slot));
                CompoundTag tag = nmsItem.getTag();
                List<Integer> mobIDs = new ArrayList<>(Ints.asList(tag.getIntArray("pcse_con_mobs")));
                if (mobIDs.size() == 1) {
                    inv.remove(CraftItemStack.asBukkitCopy(nmsItem));
                    CustomMob.mobs.remove(id);
                } else {
                    mobIDs.remove(Integer.valueOf(id));
                    tag.putIntArray("pcse_con_mobs", mobIDs);
                    nmsItem.setTag(tag);
                    inv.setItem(slot, CraftItemStack.asBukkitCopy(nmsItem));
                }
            }
        }

    }

}
