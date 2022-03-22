package de.felix_kurz.premiumcustomspawneggs.items;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FullCore extends ItemStack {

    private ConfigurationManager cfgM = Main.getCfgM();

    public FullCore() {
        super(Main.getCfgM().getCoreMaterial("fullcore"));
        ItemMeta meta = getItemMeta();
        meta.setCustomModelData(cfgM.getCoreCustomDataModel("fullcore"));
        meta.setDisplayName(cfgM.getCoreName("fullcore"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        if (cfgM.isCoreEnchanted("fullcore")) meta.addEnchant(Enchantment.LOYALTY, 1, true);
        setItemMeta(meta);
    }
}
