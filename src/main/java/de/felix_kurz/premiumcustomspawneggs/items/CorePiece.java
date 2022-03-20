package de.felix_kurz.premiumcustomspawneggs.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CorePiece extends ItemStack {

    public CorePiece() {
        super(Material.GOLDEN_HOE);
        ItemMeta meta = getItemMeta();
        meta.setCustomModelData(1111112);
        setItemMeta(meta);
    }
}
