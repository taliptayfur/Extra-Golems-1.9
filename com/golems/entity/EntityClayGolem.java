package com.golems.entity;

import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityClayGolem extends GolemBase 
{			
	public EntityClayGolem(World world) 
	{
		super(world, 2.0F, Blocks.clay);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("clay"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 8 + this.rand.nextInt(8 + lootingLevel);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Items.clay_ball, size));
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_gravel_step;
	}
}
