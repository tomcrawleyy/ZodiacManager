package com.zodiacmc.ZodiacManager.AutoRank.Ranking;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Plugins.ServerRestarter;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class RankUpdater {
	
	private static RankUpdater instance;
	private Map<User, Rank> queuedRankups;
	
	private RankUpdater() {
		queuedRankups = new HashMap<User, Rank>();
	}
	
	public static RankUpdater getInstance() {
		if (instance == null)
			instance = new RankUpdater();
		return instance;
	}
	
	public void processRankUp(User user, Rank newRank) {
		String prefix = ServerRestarter.getInstance().getBaseCommand().getPrefix();
		if (!user.isOnline()) {
			queuedRankups.put(user, newRank);
			ConsoleUtil.sendMessage(prefix + " &aRankup for user " + user.getName() + " queued for later processing.");
			return;
		}
		for (String reward : newRank.getRankupRewards()) {
			ConsoleUtil.sendMessage(reward.replace("%player%", user.getName()));
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), reward.replace("%player%", user.getName()));
		}
		Player player = Bukkit.getServer().getPlayer(user.getName());
		player.sendMessage(StringUtil.parseColours(prefix + newRank.getRankupMessage()));
		user.setRank(newRank);
		user.updateData();
	}

}
