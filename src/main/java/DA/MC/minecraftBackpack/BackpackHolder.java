package DA.MC.minecraftBackpack;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class BackpackHolder implements InventoryHolder {
    private final UUID id;
    private Inventory inventory;

    public BackpackHolder(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
