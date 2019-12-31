package com.zodiacmc.ZodiacManager.AutoRank.Configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankManager;
import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class RankConfig implements IConfiguration {
	
	private FileConfiguration config;
	private File file;
	private FileManager fm = FileManager.getInstance();
	private static RankConfig instance;
	private RankConfig() {}
	public static RankConfig getInstance() {
		if (instance == null)
			instance = new RankConfig();
		return instance;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public File getFile() {
		return file;
	}
	
	public void loadConfig(Plugin p) {
		file = new File(p.getDataFolder() + "/AutoRank/", "ranks.yml");
		config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			config.set("Default.prefix", "SamplePrefix");
			config.set("Default.hoursRequired", 0);
			config.set("Default.rankupMessage", "Welcome to Zodiac!");
			config.set("Default.rankupRewards", new String[]{"eco give %player% 500", "acb %player% 500"});
			config.set("Default.dailyClaimblocks", 200);
			config.set("Default.dailyCash", 500);
			config.set("Default.rankType", "Default");
			config.set("Default.nextRank", "Member");
			config.set("Member.prefix", "SamplePrefix");
			config.set("Member.hoursRequired", 1);
			config.set("Member.rankupMessage", "You are now rank Member!");
			config.set("Member.rankupRewards", new String[]{"eco give %player% 500", "acb %player% 500"});
			config.set("Member.dailyClaimblocks", 200);
			config.set("Member.dailyCash", 500);
			config.set("Member.rankType", "Default");
			saveConfig();
		}
		RankManager rankManager = RankManager.getInstance();
		List<Rank> ranks = new ArrayList<Rank>();
		for (String rank : config.getKeys(false)) {
			String prefix = config.getString(rank + ".prefix");
			int hours = config.getInt(rank + ".hoursRequired");
			String rankupMessage = config.getString(rank + ".rankupMessage");
			List<String> rankUpRewards = config.getStringList(rank + ".rankupRewards");
			int dailyClaimblocks = config.getInt(rank + ".dailyClaimblocks");
			int dailyCash = config.getInt(rank + ".dailyCash");
			String rankType = config.getString(rank + ".rankType");
			String nextRank = config.getString(rank + ".nextRank");
			Rank r = new Rank(rank, prefix, rankType, hours, rankupMessage, rankUpRewards, dailyClaimblocks, dailyCash, nextRank);
			ConsoleUtil.sendMessage("&f(&dAuto&fRank) " + rank + ", prefix " + prefix + ", rankType " + rankType + ", hoursRequired " + hours + ", nextRank " + nextRank);
			rankManager.loadRank(r);
			ranks.add(r);
		}
		for (Rank rank : ranks) {
			rank.getNextRank();
		}
	}
	
	public ConfigType getType() {
		return ConfigType.RANK;
	}
	
	public void saveConfig() {
		fm.saveConfig(this);
	}

}
