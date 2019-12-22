package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Cuboids.Factories.CuboidRedefinerFactory;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class RedefineMall extends SubCommand {

	public RedefineMall() {
		super("RedefineMall", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		CuboidFactoryManager cuboidFactoryManager = CuboidFactoryManager.getInstance();
		Player p = (Player) sender;
		User u = UserManager.getInstance().getOnlineUser(sender.getName());
		Mall mall = null;
		if (cuboidFactoryManager.isInSetupMode(u))
			return this.error("You are already in setup mode! Please finish any other cuboid creations before this or type /AutoMalls ExitSetupMode");
		if (args.length == 0) {
			for (Mall localMall : Mall.getMalls()) {
				if (localMall.getCuboid().isInCuboid(p.getLocation())) {
					mall = localMall;
					break;
				}
			}
			if (mall == null)
				return this.error(
						"You must either stand inside of a mall or specify using /AutoMalls RedefineMall <Default|Donor>");
		}
		if ((args.length == 1 && !args[0].equalsIgnoreCase("Default") && !args[0].equalsIgnoreCase("Donor"))
				|| args.length > 1)
			return this.usage("AutoMalls RedefineMall <Default|Donor>");
		if (args.length == 1) {
			for (Mall localMall : Mall.getMalls()) {
				if (localMall.getType().getReadableName().equalsIgnoreCase(args[0])) {
					mall = localMall;
					break;
				}
			}
		}
		cuboidFactoryManager.addUser(u, new CuboidRedefinerFactory(u, mall, true));
		return this.success("You are now in setup mode! Place a block in the first corner");
	}

}
