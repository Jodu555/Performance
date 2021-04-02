package de.Jodu555.performance.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import de.Jodu555.performance.Performance;
import de.Jodu555.performance.api.NormalInventory;

public class InventoryManager {

	public static int updateTask;

	// Redstone: Allles
	// Diamond: Tps
	// Iron: Ram

	public static void openInventory(Player player) {
		NormalInventory inv = new NormalInventory("§6§l« §f§6Ressources §6§l»", InventoryType.CHEST);

		inv.fill(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

		inv.setSlot(10, new ItemBuilder(Material.DIAMOND).setName("§bTPS")
				.setLore(" ", "§bServer TPS: §3" + Performance.getInstance().getSapi().getServerTPS(), " ").build());

		inv.setSlot(13, new ItemBuilder(Material.REDSTONE).setName("§bAlle §aInsgesamt")
				.setLore(" ", "§bServer TPS: §3" + Performance.getInstance().getSapi().getServerTPS(), " ",
						"§aFreier Ram: §2" + Performance.getInstance().getSapi().getFreeMemory(false),
						"§aMaximaler Ram: §2" + Performance.getInstance().getSapi().getMaxMemory(),
						"§aBenutzter Ram: §2" + Performance.getInstance().getSapi().getUsedMemory(false), " ")
				.build());

		inv.setSlot(16, new ItemBuilder(Material.IRON_INGOT).setName("§aRam")
				.setLore(" ", "§aFreier Ram: §2" + Performance.getInstance().getSapi().getFreeMemory(false),
						"§aMaximaler Ram: §2" + Performance.getInstance().getSapi().getMaxMemory(),
						"§aBenutzter Ram: §2" + Performance.getInstance().getSapi().getUsedMemory(false), " ")
				.build());

		inv.show(player);
	}

	@SuppressWarnings("deprecation")
	public static void startUpdatetask() {
		updateTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Performance.getInstance(), new Runnable() {

			@Override
			public void run() {

				for (Player player : Bukkit.getOnlinePlayers()) {
					ScoreBoardManager.setScoreboard(player);
					if (player.getOpenInventory().getTopInventory() != null) {
						if (player.getOpenInventory().getTitle().equalsIgnoreCase("§6§l« §f§6Ressources §6§l»")) {
							openInventory(player);
						}
					}
				}
			}
		}, 20, 20);
	}

}
