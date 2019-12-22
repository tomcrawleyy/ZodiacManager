package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class ExitSetupMode extends SubCommand {

	public ExitSetupMode() {
		super("ExitSetupMode", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		User u = UserManager.getInstance().getOnlineUser(p);
		CuboidFactoryManager cfm = CuboidFactoryManager.getInstance();
		if (args.length != 0)
			return this.usage("AutoMalls ExitSetupMode");
		if (!cfm.isInSetupMode(u))
			return this.error("You are not in setup mode!");
		cfm.removeUser(u);
		return this.success("You have been successfully removed from setup mode!");
	}

}
