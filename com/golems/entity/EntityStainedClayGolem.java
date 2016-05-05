package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityStainedClayGolem extends GolemColorizedMultiTextured
{
	public static final String DROP_META = "Drop Metadata";
	
	private static final ResourceLocation TEXTURE_BASE = GolemBase.makeGolemTexture("stained_clay");
	private static final ResourceLocation TEXTURE_OVERLAY = GolemBase.makeGolemTexture("stained_clay_grayscale");
	
	public EntityStainedClayGolem(World world)
	{
		super(world, Config.STAINED_CLAY.getBaseAttack(), Blocks.stained_hardened_clay, TEXTURE_BASE, TEXTURE_OVERLAY, ItemDye.dyeColors);
	}
	
	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.STAINED_CLAY.getMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int keyReturn = Config.STAINED_CLAY.getInt(DROP_META);
		int meta = keyReturn < 0 ? 15 - this.getTextureNum() : keyReturn;
		int size = 1 + lootingLevel + rand.nextInt(3);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.stained_hardened_clay, size > 4 ? 4 : size, meta));
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
