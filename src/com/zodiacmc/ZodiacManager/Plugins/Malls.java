package com.zodiacmc.ZodiacManager.Plugins;

import com.zodiacmc.ZodiacManager.Commands.BaseCommand;

public class Malls implements IPlugin{
	
	private BaseCommand command;
	private static Malls instance;
	
	public static Malls getInstance() {
		if (instance == null)
			instance = new Malls();
		return instance;
	}
	
	private Malls() {
		command = new BaseCommand("AutoMalls", "&7(&dZodiac&fMalls&7)");
	}
	
	public BaseCommand getBaseCommand() {
		return command;
	}

}
