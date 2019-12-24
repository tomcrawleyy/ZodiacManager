package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class CheckPermissions extends SubCommand {

	public CheckPermissions() {
		super("CheckPermissions", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		User u = UserManager.getInstance().getOnlineUser(p);
		Mall mall = null;
		Shop shop = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(p.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return this.error("You must be standing inside a shop to perform this command!");
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return this.error("You must be standing inside a shop to perform this command!");
		if (args.length > 0) {
			if (args.length > 1)
				return this.usage("AutoMalls CheckPermissions <Optional<TargetUser>>");
			User targetUser = UserManager.getInstance().getUserOnlineOrOffline(args[0]);
			if (!targetUser.exists())
				return this.error("Target user does not exist!");
			if (!shop.getTrustedUsers().containsKey(targetUser))
				return this.error("User is not trusted in this shop!");
			u.sendMessage("&aTarget user has the following permissions:");
			for (MallPermissionType localType : shop.getTrusteesPermissions(targetUser)) {
				u.sendMessage("-" + localType.getReadableName());
			}
			return true;
		}
		if (!shop.getTrustedUsers().containsKey(u))
			return this.error("You are not trusted in this shop!");
		u.sendMessage("&You are trusted with the following permissions:");
		for (MallPermissionType localType : shop.getTrusteesPermissions(u)) {
			u.sendMessage("-" + localType.getReadableName());
		}
		return true;
	}

}
