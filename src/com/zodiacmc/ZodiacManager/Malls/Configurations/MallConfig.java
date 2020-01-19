package com.zodiacmc.ZodiacManager.Malls.Configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;
import com.zodiacmc.ZodiacManager.Cuboids.Cuboid;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;
import com.zodiacmc.ZodiacManager.Serializers.CuboidSerializer;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class MallConfig implements IConfiguration {

	private static MallConfig instance;
	private FileConfiguration config;
	private File file;
	private FileManager fm = FileManager.getInstance();

	private MallConfig() {
	}

	public static MallConfig getInstance() {
		if (instance == null)
			instance = new MallConfig();
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
		this.file = new File(p.getDataFolder() + "/AutoMalls/", "mall.yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
		if (this.file.exists()) {
			for (MallType type : MallType.values()) {
				if (!this.config.isConfigurationSection("Malls." + type.getReadableName()))
					continue;
				String mallCuboidString = this.config.getString("Malls." + type.getReadableName() + ".Cuboid");
				Cuboid mallCuboid = CuboidSerializer.deserialize(mallCuboidString, CuboidType.MALL);
				Mall mall = new Mall(type, mallCuboid);
				if (this.config.isString("Malls." + type.getReadableName() + ".Warp")) {
					mall.setWarp(LocationUtil.fromString(this.config.getString("Malls." + type.getReadableName() + ".Warp")));
				}
				for (int i = 0; i < 1000; i++) {
					if (!this.config.isConfigurationSection("Malls." + type.getReadableName() + ".Shops." + i)) {
						ConsoleUtil.sendMessage("DebugMessage 1 " + i + " " + type.getReadableName());
						break;
					}
					String shopCuboidString = this.config
							.getString("Malls." + type.getReadableName() + ".Shops." + i + ".Cuboid");
					int price = this.config.getInt("Malls." + type.getReadableName() + ".Shops." + i + ".Price");
					Cuboid shopCuboid = CuboidSerializer.deserialize(shopCuboidString, CuboidType.SHOP);
					Shop shop = new Shop(mall, shopCuboid, price);
					
					if (this.config.isString("Malls." + type.getReadableName() + ".Shops." + i + ".Owner")) {
						String owner = this.config
								.getString("Malls." + type.getReadableName() + ".Shops." + i + ".Owner");
						User u = UserManager.getInstance().getUser(owner);
						shop.setOwner(u);
						long timeLeft = this.config
								.getLong("Malls." + type.getReadableName() + ".Shops." + i + ".TimeLeft");
						shop.setTimeLeft(timeLeft, TimeUnit.MILLISECONDS);
					}
					
					if (this.config.isString("Malls." + type.getReadableName() + ".Shops." + i + ".Warp")) {
						String warp = this.config
								.getString("Malls." + type.getReadableName() + ".Shops." + i + ".Warp");
						Location warpLoc = LocationUtil.fromString(warp);
						shop.setWarp(warpLoc);
					}
					
					if (!this.config.isConfigurationSection(
							"Malls." + type.getReadableName() + ".Shops." + i + ".TrustedUsers"))
						continue;
					Map<User, List<MallPermissionType>> trustedUsers = new HashMap<User, List<MallPermissionType>>();
					for (String trustedUser : this.config
							.getConfigurationSection(
									"Malls." + type.getReadableName() + ".Shops." + i + ".TrustedUsers")
							.getKeys(true)) {
						List<MallPermissionType> userPermissions = new ArrayList<MallPermissionType>();
						User tu = UserManager.getInstance().getUser(trustedUser);
						for (String permissionType : this.config.getConfigurationSection(
								"Malls." + type.getReadableName() + ".Shops." + i + ".TrustedUsers." + trustedUser)
								.getKeys(true)) {
							for (MallPermissionType localPermissionType : MallPermissionType.values()) {
								if (localPermissionType.name().equalsIgnoreCase(permissionType)) {
									userPermissions.add(localPermissionType);
								}
							}
						}
						trustedUsers.put(tu, userPermissions);
					}
					for (User localUser : trustedUsers.keySet()) {
						shop.getTrustedUsers().put(localUser, trustedUsers.get(localUser));
					}
					continue;
				}
			}
		}

		saveConfig();

	}

	public void saveMall(Mall mall) {
		config.set("Malls." + mall.getType().getReadableName() + ".Cuboid", mall.getCuboid().serialize());
		if (mall.getWarp() != null) {
			config.set("Malls." + mall.getType().getReadableName() + ".Warp", LocationUtil.toString(mall.getWarp()));
		}
		saveConfig();
		for (Shop shop : mall.getShops()) {
			saveShop(shop);
		}
	}

	public void saveMalls() {
		
		for (Mall mall : Mall.getMalls()) {
//			ConsoleUtil.sendMessage("Saving: " + mall.getType().getReadableName());
			saveMall(mall);
		}
	}

	public void saveShop(Shop shop) {
//		ConsoleUtil.sendMessage("Saving shop:" + shop.getId());
		Mall mall = shop.getMall();
		if (shop.getOwner() != null) {
			config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".Owner",
					shop.getOwner().getName());
			config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".TimeLeft",
					shop.getTimeLeft());
		} else {
			config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".Owner",
					null);
			config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".TimeLeft",
					null);
		}
		config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".Price", shop.getPrice());
		config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".Cuboid",
				shop.getCuboid().serialize());
		config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".Warp",
				LocationUtil.toString(shop.getWarp()));
		for (User u : shop.getTrustedUsers().keySet()) {
			List<String> permissionTypes = new ArrayList<String>();
			for (MallPermissionType type : shop.getTrustedUsers().get(u)) {
				permissionTypes.add(type.getReadableName());
			}
			config.set("Malls." + mall.getType().getReadableName() + ".Shops." + shop.getId() + ".TrustedPlayers."
					+ u.getName(), permissionTypes);
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
