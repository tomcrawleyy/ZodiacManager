package com.zodiacmc.ZodiacManager.Plugins;

import com.zodiacmc.ZodiacManager.AutoRank.Commands.Check;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.ChoosePrefix;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.Demote;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.Promote;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.SetPlaytime;
import com.zodiacmc.ZodiacManager.AutoRank.Commands.SetRank;
import com.zodiacmc.ZodiacManager.AutoRank.Configurations.ChoosePrefixConfig;
import com.zodiacmc.ZodiacManager.AutoRank.Configurations.OldPlaytimeConfig;
import com.zodiacmc.ZodiacManager.AutoRank.Configurations.RankConfig;
import com.zodiacmc.ZodiacManager.AutoRank.Scheduling.PlaytimeUpdater;
import com.zodiacmc.ZodiacManager.Commands.BaseCommand;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class AutoRank implements IPlugin {
	
	private BaseCommand baseCommand;
	private static AutoRank instance;
	private static FileManager fm = FileManager.getInstance();
	
	public static AutoRank getInstance() {
		if (instance == null)
			instance = new AutoRank();
		return instance;
	}
	

	private AutoRank() {
		ConsoleUtil.loadupMessage("&d+=====================================+");
		ConsoleUtil.loadupMessage("&d|     &aZodiac&c\\<({~v3.0~})>/&aManager     &d|");
		ConsoleUtil.loadupMessage("&d|               Auto &fRank             &d|");
		ConsoleUtil.loadupMessage("&d+=====================================+");
		fm.loadFile(OldPlaytimeConfig.getInstance());
		fm.loadFile(ChoosePrefixConfig.getInstance());
		fm.loadFile(RankConfig.getInstance());
		baseCommand = new BaseCommand("AutoRank", "&7(&dAuto&fRank&7)");
		baseCommand.instantiateCommand(new Check());
		baseCommand.instantiateCommand(new Demote());
		baseCommand.instantiateCommand(new Promote());
		baseCommand.instantiateCommand(new SetPlaytime());
		baseCommand.instantiateCommand(new SetRank());
		baseCommand.instantiateCommand(new ChoosePrefix());
		PlaytimeUpdater.getInstance().start(this);
	}

	@Override
	public BaseCommand getBaseCommand() {
		return baseCommand;
	}

}
