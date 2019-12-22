package com.zodiacmc.ZodiacManager.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankType;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Malls.Commands.IgnoreProtection;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;

public class UserManager {

	private static UserManager instance;
	private CuboidFactoryManager cuboidFactoryManager = CuboidFactoryManager.getInstance();
	private Map<String, User> onlineUsers;
	private List<User> onlineUserList;
	private List<User> onlineStaffList;

	private UserManager() {
		instance = this;
		onlineUsers = new HashMap<String, User>();
		onlineUserList = new ArrayList<User>();
		onlineStaffList = new ArrayList<User>();
	}

	public List<User> getOnlineUsers() {
		return onlineUserList;
	}

	public User getUserOnlineOrOffline(String name) {
		User u = getOnlineUser(name);
		if (u == null)
			u = getOfflineUser(name);
		return u;
	}

	public User getOnlineUser(Player p) {
		return getOnlineUser(p.getName());
	}

	public User getOnlineUser(String name) {
		return onlineUsers.get(name);
	}

	public List<User> getOnlineStaff() {
		return onlineStaffList;
	}

	public User getOfflineUser(String name) {
		return new User(name);
	}

	public void login(Player p) {
		User u = new User(p);
		onlineUsers.put(p.getName(), u);
		onlineUserList.add(u);
		if (u.getRank().getRankType() == RankType.STAFF)
			onlineStaffList.add(u);
		for (Mall mall : Mall.getMalls()) {
			for (Shop shop : mall.getShops()) {
				if (shop.getOwner().getName().equalsIgnoreCase(u.getName())) {
					shop.setOwner(u);
				}
			}
		}
	}

	public void logout(Player p) {
		User u = onlineUsers.get(p.getName());
		if (u.getRank().getRankType() == RankType.STAFF) {
			onlineStaffList.remove(u);
		}
		if (IgnoreProtection.getUsersIgnoringProtection().contains(u))
			IgnoreProtection.getUsersIgnoringProtection().remove(u);
		onlineUsers.remove(p.getName());
		u.updateData();
		u.handleWorldBlocks();
		onlineUserList.remove(u);
		if (cuboidFactoryManager.isInSetupMode(u)) {
			cuboidFactoryManager.removeUser(u);
		}
	}

	public static UserManager getInstance() {
		if (instance == null)
			instance = new UserManager();
		return instance;
	}

}
