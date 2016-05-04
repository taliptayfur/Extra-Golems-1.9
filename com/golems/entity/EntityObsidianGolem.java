package com.golems.entity;

import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityObsidianGolem extends GolemBase 
{			
	public EntityObsidianGolem(World world) 
	{
		super(world, 18.0F, Blocks.obsidian);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("obsidian"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 1 + this.rand.nextInt(2 + (lootingLevel > 2 ? 2 : lootingLevel));
		this.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.obsidian, size));
	}
   
	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
