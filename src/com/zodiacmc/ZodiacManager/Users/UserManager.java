package com.zodiacmc.ZodiacManager.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankType;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Malls.Commands.IgnoreProtection;

public class UserManager {

	private static UserManager instance;
	private CuboidFactoryManager cuboidFactoryManager = CuboidFactoryManager.getInstance();
	private Map<String, User> onlineUsers;
	private List<User> onlineUserList;
	private List<User> onlineStaffList;
	private List<User> offlineUserList;

	private UserManager() {
		instance = this;
		onlineUsers = new HashMap<String, User>();
		onlineUserList = new ArrayList<User>();
		onlineStaffList = new ArrayList<User>();
		offlineUserList = new ArrayList<User>();
	}

	public List<User> getOnlineUsers() {
		return onlineUserList;
	}

	public User getUser(String name) {
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
		for (User localUser : offlineUserList) {
			if (localUser.getName().equalsIgnoreCase(name))
				return localUser;
		}
		User u = new User(name);
		offlineUserList.add(u);
		return u;
	}

	public void login(Player p) {
		User u = null;
		for (User localUser : offlineUserList) {
			if (localUser.getName().equalsIgnoreCase(p.getName())) {
				u = localUser;
				break;
			}
		}
		if (u == null) {
			u = new User(p);
		} else {
			offlineUserList.remove(u);
		}
		onlineUsers.put(p.getName(), u);
		onlineUserList.add(u);
		if (u.getRank().getRankType() == RankType.STAFF)
			onlineStaffList.add(u);
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
		offlineUserList.add(u);
	}

	public static UserManager getInstance() {
		if (instance == null)
			instance = new UserManager();
		return instance;
	}

}
