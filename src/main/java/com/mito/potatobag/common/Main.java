package com.mito.potatobag.common;

import com.mito.potatobag.common.item.ItemBlockSetter;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {

	public static final String MODID = "mito_potato_place_machine";
	public static final String VERSION = "1.0.0";
	public static boolean debug = false;

	public static Item ItemBlockSetter;

	@Mod.Instance(Main.MODID)
	public static Main INSTANCE;

	@SidedProxy(clientSide = "com.mito.potatobag.client.mitoClientProxy", serverSide = "com.mito.potatobag.common.mitoCommonProxy")
	public static mitoCommonProxy proxy;

	public static final int GUI_ID_BBSetter = 0;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		proxy.preInit();

		ItemBlockSetter = new ItemBlockSetter().setUnlocalizedName("ItemBlockSetter").setCreativeTab(CreativeTabs.tabTools);

		GameRegistry.registerItem(ItemBlockSetter, "ItemBlockSetter");

		PacketHandler.init();
	}

	@EventHandler
	public void Init(FMLInitializationEvent e) {

		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);

		proxy.init();

		//GUIの登録

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		RegisterRecipe();

	}

	public void RegisterRecipe() {
		GameRegistry.addRecipe(new ItemStack(this.ItemBlockSetter),
				"GBG",
				"GCG",
				"GGG",
				'B', Items.potato,
				'C', Blocks.chest,
				'G', Items.gold_ingot);

	}
}
