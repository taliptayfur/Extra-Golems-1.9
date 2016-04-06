package com.golems.entity;

import java.util.List;
import java.util.Random;

import com.golems.main.Config;
import com.golems.main.ContentInit;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityEndstoneGolem extends GolemBase 
{			
	/** countdown timer for next teleport **/
	protected int teleportDelay;
	/** Max distance for one teleport; range is 32.0 for endstone golem **/
	protected double range;
	protected boolean canTeleport;
	protected boolean hasAmbientParticles;
	
	protected int ticksBetweenIdleTeleports;
	/** Percent chance to teleport away when hurt by non-projectile **/
	protected int chanceToTeleportWhenHurt;
	
	/** Default constructor **/
	public EntityEndstoneGolem(World world) 
	{
		this(world, 8.0F, Blocks.end_stone, 32.0D, Config.ALLOW_ENDSTONE_SPECIAL, true);
	}
	
	/**
	 * Flexible constructor to allow child classes to customize.
	 * 
	 * @param world the worldObj
	 * @param attack base attack damage
	 * @param pick Creative pick-block return
	 * @param teleportRange 64.0 for enderman, 32.0 for endstone golem
	 * @param teleportingAllowed usually set by the config, checked here
	 * @param ambientParticles whether always to display "portal" particles 
	 **/
	public EntityEndstoneGolem(World world, float attack, Block pick, double teleportRange, boolean teleportingAllowed, boolean ambientParticles)
	{
		super(world, attack, pick);
		this.ticksBetweenIdleTeleports = 200;
		this.chanceToTeleportWhenHurt = 15;
		this.range = teleportRange;
		this.canTeleport = teleportingAllowed;
		this.hasAmbientParticles = ambientParticles;
	}

	/**
	 * Flexible contructor to allow child classes to customize.
	 * 
	 * @param world the worldObj
	 * @param attack base attack damage
	 * @param teleportRange 64.0 for enderman, 32.0 for endstone golem
	 * @param teleportingAllowed usually set by the config, checked here
	 * @param ambientParticles whether to always display "portal" particles
	 **/
	public EntityEndstoneGolem(World world, float attack, double teleportRange, boolean teleportingAllowed, boolean ambientParticles) 
	{
		this(world, attack, ContentInit.golemHead, teleportRange, teleportingAllowed, ambientParticles);
	}
	
	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("end_stone"));
	}
	
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		GolemBase.addDropEntry(dropList, Blocks.end_stone, 0, 2, 2 + lootingLevel, 90);
		GolemBase.addDropEntry(dropList, Items.ender_pearl, 0, 2, 4 + lootingLevel, 40);
		GolemBase.addDropEntry(dropList, Items.ender_eye, 0, 1, 1 + lootingLevel, 6);
	}
	
	/**
     * Teleports the entity to the specified location. Used for Enderman and Chorus Fruit teleportation
     */
    public boolean teleportTo_(double x, double y, double z)
    {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        boolean flag = false;
        BlockPos blockpos = new BlockPos(this);
        World world = this.worldObj;
        Random random = this.getRNG();

        if (world.isBlockLoaded(blockpos))
        {
            boolean flag1 = false;

            while (!flag1 && blockpos.getY() > 0)
            {
                BlockPos blockpos1 = blockpos.down();
                IBlockState iblockstate = world.getBlockState(blockpos1);

                if (iblockstate.getMaterial().blocksMovement())
                {
                    flag1 = true;
                }
                else
                {
                    --this.posY;
                    blockpos = blockpos1;
                }
            }

            if (flag1)
            {
                this.setPositionAndUpdate(this.posX, this.posY, this.posZ);

                if (world.getCubes(this, this.getEntityBoundingBox()).isEmpty() && !world.isAnyLiquid(this.getEntityBoundingBox()))
                {
                    flag = true;
                }
            }
        }

        if (!flag)
        {
            this.setPositionAndUpdate(d0, d1, d2);
            return false;
        }
        else
        {
            int i = 128;

            for (int j = 0; j < i; ++j)
            {
                double d6 = (double)j / ((double)i - 1.0D);
                float f = (random.nextFloat() - 0.5F) * 0.2F;
                float f1 = (random.nextFloat() - 0.5F) * 0.2F;
                float f2 = (random.nextFloat() - 0.5F) * 0.2F;
                double d3 = d0 + (this.posX - d0) * d6 + (random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double d4 = d1 + (this.posY - d1) * d6 + random.nextDouble() * (double)this.height;
                double d5 = d2 + (this.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, (double)f, (double)f1, (double)f2, new int[0]);
            }

            if (this instanceof EntityCreature)
            {
                ((EntityCreature)this).getNavigator().clearPathEntity();
            }

            return true;
        }
    }
	
	protected boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * range;
        double d1 = this.posY + (this.rand.nextDouble() - 0.5D) * range * 0.5D;
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * range;
        return this.teleportTo(d0, d1, d2);
    }

	@Override
	public void onLivingUpdate()
	{
	    super.onLivingUpdate();

	    if (this.worldObj.isRemote)
        {
            for (int i = 0; this.hasAmbientParticles && i < 2; ++i)
            {
                this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        }
	    
	    if (this.getAITarget() != null)
        {
            this.faceEntity(this.getAITarget(), 100.0F, 100.0F);
            if(rand.nextInt(5) == 0)
            {
            	this.teleportToEntity(this.getAITarget());
            }
        }
	    else if(rand.nextInt(this.ticksBetweenIdleTeleports) == 0)
	    {
	    	this.teleportRandomly();
	    }
	    
	    if (!this.worldObj.isRemote && this.isEntityAlive())
        {
            if (this.getAITarget() != null)
            {
                if (this.getAITarget() instanceof EntityMob)
                {
                    if (this.getAITarget().getDistanceSqToEntity(this) < 16.0D)
                    {
                        this.teleportRandomly();
                    }
                    
                    this.teleportDelay = 0;
                }
                else if (this.getAITarget().getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && this.teleportToEntity(this.getAITarget()))
                {
                    this.teleportDelay = 0;
                }
            }
            else
            {           
                this.teleportDelay = 0;
            }
        }
	   	
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource src, float amnt)
    {
        if (this.isEntityInvulnerable(src))
        {
            return false;
        }
        else
        {
        	
            if (src instanceof EntityDamageSourceIndirect)
            {
                for (int i = 0; i < 32; ++i)
                {
                    if (this.teleportRandomly())
                    {
                        return true;
                    }
                }

                return super.attackEntityFrom(src, amnt);
            }
            else
            {
            	if(rand.nextInt(this.chanceToTeleportWhenHurt) == 0 || (this.getAITarget() != null && rand.nextBoolean()))
            	{
            		this.teleportRandomly();
            	}
            		
                return super.attackEntityFrom(src, amnt);
            }
        }
    }

	/**
     * Teleport the enderman to another entity
     */
    protected boolean teleportToEntity(Entity p_70816_1_)
    {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.xCoord * d0;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.yCoord * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.zCoord * d0;
        return this.teleportTo(d1, d2, d3);
    }

    /**
     * Teleport the enderman
     */
    private boolean teleportTo(double x, double y, double z)
    {
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if(!this.canTeleport || net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) 
        {
        	return false;
        }
        boolean flag = this.teleportTo_(event.getTargetX(), event.getTargetY(), event.getTargetZ());

        if (flag)
        {
            this.worldObj.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.entity_endermen_teleport, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.entity_endermen_teleport, 1.0F, 1.0F);
        }

        return flag;
    }
	
	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
