package de.felix_kurz.premiumcustomspawneggs.recipes;

import de.felix_kurz.premiumcustomspawneggs.items.CoreShard;
import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapelessRecipe;

public class CorePieceRecipe {

    private ShapelessRecipe recipe;

    public CorePieceRecipe(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "corepiece");
        recipe = new ShapelessRecipe(key, new FullCore());
        recipe.addIngredient(8, new CoreShard().getData());
    }

    public ShapelessRecipe getRecipe() {
        return recipe;
    }
}
