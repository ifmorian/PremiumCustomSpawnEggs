package de.felix_kurz.premiumcustomspawneggs.items;

import de.felix_kurz.premiumcustomspawneggs.configuration.ConfigurationManager;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CorePiece extends ItemStack {

    private ConfigurationManager cfgM = Main.getCfgM();

    public CorePiece() {
        super(Main.getCfgM().getCoreMaterial("piece"));
        ItemMeta meta = getItemMeta();
        meta.setCustomModelData(cfgM.getCoreCustomDataModel("piece"));
        meta.setDisplayName(cfgM.getCoreName("piece"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        if (cfgM.isCoreEnchanted("piece")) meta.addEnchant(Enchantment.LOYALTY, 1, true);
        setItemMeta(meta);
    }
}
