package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityIceGolem extends GolemBase 
{			
	public EntityIceGolem(World world) 
	{
		super(world, 6.0F, Blocks.packed_ice);
		((PathNavigateGround)this.getNavigator()).setCanSwim(true);
	}

	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("ice"));
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		// calling every other tick reduces lag by 50%
		if(this.ticksExisted % 2 == 0)
		{
			int x = MathHelper.floor_double(this.posX);
			int y = MathHelper.floor_double(this.posY - 0.20000000298023224D);
			int z = MathHelper.floor_double(this.posZ);
			BlockPos below = new BlockPos(x,y,z);

			if(this.worldObj.getBiomeGenForCoords(below).getFloatTemperature(below) > 1.0F)
			{
				this.attackEntityFrom(DamageSource.onFire, 1.0F);
			}
			
			if(Config.ALLOW_ICE_SPECIAL)
			{
				freezeBlocks(this.worldObj, x, y, z);
			}
				
		}
	}
	
	public boolean freezeBlocks(World world, int x, int y, int z)
	{
		final int ICE_RANGE = 3;
		BlockPos below = new BlockPos(x,y,z);
		boolean flag = false;

		// check sphere around golem to absorb water
		for(int i = -ICE_RANGE; i <= ICE_RANGE; i++)
		{
			for(int j = 1; j >= -1; j--)
			{
				for(int k = -ICE_RANGE; k <= ICE_RANGE; k++)
				{
					if(below.distanceSq(x + i, y, z + k) <= ICE_RANGE * ICE_RANGE)
					{
						BlockPos pos = new BlockPos(x + i, y + j, z + k);
						Block b1 = this.worldObj.getBlockState(pos).getBlock();
						Block toBecome = null;

						boolean shouldBeThinIce = false;

						if(b1 == Blocks.water)
						{
							toBecome = Blocks.packed_ice;
						}
						// intentionally not an 'if else' -- causes less water to become packed ice (randomized)
						if(b1 == Blocks.flowing_water || (toBecome == Blocks.packed_ice && rand.nextBoolean()))
						{
							toBecome = Blocks.ice;	    		
						}
						else if(b1 == Blocks.lava)
						{
							toBecome = Blocks.obsidian;    		
						}
						else if(b1 == Blocks.flowing_lava)
						{
							toBecome = Blocks.cobblestone;  		
						}
						
						flag = toBecome != null ? this.worldObj.setBlockState(pos, toBecome.getDefaultState(), 3) : false;
					}
				}
			}	
		}
		return flag;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if(super.attackEntityAsMob(entity))
		{
			if(entity.isBurning())
			{
				this.attackEntityFrom(DamageSource.generic, 0.5F);
			}
			return true;
		}
		return false;  
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
		int size = 1 + lootingLevel;
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.ice, size > 4 ? 4 : size));
		if(lootingLevel > 0 || !Config.CAN_USE_REGULAR_ICE)
		{
			GolemBase.addDropEntry(dropList, Blocks.packed_ice, 0, 0, size > 2 ? 2 : size, 80);
		}
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.block_glass_break;
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_glass_step;
	}
}
