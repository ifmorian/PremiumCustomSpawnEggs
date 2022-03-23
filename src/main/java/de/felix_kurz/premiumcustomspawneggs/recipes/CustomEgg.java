package de.felix_kurz.premiumcustomspawneggs.recipes;

import de.felix_kurz.premiumcustomspawneggs.items.CorePiece;
import de.felix_kurz.premiumcustomspawneggs.items.CoreShard;
import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class CustomEgg {

    private final Main plugin = Main.getPlugin();

    private final String id;
    private final String name;
    private final Material type;
    private final boolean enchanted;
    private final boolean throwable;
    private int amount;
    private final HashMap<Character, String> ingredients;
    private final String[] matrix;
    private final String entity;
    private final int spawnAmount;

    private ItemStack item;

    private ShapedRecipe recipe;

    public CustomEgg(String id, String name, String type, boolean enchanted, boolean throwable, int amount, HashMap<Character, String> ingredients, String[] matrix, String entity, int spawnAmount) {
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

    public ItemStack createItem() {
        item = new ItemStack(type, amount);

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
        return item;
    }

    public void createRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, id);
        recipe = new ShapedRecipe(key, item);
        for (Map.Entry<Character, String> entry : ingredients.entrySet()) {
            try {
                if (entry.getValue().equals("fullcore")) recipe.setIngredient(entry.getKey(), (RecipeChoice) new FullCore().getItem());
                else if (entry.getValue().equals("piece")) recipe.setIngredient(entry.getKey(), (RecipeChoice) new CorePiece().getItem());
                else if (entry.getValue().equals("shard")) recipe.setIngredient(entry.getKey(), (RecipeChoice) new CoreShard().getItem());
                else recipe.setIngredient(entry.getKey(), Material.getMaterial(entry.getValue()));
            } catch (Exception ignore) {}
        }
        recipe.shape(matrix);
    }

    public void setupRecipe() {
        Bukkit.addRecipe(recipe);
    }

    public static CustomEgg getEgg(String egg) {
        ConfigurationSection section = Main.getCfgM().getEgg(egg);
        if (section == null) return null;
        return new CustomEgg(
                egg,
                section.getString("name"),
                section.getString("item"),
                section.getBoolean("enchanted"),
                section.getBoolean("throwable"),
                section.getInt("amountFromCrafting"),
                (HashMap) section.get("ingredients"),
                new String[]{section.getString("recipe.row1"), section.getString("recipe.row2"), section.getString("recipe.row3")},
                section.getString("spawnedEntity"),
                section.getInt("amount")
        );
    }

    public static ItemStack getEggItem(String egg, int n) {
        ConfigurationSection section = Main.getCfgM().getEgg(egg);
        if (section == null) return null;
        HashMap<Character, String> ingredients = new HashMap<>();
        CustomEgg e = new CustomEgg(
                egg,
                section.getString("name"),
                section.getString("item"),
                section.getBoolean("enchanted"),
                section.getBoolean("throwable"),
                n,
                (HashMap) section.get("ingredients"),
                new String[]{section.getString("recipe.row1"), section.getString("recipe.row2"), section.getString("recipe.row3")},
                section.getString("spawnedEntity"),
                section.getInt("amount")
        );
        return e.createItem();
    }

}
