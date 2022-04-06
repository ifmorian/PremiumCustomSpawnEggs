package de.felix_kurz.premiumcustomspawneggs.items;

import de.felix_kurz.premiumcustomspawneggs.entities.CustomMob;
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

    public ItemStack item;

    public MobRemote(int[] mobIDs, CustomMob mob) {
        item = new ItemStack(Material.STICK);

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
        tag.putIntArray("pcse_con_mobs", mobIDs);
        tag.putString("pcse_mob_id", mob.id);
        nmsItem.setTag(tag);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dRemote Control of " + mob.id);
        meta.addEnchant(Enchantment.LOYALTY, 1, true);
        List<String> lore = new ArrayList<>();
        if (mob.abilities[0] != null) lore.add("§7§lRIGHT " + mob.abilities[0].name);
        if (mob.abilities[1] != null) lore.add("§7§lLEFT " + mob.abilities[1].name);
        if (mob.abilities[2] != null) lore.add("§7§lSNEAK+RIGHT " + mob.abilities[2].name);
        if (mob.abilities[3] != null) lore.add("§7§lSNEAK+LEFT " + mob.abilities[3].name);
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
