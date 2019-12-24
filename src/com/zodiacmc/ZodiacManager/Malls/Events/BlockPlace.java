package com.zodiacmc.ZodiacManager.Malls.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import com.zodiacmc.ZodiacManager.Malls.Commands.IgnoreProtection;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class BlockPlace implements Listener {

	@EventHandler
	public void onMallPlace(BlockPlaceEvent e) {

		Player p = e.getPlayer();
		User u = UserManager.getInstance().getOnlineUser(p);

		Mall mall = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(e.getBlock().getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return;
		Shop shop = null;
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(e.getBlock().getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (IgnoreProtection.getUsersIgnoringProtection().contains(u))
			return;
		if (shop == null) {
			u.sendMessage("&cError: You do not have the required permissions to build here.");
			e.setCancelled(true);
			return;
		}
		if (shop.getOwner() == u)
			return;
		if (shop.getTrustedUsers().containsKey(u) && shop.getTrusteesPermissions(u).contains(MallPermissionType.BUILD))
			return;
		u.sendMessage("&cError: You do not have permissions to build here!");
		e.setCancelled(true);
	}

}
