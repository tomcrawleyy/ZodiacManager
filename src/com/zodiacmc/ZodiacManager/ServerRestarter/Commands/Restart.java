package com.zodiacmc.ZodiacManager.ServerRestarter.Commands;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.ServerRestarter.Scheduling.RestartScheduler;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class Restart extends SubCommand {
	
	public Restart() {
		super("Restart", false, true);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {
			sender.sendMessage(StringUtil.parseColours("&cUsage: /ServerRestarter Restart"));
			return true;
		}
		RestartScheduler.getInstance().forceRestart();
		return true;
	}

	@Override
	public String permissionRequired() {
		return "ServerRestarter.Restart";
	}

}
