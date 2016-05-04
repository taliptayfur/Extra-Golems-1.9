package com.golems.entity;

import java.util.List;

import com.golems.entity.ai.EntityAIPlaceRandomBlocks;
import com.golems.main.Config;
import com.golems.main.ExtraGolems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityMushroomGolem extends GolemMultiTextured
{			
	public static final String shroomPrefix = "shroom";
	public static final String[] shroomTypes = {"red","brown"};
	public final IBlockState[] mushrooms = {Blocks.brown_mushroom.getDefaultState(), Blocks.red_mushroom.getDefaultState()};
	public final Block[] soils = {Blocks.dirt, Blocks.grass, Blocks.mycelium};

	public EntityMushroomGolem(World world) 
	{
		super(world, 3.0F, Blocks.red_mushroom_block, shroomPrefix, shroomTypes);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIPlaceRandomBlocks(this, Config.TWEAK_MUSHROOM, mushrooms, soils, Config.ALLOW_MUSHROOM_SPECIAL));
	}
	
	@Override
	public String getModId() 
	{
		return ExtraGolems.MODID;
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)	
	{
		int size = 4 + this.rand.nextInt(6 + lootingLevel * 2);
		Block shroom = rand.nextBoolean() ? Blocks.red_mushroom : Blocks.brown_mushroom;
		this.addGuaranteedDropEntry(dropList, new ItemStack(shroom, size));
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_grass_step;
	}
}
