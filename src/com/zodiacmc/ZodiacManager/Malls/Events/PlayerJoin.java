package com.zodiacmc.ZodiacManager.Malls.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import com.zodiacmc.ZodiacManager.Listeners.IPlayerJoinListener;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Plugins.AutoMalls;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class PlayerJoin implements IPlayerJoinListener {

	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		User u = UserManager.getInstance().getOnlineUser(p);
		List<Shop> ownedShops = new ArrayList<Shop>();
		for (Mall mall : Mall.getMalls()) {
			for (Shop shop : mall.getShops()) {
				if (shop.getOwner() == u)
					ownedShops.add(shop);
				break;
			}
		}
		for (Shop shop : ownedShops) {
			u.sendMessage(AutoMalls.getInstance().getBaseCommand().getPrefix() + " &aYou have a shop in the "
					+ shop.getMall().getType().getReadableName() + " mall that will expire in "
					+ TimeUtil.getReadableTime(shop.getTimeLeft(), TimeUnit.MILLISECONDS, false) + "!");
		}
	}

}
