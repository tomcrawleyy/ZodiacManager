package com.zodiacmc.ZodiacManager.Cuboids;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Factories.MallFactory;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Factories.ShopFactory;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class CuboidFactoryManager implements Listener {

	private static CuboidFactoryManager instance;
	private Map<User, CuboidFactory> users = new HashMap<User, CuboidFactory>();

	private CuboidFactoryManager() {

	}

	@EventHandler
	public void onCreate(BlockPlaceEvent e) {

		Player p = e.getPlayer();
		User u = UserManager.getInstance().getOnlineUser(p);
		if (!isInSetupMode(u))
			return;
		CuboidFactory factoryInstance = getUser(u);
		if (factoryInstance.getLocation1() == null) {
			factoryInstance.setLocation1(e.getBlock().getLocation());
			u.sendMessage("&aLocation 1 has been set, please place a block in the opposite corner!");
			if (factoryInstance.getType().shouldIgnoreY()) {
				u.sendMessage(
						"&aHeight is ignored in this protection type, meaning all blocks are protected from bedrock to build height!");
			} else {
				u.sendMessage(
						"&cCAUTION: Block height is not ignored in this setup, make sure you give it a reasonable height difference!");
			}
			e.setCancelled(true);
			return;
		}
		CuboidType type = factoryInstance.getType();
		switch (type) {
		case MALL:
			new Mall(((MallFactory) factoryInstance).getMallType(),
					factoryInstance.setLocation2(e.getBlock().getLocation()));
			u.sendMessage("&aMall created! You are no longer in setup mode!");
			break;
		case SHOP:
			ShopFactory sf = (ShopFactory) factoryInstance;
			if (!sf.getMall().getCuboid().isInCuboid(e.getBlock().getLocation())) {
				u.sendMessage("&cError: You cannot set a shop outside of the mall perimeter.");
				e.setCancelled(true);
				return;
			}
			sf.getMall().addShop(new Shop(sf.getMall(), sf.setLocation2(e.getBlock().getLocation()), sf.getPrice()));
			u.sendMessage("&aShop has been added successfully!");
			break;
		default:
			return;
		}
		removeUser(u);
		e.setCancelled(true);
		return;
	}

	public boolean isInSetupMode(User u) {
		return users.containsKey(u);
	}

	public boolean isInSetupMode(User u, CuboidType type) {
		if (users.containsKey(u) && users.get(u).getType() == type)
			return true;
		return false;
	}

	public CuboidFactory getUser(User u) {
		return users.get(u);
	}

	public void removeUser(User u) {
		users.remove(u);
	}

	public void addUser(User u, CuboidFactory factory) {
		if (users.containsKey(u))
			return;
		users.put(u, factory);
	}

	public static CuboidFactoryManager getInstance() {
		if (instance == null)
			instance = new CuboidFactoryManager();
		return instance;
	}

}
