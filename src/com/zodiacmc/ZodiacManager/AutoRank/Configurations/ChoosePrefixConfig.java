package com.zodiacmc.ZodiacManager.AutoRank.Configurations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;

public class ChoosePrefixConfig implements IConfiguration {

	private File file;
	private FileConfiguration config;
	private static ChoosePrefixConfig instance;
	private List<String> availablePrefixes;

	private ChoosePrefixConfig() {
	}

	public static ChoosePrefixConfig getInstance() {
		if (instance == null)
			instance = new ChoosePrefixConfig();
		return instance;
	}

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
		file = new File(p.getDataFolder() + "/AutoRank/", "choose-prefix.yml");
		config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			List<String> zodiacSigns = new ArrayList<String>();
			zodiacSigns.add("&7&m =&r&f(&bAries&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bTaurus&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bGemini&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bCancer&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bLeo&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bVirgo&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bLibra&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bScorpio&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bSaggitarius&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bCapricorn&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bAquarius&f)&7&m= &r &7");
			zodiacSigns.add("&7&m =&r&f(&bPisces&f)&7&m= &r &7");
			config.set("available-prefixes", zodiacSigns);
			availablePrefixes = zodiacSigns;
			saveConfig();
		} else {
			availablePrefixes = config.getStringList("available-prefixes");
		}
	}
	
	public List<String> getAvailablePrefixes(){
		return availablePrefixes;
	}

	@Override
	public ConfigType getType() {
		return ConfigType.CHOOSERANK;
	}

	@Override
	public void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
