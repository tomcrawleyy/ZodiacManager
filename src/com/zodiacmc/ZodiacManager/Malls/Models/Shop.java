package com.zodiacmc.ZodiacManager.Malls.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Location;

import com.zodiacmc.ZodiacManager.Cuboids.Cuboid;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;

public class Shop {

	private User user;
	private Cuboid cuboid;
	private Mall mall;
	private int id;
	private long timeLeft;
	private Location warp;
	private Map<User, List<MallPermissionType>> trustedUsers;

	public Shop(Mall mall, Cuboid cuboid) {
		this.mall = mall;
		this.cuboid = cuboid;
		this.trustedUsers = new HashMap<User, List<MallPermissionType>>();
		this.id = this.mall.getShops().size();
		this.mall.addShop(this);
	}

	public Shop(Mall mall, Cuboid cuboid, User user) {
		this.mall = mall;
		this.cuboid = cuboid;
		this.user = user;
		this.trustedUsers = new HashMap<User, List<MallPermissionType>>();
		this.id = this.mall.getShops().size();
		this.mall.addShop(this);
	}
	
	public Shop(Mall mall, Cuboid cuboid, User user, Location warp) {
		this.mall = mall;
		this.cuboid = cuboid;
		this.user = user;
		this.trustedUsers = new HashMap<User, List<MallPermissionType>>();
		this.id = this.mall.getShops().size();
		this.mall.addShop(this);
		this.warp = warp;
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

}
