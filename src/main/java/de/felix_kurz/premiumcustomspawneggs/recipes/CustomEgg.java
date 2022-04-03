package de.felix_kurz.premiumcustomspawneggs.recipes;

import de.felix_kurz.premiumcustomspawneggs.items.CorePiece;
import de.felix_kurz.premiumcustomspawneggs.items.CoreShard;
import de.felix_kurz.premiumcustomspawneggs.items.FullCore;
import de.felix_kurz.premiumcustomspawneggs.main.Main;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
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
    private boolean glow;
    private String glowColor;

    private ItemStack item;
    private ShapedRecipe recipe;

    public static final HashMap<String, CustomEgg> eggs = new HashMap<>();

    public CustomEgg(String id, String name, String type, boolean enchanted, boolean throwable, int amount, HashMap<Character, String> ingredients,
                     String[] matrix, String entity, int spawnAmount, boolean glow, String glowColor) {
        this.id = id;
        this.name = name;
        this.type = Material.getMaterial(type);
        this.enchanted = enchanted;
        this.throwable = throwable;
        this.amount = amount;
        this.ingredients = ingredients;
        this.matrix = matrix;
        this.entity = entity;
        this.spawnAmount = spawnAmount;
        this.glow = glow;
        this.glowColor = glowColor.toLowerCase();
    }

    public static void setupEggs() {
        for (String path : Main.getCfgM().getEggs().getKeys(false)) {
            CustomEgg egg = getEgg(path);
            egg.createItem();
            egg.setupRecipe();
            eggs.put(egg.id, egg);
        }
    }

    public void createItem() {
        item = new ItemStack(type, amount);

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
        tag.putString("pcse_entity", entity);
        tag.putBoolean("pcse_throw", throwable);
        tag.putInt("pcse_amount", spawnAmount);
        tag.putBoolean("pcse_glow", glow);
        tag.putString("pcse_color", glowColor);
        nmsItem.setTag(tag);
        item = CraftItemStack.asBukkitCopy(nmsItem);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (enchanted) meta.addEnchant(Enchantment.LOYALTY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }

    public void createRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, id);
        recipe = new ShapedRecipe(key, item);
        recipe.shape(matrix);
        for (Map.Entry<Character, String> entry : ingredients.entrySet()) {
            try {
                switch (entry.getValue()) {
                    case "fullcore" -> recipe.setIngredient(entry.getKey(), new RecipeChoice.ExactChoice(new FullCore().getItem()));
                    case "piece" -> recipe.setIngredient(entry.getKey(), new RecipeChoice.ExactChoice(new CorePiece().getItem()));
                    case "shard" -> recipe.setIngredient(entry.getKey(), new RecipeChoice.ExactChoice(new CoreShard().getItem()));
                    default -> {
                        recipe.setIngredient(entry.getKey(), Material.getMaterial(entry.getValue()));
                    }
                }
            } catch (Exception ignore) {}
        }
    }

    public void setupRecipe() {
        createRecipe();
        Bukkit.addRecipe(recipe);
    }

    public static CustomEgg getEgg(String egg) {
        ConfigurationSection section = Main.getCfgM().getEgg(egg);
        if (section == null) return null;
        HashMap<Character, String> ingredients = new HashMap<>();
        for (String c : section.getConfigurationSection("ingredients").getKeys(false)) {
            ingredients.put(c.charAt(0), section.getString("ingredients." + c));
        }
        return new CustomEgg(
                egg,
                section.getString("name"),
                section.getString("item"),
                section.getBoolean("enchanted"),
                section.getBoolean("throwable"),
                section.getInt("amountFromCrafting"),
                ingredients,
                new String[]{section.getString("recipe.row1"), section.getString("recipe.row2"), section.getString("recipe.row3")},
                section.getString("spawnedEntity"),
                section.getInt("amount"),
                section.getBoolean("glow"),
                section.getString("glowColor")
        );
    }

}
