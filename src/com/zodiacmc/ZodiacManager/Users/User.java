package com.zodiacmc.ZodiacManager.Users;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.AutoRank.Configurations.OldPlaytimeConfig;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankManager;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankType;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankUpdater;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTask;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTaskException;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class User {

	private FileConfiguration playerConfig;
	private File pfile;
	private String name;
	private long lastCheck;
	private long timePlayed = 0;
	private Rank rank;
	private boolean fileExists = true;
	private Map<WorldBlockType, List<WorldBlock>> worldBlocks;
	private List<Shop> ownedShops;
	private List<Shop> trustedShops;

	public User(String name) {
		this.name = name;
		this.worldBlocks = new HashMap<WorldBlockType, List<WorldBlock>>();
		this.lastCheck = new Date().getTime();
		this.ownedShops = new ArrayList<Shop>();
		this.trustedShops = new ArrayList<Shop>();
		this.loadConfiguration(false);
	}

	public User(Player player) {
		this.name = player.getName();
		this.worldBlocks = new HashMap<WorldBlockType, List<WorldBlock>>();
		this.lastCheck = new Date().getTime();
		this.ownedShops = new ArrayList<Shop>();
		this.trustedShops =  new ArrayList<Shop>();
		WorldBlock.getScheduledBlocks().removeIf(t -> t.getPlacedBy().getName() == name);
		this.loadConfiguration(true);
		if (WorldBlock.getScheduledRemovals().containsKey(this)) {
			for (ScheduledTask task : WorldBlock.getScheduledRemovals().get(this)) {
				task.cancel();
			}
			WorldBlock.getScheduledRemovals().remove(this);
		}
		List<WorldBlock> offlineInstances = WorldBlock.getOfflineUserInstances(this);
		if (offlineInstances != null) {
			for (WorldBlock block : offlineInstances) {
				block.setPlacedBy(this);
			}
		}
	}
	
	public List<Shop> getTrustedShops(){
		return this.trustedShops;
	}
	
	public List<Shop> getOwnedShops(){
		return this.ownedShops;
	}

	public List<WorldBlock> getWorldBlocks(WorldBlockType type) {
		if (!this.worldBlocks.containsKey(type))
			this.worldBlocks.put(type, new ArrayList<WorldBlock>());
		return this.worldBlocks.get(type);
	}

	public void handleWorldBlocks() {
		List<ScheduledTask> tasks = new ArrayList<ScheduledTask>();
		for (WorldBlockType type : WorldBlockType.values()) {
			WorldBlockConfig config = WorldBlockConfig.getInstance(type);
			if (!config.destroyOnLogout())
				continue;
			long delay = config.getRemovalDelay(rank, TimeUnit.MILLISECONDS);
			if (delay == 0) {
				for (WorldBlock block : this.getWorldBlocks(type)) {
					block.destroy();
				}
			} else {
				try {
					ScheduledTask t = ScheduledTask.Builder.create(name + "WorldBlockRemover: " + type.name())
							.delay((int) delay).execute(task -> {
								for (WorldBlock block : this.getWorldBlocks(type)) {
									block.destroy();
								}
								WorldBlock.getScheduledRemovals().get(this).remove(task);
							}).start();
					WorldBlock.getScheduledBlocks().addAll(this.getWorldBlocks(type));
					tasks.add(t);
				} catch (ScheduledTaskException e) {
					e.printStackTrace();
				}
			}
		}
		WorldBlock.getScheduledRemovals().put(this, tasks);
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public long getTimePlayed(boolean update) {
		if (update)
			updateData();
		return timePlayed;
	}
	
	public void sendMessage(String message) {
		if (this.isOnline()) {
			Player p = Bukkit.getPlayer(this.name);
			p.sendMessage(StringUtil.parseColours(message));
		}
	}

	public void setTimePlayed(long milliseconds) {
		this.timePlayed = milliseconds;
		lastCheck = new Date().getTime();
	}

	public boolean exists() {
		return fileExists;
	}

	public boolean isOnline() {
		return UserManager.getInstance().getOnlineUsers().contains(this);
	}

	public String getFormattedTimePlayed() {
		updateData();
		return TimeUtil.getReadableTime(timePlayed, TimeUnit.MILLISECONDS, false);
	}

	private void loadConfiguration(boolean generate) {
		Plugin p = ZodiacManager.getInstance();
		pfile = new File(p.getDataFolder() + "/Players/", name + ".yml");
		playerConfig = YamlConfiguration.loadConfiguration(pfile);
		if (!pfile.exists()) {
			fileExists = false;
			if (generate) {
				playerConfig.set("rank", "Default");
				rank = RankManager.getInstance().getRank("Default");
				Long oldPlaytime = OldPlaytimeConfig.getInstance().getPlaytime(this);
				if (oldPlaytime != null) {
					timePlayed = TimeUnit.MINUTES.toMillis(oldPlaytime);
				}
				for (WorldBlockType type : WorldBlockType.values()) {
					playerConfig.set(type.name() + "s", new ArrayList<String>());
				}
				saveData();
				fileExists = true;
			}
		} else {
			for (WorldBlockType type : WorldBlockType.values()) {
				for (String instance : playerConfig.getStringList(type.name().toLowerCase() + "s")) {
					this.getWorldBlocks(type).add(new WorldBlock(type, LocationUtil.fromString(instance), this));
				}
			}
			timePlayed = playerConfig.getLong("timePlayed");
			rank = RankManager.getInstance().getRank(playerConfig.getString("rank"));
		}
	}

	private void findRank() {
		if (rank.getRankType() != RankType.DEFAULT)
			return;
		Rank nextRank = rank.getNextRank();
		if (nextRank != null) {
			if (nextRank.getRankType() != RankType.DEFAULT)
				return;
			if (TimeUnit.HOURS.toMillis((long) nextRank.getHoursRequired()) < timePlayed) {
				RankUpdater.getInstance().processRankUp(this, nextRank);
				findRank();
			}
		}
	}

	public void updateData() {
		Date date = new Date();
		long timeDifference = date.getTime() - lastCheck;
		timePlayed += timeDifference;
		findRank();
		playerConfig.set("rank", rank.getName());
		lastCheck = date.getTime();
		playerConfig.set("timePlayed", timePlayed);
		List<String> serializedWorldBlocks;
		for (WorldBlockType type : WorldBlockType.values()) {
			serializedWorldBlocks = new ArrayList<String>();
			for (WorldBlock worldBlock : this.getWorldBlocks(type)) {
				serializedWorldBlocks.add(LocationUtil.toString(worldBlock.getLocation()));
			}
			playerConfig.set(type.name().toLowerCase() + "s", serializedWorldBlocks);
		}
		saveData();
	}

	public void saveData() {
		try {
			playerConfig.save(pfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
