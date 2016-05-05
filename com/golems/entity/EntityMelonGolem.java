package com.golems.entity;

import java.util.ArrayList;
import java.util.List;

import com.golems.entity.ai.EntityAIPlaceRandomBlocks;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityMelonGolem extends GolemBase 
{	
	public static final String ALLOW_SPECIAL = "Allow Special: Plant Flowers";
	public static final String FREQUENCY = "Flower Frequency";
	
	private IBlockState[] flowers;
	private final Block[] soils = {Blocks.dirt, Blocks.grass, Blocks.mycelium, Blocks.farmland};
	
	public EntityMelonGolem(World world) 
	{
		super(world, Config.MELON.getBaseAttack(), Blocks.melon_block);	
		this.setCanSwim(true);
		
		// init list and AI for planting flowers
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
		int freq = Config.MELON.getInt(FREQUENCY);
		boolean allowed = Config.MELON.getBoolean(ALLOW_SPECIAL);
		this.tasks.addTask(2, new EntityAIPlaceRandomBlocks(this, freq, flowers, soils, allowed));
	}
	
	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();	
	}

	@Override
	protected ResourceLocation applyTexture()
	{
		return this.makeGolemTexture("melon");
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.MELON.getMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(6 + lootingLevel * 4);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Items.melon, size));
		this.addDropEntry(dropList, Items.melon_seeds, 0, 1, 6 + lootingLevel, 20 + lootingLevel * 10);
		this.addDropEntry(dropList, Items.speckled_melon, 0, 1, 1, 2 + lootingLevel * 10);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
