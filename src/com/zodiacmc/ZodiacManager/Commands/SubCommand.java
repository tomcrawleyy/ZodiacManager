package com.zodiacmc.ZodiacManager.Commands;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public abstract class SubCommand implements ISubCommand {
	
	protected BaseCommand command;
	protected String name;
	protected boolean isPlayerOnly, hasPermission;
	protected CommandSender sender;
	
	public SubCommand(String name, boolean isPlayerOnly) {
		this.name = name;
	}
	
	public String name() {
		return name;
	}
	
	public void setBaseCommand(BaseCommand command) {
		this.command = command;
	}
	
	public BaseCommand getBaseCommand() {
		return command;
	}
	
	public String permissionRequired(){
		return command.getName() + "." + name;
	}
	
	public boolean isPlayerOnly() {
		return isPlayerOnly;
	}
	
	public void setSender(CommandSender sender) {
		this.sender = sender;
	}
	
	public boolean resolve(String message) {
		this.sender.sendMessage(StringUtil.parseColours(this.command.getPrefix() + " &a" + message));
		return true;
	}
	
	public boolean success(String message) {
		this.sender.sendMessage(StringUtil.parseColours(this.command.getPrefix() + " &aSuccess: " + message));
		return true;
	}
	
	public boolean error(String message) {
		this.sender.sendMessage(StringUtil.parseColours(this.command.getPrefix() + " &cError: " + message));
		return true;
	}
	
	public boolean usage(String message) {
		this.sender.sendMessage(StringUtil.parseColours(this.command.getPrefix() + " &cUsage: /" + message));
		return true;
	}

}
