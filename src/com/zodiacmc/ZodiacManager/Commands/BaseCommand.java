package com.zodiacmc.ZodiacManager.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class BaseCommand implements CommandExecutor {

	private String name;
	private String prefix;
	public List<ISubCommand> subCommands;
	private static Map<String, BaseCommand> commands = new HashMap<String, BaseCommand>();
	private ZodiacManager zodiacManager = ZodiacManager.getInstance();

	public BaseCommand(String name, String prefix) {
		this.prefix = prefix;
		if (commands.keySet().contains(name)) {
			ConsoleUtil.sendMessage(this.prefix + " &cError: This command has already been instantiated!");
			return;
		}
		commands.put(name, this);
		subCommands = new ArrayList<ISubCommand>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void instantiateCommand(ISubCommand command) {
		if (subCommands.contains(command)) {
			ConsoleUtil.sendMessage(prefix + "&cError: This sub-command has already been instantiated!");
			return;
		}
		command.setBaseCommand(this);
		subCommands.add(command);
	}
	
	public String getFilePath() {
		return zodiacManager.getDataFolder() + "/" + name + "/";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!cmd.getName().equalsIgnoreCase(name))
			return false;
		if (args.length > 0) {
			for (ISubCommand subCommand : subCommands) {
				if (args[0].equalsIgnoreCase(subCommand.name())) {
					if (subCommand.isPlayerOnly() && (!(sender instanceof Player))) {
						continue;
					}
					if (sender instanceof Player) {
						Player player = (Player) sender;
						if (!player.hasPermission(subCommand.permissionRequired())) {
							continue;
						}
					}
					String[] newArgs = new String[args.length - 1];
					for (int i = 1; i < args.length; i++)
						newArgs[i - 1] = args[i];
					return subCommand.processCommand(sender, newArgs);
				}
			}
		}
		sender.sendMessage(StringUtil.parseColours("&a-----" + name + " Available Sub-Commands-----"));
		for (ISubCommand subCommand : subCommands) {
			if (subCommand.isPlayerOnly() && (!(sender instanceof Player)))
				continue;
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission(subCommand.permissionRequired())) {
					continue;
				}
			}
			sender.sendMessage(StringUtil.parseColours(prefix + " /" + name + " " + subCommand.name()));
		}
		return true;
	}
}
