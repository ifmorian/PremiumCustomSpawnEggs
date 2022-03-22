package de.felix_kurz.premiumcustomspawneggs.items;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CoreShard extends ItemStack {

    private ConfigurationManager cfgM = Main.getCfgM();

    public CoreShard() {
        super(Material.GOLDEN_HOE);
        ItemMeta meta = getItemMeta();
        meta.setCustomModelData(1111113);
        meta.setDisplayName(cfgM.getCoreName("shard"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        if (cfgM.isCoreEnchanted("shard")) meta.addEnchant(Enchantment.LOYALTY, 1, true);
        setItemMeta(meta);
    }
}
