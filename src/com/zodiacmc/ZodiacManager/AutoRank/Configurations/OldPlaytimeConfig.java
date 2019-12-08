package com.zodiacmc.ZodiacManager.AutoRank.Configurations;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class OldPlaytimeConfig implements IConfiguration {
	
	private FileConfiguration config;
	private File file;
	private FileManager fm = FileManager.getInstance();
	private static OldPlaytimeConfig instance;
	
	private OldPlaytimeConfig() { }
	
	public static OldPlaytimeConfig getInstance() {
		if (instance == null)
			instance = new OldPlaytimeConfig();
		return instance;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public File getFile() {
		return file;
	}
	
	public void loadConfig(Plugin p) {
		this.file = new File(p.getDataFolder()+"/AutoRank/", "oldplaytimes.yml");
		this.config = YamlConfiguration.loadConfiguration(file);
	}
	
	public Long getPlaytime(User user) {
		int var = config.getInt(user.getName().toLowerCase());
		ConsoleUtil.sendMessage(user.getName() + " " + var);
		return (long)var;
	}
	
	public ConfigType getType() {
		return ConfigType.OLDPLAYTIME;
	}
	
	public void saveConfig() {
		fm.saveConfig(this);
	}

}
