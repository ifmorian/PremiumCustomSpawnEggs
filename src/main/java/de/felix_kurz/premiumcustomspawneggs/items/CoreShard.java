package de.felix_kurz.premiumcustomspawneggs.items;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CoreShard {

    private ConfigurationManager cfgM = Main.getCfgM();

    private ItemStack item;

    public CoreShard() {
        item = new ItemStack(cfgM.getCoreMaterial("shard"));

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
        tag.putString("pcse_core", "shard");
        nmsItem.setTag(tag);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(cfgM.getCoreCustomDataModel("shard"));
        meta.setDisplayName(cfgM.getCoreName("shard"));
        meta.setUnbreakable(true);
        if (cfgM.isCoreEnchanted("shard")) meta.addEnchant(Enchantment.LOYALTY, 1, true);
        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }
}
