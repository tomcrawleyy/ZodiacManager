package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class Untrust extends SubCommand {

	public Untrust() {
		super("Untrust", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		UserManager userManager = UserManager.getInstance();
		User u = userManager.getOnlineUser(sender.getName());
		Player p = (Player) sender;
		if (args.length == 0 | args.length > 2)
			return this.usage("AutoMalls Untrust <PlayerName> <Optional<PermissionType>>");
		User targetUser = UserManager.getInstance().getUserOnlineOrOffline(args[0]);
		Mall mall = null;
		Shop shop = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(p.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return this.error("You must be standing inside of a mall to perform this command!");
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return this.error("You must be standing inside of a shop to perform this command!");
		if (shop.getOwner() != u) {
			if (!shop.getTrustedUsers().containsKey(u) && !IgnoreProtection.getUsersIgnoringProtection().contains(u))
				return this.error("You are not trusted in this shop!");
		}
		if (!shop.getTrustedUsers().containsKey(targetUser))
			return this.error("User is not trusted!");
		if (args.length == 2) {
			MallPermissionType type = null;
			for (MallPermissionType localType : MallPermissionType.values()) {
				if (localType.getReadableName().equalsIgnoreCase(args[1])) {
					type = localType;
					break;
				}
			}
			if (type == null) {
				sender.sendMessage(StringUtil.parseColours(
						command.getPrefix() + " &cError: Invalid Permission Type, Valid Ones Shown Below"));
				for (MallPermissionType localPermissionType : MallPermissionType.values()) {
					sender.sendMessage(StringUtil.parseColours(localPermissionType.getReadableName()));
				}
				return true;
			}
			if (u != shop.getOwner()) {
				switch (type) {
				case MANAGEMENT:
				case REFILLMANAGEMENT:
				case RENEWMANAGEMENT:
				case WARPMANAGEMENT:
				case ALL:
				case BUILD:
					if (!shop.getTrusteesPermissions(u).contains(MallPermissionType.MANAGEMENT))
						return this.error("You do not have the required permission to manage others' build permissions!");
					break;
				case REFILL:
					if (!shop.getTrusteesPermissions(u).contains(MallPermissionType.REFILLMANAGEMENT))
						return this.error("You do not have the required permission to manage others' refill permissions!");
					break;
				case RENEW:
					if (!shop.getTrusteesPermissions(u).contains(MallPermissionType.RENEWMANAGEMENT))
						return this.error("You do not have the required permission to manage others' renew permissions!");
					break;
				case SETWARP:
					if (!shop.getTrusteesPermissions(u).contains(MallPermissionType.WARPMANAGEMENT))
						return this.error("You do not have the required permission to manage others' warp permissions!");
					break;
				default:
					return this.error("Only the shop owner or server staff can revoke this permission!");
				}
			}
			shop.getTrustedUsers().get(targetUser).remove(type);
			return this.success(targetUser.getName() + " has had the permission " + type.getReadableName()
					+ " successfully revoked!");
		}
		return this.success("");
	}

}
