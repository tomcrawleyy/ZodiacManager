package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactory;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.MallFactory;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class AddMall extends SubCommand {

	public AddMall() {
		super("SetMall", true, true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User u = UserManager.getInstance().getOnlineUser((Player) sender);
		if (args.length > 1)
			return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AutoMalls SetMall <Default|Donor>");
		if (CuboidFactory.isInSetupMode(u))
			return CommandUtil.success(sender, command.getPrefix()
					+ " &cError: You are already in setupmode, please finish any other cuboid creations before attempting this!");
		if (args.length != 1 | (!args[0].equalsIgnoreCase("default") && !args[0].equalsIgnoreCase("donor"))) {
			return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AutoMalls SetMall <Default|Donor>");
		}
		for (Mall mall : Mall.getMalls()) {
			if (mall.getType().getReadableName().equalsIgnoreCase(args[0]))
				return CommandUtil.success(sender, command.getPrefix()
						+ " &cError: This mall has already been setup, if you would like to redefine the malls protection, use /AutoMalls RedefineMall <Default|Donor>");
		}
		CuboidFactory.addUser(u, new MallFactory(args[0], u));
		return CommandUtil.success(sender, command.getPrefix()
				+ " &aYou are now in setup mode! Place a block in the first corner to mark its location.");
	}

	public String permissionRequired() {
		return "AutoMalls.AddMall";
	}

}
