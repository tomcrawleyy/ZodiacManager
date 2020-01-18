package com.zodiacmc.ZodiacManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.zodiacmc.ZodiacManager.AutoRank.Scheduling.PlaytimeUpdater;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Configurations.Config;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Malls.Scheduling.ShopUpdater;
import com.zodiacmc.ZodiacManager.Plugins.AutoMalls;
import com.zodiacmc.ZodiacManager.Plugins.AutoRank;
import com.zodiacmc.ZodiacManager.Plugins.ChunkManager;
import com.zodiacmc.ZodiacManager.Plugins.MinimumPrices;
import com.zodiacmc.ZodiacManager.Plugins.ServerRestarter;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class ZodiacManager extends JavaPlugin {
	
	private static ZodiacManager instance;
	
	public ZodiacManager() {
		if (instance == null)
			instance = this;
	}
	
	public void onEnable() {
		FileManager fm = FileManager.getInstance();
		fm.loadFile(new Config());
		ConsoleUtil.loadupMessage("&d+=====================================+");
		ConsoleUtil.loadupMessage("&d|     &aZodiac&c\\<({~v3.0~})>/&aManager     &d|");
		ConsoleUtil.loadupMessage("&d|              By TomCrawley          |");
		ConsoleUtil.loadupMessage("&d|              &a& Reichman2            &d|");
		ConsoleUtil.loadupMessage("&d+=====================================+");
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Events.EntityExplode(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Events.WeatherChange(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Events.PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Events.PlayerQuit(), this);
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Patches.NetherRTP(), this);
		
		getCommand("serverrestarter").setExecutor(ServerRestarter.getInstance().getBaseCommand());
		
		getCommand("autorank").setExecutor(AutoRank.getInstance().getBaseCommand());

		getCommand("minimumprices").setExecutor(MinimumPrices.getInstance().getBaseCommand());
		
		getCommand("automalls").setExecutor(AutoMalls.getInstance().getBaseCommand());
		
		getCommand("chunkmanager").setExecutor(ChunkManager.getInstance().getBaseCommand());
	}
	
	public void onDisable() {
		for (WorldBlockType type : WorldBlockType.values()) {
			WorldBlockConfig config = WorldBlockConfig.getInstance(type);
			if (config.destroyOnLogout()) {
				for (WorldBlock block : WorldBlock.getLoadedInstances(type)) {
					block.destroy();
				}
			}
		}
		PlaytimeUpdater.getInstance().stop();
		ShopUpdater.getInstance().stop();
	}
	
	public static ZodiacManager getInstance() {
		return instance;
	}

}
