package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;
import com.zodiacmc.ZodiacManager.Malls.Models.Shop;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class CheckTime extends SubCommand {

	public CheckTime() {
		super("CheckTime", true, true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User u = UserManager.getInstance().getOnlineUser(sender.getName());
		List<Shop> ownedShops = new ArrayList<Shop>();
		for (Mall mall : Mall.getMalls()) {
			for (Shop shop : mall.getShops()) {
				if (shop.getOwner() == u) {
					ownedShops.add(shop);
				}
			}
		}
		if (ownedShops.size() == 0)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: You do not own any shops!");
		for (Shop shop : ownedShops) {
			u.sendMessage(command.getPrefix() + " &aYou have a plot in the " + shop.getMall().getType().getReadableName()
					+ " mall, at [" + LocationUtil.toString(shop.getCuboid().getCenter()) + "] that will expire in "
					+ TimeUtil.getReadableTime(shop.getTimeLeft(), TimeUnit.MILLISECONDS, false));
		}
		return true;
	}

	public String permissionRequired() {
		return "AutoMalls.CheckTime";
	}

}
