package com.golems.main;

import com.golems.content.BlockGolemHead;
import com.golems.content.BlockLightProvider;
import com.golems.content.BlockPowerProvider;
import com.golems.content.ItemBedrockGolem;
import com.golems.content.ItemGolemPaper;
import com.golems.content.TileEntityMovingLightSource;
import com.golems.content.TileEntityMovingPowerSource;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ContentInit 
{
	public static Item golemPaper;				public static final String N_PAPER = "golem_paper";
	public static Item spawnBedrockGolem;		public static final String N_SPAWNER = "spawn_bedrock_golem";
	public static Block golemHead;				public static final String N_HEAD = "golem_head";
	public static Block blockLightSourceFull;	public static final String N_LIGHT_PROVIDER_FULL = "light_provider_full";
	public static Block blockLightSourceHalf;	public static final String N_LIGHT_PROVIDER_HALF = "light_provider_half";
	public static Block blockWaterLightFull;	public static final String N_WATER_LIGHT_FULL = "water_light_provider_full";
	public static Block blockPowerSource;		public static final String N_POWER_PROVIDER = "power_provider_full";
	
	public static void mainRegistry()
	{
		golemPaper = new ItemGolemPaper().setUnlocalizedName(N_PAPER);
		spawnBedrockGolem = new ItemBedrockGolem().setUnlocalizedName(N_SPAWNER);
		golemHead = new BlockGolemHead().setUnlocalizedName(N_HEAD);
		blockLightSourceFull = new BlockLightProvider(1.0F).setUnlocalizedName(N_LIGHT_PROVIDER_FULL);
		blockLightSourceHalf = new BlockLightProvider(0.5F).setUnlocalizedName(N_LIGHT_PROVIDER_HALF);
		blockWaterLightFull = new BlockLightProvider(1.0F).setUnlocalizedName(N_WATER_LIGHT_FULL);
		blockPowerSource = new BlockPowerProvider().setUnlocalizedName(N_POWER_PROVIDER);
		
		GameRegistry.registerTileEntity(TileEntityMovingLightSource.class, ExtraGolems.MODID + ":TileEntityMovingLightSource");
		GameRegistry.registerTileEntity(TileEntityMovingPowerSource.class, ExtraGolems.MODID + ":TileEntityMovingPowerSource");
		
		GameRegistry.registerItem(golemPaper, N_PAPER);
		GameRegistry.registerItem(spawnBedrockGolem, N_SPAWNER);
		GameRegistry.registerBlock(golemHead, N_HEAD);
        GameRegistry.registerBlock(blockLightSourceFull, N_LIGHT_PROVIDER_FULL);
        GameRegistry.registerBlock(blockLightSourceHalf, N_LIGHT_PROVIDER_HALF);
        GameRegistry.registerBlock(blockWaterLightFull, N_WATER_LIGHT_FULL);
        GameRegistry.registerBlock(blockPowerSource, N_POWER_PROVIDER);
	}
}
