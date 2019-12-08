package com.zodiacmc.ZodiacManager.Utilities;

import org.bukkit.ChatColor;

public class StringUtil {
	public static String parseColours(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
