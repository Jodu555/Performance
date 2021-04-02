package de.Jodu555.performance.api;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.Jodu555.performance.Performance;

public class NormalInventory {
	
	private InventoryType inventoryType;
	private String inventoryName;
	private int inventorySlots;
	private HashMap<Integer, InventoryItem> slots = new HashMap<>();
	private Inventory inv;
	
	private Player watcher;
	
	//TODO: Clickable Cancel

	public NormalInventory(String name, InventoryType type) {
		this.inventoryName = name;
		this.inventoryType = type;
		this.inventorySlots = type.getDefaultSize();
		init();
	}

	public NormalInventory(String name, int slots) {
		this.inventoryName = name;
		this.inventorySlots = slots;
		init();
	}

	private void init() {
		Performance.getInstance().inventorylist.add(this);
	}

	public void show(Player player) {
		this.watcher = player;
		player.openInventory(build());
	}
	
	public void update() {
		if(watcher != null) {
			watcher.openInventory(build());
		}
	}
	
	public Inventory build() {
		if (this.inventorySlots != 0) {
			this.inv = Bukkit.createInventory(null, this.inventorySlots, this.inventoryName);
		} else {
			this.inv = Bukkit.createInventory(null, this.inventoryType, this.inventoryName);
		}

		for (Integer i : this.slots.keySet()) {
			try {
				this.inv.setItem(i, this.slots.get(i).getItem());
			} catch (Exception e) {
				System.out.println("Fehler in Setzung der Items, Bitte überprüfe die Slots der Items");
			}
		}
		return this.inv;
	}

	public void setSlot(int slot, InventoryItem item) {
		this.slots.put(slot, item);
	}

	public void setSlot(int slot, ItemStack item) {
		this.slots.put(slot, new InventoryItem(item));
	}

	public void addItem(InventoryItem item) {
		int slot = 0;
		for (int i = 0; i < this.inventorySlots; i++) {
			if (this.slots.get(i) == null) {
				slot = i;
				break;
			}
		}
		this.slots.put(slot, item);
	}

	public void addItem(ItemStack item) {
		int slot = 0;
		for (int i = 0; i < this.inventorySlots; i++) {
			if (this.slots.get(i) == null) {
				slot = i;
				break;
			}
		}
		this.slots.put(slot, new InventoryItem(item));
	}

	public void fill(InventoryItem item) {
		for (int i = 0; i < getInventorySlots(); i++) {
			setSlot(i, item);
		}
	}

	public void fill(ItemStack item) {
		for (int i = 0; i < getInventorySlots(); i++) {
			setSlot(i, item);
		}
	}

	public InventoryItem getSlot(int slot) {
		return this.slots.get(slot);
	}

	public String getInventoryName() {
		return inventoryName;
	}

	public int getInventorySlots() {
		return inventorySlots;
	}
	
}
