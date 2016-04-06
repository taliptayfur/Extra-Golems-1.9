package com.golems.entity;

import java.util.List;

import com.golems.main.ContentInit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBedrockGolem extends GolemBase 
{

	public EntityBedrockGolem(World world) 
	{
		super(world, 32.0F, Blocks.bedrock);
	}

	@Override
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("bedrock"));
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		return super.attackEntityAsMob(entity);
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource p_180431_1_)
    {
        return true;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand, ItemStack itemstack)
	{
		// creative players can "despawn" by using spawnBedrockGolem on this entity
		if(player.capabilities.isCreativeMode)
		{
			if (itemstack != null && itemstack.getItem() == ContentInit.spawnBedrockGolem)
			{		
				player.swingArm(hand);
				if(!this.worldObj.isRemote)
				{
					this.setDead();
				}
			}
		}

		return super.processInteract(player, hand, itemstack);
	}

	@Override
	protected void damageEntity(DamageSource source, float amount) {}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(999.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.24D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel) {}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_place;
	}
}
