package com.golems.main;

import com.golems.events.GolemClientEventHandler;
import com.golems.events.GolemCommonEventHandler;
import com.golems.proxies.CommonProxy;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = ExtraGolems.MODID, name = ExtraGolems.NAME, version = ExtraGolems.VERSION, acceptedMinecraftVersions = ExtraGolems.MCVERSION)
public class ExtraGolems 
{	
	public static final String MODID = "golems";
	public static final String NAME = "Extra Golems";
	public static final String VERSION = "4.01";
	public static final String MCVERSION = "1.9";
	public static final String CLIENT = "com." + MODID + ".proxies.ClientProxy";
	public static final String SERVER = "com." + MODID + ".proxies.CommonProxy";
	
	@SidedProxy(clientSide = ExtraGolems.CLIENT, serverSide = ExtraGolems.SERVER)
	public static CommonProxy proxy;
	
	@Mod.Instance(ExtraGolems.MODID)
	public static ExtraGolems instance;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) 
	{	
		Config.mainRegistry(new Configuration(event.getSuggestedConfigurationFile()));
		ContentInit.mainRegistry();
		GolemEntityRegister.mainRegistry();
		proxy.registerEntityRenders();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) 
	{		
		registerCrafting();
		MinecraftForge.EVENT_BUS.register(new GolemCommonEventHandler());	// register event handler
		if(event.getSide() == Side.CLIENT)
		{
			MinecraftForge.EVENT_BUS.register(new GolemClientEventHandler());
			proxy.registerBlockRenders();
			proxy.registerItemRenders();
		}
	}
	
	public static void registerCrafting()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ContentInit.golemPaper, 1), Items.feather,Items.redstone,new ItemStack(Items.dye, 1, 0),Items.paper);
		GameRegistry.addShapelessRecipe(new ItemStack(ContentInit.golemHead, 1), ContentInit.golemPaper,Blocks.pumpkin);
	}
}

