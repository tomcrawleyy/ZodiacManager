package com.zodiacmc.ZodiacManager.Malls.Configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class MallConfig implements IConfiguration {

	private FileConfiguration config;
	private File file;
	private FileManager fm = FileManager.getInstance();

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
		this.file = new File(p.getDataFolder() + "/AutoMalls/", "mall.yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
		if (!this.file.exists()) {
			config.set("Malls.Default.Shops", new ArrayList<>());
			config.set("Malls.Donor.Shops", new ArrayList<>());
		}
	}

	public void saveMalls() {
		for (Mall mall : Mall.getMalls()) {
			config.set("Malls." + mall.getType().getReadableName() + ".Cuboid", mall.getCuboid().serialize());
			for (Shop shop : mall.getShops()) {
				if (shop.getOwner() != null)
					config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + " .Owner",
							shop.getOwner().getName());
				config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".Price", shop.getPrice());
				config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".Cuboid", shop.getCuboid().serialize());
				config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".TimeLeft", shop.getTimeLeft());
				config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".Warp", LocationUtil.toString(shop.getWarp()));
				for (User u : shop.getTrustedUsers().keySet()) {
					List<String> permissionTypes =  new ArrayList<String>();
					for (MallPermissionType type : shop.getTrustedUsers().get(u)) {
						permissionTypes.add(type.getReadableName());
					}
					config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".TrustedPlayers." + u.getName(), permissionTypes);
				}
			}
		}
		saveConfig();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.MALL;
	}

	@Override
	public void saveConfig() {
		fm.saveConfig(this);
	}

}
