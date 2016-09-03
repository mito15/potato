package com.mito.potatobag.client;

import com.mito.potatobag.common.item.ItemBlockSetter;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

public class BraceHighLightHandler {

	mitoClientProxy proxy;

	public BraceHighLightHandler(mitoClientProxy p) {

		this.proxy = p;
	}

	@SubscribeEvent
	public void onDrawBlockHighlight(DrawBlockHighlightEvent e) {
		if (e.currentItem != null && (e.currentItem.getItem() instanceof ItemBlockSetter)) {
			ItemBlockSetter itembrace = (ItemBlockSetter) e.currentItem.getItem();
			MovingObjectPosition mop = itembrace.getMovingOPWithKey(e.currentItem, e.player.worldObj, e.player, proxy.getKey(), e.target, e.partialTicks);
			boolean flag = itembrace.drawHighLightBox(e.currentItem, e.player, e.partialTicks, mop);
			if (flag) {
				if (e.isCancelable()) {
					e.setCanceled(true);
				}
			}
		}
	}

}
