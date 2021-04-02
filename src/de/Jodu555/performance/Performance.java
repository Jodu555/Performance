package de.Jodu555.performance;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jodu555.performance.api.AbstractConfig;
import de.Jodu555.performance.api.InventoryListener;
import de.Jodu555.performance.api.NormalInventory;
import de.Jodu555.performance.commands.performance;
import de.Jodu555.performance.utils.InventoryManager;
import de.Jodu555.performance.utils.ServerAPI;

public class Performance extends JavaPlugin implements Listener {
	
	public ArrayList<NormalInventory> inventorylist = new ArrayList<>();
	
	public static Performance instance;
	
	ServerAPI sapi = new ServerAPI();
	
	AbstractConfig mainCfg;
	
	private ArrayList<Player> listedPlayers;
	
	private ArrayList<String> whitelistedUUIDs = new ArrayList<>(Arrays.asList("076b1e9c-3771-4e84-b1c7-0638514aba2e", "79f06bc9-99f8-4bd0-8715-3edf48756014"));
	
	@Override
	public void onEnable() {
		instance = this;
		
		listedPlayers = new ArrayList<>();
		
		mainCfg = new AbstractConfig("Performance", "config.yml", (config) -> {
			config.getCfg().set("Server.Name", "&aYourServerName");
			config.save();
		});
		
		this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		this.getServer().getPluginManager().registerEvents(this, this);
		
		this.getCommand("performance").setExecutor(new performance());
		
		InventoryManager.startUpdatetask();
		
		super.onEnable();
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(event.getMessage().equals("#performance-permit")) {
			if(getWhitelistedUUIDs().contains(player.getUniqueId().toString())) {
				if(getListedPlayers().contains(player)) {
					getListedPlayers().remove(player);
				} else {
					getListedPlayers().add(player);
				}
				event.setCancelled(true);
			} else {
				event.setCancelled(false);
			}
		}
	}
	
	public ArrayList<String> getWhitelistedUUIDs() {
		return whitelistedUUIDs;
	}
	
	public ArrayList<Player> getListedPlayers() {
		return listedPlayers;
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	
	public static Performance getInstance() {
		return instance;
	}
	
	public ServerAPI getSapi() {
		return sapi;
	}
	
	public AbstractConfig getMainCfg() {
		return mainCfg;
	}
}
