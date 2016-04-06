package com.golems.entity;

import java.util.List;

import com.golems.content.ContainerPortableWorkbench;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityCraftingGolem extends GolemBase
{
	public EntityCraftingGolem(World world) 
	{
		super(world, 2.0F, Blocks.crafting_table);
	}

	@Override
	protected void applyAttributes() 
	{
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
	  	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.29D);
	}

	@Override
	protected void applyTexture() 
	{
		this.setTextureType(this.getGolemTexture("crafting"));
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand, ItemStack itemstack)
	{
		if(!player.worldObj.isRemote && itemstack == null)
		{
			// display crafting grid for player
			player.displayGui(new EntityCraftingGolem.InterfaceCraftingGrid(player.worldObj, player.playerLocation));
			player.addStat(StatList.craftingTableInteraction);
			player.swingArm(hand);
		}
		
		return super.processInteract(player, hand, itemstack);
	}

	@Override
	public void addGolemDrops(List<WeightedRandomChestContent> dropList, boolean recentlyHit, int lootingLevel) 
	{
		GolemBase.addGuaranteedDropEntry(dropList, new ItemStack(Blocks.crafting_table, 1 + rand.nextInt(2)));
		GolemBase.addDropEntry(dropList, Blocks.planks, 0, 1, 6, 70 + lootingLevel * 10);
	}

	@Override
	public SoundEvent getGolemSound() 
	{
		return SoundEvents.block_wood_step;
	}

	public static class InterfaceCraftingGrid extends net.minecraft.block.BlockWorkbench.InterfaceCraftingTable
    {
        private final World world2;
        private final BlockPos position2;

        public InterfaceCraftingGrid(World worldIn, BlockPos pos)
        {
            super(worldIn, pos);
            this.world2 = worldIn;
            this.position2 = pos;
        }

        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
        {
            return new ContainerPortableWorkbench(playerInventory, this.world2, this.position2);
        }
    }
}
