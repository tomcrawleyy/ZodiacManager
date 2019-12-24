package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Factories.MallFactory;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class SetMall extends SubCommand {

	public SetMall() {
		super("SetMall", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		CuboidFactoryManager cuboidFactoryManager = CuboidFactoryManager.getInstance();
		User u = UserManager.getInstance().getOnlineUser((Player) sender);
		if (cuboidFactoryManager.isInSetupMode(u))
			return this.error("You are already in setup mode! Please finish any other cuboid creations before this or type /AutoMalls ExitSetupMode");
		if (args.length != 1 || (!args[0].equalsIgnoreCase("default") && !args[0].equalsIgnoreCase("donor"))) {
			return this.usage("AutoMalls SetMall <Default|Donor>");
		}
		for (Mall mall : Mall.getMalls()) {
			if (mall.getType().getReadableName().equalsIgnoreCase(args[0]))
				return this.error(
						"This mall has already been setup, if you would like to redefine the malls protection, use /AutoMalls RedefineMall <Default|Donor>");
		}
		if (!IgnoreProtection.getUsersIgnoringProtection().contains(u)) {
			IgnoreProtection.getUsersIgnoringProtection().add(u);
		}
		cuboidFactoryManager.addUser(u, new MallFactory(args[0], u));
		return this.resolve("&aYou are now in setup mode! Place a block in the first corner to mark its location.");
	}

}
