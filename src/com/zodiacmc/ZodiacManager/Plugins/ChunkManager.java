package com.zodiacmc.ZodiacManager.Plugins;

import org.bukkit.Bukkit;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.CheckLimit;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.CheckOnline;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.CheckRemovalDelay;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.ClearAll;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.ListConfiguredBlocks;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.SetLimit;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.SetRemovalDelay;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.SetRemoveAfterLogout;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.UnloadBlocks;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.ChunkManager.Scheduling.BlockUpdater;
import com.zodiacmc.ZodiacManager.Commands.BaseCommand;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class ChunkManager implements IPlugin {
	
	private BaseCommand baseCommand;
	private static ChunkManager instance;
	private static FileManager fm = FileManager.getInstance();
	
	public static ChunkManager getInstance() {
		if (instance == null)
			instance = new ChunkManager();
		return instance;
	}
	private ChunkManager() {
		ConsoleUtil.loadupMessage("&d+=====================================+");
		ConsoleUtil.loadupMessage("&d|     &aZodiac&c\\<({~v3.0~})>/&aManager     &d|");
		ConsoleUtil.loadupMessage("&d|             Chunk &fManager           &d|");
		ConsoleUtil.loadupMessage("&d+=====================================+");
		for(WorldBlockType type : WorldBlockType.values()) {
			fm.loadFile(WorldBlockConfig.getInstance(type));
		}
		baseCommand = new BaseCommand("ChunkManager", "&7(&dChunk&fManager&7)");
		baseCommand.instantiateCommand(new CheckLimit());
		baseCommand.instantiateCommand(new CheckOnline());
		baseCommand.instantiateCommand(new CheckRemovalDelay());
		baseCommand.instantiateCommand(new ClearAll());
		baseCommand.instantiateCommand(new SetLimit());
		baseCommand.instantiateCommand(new SetRemovalDelay());
		baseCommand.instantiateCommand(new SetRemoveAfterLogout());
		baseCommand.instantiateCommand(new UnloadBlocks());
		baseCommand.instantiateCommand(new ListConfiguredBlocks());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.ChunkManager.Events.BlockBreak(), ZodiacManager.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.ChunkManager.Events.BlockPlace(), ZodiacManager.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.ChunkManager.Events.FillerPatch(), ZodiacManager.getInstance());
		BlockUpdater.getInstance().start(this);
	}
	
	@Override
	public BaseCommand getBaseCommand() {
		return baseCommand;
	}
}
