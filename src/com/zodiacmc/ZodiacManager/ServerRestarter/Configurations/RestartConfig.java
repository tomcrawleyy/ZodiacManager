package com.zodiacmc.ZodiacManager.ServerRestarter.Configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;

public class RestartConfig implements IConfiguration {
	
	private FileManager fm = FileManager.getInstance();
	private FileConfiguration config;
	private File file;

	@Override
	public FileConfiguration getConfig() {
		return config;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void loadConfig(Plugin p) {
		this.file = new File(p.getDataFolder()+ "/ServerRestarter/", "RestartConfig.yml");
		this.config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			config.set("RestartIntervalInHours", 2);
			saveConfig();
		}
		
	}

	@Override
	public ConfigType getType() {
		return ConfigType.RESTART;
	}

	@Override
	public void saveConfig() {
		fm.saveConfig(this);
	}

}
