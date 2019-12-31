package com.zodiacmc.ZodiacManager.Malls.Events;

import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankType;
import com.zodiacmc.ZodiacManager.Cuboids.Cuboid;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;
import com.zodiacmc.ZodiacManager.Plugins.AutoMalls;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class PlayerMove implements Listener {

	String prefix = "&f(&dAuto&fRank) ";

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		User u = UserManager.getInstance().getOnlineUser(p);
		Mall mall = null;
		Cuboid c = null;
		for (Mall localMall : Mall.getMalls()) {
			c = localMall.getCuboid();
			if (c.isInCuboid(e.getFrom()) || c.isInCuboid(e.getTo())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return;
		if (c.isInCuboid(e.getFrom()) && !c.isInCuboid(e.getTo())) {
			u.sendMessage(prefix + " &aYou are now leaving the " + mall.getType().getReadableName() + " mall!");
			return;
		}
		if (!c.isInCuboid(e.getFrom()) && c.isInCuboid(e.getTo())) {
			if (mall.getType() == MallType.DONOR) {
				if (u.getRank().getRankType() == RankType.DEFAULT) {
					u.sendMessage("&cError: Only Donators or Staff members can enter this mall!");
					e.setCancelled(true);
					return;
				}
			}
			u.sendMessage(prefix + " &aYou are now entering the " + mall.getType().getReadableName() + " mall!");
			return;
		}
		
		Shop shop = null;
		for (Shop localShop : mall.getShops()) {
			c = localShop.getCuboid();
			if (c.isInCuboid(e.getFrom()) || c.isInCuboid(e.getTo())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return;
		if (c.isInCuboid(e.getFrom()) && !c.isInCuboid(e.getTo())) {
			if (shop.getOwner() == u) {
				u.sendMessage(prefix + "&aYou are now leaving your shop!");
			} else if (shop.getOwner() == null) {
				u.sendMessage(prefix + "&aYou are now leaving a for sale shop!");
			} else {
				u.sendMessage(prefix + "&aYou are now leaving " + shop.getOwner().getName() + "'s shop!");
			}
			return;
		}
		if (!c.isInCuboid(e.getFrom()) && c.isInCuboid(e.getTo())) {
			if (shop.getOwner() == u) {
				u.sendMessage(prefix + "&aWelcome back to your shop!");
				u.sendMessage(prefix + "&aThis shop will expire in " + TimeUtil.getReadableTime(shop.getTimeLeft(), TimeUnit.MILLISECONDS, false));
			} else if (shop.getOwner() == null) {
				u.sendMessage(prefix + "&aThis shop is for sale! Price per day: $" + shop.getPrice() + ", 7 days included in initial lease.");
			} else {
				u.sendMessage(prefix + "You are now entering " + shop.getOwner().getName() + "'s shop!");
			}
			return;
		}
	}

}
