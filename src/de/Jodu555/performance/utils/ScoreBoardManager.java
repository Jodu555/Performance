package de.Jodu555.performance.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.Jodu555.performance.Performance;
import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R3.Scoreboard;
import net.minecraft.server.v1_16_R3.ScoreboardObjective;
import net.minecraft.server.v1_16_R3.ScoreboardServer.Action;

public class ScoreBoardManager {

	public static void setScoreboard(Player player) {

		Scoreboard board = new Scoreboard();

		String servername = ChatColor.translateAlternateColorCodes('&',
				Performance.getInstance().getMainCfg().getCfg().getString("Server.Name"));

		String id = "performance";
		
		ScoreboardObjective obj = new ScoreboardObjective(board, id, IScoreboardCriteria.DUMMY,
				new ChatMessage(servername), IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);

		PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
		PacketPlayOutScoreboardObjective removepacket = new PacketPlayOutScoreboardObjective(obj, 1);
		PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj, 0);

		if (!Performance.getInstance().getListedPlayers().contains(player)) {
			sendPacket(player, removepacket);
			return;
		}

		ArrayList<PacketPlayOutScoreboardScore> scores = new ArrayList<>();

		obj.setDisplayName(new ChatMessage(servername));

		PacketPlayOutScoreboardScore s0 = new PacketPlayOutScoreboardScore(Action.CHANGE, id, "§7", 9);
		PacketPlayOutScoreboardScore s1 = new PacketPlayOutScoreboardScore(Action.CHANGE, id, "§bTPS", 8);
		PacketPlayOutScoreboardScore s2 = new PacketPlayOutScoreboardScore(Action.CHANGE, id,
				"§3" + Performance.getInstance().getSapi().getServerTPS(), 7);
		PacketPlayOutScoreboardScore s3 = new PacketPlayOutScoreboardScore(Action.CHANGE, id, "§b", 6);
		PacketPlayOutScoreboardScore s4 = new PacketPlayOutScoreboardScore(Action.CHANGE, id,
				"§aBenutzter Ram:", 5);
		PacketPlayOutScoreboardScore s5 = new PacketPlayOutScoreboardScore(Action.CHANGE, id,
				"§2" + Performance.getInstance().getSapi().getUsedMemory(false), 4);
		PacketPlayOutScoreboardScore s6 = new PacketPlayOutScoreboardScore(Action.CHANGE, id, "§f§a", 3);
		PacketPlayOutScoreboardScore s7 = new PacketPlayOutScoreboardScore(Action.CHANGE, id,
				"§aMaximaler Ram:", 2);
		PacketPlayOutScoreboardScore s8 = new PacketPlayOutScoreboardScore(Action.CHANGE, id,
				"§2" + Performance.getInstance().getSapi().getMaxMemory(), 1);
		PacketPlayOutScoreboardScore s9 = new PacketPlayOutScoreboardScore(Action.CHANGE, id, "§d", 0);

		sendPacket(player, removepacket);
		sendPacket(player, createpacket);
		sendPacket(player, display);

		scores.addAll(Arrays.asList(s0, s1, s2, s3, s4, s5, s6, s7, s8, s9));

		sendPacket(player, s0);
		sendPacket(player, s1);
		sendPacket(player, s2);
		sendPacket(player, s3);
		sendPacket(player, s4);
		sendPacket(player, s5);
		sendPacket(player, s6);
		sendPacket(player, s7);
		sendPacket(player, s8);
		sendPacket(player, s9);
	}

	private static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }
	
	public static void sendPacket(Player p, Packet<?> packet) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

}
