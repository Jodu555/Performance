package de.Jodu555.performance.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.Jodu555.performance.Performance;
import de.Jodu555.performance.utils.InventoryManager;

public class performance implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(player.hasPermission("performance.use")) {
				if(args.length == 0) {
				InventoryManager.openInventory(player);
				} else if (args.length == 1) {
					if(args[0].equalsIgnoreCase("on"))  {
						if(!Performance.getInstance().getListedPlayers().contains(player)) {
							Performance.getInstance().getListedPlayers().add(player);
							player.sendMessage("§aDas Performance Scoreboard wurde aktiviert!");
						} else {
							player.sendMessage("§7Das Performance Scoreboard ist bereits aktiviert!");
						}
					} else if(args[0].equalsIgnoreCase("off")) {
						if(Performance.getInstance().getListedPlayers().contains(player)) {
							Performance.getInstance().getListedPlayers().remove(player);
							player.sendMessage("§cDas Performance Scoreboard wurde deaktiviert!");
						} else {
							player.sendMessage("§7Das Performance Scoreboard ist bereits deaktiviert!");
						}
					}
				}
			}
		}
		
		return false;
	}

}
