package de.felix_kurz.premiumcustomspawneggs.recipes;

import de.felix_kurz.premiumcustomspawneggs.items.CorePiece;
import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

public class FullCoreRecipe {

    private ShapelessRecipe recipe;

    public FullCoreRecipe(Main plugin) {
        RecipeChoice corePiece = new RecipeChoice.ExactChoice(new CorePiece());
        NamespacedKey key = new NamespacedKey(plugin, "fullcore");
        recipe = new ShapelessRecipe(key, new FullCore());
        recipe.getIngredientList().add(new CorePiece());
    }

    public ShapelessRecipe getRecipe() {
        return recipe;
    }
}
