package de.felix_kurz.premiumcustomspawneggs.recipes;

import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

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
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
    }

    public void createRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, id);
    }

}
