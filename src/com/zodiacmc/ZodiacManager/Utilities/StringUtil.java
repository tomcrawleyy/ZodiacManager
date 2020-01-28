package com.zodiacmc.ZodiacManager.Utilities;

import org.bukkit.ChatColor;

public class StringUtil {
	public static String parseColours(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	/**
	 * Alias for StringUtil.parseColours(String)
	 * @return A string with formatted colors
	 */
	public static String parseColors(String msg) {
		return parseColours(msg);
	}
}
