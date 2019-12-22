package com.zodiacmc.ZodiacManager.Malls.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.zodiacmc.ZodiacManager.Malls.Commands.IgnoreProtection;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class BlockBreak implements Listener {
	
	@EventHandler
	public void onMallBreak(BlockBreakEvent e) {
		
		Player p = e.getPlayer();
		User u = UserManager.getInstance().getOnlineUser(p);
		
		Mall mall = null;
		Shop shop = null;
		
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(e.getBlock().getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return;
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(e.getBlock().getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null) {
			if (IgnoreProtection.getUsersIgnoringProtection().contains(u))
				return;
			u.sendMessage("&cError: You cannot build here!");
			e.setCancelled(true);
			return;
		}
		if (shop.getOwner() == u)
			return;
		if (shop.getTrustedUsers().containsKey(u) && shop.getTrusteesPermissions(u).contains(MallPermissionType.BUILD))
			return;
		if (IgnoreProtection.getUsersIgnoringProtection().contains(u))
			return;
		u.sendMessage("&cError: You do not have permissions to do this here!");
		e.setCancelled(true);
	}

}
