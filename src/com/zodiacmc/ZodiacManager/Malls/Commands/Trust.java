package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class Trust extends SubCommand {

	public Trust() {
		super("Trust", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		UserManager userManager = UserManager.getInstance();
		User user = userManager.getOnlineUser(sender.getName());
		Player p = (Player) sender;
		if (args.length > 2 | args.length < 1)
			return this.usage("AutoMalls Trust <PlayerName> <Optional<PermissionType>>");
		User targetUser = userManager.getUserOnlineOrOffline(args[0]);
		MallPermissionType permissionType = null;
		if (args.length == 2) {
			for (MallPermissionType localPermissionType : MallPermissionType.values()) {
				if (args[1].equalsIgnoreCase(localPermissionType.name())) {
					permissionType = localPermissionType;
					break;
				}
			}
			if (permissionType == null) {
				sender.sendMessage(StringUtil.parseColours(
						command.getPrefix() + " &cError: Invalid Permission Type, Valid Ones Shown Below"));
				for (MallPermissionType localPermissionType : MallPermissionType.values()) {
					sender.sendMessage(StringUtil.parseColours(localPermissionType.getReadableName()));
				}
				return true;
			}
		}
		permissionType = permissionType == null ? MallPermissionType.BUILD : permissionType;
		Mall mall = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(p.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return this.error("You must be standing inside of a mall to perform this command!");
		Shop shop = null;
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return this.error("You must be standing inside of a shop to perform this command!");
		if (shop.getOwner() != user) {
			if (shop.getTrustedUsers().containsKey(user)) {
				if (permissionType == MallPermissionType.MANAGEMENT
						| permissionType == MallPermissionType.REFILLMANAGEMENT
						| permissionType == MallPermissionType.RENEWMANAGEMENT
						| permissionType == MallPermissionType.WARPMANAGEMENT
						| permissionType == MallPermissionType.ALL)
					return this.error("Only the shop owner can allocate this permission!");
				List<MallPermissionType> userPermissions = shop.getTrusteesPermissions(user);
				boolean trust = false;
				switch (permissionType) {
				case REFILL:
					if (userPermissions.contains(MallPermissionType.REFILLMANAGEMENT))
						trust = true;
					break;
				case RENEW:
					if (userPermissions.contains(MallPermissionType.RENEWMANAGEMENT))
						trust = true;
					break;
				case SETWARP:
					if (userPermissions.contains(MallPermissionType.WARPMANAGEMENT))
						trust = true;
					break;
				default:
					if (userPermissions.contains(MallPermissionType.MANAGEMENT))
						trust = true;
					break;
				}
				if (!trust)
					return this.error("You do not have the required permissions for this!");
			} else {
				return this.error("You are not trusted in this shop!");
			}

		}
		List<MallPermissionType> targetsPermissions = shop.getTrusteesPermissions(targetUser);
		if (targetsPermissions.contains(permissionType))
			return this.error(targetUser.getName() + " is already trusted with that permission!");
		shop.getTrusteesPermissions(targetUser).add(permissionType);
		return this.resolve("Player " + targetUser.getName() + " has been successfully trusted to " + permissionType.getDescription());
	}

}
