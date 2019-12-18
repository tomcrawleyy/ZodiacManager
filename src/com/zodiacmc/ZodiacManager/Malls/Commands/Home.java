package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class Home extends SubCommand {

	public Home() {
		super("Home", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User u = UserManager.getInstance().getOnlineUser(sender.getName());
		List<Shop> ownedShops = new ArrayList<Shop>();
		for (Mall mall : Mall.getMalls()) {
			for (Shop shop : mall.getShops()) {
				if (shop.getOwner() == u)
					ownedShops.add(shop);
			}
		}
		if (args.length == 0) {
			if (ownedShops.size() == 0) {
				return this.error("You do not own any shops!");
			}
			if (ownedShops.size() == 1) {
				Player p = (Player) sender;
				p.teleport(ownedShops.get(0).getCuboid().getCenter());
				return this.resolve("You have been successfully teleported to you shop!");
			}
			if (ownedShops.size() == 2) {
				return this.error("You own more than one shop, Usage: /AutoMalls Home <Default|Donor>");
			}
			if (ownedShops.size() > 2) {
				for (User user : UserManager.getInstance().getOnlineStaff()) {
					user.sendMessage(command.getPrefix() + " &cStaffNotice: " + u.getName() + " has " + ownedShops.size() + " mall plots!");
				}
				return this.error("You own too many shops!");
			}
				//handle error
		}
		if (args.length > 1 | (args.length == 1 && !args[0].equalsIgnoreCase("default") && !args[0].equalsIgnoreCase("donor"))) {
			return this.usage("AutoMalls Home <Optional<Donor|Default>>");
		}
		MallType type = null;
		for (MallType localType : MallType.values()) {
			if (args[0].equalsIgnoreCase(localType.name())) {
				type = localType;
				break;
			}
		}
		for (Shop shop : ownedShops) {
			if (shop.getMall().getType() == type) {
				Player p = (Player)sender;
				p.teleport(shop.getCuboid().getCenter());
				return this.resolve("You have been successfully teleported to your shop!");
			}
		}
		return this.error("You do not own a shop in the mall you specified!");
	}

}
