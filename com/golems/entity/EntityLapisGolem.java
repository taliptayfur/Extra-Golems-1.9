package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityLapisGolem extends GolemBase 
{			
	public static final String ALLOW_SPECIAL = "Allow Special: Potion Effects";
	
	private Potion[] badEffects = 
		{MobEffects.blindness,MobEffects.moveSlowdown,MobEffects.poison,
		 MobEffects.weakness,MobEffects.wither,MobEffects.unluck};

	public EntityLapisGolem(World world) 
	{
		super(world, Config.LAPIS.getBaseAttack(), Blocks.lapis_block);
	}

	@Override
	protected ResourceLocation applyTexture()
	{
		return this.makeGolemTexture("lapis");
	}
	
	/** Attack by adding potion effect as well */
	@Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
		if(super.attackEntityAsMob(entityIn) && entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase)entityIn;
			if(Config.LAPIS.getBoolean(ALLOW_SPECIAL))
			{
				Potion potionID = entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD ? MobEffects.heal : badEffects[rand.nextInt(badEffects.length)];
				entity.addPotionEffect(new PotionEffect(potionID, 20 * (3 + rand.nextInt(10)), 1 + rand.nextInt(3)));
			}
			return true;
		}
        return false;
    }
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Config.LAPIS.getMaxHealth());
	  	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 8 + this.rand.nextInt(10) + lootingLevel * 4;
		this.addGuaranteedDropEntry(dropList, new ItemStack(Items.dye, size, 4));
		this.addDropEntry(dropList, Items.gold_ingot, 0, 1, 1 + lootingLevel, 8 + lootingLevel * 30);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_stone_step;
	}
}
