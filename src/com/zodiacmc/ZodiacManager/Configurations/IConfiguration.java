package com.zodiacmc.ZodiacManager.Configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public interface IConfiguration {
	
	public FileConfiguration getConfig();
	public File getFile();
	public void loadConfig(Plugin p);
	public ConfigType getType();
	public void saveConfig();

}
