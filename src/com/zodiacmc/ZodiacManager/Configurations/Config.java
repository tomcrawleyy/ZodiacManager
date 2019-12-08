package com.zodiacmc.ZodiacManager.Configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config implements IConfiguration {
	
	private FileConfiguration config;
	private File file;
	private FileManager fm = FileManager.getInstance();
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public void loadConfig(Plugin p) {
		this.file = new File(p.getDataFolder(), "config.yml");
		this.config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			config.set("enableDebugMode", false);
			saveConfig();
		}
	}
	
	public void saveConfig() {
		fm.saveConfig(this);
	}
	
	@Override
	public ConfigType getType() {
		return ConfigType.DEFAULT;
	}

}
