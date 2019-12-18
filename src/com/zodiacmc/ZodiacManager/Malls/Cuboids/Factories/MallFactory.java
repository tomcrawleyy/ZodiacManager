package com.zodiacmc.ZodiacManager.Malls.Cuboids.Factories;

import org.bukkit.Location;

import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactory;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;
import com.zodiacmc.ZodiacManager.Users.User;

public class MallFactory extends CuboidFactory {
	
	private MallType type;
	public MallFactory(String name, User u) {
		super(u, CuboidType.MALL);
		for (MallType localType : MallType.values()) {
			if (localType.getReadableName().equalsIgnoreCase(name)) {
				this.type = localType;
				break;
			}
		}
	}
	
	public MallType getMallType() {
		return this.type;
	}

}
