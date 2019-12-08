package com.zodiacmc.ZodiacManager.Utilities;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtil {
	
	public static boolean success(CommandSender source, String message) {
		source.sendMessage(StringUtil.parseColours(message));
		return true;
	}
	
	public static boolean success(Player source, String message) {
		source.sendMessage(StringUtil.parseColours(message));
		return true;
	}

}
