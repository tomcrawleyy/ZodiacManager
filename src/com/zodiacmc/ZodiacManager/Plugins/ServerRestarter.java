package com.zodiacmc.ZodiacManager.Plugins;

import com.zodiacmc.ZodiacManager.Commands.BaseCommand;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.ServerRestarter.Commands.Info;
import com.zodiacmc.ZodiacManager.ServerRestarter.Commands.Restart;
import com.zodiacmc.ZodiacManager.ServerRestarter.Configurations.RestartConfig;
import com.zodiacmc.ZodiacManager.ServerRestarter.Scheduling.RestartScheduler;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class ServerRestarter implements IPlugin {
	
	private BaseCommand command;
	private static ServerRestarter instance;
	private static FileManager fm = FileManager.getInstance();
	
	public static ServerRestarter getInstance() {
		if (instance == null)
			instance = new ServerRestarter();
		return instance;
	}
	
	private ServerRestarter() {
		ConsoleUtil.loadupMessage("&d+=====================================+");
		ConsoleUtil.loadupMessage("&d|     &aZodiac&c\\<({~v3.0~})>/&aManager     &d|");
		ConsoleUtil.loadupMessage("&d|            Server &fRestarter         &d|");
		ConsoleUtil.loadupMessage("&d+=====================================+");
		fm.loadFile(RestartConfig.getInstance());
		command = new BaseCommand("ServerRestarter", "&7(&dServer&fRestarter&7)");
		command.instantiateCommand(new Info());
		command.instantiateCommand(new Restart());
		RestartScheduler.getInstance().start(this);
	}

	@Override
	public BaseCommand getBaseCommand() {
		return command;
	}

}
