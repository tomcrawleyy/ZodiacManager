package com.zodiacmc.ZodiacManager.Utilities;

import org.bukkit.Bukkit;

public class ConsoleUtil {
	
	public static void sendMessage(String msg) {
		Bukkit.getConsoleSender().sendMessage(StringUtil.parseColours(msg));
	}

}
