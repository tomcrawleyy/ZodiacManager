package com.zodiacmc.ZodiacManager.Plugins;

import com.zodiacmc.ZodiacManager.ChunkManager.Commands.CheckLimit;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.CheckOnline;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.CheckRemovalDelay;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.ClearAll;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.SetLimit;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.SetRemovalDelay;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.SetRemoveAfterLogout;
import com.zodiacmc.ZodiacManager.ChunkManager.Commands.UnloadBlocks;
import com.zodiacmc.ZodiacManager.Commands.BaseCommand;

public class ChunkManager implements IPlugin {
	
	private BaseCommand baseCommand;
	private static ChunkManager instance;
	
	public static ChunkManager getInstance() {
		if (instance == null)
			instance = new ChunkManager();
		return instance;
	}
	private ChunkManager() {
		baseCommand = new BaseCommand("ChunkManager", "&7(&dChunk&fManager&7)");
		baseCommand.instantiateCommand(new CheckLimit());
		baseCommand.instantiateCommand(new CheckOnline());
		baseCommand.instantiateCommand(new CheckRemovalDelay());
		baseCommand.instantiateCommand(new ClearAll());
		baseCommand.instantiateCommand(new SetLimit());
		baseCommand.instantiateCommand(new SetRemovalDelay());
		baseCommand.instantiateCommand(new SetRemoveAfterLogout());
		baseCommand.instantiateCommand(new UnloadBlocks());
	}
	
	@Override
	public BaseCommand getBaseCommand() {
		return baseCommand;
	}
}
