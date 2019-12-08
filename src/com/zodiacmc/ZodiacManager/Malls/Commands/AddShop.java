package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactory;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.ShopFactory;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class AddShop extends SubCommand {

	public AddShop() {
		super("AddShop", true, true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		User u = UserManager.getInstance().getOnlineUser(player);

		if (CuboidFactory.isInSetupMode(u))
			return CommandUtil.success(sender, command.getPrefix()
					+ " &cError: You are already in setupmode, please finish any other cuboid creations before attempting this!");

		for (Mall mall : Mall.getMalls()) {
			if (mall.getCuboid().isInCuboid(player.getLocation())) {
				CuboidFactory.addUser(u, new ShopFactory(u, mall));
				return CommandUtil.success(sender, command.getPrefix()
						+ " &aYou are now in setup mode! Place a block in the first corner 1 block above the floor to mark its location!");
			}
		}
		return CommandUtil.success(sender, command.getPrefix() + " &cError: You must be standing inside a mall to perform this command.");
	}

	public String permissionRequired() {
		return "AutoMalls.AddShop";
	}

}
