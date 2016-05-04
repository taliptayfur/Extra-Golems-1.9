package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityNetherBrickGolem extends GolemBase 
{			
	/** Golem should stand in one spot for number of ticks before affecting the block below it */
	private int ticksStandingStill;

	public EntityNetherBrickGolem(World world) 
	{
		super(world, 6.5F, Blocks.nether_brick);
		this.ticksStandingStill = 0;
		this.isImmuneToFire = true;
		this.stepHeight = 1.0F;
		this.tasks.addTask(0, new EntityAISwimming(this));
	}

	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("nether_brick"));
	}

	/** Attack by lighting on fire as well */
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if(super.attackEntityAsMob(entity))
		{
			if(Config.ALLOW_NETHERBRICK_SPECIAL_FIRE)
			{
				entity.setFire(2 + rand.nextInt(5));
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
		if(Config.ALLOW_NETHERBRICK_SPECIAL_LAVA)
		{
			int x = MathHelper.floor_double(this.posX);
			int y = MathHelper.floor_double(this.posY - 0.20000000298023224D);
			int z = MathHelper.floor_double(this.posZ);
			BlockPos below = new BlockPos(x,y,z);
			Block b1 = this.worldObj.getBlockState(below).getBlock();
			// debug:
			//System.out.println("below=" + below + "; lastPos = " + new BlockPos(MathHelper.floor_double(this.lastTickPosX), this.lastTickPosY, MathHelper.floor_double(this.lastTickPosZ)));
			//System.out.println("block on= " + b1.getUnlocalizedName() + "; ticksStandingStill=" + ticksStandingStill);
			
			if(x == MathHelper.floor_double(this.lastTickPosX) && z == MathHelper.floor_double(this.lastTickPosZ))
			{
				if(++this.ticksStandingStill >= Config.TWEAK_NETHERBRICK && b1 == Blocks.cobblestone && rand.nextInt(16) == 0)
				{
					this.worldObj.setBlockState(below, Blocks.lava.getDefaultState(), 3);
					this.ticksStandingStill = 0;
				}
			}
			else
			{
				this.ticksStandingStill = 0;
			}
		}
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 4 + this.rand.nextInt(8 + lootingLevel);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Items.netherbrick, size));
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
