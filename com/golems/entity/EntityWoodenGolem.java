package com.golems.entity;

import java.util.List;

import com.golems.main.ExtraGolems;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityWoodenGolem extends GolemMultiTextured
{			
	public static final String woodPrefix = "wooden";
	private static final String[] woodTypes = {"oak","spruce","birch","jungle","acacia","big_oak"};

	public EntityWoodenGolem(World world) 
	{
		super(world, 3.0F, new ItemStack(Blocks.log), woodPrefix, woodTypes);
		this.tasks.addTask(0, new EntityAISwimming(this));
		((PathNavigateGround)this.getNavigator()).setCanSwim(true);
	}	
	
	@Override
	public ItemStack getCreativeReturn()
	{
		// try to return the same block of this golem's texture
		Block block = Blocks.log;
		int damage = this.getTextureNum() % woodTypes.length;
		if(this.getTextureNum() > 3)
		{
			block = Blocks.log2;
			damage %= 2;
		}
		return new ItemStack(block, 1, damage);
	}
	
	@Override
	public String getModId() 
	{
		return ExtraGolems.MODID;
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(4 + lootingLevel * 4);
		int meta = this.getTextureNum() % woodTypes.length;
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Item.getItemFromBlock(Blocks.planks), size > 16 ? 16 : size, meta));
		GolemBase.addDropEntry(dropList, Items.stick, 0, 1, 4, 10 + lootingLevel * 4);
		GolemBase.addDropEntry(dropList, Blocks.sapling, 0, 1, 2, 4 + lootingLevel * 4);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_wood_step;
	}
}
