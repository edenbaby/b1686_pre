package DA.MC.minecraftBackpack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class BackpackListener implements Listener {
    private final Plugin plugin;
    private final BackpackManager manager;
    private final NamespacedKey backpackKey;
    private final NamespacedKey recipeKey;

    public BackpackListener(Plugin plugin, BackpackManager manager, NamespacedKey backpackKey, NamespacedKey recipeKey) {
        this.plugin = plugin;
        this.manager = manager;
        this.backpackKey = backpackKey;
        this.recipeKey = recipeKey;
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Recipe r = event.getRecipe();
        if (r instanceof org.bukkit.Keyed) {
            org.bukkit.Keyed k = (org.bukkit.Keyed) r;
            if (!k.getKey().equals(recipeKey)) return;
        } else {
            return;
        }
        ItemStack stack = event.getCurrentItem();
        if (stack == null || stack.getType() != Material.BUNDLE) return;
        ItemMeta meta = stack.getItemMeta();
        UUID id = UUID.randomUUID();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(backpackKey, PersistentDataType.STRING, id.toString());
        meta.setDisplayName("Backpack");
        stack.setItemMeta(meta);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!(event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR ||
                event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) return;
        ItemStack item = event.getItem();
        if (item.getType() != Material.BUNDLE) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String idStr = pdc.get(backpackKey, PersistentDataType.STRING);
        if (idStr == null) return;
        UUID id = UUID.fromString(idStr);
        Player player = event.getPlayer();
        BackpackHolder holder = new BackpackHolder(id);
        Inventory inv = Bukkit.createInventory(holder, 54, "Backpack");
        holder.setInventory(inv);
        ItemStack[] contents = manager.getStoredContents(id);
        for (int i = 0; i < 53; i++) {
            inv.setItem(i, i < contents.length ? contents[i] : null);
        }
        inv.setItem(53, manager.createCloseItem());
        player.openInventory(inv);
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory top = event.getView().getTopInventory();
        if (!(top.getHolder() instanceof BackpackHolder)) return;
        if (event.getClickedInventory() == top && event.getSlot() == 53) {
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player) {
                ((Player) event.getWhoClicked()).closeInventory();
            }
            return;
        }
        if (event.getClickedInventory() == top && event.getSlot() == 53) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory top = event.getView().getTopInventory();
        if (!(top.getHolder() instanceof BackpackHolder)) return;
        if (event.getRawSlots().contains(53)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory top = event.getView().getTopInventory();
        if (!(top.getHolder() instanceof BackpackHolder)) return;
        BackpackHolder holder = (BackpackHolder) top.getHolder();
        ItemStack[] save = new ItemStack[53];
        for (int i = 0; i < 53; i++) {
            save[i] = top.getItem(i);
        }
        manager.setStoredContents(holder.getId(), save);
        manager.save();
    }
}
