package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySpongeGolem extends GolemBase 
{	
	public static final String ALLOW_SPECIAL = "Allow Special: Absorb Water";
	public static final String INTERVAL = "Water Soaking Frequency";
	public static final String RANGE = "Water Soaking Range";
	public static final String PARTICLES = "Can Render Sponge Particles";
	
	public EntitySpongeGolem(World world) 
	{
		super(world, Config.SPONGE.getBaseAttack(), Blocks.sponge);
		this.setCanSwim(true);
	}

	@Override
	protected ResourceLocation applyTexture()
	{
		return this.makeGolemTexture("sponge");
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		int interval = Config.SPONGE.getInt(INTERVAL);
		if(Config.SPONGE.getBoolean(ALLOW_SPECIAL) && (interval <= 1 || this.ticksExisted % interval == 0))
		{
			int x = MathHelper.floor_double(this.posX);
			int y = MathHelper.floor_double(this.posY - 0.20000000298023224D) + 2;
			int z = MathHelper.floor_double(this.posZ);
			BlockPos center = new BlockPos(x,y,z);
			int range = Config.SPONGE.getInt(RANGE);

			// check sphere around golem to absorb water
			for(int i = -range; i <= range; i++)
			{
				for(int j = -range; j <= range; j++)
				{
					for(int k = -range; k <= range; k++)
					{
						if(center.distanceSq(x + i, y + j, z + k) <= range * range)
						{
							BlockPos pos = new BlockPos(x + i, y + j, z + k);
							IBlockState state = this.worldObj.getBlockState(pos);

							if(state.getMaterial() == Material.water || state.getBlock() == Blocks.water || state.getBlock() == Blocks.flowing_water)
							{
								this.worldObj.setBlockToAir(pos);
							}
						}
					}
				}	
			}
		}

		if(Config.SPONGE.getBoolean(PARTICLES) && Math.abs(this.motionX) < 0.05D && Math.abs(this.motionZ) < 0.05D && worldObj.isRemote)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.DRIP_WATER, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width * 0.6D, this.posY + this.rand.nextDouble() * (double)this.height - 0.75D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.6D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.9D) * 0.5D, new int[0]);
		}
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.SPONGE.getMaxHealth());
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)	
	{
		int size = 1 + this.rand.nextInt(3 + lootingLevel);
		this.addGuaranteedDropEntry(dropList, new ItemStack(Item.getItemFromBlock(Blocks.sponge), size > 4 ? 4 : size));
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_cloth_place;
	}
}
