package com.zodiacmc.ZodiacManager.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastUtil {
	
	public static void globalBroadcast(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(StringUtil.parseColours(message));
		}
		ConsoleUtil.sendMessage(message);
	}

}
