package com.mito.potatobag.common;

import com.mito.potatobag.common.item.itemGUI.ContainerItemBlockSetter;
import com.mito.potatobag.common.item.itemGUI.GuiItemBlockSetter;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Main.GUI_ID_BBSetter) {
			return new ContainerItemBlockSetter(player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Main.GUI_ID_BBSetter) {
			return new GuiItemBlockSetter(player.inventory);
		}
		return null;
	}

}
