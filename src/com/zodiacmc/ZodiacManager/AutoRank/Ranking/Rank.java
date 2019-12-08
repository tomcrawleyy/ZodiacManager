package com.zodiacmc.ZodiacManager.AutoRank.Ranking;

import java.util.List;

import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class Rank {
	private String name, rankupMessage, prefix, nextRankString;
	private int hours, dailyClaimblocks, dailyCash;
	private Rank previousRank, nextRank;
	private RankType type;
	private List<String> rankupRewards;
	private RankManager rankManager = RankManager.getInstance();
	
	public Rank(String name, String prefix, String rankType, int hours, String rankupMessage, List<String> rankupRewards, int dailyClaimblocks, int dailyCash, String nextRank) {
		this.name = name;
		this.hours = hours;
		this.rankupMessage = rankupMessage;
		this.rankupRewards = rankupRewards;
		this.dailyClaimblocks = dailyClaimblocks;
		this.dailyCash = dailyCash;
		this.prefix = prefix;
		if (nextRank != null) {
			this.nextRankString = nextRank;
		}
		for (RankType type : RankType.values()) {
			if (type.name().equalsIgnoreCase(rankType)) {
				this.type = type;
			}
		}
		if (type == null)
			ConsoleUtil.sendMessage("&cError: RankType is Null for Rank " + name);
	}
	
	public Rank getPreviousRank() {
		return previousRank;
	}
	
	public Rank getNextRank() {
		if (nextRankString == "" | nextRankString == null) {
			return null;
		}
		if (nextRank == null) {
			nextRank = rankManager.getRank(nextRankString);
			nextRank.setPreviousRank(this);
		}
		return nextRank;
	}
	
	public boolean hasNextRank() {
		return (nextRankString != null && nextRankString != "");
	}
	
	public boolean hasPreviousRank() {
		return previousRank != null;
	}
	public void setPreviousRank(Rank rank) {
		if (previousRank != null) {
			ConsoleUtil.sendMessage("&cError: Previous rank has already been set!");
			return;
		}
		previousRank = rank;
			previousRank = rank;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getName() {
		return name;
	}
	
	public int getHoursRequired() {
		return hours;
	}
	
	public RankType getRankType() {
		return type;
	}
	
	public String getRankupMessage() {
		return rankupMessage;
	}
	
	public List<String> getRankupRewards() {
		return rankupRewards;
	}
	
	public int getDailyClaimblocks() {
		return dailyClaimblocks;
	}
	
	public int getDailyCash() {
		return dailyCash;
	}

}
