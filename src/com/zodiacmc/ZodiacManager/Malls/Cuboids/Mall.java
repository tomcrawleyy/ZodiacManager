package com.zodiacmc.ZodiacManager.Malls.Cuboids;

import java.util.ArrayList;
import java.util.List;

import com.zodiacmc.ZodiacManager.Cuboids.Cuboid;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidContainer;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;

public class Mall implements CuboidContainer {
	
	private static List<Mall> malls = new ArrayList<Mall>();
	private MallType type;
	private Cuboid cuboid;
	private int dailyCost;
	private List<Shop> shops;
	
	public Mall(MallType type, Cuboid cuboid) {
		this.type = type;
		this.cuboid = cuboid;
		this.shops = new ArrayList<Shop>();
		malls.add(this);
	}
	
	public List<Shop> getShops(){
		return shops;
	}
	
	public void addShop(Shop shop) {
		shops.add(shop);
	}
	
	public static List<Mall> getMalls(){
		return malls;
	}
	
	public void setDailyCost(int newCost) {
		this.dailyCost = newCost;
	}
	
	public int getDailyCost() {
		return dailyCost;
	}
	
	public Cuboid getCuboid() {
		return cuboid;
	}
	
	public MallType getType()
	{
		return type;
	}

	public void setCuboid(Cuboid cuboid) {
		this.cuboid = cuboid;
	}
}
