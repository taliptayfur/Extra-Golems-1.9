package com.golems.proxies;

import com.golems.entity.EntityBedrockGolem;
import com.golems.entity.EntityBookshelfGolem;
import com.golems.entity.EntityClayGolem;
import com.golems.entity.EntityCoalGolem;
import com.golems.entity.EntityCraftingGolem;
import com.golems.entity.EntityDiamondGolem;
import com.golems.entity.EntityEmeraldGolem;
import com.golems.entity.EntityEndstoneGolem;
import com.golems.entity.EntityGlassGolem;
import com.golems.entity.EntityGlowstoneGolem;
import com.golems.entity.EntityGoldGolem;
import com.golems.entity.EntityHardenedClayGolem;
import com.golems.entity.EntityIceGolem;
import com.golems.entity.EntityLapisGolem;
import com.golems.entity.EntityLeafGolem;
import com.golems.entity.EntityMelonGolem;
import com.golems.entity.EntityMushroomGolem;
import com.golems.entity.EntityNetherBrickGolem;
import com.golems.entity.EntityObsidianGolem;
import com.golems.entity.EntityPrismarineGolem;
import com.golems.entity.EntityQuartzGolem;
import com.golems.entity.EntityRedSandstoneGolem;
import com.golems.entity.EntityRedstoneGolem;
import com.golems.entity.EntitySandstoneGolem;
import com.golems.entity.EntitySeaLanternGolem;
import com.golems.entity.EntitySlimeGolem;
import com.golems.entity.EntitySpongeGolem;
import com.golems.entity.EntityStainedClayGolem;
import com.golems.entity.EntityStainedGlassGolem;
import com.golems.entity.EntityStrawGolem;
import com.golems.entity.EntityTNTGolem;
import com.golems.entity.EntityWoodenGolem;
import com.golems.entity.EntityWoolGolem;
import com.golems.entity.GolemBase;
import com.golems.entity.GolemColorized;
import com.golems.main.GolemItems;
import com.golems.renders.RenderColoredGolem;
import com.golems.renders.RenderGolem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy 
{	
	@Override
	public void registerBlockRenders()
	{
		// get the item model mesher
		ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		// manually add golem head using pumpkin models
		renderItem.register(GolemItems.itemGolemHead, 0, new ModelResourceLocation("minecraft:pumpkin", "inventory"));
		registerItemModel(renderItem, GolemItems.itemLightSourceFull);
		registerItemModel(renderItem, GolemItems.itemLightSourceHalf);
		registerItemModel(renderItem, GolemItems.itemPowerSource);
		//Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().registerBlockWithStateMapper(GolemItems.blockPowerSource, (new StateMap.Builder()).ignore(new IProperty[] {BlockPowerProvider.POWER}).build());
	}

	@Override
	public void registerItemRenders()
	{		
		ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		registerItemModel(renderItem, GolemItems.golemPaper);	
		registerItemModel(renderItem, GolemItems.spawnBedrockGolem);
	}

	@Override
	public void registerEntityRenders()
	{
		// register entity renders by calling a helper function
		register(EntityBedrockGolem.class);
		register(EntityBookshelfGolem.class);
		register(EntityClayGolem.class);
		register(EntityCoalGolem.class);
		register(EntityCraftingGolem.class);
		register(EntityDiamondGolem.class);
		register(EntityEmeraldGolem.class);
		register(EntityEndstoneGolem.class);
		register(EntityGlassGolem.class);
		register(EntityGlowstoneGolem.class);
		register(EntityGoldGolem.class);
		register(EntityHardenedClayGolem.class);
		register(EntityIceGolem.class);
		register(EntityLapisGolem.class);
		registerColorized(EntityLeafGolem.class);
		register(EntityMelonGolem.class);
		register(EntityMushroomGolem.class);
		register(EntityNetherBrickGolem.class);
		register(EntityObsidianGolem.class);
		register(EntityPrismarineGolem.class);
		register(EntityQuartzGolem.class);
		register(EntityRedSandstoneGolem.class);
		register(EntityRedstoneGolem.class);
		register(EntitySandstoneGolem.class);
		register(EntitySeaLanternGolem.class);
		register(EntitySlimeGolem.class);
		register(EntitySpongeGolem.class);
		registerColorized(EntityStainedClayGolem.class);
		registerColorized(EntityStainedGlassGolem.class);
		register(EntityStrawGolem.class);
		register(EntityTNTGolem.class);
		register(EntityWoodenGolem.class);
		register(EntityWoolGolem.class);	
	}

	/**	Registers an entity with the RenderGolem rendering class */
	public static void register(Class<? extends GolemBase> golem)
	{
		RenderingRegistry.registerEntityRenderingHandler(golem, new IRenderFactory<GolemBase>() 
		{
			@Override
			public Render<? super GolemBase> createRenderFor(RenderManager manager) 
			{
				return new RenderGolem(manager);
			}
		});
	}
	
	public static void registerColorized(Class<? extends GolemColorized> golem)
	{
		RenderingRegistry.registerEntityRenderingHandler(golem, new IRenderFactory<GolemColorized>() 
		{
			@Override
			public Render<? super GolemColorized> createRenderFor(RenderManager manager) 
			{
				return new RenderColoredGolem(manager);
			}
		});
	}
	
	private void registerItemModel(ItemModelMesher renderItem, Item item)
	{
		renderItem.register(item, 0, 
				new ModelResourceLocation(
						item.getRegistryName()
						.toString(), "inventory"));
	}
}
