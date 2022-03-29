package de.felix_kurz.premiumcustomspawneggs.items.remote;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobRemote {

    private int[] mobIDs;
    private String mobName;

    private ItemStack item;

    public MobRemote(int[] mobIDs) {
        this.mobIDs = mobIDs;

        item = new ItemStack(Material.STICK);

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
        tag.putIntArray("pcse_con_mobs", mobIDs);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dRemote Control of " + mobName);
        meta.addEnchant(Enchantment.LOYALTY, 1, true);
        item.setItemMeta(meta);
    }

}
