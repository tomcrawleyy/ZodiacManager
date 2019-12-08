package com.zodiacmc.ZodiacManager.Plugins;

import com.zodiacmc.ZodiacManager.AutoRank.Commands.Check;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.ChoosePrefix;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.Demote;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.Promote;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.SetPlaytime;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.SetRank;
import com.zodiacmc.ZodiacManager.AutoRank.Scheduling.PlaytimeUpdater;
import com.zodiacmc.ZodiacManager.Commands.BaseCommand;

public class AutoRank implements IPlugin {
	
	private BaseCommand baseCommand;
	private static AutoRank instance;
	
	public static AutoRank getInstance() {
		if (instance == null)
			instance = new AutoRank();
		return instance;
	}
	

	private AutoRank() {
		baseCommand = new BaseCommand("AutoRank", "&7(&dAuto&fRank&7)");
		baseCommand.instantiateCommand(new Check());
		baseCommand.instantiateCommand(new Demote());
		baseCommand.instantiateCommand(new Promote());
		baseCommand.instantiateCommand(new SetPlaytime());
		baseCommand.instantiateCommand(new SetRank());
		baseCommand.instantiateCommand(new ChoosePrefix());
		PlaytimeUpdater.getInstance().start();
	}

	@Override
	public BaseCommand getBaseCommand() {
		return baseCommand;
	}

}
