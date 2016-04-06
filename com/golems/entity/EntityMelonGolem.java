package com.golems.entity;

import java.util.ArrayList;
import java.util.List;

import com.golems.entity.ai.EntityAIPlantFlowers;
import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityMelonGolem extends GolemBase 
{	
	private IBlockState[] flowers;
	private final Block[] soils = {Blocks.dirt, Blocks.grass, Blocks.mycelium};
	
	public EntityMelonGolem(World world) 
	{
		super(world, 1.5F, Blocks.melon_block);	
	}
	
	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		List<IBlockState> lFlowers = new ArrayList();
		for(EnumFlowerType e : BlockFlower.EnumFlowerType.values())
		{
			lFlowers.add(e.getBlockType().getBlock().getStateFromMeta(e.getMeta()));
		}
		for(int j = 0; j < 3; j++)
		{
			lFlowers.add(Blocks.tallgrass.getStateFromMeta(j));
		}
		this.flowers = lFlowers.toArray(new IBlockState[lFlowers.size()]);
			
		this.tasks.addTask(2, new EntityAIPlantFlowers(this, Config.TWEAK_MELON, flowers, soils, Config.ALLOW_MELON_SPECIAL));
	}

	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("melon"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(6 + lootingLevel * 4);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.melon, size));
		GolemBase.addDropEntry(dropList, Items.melon_seeds, 0, 1, 6 + lootingLevel, 20 + lootingLevel * 10);
		GolemBase.addDropEntry(dropList, Items.speckled_melon, 0, 1, 1, 2 + lootingLevel * 10);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
