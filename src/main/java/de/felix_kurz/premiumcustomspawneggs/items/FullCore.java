package de.felix_kurz.premiumcustomspawneggs.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FullCore extends ItemStack {

    public FullCore() {
        super(Material.GOLDEN_HOE);
        ItemMeta meta = getItemMeta();
        meta.setCustomModelData(1111111);
        setItemMeta(meta);
    }
}
