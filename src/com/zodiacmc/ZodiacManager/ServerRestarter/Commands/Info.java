package com.zodiacmc.ZodiacManager.ServerRestarter.Commands;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.ServerRestarter.Scheduling.RestartScheduler;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class Info extends SubCommand {

	public Info() {
		super("Info", false, false);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {
			sender.sendMessage(StringUtil.parseColours("&cUsage: /ServerRestarter Info"));
		}
		sender.sendMessage(StringUtil.parseColours(command.getPrefix() + " &aThere is &f[&d" + RestartScheduler.getInstance().getTimeLeft() + "&f] &auntil the next scheduled restart."));
		return true;
	}

	@Override
	public String permissionRequired() {
		return null;
	}

}
