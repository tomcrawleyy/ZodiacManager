package com.zodiacmc.ZodiacManager.Commands;

import org.bukkit.command.CommandSender;

public interface ISubCommand {

	public boolean processCommand(CommandSender sender, String[] args);
	public boolean isPlayerOnly();
	public String permissionRequired();
	public String name();
	public void setBaseCommand(BaseCommand command);
	public BaseCommand getBaseCommand();
}
