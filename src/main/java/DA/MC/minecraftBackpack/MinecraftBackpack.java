package DA.MC.minecraftBackpack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftBackpack extends JavaPlugin {

    private BackpackManager manager;
    private NamespacedKey backpackKey;
    private NamespacedKey recipeKey;

    @Override
    public void onEnable() {
        this.backpackKey = new NamespacedKey(this, "backpack-id");
        this.recipeKey = new NamespacedKey(this, "backpack-recipe");
        this.manager = new BackpackManager(this, backpackKey);
        this.manager.load();
        Bukkit.getPluginManager().registerEvents(new BackpackListener(this, manager, backpackKey, recipeKey), this);
        ItemStack result = new ItemStack(Material.BUNDLE, 1);
        var meta = result.getItemMeta();
        meta.setDisplayName("Backpack");
        result.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("WSW", "SWS", "WSW");
        recipe.setIngredient('W', Material.WHITE_WOOL);
        recipe.setIngredient('S', Material.STRING);
        Bukkit.addRecipe(recipe);

    }

    @Override
    public void onDisable() {
        if (manager != null) {
            manager.save();
        }
    }
