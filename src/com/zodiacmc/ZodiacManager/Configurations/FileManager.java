package com.zodiacmc.ZodiacManager.Configurations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class FileManager {

	private static FileManager instance;
	private Map<ConfigType, IConfiguration> configs;
	
	private FileManager() {
		instance = this;
		configs = new HashMap<ConfigType, IConfiguration>();
	}
	
	public static FileManager getInstance() {
		if (instance == null)
			return new FileManager();
		return instance;
	}
	
	public void loadFile(IConfiguration configuration) {
		Plugin p = ZodiacManager.getInstance();
		ConfigType type = configuration.getType();
		if (configs.containsKey(type)) {
			ConsoleUtil.sendMessage("&cError: FileType " + type.name() + "has already been loaded.");
			return;
		}
		configuration.loadConfig(p);
		configs.put(type,configuration);
	}
	
	public Map<ConfigType, IConfiguration> getConfigs(){
		return configs;
	}
	
	public void saveConfig(IConfiguration configuration) {
		try {
			configuration.getConfig().save(configuration.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public IConfiguration getConfig(ConfigType type) {
		return configs.get(type);
	}
}
