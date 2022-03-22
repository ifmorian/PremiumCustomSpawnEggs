package de.felix_kurz.premiumcustomspawneggs.recipes;

import de.felix_kurz.premiumcustomspawneggs.items.CorePiece;
import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapelessRecipe;

public class FullCoreRecipe {

    private ShapelessRecipe recipe;

    public FullCoreRecipe(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "fullcore");
        recipe = new ShapelessRecipe(key, new FullCore());
        recipe.addIngredient(8, new CorePiece().getData());
    }

    public ShapelessRecipe getRecipe() {
        return recipe;
    }
}
