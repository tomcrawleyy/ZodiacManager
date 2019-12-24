package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class SetTimeLeft extends SubCommand {

	public SetTimeLeft() {
		super("SetTimeLeft", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		if (args.length != 2)
			return this.usage("Mall SetTimeLeft <Time> <TimeUnit>");
		Mall mall = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(p.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return this.error("You must be standing inside of a shop to perform this command!");
		Shop shop = null;
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return this.error("You must be standing inside of a shop to perform this command!");
		long time;
		try {
			time = Long.parseLong(args[0]);
		} catch (NumberFormatException e) {
			return this.usage("Mall SetTimeLeft <Time> <TimeUnit>");
		}
		TimeUnit unit = null;
		for (TimeUnit localUnit : TimeUnit.values()) {
			if (args[1].equalsIgnoreCase(localUnit.name())) {
				unit = localUnit;
				break;
			}
		}
		if (unit == null)
			return this.error("Invalid TimeUnit (Days, Hours, Minutes, Seconds, Milliseconds)");
		shop.setTimeLeft(time, unit);
		return this.success("TimeLeft has been successfully updated to: "
				+ TimeUtil.getReadableTime(shop.getTimeLeft(), TimeUnit.MILLISECONDS, false));
	}

}
