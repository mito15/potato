package com.mito.potatobag.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.mito.potatobag.common.mitoCommonProxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class mitoClientProxy extends mitoCommonProxy {

	BraceHighLightHandler bh;
	private BB_KeyBinding key_ctrl;
	private BB_KeyBinding key_alt;
	private BB_KeyBinding key_shift;

	public mitoClientProxy() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	public BB_Key getKey() {
		return new BB_Key(this.isControlKeyDown(), this.isShiftKeyDown(), this.isAltKeyDown());
	}

	public boolean isControlKeyDown() {
		return Keyboard.isKeyDown(key_ctrl.overrideKeyCode);
	}

	public boolean haswheel() {
		return Mouse.hasWheel();
	}

	public int getwheel() {
		return Mouse.getDWheel();
	}

	public boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(key_shift.overrideKeyCode);
	}

	public boolean isAltKeyDown() {
		return Keyboard.isKeyDown(key_alt.overrideKeyCode);
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void preInit() {
		super.preInit();
		//mitoLogger.info("on pre initializing");

		this.bh = new BraceHighLightHandler(this);
		MinecraftForge.EVENT_BUS.register(bh);
		FMLCommonHandler.instance().bus().register(bh);
	}

	@Override
	public void init() {
		super.init();

		this.key_shift = new BB_KeyBinding("key1", Keyboard.KEY_LSHIFT, "Block Place Machine");
		this.key_alt = new BB_KeyBinding("key2", Keyboard.KEY_LMENU, "Block Place Machine");
		this.key_ctrl = new BB_KeyBinding("key3", Keyboard.KEY_LCONTROL, "Block Place Machine");
	}

	public void upkey(int count) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP player = mc.thePlayer;
		if (player != null && !mc.isGamePaused() && mc.inGameHasFocus && mc.currentScreen == null) {
			//処理とか
		}
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public void playSound(ResourceLocation rl, float vol, float pitch, float x, float y, float z) {
		Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(rl, vol, pitch, x, y, z));
	}

	@Override
	public void addDiggingEffect(World world, Vec3 center, double d0, double d1, double d2, Block block, int color) {
		Minecraft.getMinecraft().effectRenderer.addEffect((new EntityDiggingFX(world, d0, d1, d2, d0 - center.xCoord, d1 - center.yCoord, d2 - center.zCoord, block, color))
				.applyColourMultiplier((int) center.xCoord, (int) center.yCoord, (int) center.zCoord));
	}
}
