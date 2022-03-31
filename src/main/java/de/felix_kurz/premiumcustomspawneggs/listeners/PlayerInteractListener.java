package de.felix_kurz.premiumcustomspawneggs.listeners;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import de.felix_kurz.premiumcustomspawneggs.entities.pathfindergoals.WalkToLocationGoal;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteractListener implements Listener {

    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (inv.getItem(event.getHand()) == null) return;

        org.bukkit.inventory.ItemStack item = inv.getItem(event.getHand());
        ItemStack nmsItem = CraftItemStack.asNMSCopy(p.getInventory().getItem(event.getHand()));
        if (nmsItem.hasTag()) {
            if (!nmsItem.getTag().getString("pcse_entity").equals("")) {
                if (event.getClickedBlock() == null || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
                Location l = event.getClickedBlock().getLocation();
                l.setY(l.getY() + 1);
                Main.getCfgM().getMob("explosive_chicken", p.getUniqueId()).spawnEntity(l);
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
//                    e.getNavigation().moveTo(bl.getX(), bl.getY(), bl.getZ(), mob.speed);
                    e.goalSelector.removeAllGoals();
                    e.goalSelector.addGoal(0, new WalkToLocationGoal(mob, bl, mob.speed));
                }

//                I was so stupid writing that stuff below
//                bl.setY(bl.getY() + 1.2);
//                Location el = e.getBukkitEntity().getLocation();
//                Vec3 vec = new Vec3(bl.getX() - el.getX(), bl.getY() - el.getY(), bl.getZ() - el.getZ());
//                e.setDeltaMovement(vec.normalize().multiply(0.2, 0, 0.2));
//                Location loc = e.getBukkitEntity().getLocation();
//                loc.setDirection(new Vector(vec.x, 0, vec.z));
//                e.getBukkitEntity().teleport(loc);
//                el.add(e.getBukkitEntity().getLocation().getDirection());
//                if (p.getWorld().getBlockAt(el).getType().isSolid()) e.getBukkitEntity().setVelocity(new Vector(0,0.3,0));
            }
        }

    }

}
