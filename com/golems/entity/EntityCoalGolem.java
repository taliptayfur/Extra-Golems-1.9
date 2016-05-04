package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityCoalGolem extends GolemBase
{
	public EntityCoalGolem(World world) 
	{
		super(world, 2.5F, Blocks.coal_block);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("coal"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
	}
	
	/** Attack by adding potion effect as well */
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if(super.attackEntityAsMob(entity))
		{
			final int BLIND_CHANCE = 4;
			if(Config.ALLOW_COAL_SPECIAL && entity instanceof EntityLivingBase && this.rand.nextInt(BLIND_CHANCE) == 0)
			{
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.blindness, 20 * (1 + rand.nextInt(5)), 1));
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(this.isBurning())
		{
			this.setFire(2);
		}
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 8 + this.rand.nextInt(8 + lootingLevel * 2);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Items.coal, size));
		this.addDropEntry(dropList, Items.coal, 1, 1, size / 4, 40);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
