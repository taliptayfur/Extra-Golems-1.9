package com.golems.entity;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityRedSandstoneGolem extends EntitySandstoneGolem
{			
	public EntityRedSandstoneGolem(World world) 
	{
		super(world);
		this.setCreativeReturn(Blocks.red_sandstone);
	}

	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("red_sandstone"));
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 4 + this.rand.nextInt(8 + lootingLevel * 2);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.sand, size, 1));
	}
}
