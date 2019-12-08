package com.zodiacmc.ZodiacManager.Plugins;

import com.zodiacmc.ZodiacManager.Commands.BaseCommand;
import com.zodiacmc.ZodiacManager.ServerRestarter.Commands.Info;
import com.zodiacmc.ZodiacManager.ServerRestarter.Commands.Restart;
import com.zodiacmc.ZodiacManager.ServerRestarter.Scheduling.RestartScheduler;

public class ServerRestarter implements IPlugin {
	
	private BaseCommand command;
	private static ServerRestarter instance;
	
	public static ServerRestarter getInstance() {
		if (instance == null)
			instance = new ServerRestarter();
		return instance;
	}
	
	private ServerRestarter() {
		command = new BaseCommand("ServerRestarter", "&7(&dZodiac&fRestarter&7)");
		command.instantiateCommand(new Info());
		command.instantiateCommand(new Restart());
		RestartScheduler.getInstance().start();
	}

	@Override
	public BaseCommand getBaseCommand() {
		return command;
	}

}
