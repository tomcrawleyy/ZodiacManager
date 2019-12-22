package com.zodiacmc.ZodiacManager.Malls.Cuboids;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Location;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Cuboids.Cuboid;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidContainer;
import com.zodiacmc.ZodiacManager.Malls.Configurations.MallConfig;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;

public class Shop implements CuboidContainer {

	private User user;
	private Cuboid cuboid;
	private Mall mall;
	private int id, price;
	private long timeLeft;
	private Location warp;
	private Map<User, List<MallPermissionType>> trustedUsers;
	private static FileManager fm = FileManager.getInstance();

	public Shop(Mall mall, Cuboid cuboid, int price) {
		this.mall = mall;
		this.cuboid = cuboid;
		this.trustedUsers = new HashMap<User, List<MallPermissionType>>();
		this.id = this.mall.getShops().size();
		this.mall.addShop(this);
		this.price = price;
		this.warp = this.cuboid.getCenter();
	}

	public Shop(Mall mall, Cuboid cuboid, int price, User user) {
		this.mall = mall;
		this.cuboid = cuboid;
		this.user = user;
		this.trustedUsers = new HashMap<User, List<MallPermissionType>>();
		this.id = this.mall.getShops().size();
		this.mall.addShop(this);
		this.price = price;
		this.warp = this.cuboid.getCenter();
	}
	
	public Shop(Mall mall, Cuboid cuboid, int price, User user, Location warp) {
		this.mall = mall;
		this.cuboid = cuboid;
		this.user = user;
		this.trustedUsers = new HashMap<User, List<MallPermissionType>>();
		this.id = this.mall.getShops().size();
		this.mall.addShop(this);
		this.warp = warp;
		this.price = price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return this.price;
	}

	public void setWarp(Location newWarp) {
		this.warp = newWarp;
	}

	public Location getWarp() {
		if (this.warp == null) {
			return this.cuboid.getCenter();
		}
		return this.warp;
	}

	public void setTimeLeft(long value, TimeUnit unit) {
		this.timeLeft = unit.toMillis(value);
	}

	public long getTimeLeft() {
		return this.timeLeft;
	}

	public void addDays(int days) {
		this.timeLeft += TimeUnit.DAYS.toMillis(days);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int newId) {
		this.id = newId;
	}

	public List<MallPermissionType> getTrusteesPermissions(User u) {
		return trustedUsers.get(u);
	}

	public Map<User, List<MallPermissionType>> getTrustedUsers(){
		return trustedUsers;
	}
	
	public Mall getMall() {
		return mall;
	}

	public User getOwner() {
		return user;
	}

	public void setOwner(User user) {
		this.user = user;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public void setCuboid(Cuboid cuboid) {
		this.cuboid = cuboid;
	}
	
	public void reset() {
		this.user.getOwnedShops().remove(this);
		for (User user : this.trustedUsers.keySet()) {
			user.getTrustedShops().remove(this);
		}
		this.trustedUsers.clear();
		this.user = null;
		//TODO Remove all blocks from shop
	}
	
	public void saveConfig() {
		MallConfig mallConfig = (MallConfig)fm.getConfig(ConfigType.MALL);
		mallConfig.saveShop(this);
	}

}
