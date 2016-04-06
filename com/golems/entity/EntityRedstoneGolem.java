package com.golems.entity;

import java.util.List;

import com.golems.main.Config;
import com.golems.main.ContentInit;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityRedstoneGolem extends GolemBase 
{		
	private final boolean CAN_POWER;
	protected int powerOutput;
	protected int tickDelay;
	
	public EntityRedstoneGolem(World world) 
	{
		this(world, 2.0F, Blocks.redstone_block, Config.TWEAK_REDSTONE, Config.ALLOW_REDSTONE_SPECIAL);
	}
	
	public EntityRedstoneGolem(World world, float attack, Block pick, int power, final boolean CONFIG_ALLOWS_POWERING) 
	{
		super(world, attack, pick);
		this.setPowerOutput(power);
		this.CAN_POWER = CONFIG_ALLOWS_POWERING;
		this.tickDelay = 2;
	}
	
	public EntityRedstoneGolem(World world, float attack, int power, final boolean CONFIG_ALLOWS_POWERING)
	{
		this(world, attack, ContentInit.golemHead, power, CONFIG_ALLOWS_POWERING);
	}

	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("redstone"));
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
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
		if(CAN_POWER && (this.tickDelay <= 1 || this.ticksExisted % this.tickDelay == 0))
		{
			placePowerNearby();
		}
	}
	
	/** Finds air blocks nearby and replaces them with BlockMovingPowerSource **/
	protected boolean placePowerNearby() 
	{
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY - 0.20000000298023224D); // y-pos of block below golem
		int z = MathHelper.floor_double(this.posZ);
		
		// power 3 layers at golem location
		for(int k = -1; k < 3; ++k)
		{	
			BlockPos at = new BlockPos(x, y + k, z);
			// if the block is air, make it a power block
			if(this.worldObj.isAirBlock(at))
			{
				return this.worldObj.setBlockState(at, ContentInit.blockPowerSource.getStateFromMeta(this.getPowerOutput()));
			}
		}
		return false;
	}
	
	public void setPowerOutput(int toSet)
	{
		this.powerOutput = toSet % 16;
	}
	
	/**
	 * Override this to check conditions and return correct power level
	 **/
	public int getPowerOutput()
	{
		return this.powerOutput;
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 8 + rand.nextInt(14 + lootingLevel * 4);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.redstone, size > 36 ? 36 : size));
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_hit;
	}
}
