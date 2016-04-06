package com.golems.content;

import java.util.List;

import com.golems.entity.EntityBedrockGolem;
import com.golems.entity.GolemBase;
import com.golems.main.Config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBedrockGolem extends Item 
{
	public ItemBedrockGolem()
	{
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		// creative players can use this item to spawn a bedrock golem
		if(Config.ALLOW_BEDROCK_GOLEM && !worldIn.isRemote && playerIn.capabilities.isCreativeMode) 
		{
			GolemBase golem = new EntityBedrockGolem(worldIn);

			if (facing == EnumFacing.DOWN)
	        {
	            return EnumActionResult.FAIL;
	        }
	        else
	        {
	            boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
	            BlockPos blockpos1 = flag ? pos : pos.offset(facing);
	        }
			
			golem.setPlayerCreated(true);
			golem.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.up(1).getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
			worldIn.spawnEntityInWorld(golem);

			playerIn.swingArm(hand);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		String loreCreativeOnly = TextFormatting.RED + trans("tooltip.creative_only_item"); 
		par3List.add(loreCreativeOnly);

		if(GuiScreen.isShiftKeyDown())
		{
			par3List.add(I18n.translateToLocalFormatted("tooltip.use_to_spawn", new Object[] {trans("entity.golems.golem_bedrock.name")}));
			par3List.add(I18n.translateToLocalFormatted("tooltip.use_on_existing", new Object[] {trans("entity.golems.golem_bedrock.name")}));
			par3List.add(trans("tooltip.to_remove_it") + ".");
		}
		else
		{	
			String lorePressShift =
					TextFormatting.GRAY + trans("tooltip.press") + " " + 
					TextFormatting.YELLOW + trans("tooltip.shift") + " " + 
					TextFormatting.GRAY + trans("tooltip.for_more_details");
			par3List.add(lorePressShift);
		}
	}
	
	private String trans(String s)
	{
		return I18n.translateToLocal(s);
	}
}
