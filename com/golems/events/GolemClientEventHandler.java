package com.golems.events;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GolemClientEventHandler 
{
	@SubscribeEvent
	public void onAddInfo(GolemPaperAddInfoEvent event)
	{
		// debug:
		//event.infoList.add("test");
	}
}
