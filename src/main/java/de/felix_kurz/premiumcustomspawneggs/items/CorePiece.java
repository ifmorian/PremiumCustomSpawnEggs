package de.felix_kurz.premiumcustomspawneggs.items;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CorePiece {

    private ConfigurationManager cfgM = Main.getCfgM();

    private ItemStack item;

    public CorePiece() {
        item = new ItemStack(cfgM.getCoreMaterial("piece"));

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
        tag.putString("pcse_core", "piece");
        nmsItem.setTag(tag);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(cfgM.getCoreCustomDataModel("piece"));
        meta.setDisplayName(cfgM.getCoreName("piece"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        if (cfgM.isCoreEnchanted("piece")) meta.addEnchant(Enchantment.LOYALTY, 1, true);
        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }
}
