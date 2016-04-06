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

public class EntityGlowstoneGolem extends GolemLightProvider
{			
	public EntityGlowstoneGolem(World world) 
	{
		super(world, 12.0F, Blocks.glowstone, EnumLightLevel.FULL);
		this.setCanTakeFallDamage(true);
		this.isImmuneToFire = true;
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("glowstone"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 6 + this.rand.nextInt(8 + lootingLevel * 2);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.glowstone_dust, size));
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
