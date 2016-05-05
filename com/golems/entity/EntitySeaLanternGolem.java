package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntitySeaLanternGolem extends GolemLightProvider
{			
	public EntitySeaLanternGolem(World world) 
	{
		super(world, Config.SEA_LANTERN.getBaseAttack(), Blocks.sea_lantern, EnumLightLevel.WATER_FULL);
		this.tickDelay = 1;
	}
	
	protected ResourceLocation applyTexture()
	{
		return this.makeGolemTexture("sea_lantern");
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.SEA_LANTERN.getMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)	
	{
		int size = 1 + this.rand.nextInt(2 + lootingLevel);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.sea_lantern, size > 4 ? 4 : size));
		this.addDropEntry(dropList, Items.prismarine_shard, 0, 1, 3, 4 + lootingLevel * 5);
		this.addDropEntry(dropList, Items.prismarine_crystals, 0, 1, 3, 4 + lootingLevel * 5);
	}
 
	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_glass_step;
	}
}
