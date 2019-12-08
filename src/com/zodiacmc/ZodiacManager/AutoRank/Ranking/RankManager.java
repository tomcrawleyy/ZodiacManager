package com.zodiacmc.ZodiacManager.AutoRank.Ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankManager {

	private static RankManager instance;
	private Map<String, Rank> ranks;
	private List<Rank> ranksList;

	private RankManager() {
		ranks = new HashMap<String, Rank>();
		ranksList = new ArrayList<Rank>();
	}

	public static RankManager getInstance() {
		if (instance == null)
			instance = new RankManager();
		return instance;
	}

	public List<Rank> getRanks() {
		return ranksList;
	}

	public void loadRank(Rank rank) {
		ranks.put(rank.getName().toLowerCase(), rank);
		ranksList.add(rank);
	}

	public Rank getRank(String name) {
		if (ranks.containsKey(name.toLowerCase()))
			return ranks.get(name.toLowerCase());
		return null;
	}

}
