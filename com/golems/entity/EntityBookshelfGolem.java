package com.golems.entity;

import java.util.List;

import com.golems.main.Config;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityBookshelfGolem extends GolemBase 
{		
	private Potion[] goodEffects = 
		{MobEffects.fireResistance,MobEffects.regeneration,MobEffects.damageBoost,MobEffects.absorption,MobEffects.luck,
		 MobEffects.heal,MobEffects.resistance,MobEffects.invisibility,MobEffects.moveSpeed,MobEffects.healthBoost};
	
	public EntityBookshelfGolem(World world) 
	{
		super(world, 1.5F, Blocks.bookshelf);
	}
	
	protected void applyTexture()
	{
		this.setTextureType(this.getGolemTexture("books"));
	}
	
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
	    super.onLivingUpdate();

	    // Potion effects:  for this golem only
	    if(Config.ALLOW_BOOKSHELF_SPECIAL && this.getActivePotionEffects().isEmpty() && rand.nextInt(40) == 0)
	    {
	    	this.addPotionEffect(new PotionEffect(goodEffects[rand.nextInt(goodEffects.length)], 200 + 100 * (1 + rand.nextInt(5)), 1));
	    }
	}
		
	@Override
	protected void applyAttributes() 
	{
	 	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(28.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.29D);
	}
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 4 + this.rand.nextInt(6 + lootingLevel);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Items.book, size));
		GolemBase.addDropEntry(dropList, Blocks.planks, 0, 3, 12, 75);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_wood_step;
	}
}
