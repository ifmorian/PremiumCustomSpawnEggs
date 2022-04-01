package de.felix_kurz.premiumcustomspawneggs.listeners;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.entities.pathfindergoals.WalkToLocationGoal;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftProjectile;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteractListener implements Listener {

    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();
    private ConfigurationManager cfgM = Main.getCfgM();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (inv.getItem(event.getHand()) == null) return;

        org.bukkit.inventory.ItemStack item = inv.getItem(event.getHand());
        ItemStack nmsItem = CraftItemStack.asNMSCopy(p.getInventory().getItem(event.getHand()));
        if (nmsItem.hasTag()) {
            CompoundTag tag = nmsItem.getTag();
            if (!tag.getString("pcse_entity").equals("")) {
                String id = tag.getString("pcse_entity");
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    Egg projectile = p.launchProjectile(Egg.class);
                    projectile.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "pcse_entity"), PersistentDataType.STRING, id);
                    projectile.setCustomName(Main.getPlugin().getConfig().getString("mobs." + id + ".name"));
                    projectile.setCustomNameVisible(true);
                    if (tag.getBoolean("pcse_glow")) {
                        Main.getScoreboard().getTeam(tag.getString("pcse_color")).addEntry(projectile.getUniqueId().toString());
                        projectile.setGlowing(true);
                    }
                }
                if (event.getClickedBlock() == null || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
                Location l = event.getClickedBlock().getLocation();
                l.setY(l.getY() + 1);
                Main.getCfgM().getMob(id, p.getUniqueId()).spawnEntity(l);
                if (p.getGameMode() != GameMode.CREATIVE) item.setAmount(item.getAmount() - 1);
                event.setCancelled(true);
                return;
            }

            for (int id : nmsItem.getTag().getIntArray("pcse_con_mobs")) {
                Block b = event.getPlayer().getTargetBlockExact(50);
                if (b == null) return;
                Location bl = b.getLocation();

                CustomMob mob = CustomMob.mobs.get(id);

                if (mob != null) {
                    PathfinderMob e = mob.entity;
                    if (mob.r != null) mob.r.cancel();
                    e.goalSelector.removeAllGoals();
                    e.goalSelector.addGoal(0, new WalkToLocationGoal(mob, bl, mob.speed));
                }
            }
        }

    }

}
