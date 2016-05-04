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

public class EntityPrismarineGolem extends GolemBase 
{			
	public EntityPrismarineGolem(World world) 
	{
		super(world, 8.0F, Blocks.prismarine);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("prismarine"));
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(this.isInWater())
		{
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.50D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.24D);
		}
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.24D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)	
	{
		int size = 6 + this.rand.nextInt(4 + lootingLevel * 2);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Items.prismarine_shard, size));
		this.addDropEntry(dropList, Items.prismarine_crystals, 0, 1, 3, 6 + lootingLevel * 5);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
