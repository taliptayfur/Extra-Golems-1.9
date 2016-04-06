package com.golems.entity;

import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityGlassGolem extends GolemBase 
{			
	public EntityGlassGolem(World world) 
	{
		super(world, 13.0F, Blocks.glass);
		this.setCanTakeFallDamage(true);
	}
	
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("glass"));
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		GolemBase.addDropEntry(dropList, Blocks.glass, 0, lootingLevel, lootingLevel + 1, 90);
	}
	
	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_glass_step;
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.block_glass_break;
	}
}
