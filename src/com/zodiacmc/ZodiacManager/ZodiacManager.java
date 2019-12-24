package com.zodiacmc.ZodiacManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.zodiacmc.ZodiacManager.AutoRank.Configurations.ChoosePrefixConfig;
import com.zodiacmc.ZodiacManager.AutoRank.Configurations.OldPlaytimeConfig;
import com.zodiacmc.ZodiacManager.AutoRank.Configurations.RankConfig;
import com.zodiacmc.ZodiacManager.AutoRank.Scheduling.PlaytimeUpdater;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Configurations.Config;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Listeners.PlayerJoinListener;
import com.zodiacmc.ZodiacManager.Malls.Configurations.MallConfig;
import com.zodiacmc.ZodiacManager.Malls.Scheduling.ShopUpdater;
import com.zodiacmc.ZodiacManager.MinimumPrices.Configurations.MinimumPriceConfig;
import com.zodiacmc.ZodiacManager.Plugins.AutoMalls;
import com.zodiacmc.ZodiacManager.Plugins.AutoRank;
import com.zodiacmc.ZodiacManager.Plugins.ChunkManager;
import com.zodiacmc.ZodiacManager.Plugins.MinimumPrices;
import com.zodiacmc.ZodiacManager.Plugins.ServerRestarter;
import com.zodiacmc.ZodiacManager.ServerRestarter.Configurations.RestartConfig;

public class ZodiacManager extends JavaPlugin {
	
	private static ZodiacManager instance;
	
	public ZodiacManager() {
		if (instance == null)
			instance = this;
	}
	
	public void onEnable() {
		FileManager fm = FileManager.getInstance();
		fm.loadFile(new Config());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Events.EntityExplode(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Events.WeatherChange(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Events.PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Events.PlayerQuit(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Patches.NetherRTP(), this);
		
		fm.loadFile(new RestartConfig());
		getCommand("serverrestarter").setExecutor(ServerRestarter.getInstance().getBaseCommand());
		
		fm.loadFile(OldPlaytimeConfig.getInstance());
		fm.loadFile(ChoosePrefixConfig.getInstance());
		fm.loadFile(new RankConfig());
		getCommand("autorank").setExecutor(AutoRank.getInstance().getBaseCommand());
		

		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.ChunkManager.Events.BlockBreak(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.ChunkManager.Events.BlockPlace(), this);
		for(WorldBlockType type : WorldBlockType.values()) {
			fm.loadFile(WorldBlockConfig.getInstance(type));
		}
		getCommand("chunkmanager").setExecutor(ChunkManager.getInstance().getBaseCommand());
		
		fm.loadFile(MinimumPriceConfig.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.MinimumPrices.Events.BlockBreak(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.MinimumPrices.Events.BlockPlace(), this);
		getCommand("minimumprices").setExecutor(MinimumPrices.getInstance().getBaseCommand());
		
		fm.loadFile(MallConfig.getInstance());
		Bukkit.getPluginManager().registerEvents(com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager.getInstance(), this);

		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.BlockBreak(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.BlockPlace(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.CreatureSpawn(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.PlayerMove(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.PlayerTeleport(), this);
		PlayerJoinListener.getInstance().addListener(new com.zodiacmc.ZodiacManager.Malls.Events.PlayerJoin());
		getCommand("automalls").setExecutor(AutoMalls.getInstance().getBaseCommand());
	}
	
	public void onDisable() {
		// TODO loop through all anchors which are scheduled to destroy and destroy them
		// all
		for (WorldBlock block : WorldBlock.getLoadedInstances(WorldBlockType.WORLDANCHOR)) {
			block.destroy();
		}
		PlaytimeUpdater.getInstance().stop();
		ShopUpdater.getInstance().stop();
	}
	
	public static ZodiacManager getInstance() {
		return instance;
	}

}
