package com.golems.entity;

import java.util.List;

import com.golems.main.ExtraGolems;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityWoolGolem extends GolemMultiTextured 
{	
	private static final String[] coloredWoolTypes = {"black","orange","magenta","light_blue","yellow","lime","pink","gray","silver","cyan","purple","blue","brown","green","red"};

	public EntityWoolGolem(World world) 
	{
		super(world, 1.0F, Blocks.wool, coloredWoolTypes);
		this.tasks.addTask(0, new EntityAISwimming(this));
		((PathNavigateGround)this.getNavigator()).setCanSwim(true);
	}
	
	@Override
	public String getTexturePrefix() 
	{
		return "wool";
	}
	
	@Override
	public String getModId() 
	{
		return ExtraGolems.MODID;
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel)
	{
		int size = 1 + this.rand.nextInt(3) + lootingLevel;
		int meta = this.getTextureNum() % 16;
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.wool, 1 + rand.nextInt(2), 0));
		GolemBase.addDropEntry(dropList, Blocks.wool, meta, 1, 2, 60 + lootingLevel * 10);
		GolemBase.addDropEntry(dropList, Items.string, 0, 1, 2, 5 + lootingLevel * 10);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_cloth_step;
	}
}
