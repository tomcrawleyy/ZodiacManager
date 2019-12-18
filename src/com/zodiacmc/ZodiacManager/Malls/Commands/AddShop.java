package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Factories.ShopFactory;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class AddShop extends SubCommand {

	public AddShop() {
		super("AddShop", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		CuboidFactoryManager cuboidFactoryManager = CuboidFactoryManager.getInstance();
		Player player = (Player) sender;
		User u = UserManager.getInstance().getOnlineUser(player);
		if (args.length != 1)
			return this.usage("AutoMalls AddShop <Price>");
		int price;
		try {
			price = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return this.usage("AutoMalls AddShop <Price>");
		}
		if (cuboidFactoryManager.isInSetupMode(u))
			return this.error("You are already in setupmode, please finish any other cuboid creations before attempting this!");

		for (Mall mall : Mall.getMalls()) {
			if (mall.getCuboid().isInCuboid(player.getLocation())) {
				cuboidFactoryManager.addUser(u, new ShopFactory(u, mall, price));
				return this.success("You are now in setup mode! Place a block in the first corner 1 block above the floor to mark its location!");
			}
		}
		return this.error("You must be standing inside a mall to perform this command.");
	}

}
