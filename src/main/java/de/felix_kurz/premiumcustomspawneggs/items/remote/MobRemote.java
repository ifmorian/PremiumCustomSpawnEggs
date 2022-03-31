package de.felix_kurz.premiumcustomspawneggs.items.remote;

import com.google.common.collect.Table;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MobRemote {

    private int[] mobIDs;
    private String mobName;
    private String mobID;

    public ItemStack item;

    public MobRemote(int[] mobIDs, String mobID) {
        this.mobIDs = mobIDs;
        this.mobID = mobID;

        item = new ItemStack(Material.STICK);

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
        tag.putIntArray("pcse_con_mobs", mobIDs);
        tag.putString("pcse_mob_id", mobID);
        nmsItem.setTag(tag);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dRemote Control of " + mobID);
        meta.addEnchant(Enchantment.LOYALTY, 1, true);
        List<String> lore = new ArrayList<>();
        lore.add("§7§lRIGHT ");
        lore.add("§7§lLEFT ");
        lore.add("§7§lSHIFT_RIGHT ");
        lore.add("§7§lSHIFT_LEFT ");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    public static int getRemoteSlot(Inventory inv, String mobID) {
        for (ItemStack item : inv) {
            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            if (nmsItem.hasTag()) {
                CompoundTag itemTag = nmsItem.getTag();
                if (mobID.equals(itemTag.getString("pcse_mob_id"))) {
                    return inv.first(item);
                }
            }
        }
        return -1;
    }

    public static Tuple<Integer, Boolean> getRemoteSlotAndHasSpace(Inventory inv, String mobID) {
        int slot = -1;
        boolean hasSpace = false;
        for (int i = 0; i < 35; i++) {
            ItemStack item = inv.getItem(i);
            if (item == null) {
                hasSpace = true;
                continue;
            }
            net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            if (nmsItem.hasTag()) {
                CompoundTag itemTag = nmsItem.getTag();
                if (mobID.equals(itemTag.getString("pcse_mob_id"))) {
                    slot = inv.first(item);
                }
            }
        }
        return new Tuple<>(slot, hasSpace);
    }

}
