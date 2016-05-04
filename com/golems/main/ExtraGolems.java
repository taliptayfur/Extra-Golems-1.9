package com.golems.main;

import com.golems.events.handlers.GolemClientEventHandler;
import com.golems.events.handlers.GolemCommonEventHandler;
import com.golems.proxies.CommonProxy;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(modid = ExtraGolems.MODID, name = ExtraGolems.NAME, version = ExtraGolems.VERSION, acceptedMinecraftVersions = ExtraGolems.MCVERSION)
public class ExtraGolems 
{	
	public static final String MODID = "golems";
	public static final String NAME = "Extra Golems";
	public static final String VERSION = "4.02";
	public static final String MCVERSION = "1.9";
	
	@SidedProxy(clientSide = "com." + MODID + ".proxies.ClientProxy", serverSide = "com." + MODID + ".proxies.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance(ExtraGolems.MODID)
	public static ExtraGolems instance;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) 
	{	
		Config.mainRegistry(new Configuration(event.getSuggestedConfigurationFile()));
		GolemItems.mainRegistry();
		GolemEntityRegister.mainRegistry();
		proxy.registerEntityRenders();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) 
	{		
		registerCrafting();
		MinecraftForge.EVENT_BUS.register(new GolemCommonEventHandler());
		if(event.getSide() == Side.CLIENT)
		{
			MinecraftForge.EVENT_BUS.register(new GolemClientEventHandler());
			proxy.registerBlockRenders();
			proxy.registerItemRenders();
		}
	}
	
	public static void registerCrafting()
	{
		IRecipe recipeGolemPaper = new ShapelessOreRecipe(new ItemStack(GolemItems.golemPaper, 1), Items.feather, Items.redstone, "dyeBlack", Items.paper);
		GameRegistry.addRecipe(recipeGolemPaper);
		GameRegistry.addShapelessRecipe(new ItemStack(GolemItems.golemHead, 1), GolemItems.golemPaper,Blocks.pumpkin);
	}
}

