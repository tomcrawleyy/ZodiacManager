package com.zodiacmc.ZodiacManager.Utilities;

import org.bukkit.configuration.file.FileConfiguration;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;

public class DebugUtil {
	private static boolean debug = false;
	private static boolean checked = false;
	public static boolean isDebugMode() {
		if (checked)
			return debug;
		checked = true;
		IConfiguration configuration = FileManager.getInstance().getConfig(ConfigType.DEFAULT);
		FileConfiguration config = configuration.getConfig();
		return config.getBoolean("enableDebugMode");
	}
	
	public static void setDebugMode(boolean value) {
		debug = value;
		checked = true;
		FileManager fm = FileManager.getInstance();
		IConfiguration configuration = fm.getConfig(ConfigType.DEFAULT);
		configuration.getConfig().set("enableDebugMode", value);
		fm.saveConfig(configuration);
	}
	
	public static void debugMessage(String s) {
		if (isDebugMode()) {
			ConsoleUtil.sendMessage("Debug Message:" + Thread.currentThread().getStackTrace()[1].toString());
		}
	}

}
