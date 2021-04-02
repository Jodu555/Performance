package de.Jodu555.performance.api;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.Jodu555.performance.Performance;

public class InventoryListener implements Listener{
	
	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		for (NormalInventory invs : Performance.getInstance().inventorylist) {
			if (event.getClickedInventory() != null)
				event.getView().getTitle();
			if (invs.getInventoryName().equals(event.getView().getTitle())) {
				if (event.getCurrentItem() == null)
					return;
				if (event.getCurrentItem().getItemMeta() == null)
					return;
				if (event.getSlot() == event.getRawSlot()) {
					if ((event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
							|| (event.getAction() == InventoryAction.COLLECT_TO_CURSOR)) {
						event.setCancelled(true);
					}
					event.setCancelled(true);
					try {
						int slot = event.getSlot();
//						System.out.println(slot);
//						System.out.println(invs.getSlot(slot));
						if (invs.getSlot(slot).getConsumer() != null) {
							invs.getSlot(slot).getConsumer().accept(player, event.getClick());
						}
					} catch (Exception localException) {
						localException.printStackTrace();
					}
					return;
				}
			}
		}
	}
	
}
