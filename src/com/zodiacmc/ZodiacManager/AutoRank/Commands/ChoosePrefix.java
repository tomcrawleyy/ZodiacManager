package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Configurations.ChoosePrefixConfig;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class ChoosePrefix extends SubCommand {
	

	List<String> prefixes;
	
	public ChoosePrefix() {
		super("ChoosePrefix", true);
		prefixes = ChoosePrefixConfig.getInstance().getAvailablePrefixes();
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		User u = UserManager.getInstance().getOnlineUser(player);
		if (args.length == 0) {
			int i = 1;
			sender.sendMessage(StringUtil.parseColours(command.getPrefix() + " &aAvailable Prefixes:"));
			sender.sendMessage(StringUtil.parseColours("&c0. " + u.getRank().getPrefix()));
			for (String prefix : prefixes) {
				sender.sendMessage(StringUtil.parseColours("&c" + i + ". " + prefix));
				i++;
			}
			return this.usage("AutoRank ChoosePrefix <Number>");
		}
		if (args.length != 1)
			return this.usage("AutoRank ChoosePrefix <Number>");
		Integer value = null;
		try {
			value = Integer.parseInt(args[0])-1;
		} catch (NumberFormatException e) {
			return this.usage("AutoRank ChoosePrefix <Number>");
		}
		if (value < -1)
			return this.error("Number must be 0 or greater.");
		if (prefixes.size() < value+1)
			return this.error("Number must be smaller than " + (prefixes.size()+1) + ".");
		if (value == -1) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "manudelv " + player.getName() + " prefix ");
			return this.success("Prefix has been successfully updated to: " + u.getRank().getPrefix());
		}
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "manuaddv " + player.getName() + " prefix " + prefixes.get(value));
		return this.success("Prefix has been successfully updated to: " + prefixes.get(value));
	}
}
