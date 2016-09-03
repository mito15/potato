package com.mito.potatobag.common.item;

import com.mito.potatobag.client.BB_Key;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemUsePacketProcessor implements IMessage, IMessageHandler<ItemUsePacketProcessor, IMessage> {

	public int slot;
	public BB_Key key;
	public boolean wrap = false;
	public int id = -1;
	public Vec3 vec;

	public ItemUsePacketProcessor() {
	}

	public ItemUsePacketProcessor(BB_Key keyPressed, int slot, Vec3 v) {
		this.key = keyPressed;
		this.slot = slot;
		this.vec = v;
	}

	@Override
	public IMessage onMessage(ItemUsePacketProcessor message, MessageContext ctx) {
		ItemStack itemstack = null;
		if (message.slot > -1 && message.slot < 9) {
			itemstack = ctx.getServerHandler().playerEntity.inventory.getStackInSlot(message.slot);
		}
		World world = ctx.getServerHandler().playerEntity.worldObj;
		if (itemstack != null) {
			if (itemstack.getItem() != null && itemstack.getItem() instanceof ItemBlockSetter) {
				ItemBlockSetter item = (ItemBlockSetter) itemstack.getItem();
				item.RightClick(itemstack, ctx.getServerHandler().playerEntity.worldObj, ctx.getServerHandler().playerEntity, message.vec, message.key, true);
			}
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.key = new BB_Key(buf.readByte());
		this.slot = buf.readInt();
		double d1 = buf.readDouble();
		double d2 = buf.readDouble();
		double d3 = buf.readDouble();
		this.vec = Vec3.createVectorHelper(d1, d2, d3);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.key.ikey);
		buf.writeInt(this.slot);
		buf.writeDouble(this.vec.xCoord);
		buf.writeDouble(this.vec.yCoord);
		buf.writeDouble(this.vec.zCoord);
	}

}
