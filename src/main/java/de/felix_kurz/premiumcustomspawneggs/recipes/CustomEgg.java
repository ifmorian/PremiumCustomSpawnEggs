package de.felix_kurz.premiumcustomspawneggs.recipes;

import de.felix_kurz.premiumcustomspawneggs.items.CorePiece;
import de.felix_kurz.premiumcustomspawneggs.items.CoreShard;
import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class CustomEgg {

    private Main plugin = Main.getPlugin();

    private String id;
    private String name;
    private Material type;
    private boolean enchanted;
    private boolean throwable;
    private int amount;
    private HashMap<String, String> ingredients = new HashMap<>();
    private String[] matrix;
    private String entity;
    private int spawnAmount;

    private ItemStack item;

    private ShapedRecipe recipe;

    public CustomEgg(String id, String name, String type, boolean enchanted, boolean throwable, int amount, HashMap<String, String> ingredients, String[] matrix, String entity, int spawnAmount) {
        this.id = id;
        this.name = name;
        this.type = Material.getMaterial(name);
        this.enchanted = enchanted;
        this.throwable = throwable;
        this.amount = amount;
        this.ingredients = ingredients;
        this.matrix = matrix;
        this.entity = entity;
        this.spawnAmount = spawnAmount;
    }

    public void createItem() {
        item = new ItemStack(type);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("entity", entity);
        nbtItem.setBoolean("throw", throwable);
        nbtItem.setInteger("amount", spawnAmount);
        item = nbtItem.getItem();

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (enchanted) meta.addEnchant(Enchantment.LOYALTY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    public void createRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, id);
        recipe = new ShapedRecipe(key, item);
        for (Map.Entry<String, String> entry : ingredients.entrySet()) {
            if (entry.getValue().equals("fullcore")) recipe.setIngredient(entry.getKey().charAt(0), (RecipeChoice) new FullCore().getItem());
            if (entry.getValue().equals("piece")) recipe.setIngredient(entry.getKey().charAt(0), (RecipeChoice) new CorePiece().getItem());
            if (entry.getValue().equals("shard")) recipe.setIngredient(entry.getKey().charAt(0), (RecipeChoice) new CoreShard().getItem());
            recipe.setIngredient(entry.getKey().charAt(0), Material.getMaterial(entry.getValue()));
        }
    }

    public ItemStack getItem() {
        return item;
    }
}
