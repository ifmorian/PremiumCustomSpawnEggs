package de.felix_kurz.premiumcustomspawneggs.items;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CorePiece {

    private ConfigurationManager cfgM = Main.getCfgM();

    private ItemStack item;

    public CorePiece() {
        item = new ItemStack(cfgM.getCoreMaterial("piece"));

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("pcse_core", "piece");
        item = nbtItem.getItem();

        ItemMeta meta = nbtItem.getItem().getItemMeta();
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
