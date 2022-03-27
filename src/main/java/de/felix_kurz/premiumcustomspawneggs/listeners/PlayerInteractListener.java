package de.felix_kurz.premiumcustomspawneggs.listeners;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class PlayerInteractListener implements Listener {

    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.getInventory().getItem(event.getHand()) == null) return;
        Block b = event.getPlayer().getTargetBlockExact(50);
        if (b == null) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        CraftBlock b1 = (CraftBlock) b;
        Location bl = b.getLocation();
        bl.setY(bl.getY() + 1.2);
        Location el = CustomMob.e.getBukkitEntity().getLocation();
        Vec3 vec = new Vec3(bl.getX() - el.getX(), bl.getY() - el.getY(), bl.getZ() - el.getZ());
        CustomMob.e.setDeltaMovement(vec.normalize().multiply(0.2, 0, 0.2));
        Location loc = CustomMob.e.getBukkitEntity().getLocation();
        loc.setDirection(new Vector(vec.x, 0, vec.z));
        CustomMob.e.getBukkitEntity().teleport(loc);
        el.add(CustomMob.e.getBukkitEntity().getLocation().getDirection());
        if (p.getWorld().getBlockAt(el).getType().isSolid()) CustomMob.e.getBukkitEntity().setVelocity(new Vector(0,0.3,0));
    }

}
