package me.nowaha.tribecrafts;

import me.nowaha.infinitechests.InfiniteChests;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class TribeCrafts extends JavaPlugin implements Listener {

    ItemStack artifact;
    ItemStack amplifiedArtifact;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        artifact = new ItemStackBuilder(Material.DRIED_KELP).displayName("§8Artifact").lore(new String[] {"§7This item has no use on it's own,", "§7but can do great things once merged.", "", "§f§lCrafting Component"}).build();

        amplifiedArtifact = new ItemStackBuilder(Material.CHORUS_PLANT).displayName("§5§lAmplified Artifact").lore(new String[] {"§7This item has no use on it's own,", "§7but can do greater things once merged.", "", "§f§lCrafting Component"}).build();

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("observer_custom"), new ItemStack(Material.OBSERVER, 2));
        recipe.shape(
                "iii",
                "iai",
                "iii");

        recipe.setIngredient('i', Material.IRON_INGOT);
        recipe.setIngredient('a', artifact);

        ShapedRecipe amplifiedRecipe = new ShapedRecipe(NamespacedKey.minecraft("amplified_artifact"), amplifiedArtifact);
        amplifiedRecipe.shape("aia",
                                "iai",
                                "aia");

        amplifiedRecipe.setIngredient('i', artifact);
        amplifiedRecipe.setIngredient('a', Material.OBSIDIAN);

        ShapedRecipe vcUpgradeRecipe = new ShapedRecipe(NamespacedKey.minecraft("vcupgradeitem"), InfiniteChests.instance.getUpgradeItem(1));
        vcUpgradeRecipe.shape("iii",
                "iai",
                "iii");

        vcUpgradeRecipe.setIngredient('i', amplifiedArtifact);
        vcUpgradeRecipe.setIngredient('a', Material.DRAGON_EGG);

        getServer().addRecipe(recipe);
        getServer().addRecipe(amplifiedRecipe);
        getServer().addRecipe(vcUpgradeRecipe);
    }

    @EventHandler(ignoreCancelled = true)
    public void onCraftItem(CraftItemEvent e) {
        if (e.getRecipe().getResult().getType().equals(Material.OBSERVER)) {
            try {
                ShapedRecipe recipe = (ShapedRecipe) e.getRecipe();
                if (!recipe.getIngredientMap().containsValue(artifact)) {
                    e.setCancelled(true);
                    e.getWhoClicked().sendMessage("§c§lNOPE! §cThis item has a different recipe.");
                }
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
