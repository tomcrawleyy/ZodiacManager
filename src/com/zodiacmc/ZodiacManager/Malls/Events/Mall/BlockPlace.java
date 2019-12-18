package com.zodiacmc.ZodiacManager.Malls.Events.Mall;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.zodiacmc.ZodiacManager.Cuboids.Cuboid;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactory;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Factories.MallFactory;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class BlockPlace implements Listener {

	@EventHandler
	public void onMallCreate(BlockPlaceEvent e) {
		
		Player p = e.getPlayer();
		User u = UserManager.getInstance().getOnlineUser(p);
		
		CuboidFactoryManager cfm = CuboidFactoryManager.getInstance();
		if (!cfm.isInSetupMode(u))
			return;
		
		CuboidFactory userInstance = cfm.getUser(u);
		if (userInstance.getType() != CuboidType.MALL)
			return;
		
		MallFactory mallInstance = (MallFactory) userInstance;
		if (mallInstance.getLocation1() == null) {
			mallInstance.setLocation1(e.getBlock().getLocation());
			u.sendMessage("&aLocation 1 has been set, please place a block in the opposite corner of the mall!");
			u.sendMessage(
					"&aHeight is ignored in this protection type, meaning all blocks are protected from bedrock to build height!");
			return;
		}
		
		Cuboid c = mallInstance.setLocation2(e.getBlock().getLocation());
		new Mall(mallInstance.getMallType(), c);
		cfm.removeUser(u);
		u.sendMessage("&aMall created! You are no longer in setup mode!");
		return;
		
	}
	
	@EventHandler
	public void onMallRedefine(BlockPlaceEvent e) {
		
	}

}
