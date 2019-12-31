package com.zodiacmc.ZodiacManager.Utilities;

import org.bukkit.Bukkit;

public class ConsoleUtil {
	

	public static void loadupMessage(String msg) {
		Bukkit.getConsoleSender().sendMessage(StringUtil.parseColours(msg));
	}
	public static void sendMessage(String msg) {
		Bukkit.getConsoleSender().sendMessage(StringUtil.parseColours("&f(&dZodiac&fManager) " + msg));
	}

}
