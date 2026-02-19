package DA.MC.minecraftBackpack;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BackpackManager {
    private final Plugin plugin;
    private final NamespacedKey backpackKey;
    private File dataFile;
    private FileConfiguration data;

    public BackpackManager(Plugin plugin, NamespacedKey backpackKey) {
        this.plugin = plugin;
        this.backpackKey = backpackKey;
    }

    public NamespacedKey getBackpackKey() {
        return backpackKey;
    }

    public void load() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        dataFile = new File(plugin.getDataFolder(), "backpacks.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void save() {
        if (data == null) return;
        try {
            data.save(dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ItemStack[] getStoredContents(UUID id) {
        List<?> list = data.getList("packs." + id + ".contents");
        ItemStack[] result = new ItemStack[53];
        if (list == null) return result;
        int i = 0;
        for (Object o : list) {
            if (i >= 53) break;
            result[i++] = o instanceof ItemStack ? (ItemStack) o : null;
        }
        return result;
    }

    public void setStoredContents(UUID id, ItemStack[] contents) {
        List<ItemStack> list = new ArrayList<>(53);
        for (int i = 0; i < 53; i++) {
            list.add(i < contents.length ? contents[i] : null);
        }
        data.set("packs." + id + ".contents", list);
    }

    public ItemStack createCloseItem() {
        ItemStack dye = new ItemStack(Material.RED_DYE);
        var meta = dye.getItemMeta();
        meta.setDisplayName("Close");
        dye.setItemMeta(meta);
        return dye;
    }
}
