package de.Jodu555.performance.utils;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.Server;

public class ServerAPI {
	
	private static double mb = 1024 * 1024;

	public ServerAPI() {
		
	}
	
	public String clearMemory() {
		double mem = getRawUsedMemory(false);
		Runtime.getRuntime().gc();
		return String.format("%2.02f", mem - getRawUsedMemory(false));
	}

	public double getFreeMemory(boolean inPercentage) {
		if (!inPercentage)
			return new NumbersAPI(String.format("%2.02f", (getRawUsedMemory(false) - getMaxMemory()) * -1)).getDouble();
		else
			return new NumbersAPI(
					String.format("%2.02f", (((getRawUsedMemory(false) - getMaxMemory()) * -1) / getMaxMemory()) * 100))
							.getDouble();
	}

	public double getMaxMemory() {
		return Runtime.getRuntime().maxMemory() / mb;
	}

	public double getUsedMemory(boolean inPercentage) {
		if (!inPercentage)
			return new NumbersAPI(String.format("%2.02f",
					(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mb)).getDouble();
		else
			return new NumbersAPI(String.format("%2.02f", (getRawUsedMemory(false) / getMaxMemory()) * 100))
					.getDouble();
	}

	public double getRawUsedMemory(boolean inPercentage) {
		if (!inPercentage)
			return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / mb;
		else
			return (getRawUsedMemory(false) / getMaxMemory()) * 100;
	}

	public double getRawFreeMemory(boolean inPercentage) {
		if (!inPercentage)
			return (getRawUsedMemory(false) - getMaxMemory()) * -1;
		else
			return (((getRawUsedMemory(false) - getMaxMemory()) * -1) / getMaxMemory()) * 100;
	}

	public double getServerTPS() {
		try {
			Object minecraftServer = null;
			Field recentTps = null;
			Server server = Bukkit.getServer();
			Field consoleField = server.getClass().getDeclaredField("console");
			consoleField.setAccessible(true);
			minecraftServer = consoleField.get(server);

			recentTps = minecraftServer.getClass().getSuperclass().getDeclaredField("recentTps");
			recentTps.setAccessible(true);

			double tps = ((double[]) recentTps.get(minecraftServer))[0];
			if (tps > 20)
				tps = 20;
			return new NumbersAPI(String.format("%2.02f", tps)).getDouble();
		} catch (Throwable e) {
			return 20.0;
		}
	}
	
}
