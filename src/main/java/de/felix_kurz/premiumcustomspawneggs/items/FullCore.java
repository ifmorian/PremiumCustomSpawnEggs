package de.felix_kurz.premiumcustomspawneggs.items;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FullCore {

    private ConfigurationManager cfgM = Main.getCfgM();

    private ItemStack item;

    public FullCore() {
        item = new ItemStack(cfgM.getCoreMaterial("fullcore"));

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("core", "fullcore");
        item = nbtItem.getItem();

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(cfgM.getCoreCustomDataModel("fullcore"));
        meta.setDisplayName(cfgM.getCoreName("fullcore"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        if (cfgM.isCoreEnchanted("fullcore")) meta.addEnchant(Enchantment.LOYALTY, 1, true);
        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }
}
