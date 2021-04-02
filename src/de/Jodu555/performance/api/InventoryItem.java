package de.Jodu555.performance.api;

import java.util.function.BiConsumer;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class InventoryItem {
	
	private ItemStack item;
	private BiConsumer<Player, ClickType> consumer;
	
	public InventoryItem(ItemStack item) {
		this.item = item;
	}
	
	public InventoryItem(ItemStack item, BiConsumer<Player, ClickType> consumer) {
		this.item = item;
		if(consumer != null) {
			this.consumer = consumer;
		}
	}

	public InventoryItem addConsumer(BiConsumer<Player, ClickType> consumer) {
		this.consumer = consumer;
		return this;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public BiConsumer<Player, ClickType> getConsumer() {
		return consumer;
	}
	
}
