package com.zodiacmc.ZodiacManager.Cuboids.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Cuboids.Factories.CuboidRedefinerFactory;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class CuboidRedefinerFactoryEvent implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		User u = UserManager.getInstance().getOnlineUser(p);
		CuboidFactoryManager cfm = CuboidFactoryManager.getInstance();
		if (!cfm.isInSetupMode(u, CuboidType.REDEFINE))
			return;
		CuboidRedefinerFactory crf = (CuboidRedefinerFactory)cfm.getUser(u);
		if (crf.getLocation1() == null) {
			crf.setLocation1(e.getBlock().getLocation());
			u.sendMessage("&7(7fCuboid&dRedefiner&7) &aLocation 1 set! Please place a block in the opposite corner!");
			String message = crf.shouldIngoreY() ? "&aRemember, the height is ignored for this particular cuboid!" : "&cCAUTION: Height is not ignored for this cuboid type, make sure you adjust accordingly.";
			u.sendMessage(message);
			cfm.removeUser(u);
			e.setCancelled(true);
			return;
		}
		crf.getContainer().setCuboid(crf.setLocation2(e.getBlock().getLocation()));
		u.sendMessage("&7(7fCuboid&dRedefiner&7) &aRedefine success!");
	}

}
