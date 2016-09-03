package com.mito.potatobag.common;

import com.mito.potatobag.common.item.ItemUsePacketProcessor;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

	public static int nex = -1;
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("mito_potato_place_machine");


    public static void init() {

    	INSTANCE.registerMessage(ItemUsePacketProcessor.class, ItemUsePacketProcessor.class, nex++, Side.SERVER);
    }

}
