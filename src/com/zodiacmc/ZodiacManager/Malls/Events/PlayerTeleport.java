package com.zodiacmc.ZodiacManager.Malls.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankType;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class PlayerTeleport implements Listener {

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		User u = UserManager.getInstance().getOnlineUser(p);
		for (Mall mall : Mall.getMalls()) {
			if (mall.getType() != MallType.DONOR)
				return;
			if (!mall.getCuboid().isInCuboid(e.getTo()))
				return;
			if (u.getRank().getRankType() != RankType.DEFAULT)
				return;
			u.sendMessage("&cError: Only Donators or Staff Members may enter this mall!");
			e.setCancelled(true);
			return;
		}
	}

}
