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

public class EntitySeaLanternGolem extends GolemLightProvider
{			
	public EntitySeaLanternGolem(World world) 
	{
		super(world, 4.0F, Blocks.sea_lantern, EnumLightLevel.WATER_FULL);
		this.tickDelay = 1;
	}
	
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("sea_lantern"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)	
	{
		int size = 1 + this.rand.nextInt(2 + lootingLevel);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.sea_lantern, size > 4 ? 4 : size));
		GolemBase.addDropEntry(dropList, Items.prismarine_shard, 0, 1, 3, 4 + lootingLevel * 5);
		GolemBase.addDropEntry(dropList, Items.prismarine_crystals, 0, 1, 3, 4 + lootingLevel * 5);
	}
 
	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_glass_step;
	}
}
