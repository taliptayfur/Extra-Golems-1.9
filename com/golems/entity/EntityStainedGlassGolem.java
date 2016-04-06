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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityStainedGlassGolem extends GolemColorizedMultiTextured
{
	private static final ResourceLocation TEXTURE_BASE = GolemBase.getGolemTexture("stained_glass");
	private static final ResourceLocation TEXTURE_OVERLAY = GolemBase.getGolemTexture("stained_glass_grayscale");
	
	public EntityStainedGlassGolem(World world)
	{
		super(world, 12.0F, Blocks.stained_glass, TEXTURE_BASE, TEXTURE_OVERLAY, ItemDye.dyeColors);
		this.setCanTakeFallDamage(true);
	}
	
	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(9.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	}
	
	/**
     * Whether {@link overlay} should be rendered as transparent.
     * Is not called for rendering {@link base}
     **/
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasTransparency()
    {
    	return true;
    }
	
	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int meta = Config.TWEAK_STAINED_GLASS < 0 ? 15 - this.getTextureNum() : Config.TWEAK_STAINED_GLASS;
		int size = lootingLevel + rand.nextInt(3 + lootingLevel);
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.stained_glass, size > 4 ? 4 : size, meta));
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_glass_step;
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.block_glass_break;
	}
}
