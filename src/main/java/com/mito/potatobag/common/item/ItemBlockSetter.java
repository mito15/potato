package com.mito.potatobag.common.item;

import java.util.List;

import com.mito.potatobag.client.BB_Key;
import com.mito.potatobag.client.RenderHighLight;
import com.mito.potatobag.common.Main;
import com.mito.potatobag.common.PacketHandler;
import com.mito.potatobag.utilities.MitoMath;
import com.mito.potatobag.utilities.MitoUtil;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemBlockSetter extends Item {

	public ItemBlockSetter() {
		super();
		this.setMaxStackSize(1);
		this.setTextureName("bag:bag");
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}

	public void nbtInit(NBTTagCompound nbt, ItemStack itemstack) {
		nbt.setBoolean("activated", false);
		nbt.setDouble("setX", 0.0D);
		nbt.setDouble("setY", 0.0D);
		nbt.setDouble("setZ", 0.0D);
	}

	public void RightClick(ItemStack itemstack, World world, EntityPlayer player, Vec3 vec, BB_Key key, boolean p_77663_5_) {
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
			this.nbtInit(nbt, itemstack);
		}
		if (!world.isRemote) {
			if (vec != null) {
				if (nbt.getBoolean("activated")) {
					Vec3 end = MitoMath.copyVec3(vec);
					Vec3 set = Vec3.createVectorHelper(nbt.getDouble("setX"), nbt.getDouble("setY"), nbt.getDouble("setZ"));
					this.onActiveClick(world, player, itemstack, set, end, nbt);
					this.nbtInit(nbt, itemstack);
				} else {
					nbt.setDouble("setX", vec.xCoord);
					nbt.setDouble("setY", vec.yCoord);
					nbt.setDouble("setZ", vec.zCoord);
					nbt.setBoolean("activated", true);
				}
			}
		}
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int meta, boolean p_77663_5_) {

		NBTTagCompound nbt = itemstack.getTagCompound();

		if (nbt != null) {
			if (nbt.getBoolean("activated")) {
				if (entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entity;
					if (player.getCurrentEquippedItem() != itemstack) {
						this.nbtInit(nbt, itemstack);
					}
				} else {
					this.nbtInit(nbt, itemstack);
				}
			}
		} else {
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
			this.nbtInit(nbt, itemstack);
		}
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack itemstack, EntityPlayer player) {
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt != null && nbt.getBoolean("activated")) {
			this.nbtInit(nbt, itemstack);
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		NBTTagCompound nbt = getTagCompound(itemStack);
		if (player.isSneaking() && !nbt.getBoolean("activated")) {
			if (!world.isRemote)
				player.openGui(Main.INSTANCE, Main.INSTANCE.GUI_ID_BBSetter, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		} else {
			player.setItemInUse(itemStack, 71999);
		}
		/*
		BB_MovingObjectPosition movingOP = this.getMovingOPWithKey(itemStack, world, player, new BB_Key(false, false, false), 1.0);
		mitoLogger.info("coordinate  x : " + movingOP.hitVec.xCoord + " y : " + movingOP.hitVec.yCoord + " z : " + movingOP.hitVec.zCoord);*/
		return itemStack;
	}

	public MovingObjectPosition getMovingOPWithKey(ItemStack itemstack, World world, EntityPlayer player, BB_Key key, MovingObjectPosition mop, double partialticks) {
		NBTTagCompound nbt = this.getNBT(itemstack);

		if (mop != null) {
			MitoUtil.snapBlockOffset(mop);
		}

		return mop;
	}

	public void onActiveClick(World world, EntityPlayer player, ItemStack itemstack, Vec3 set, Vec3 end, NBTTagCompound nbt) {
		if (!world.isRemote) {
			ItemStack currentItem;
			ItemStack[] items = new ItemStack[54];
			NBTTagList tags = (NBTTagList) itemstack.getTagCompound().getTag("Items");
			if (tags == null)
				tags = new NBTTagList();

			for (int i = 0; i < tags.tagCount(); i++) {
				NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
				int slot = tagCompound.getByte("Slot");
				if (slot >= 0 && slot < items.length) {
					items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
				}
			}
			int slot = 0;

			if (MitoMath.subAbs(set, end) < 100) {
				if (items[slot] != null && items[slot].getItem() == Items.potato) {
					List<int[]> list = MitoUtil.getBlocksOnLine(world, set, end, null, 1.0, true);
					for (int n = 0; n < list.size(); n++) {
						int[] aint = list.get(n);
						world.setBlock(aint[0], aint[1], aint[2], Blocks.air);
					}
					return;
				}
				List<int[]> list = MitoUtil.getBlocksOnLine(world, set, end, null, 0.98);
				for (int n = 0; n < list.size(); n++) {
					int[] aint = list.get(n);
					boolean flag = true;
					while (flag) {
						if (slot > 53) {
							n = list.size();
							break;
						} else
							if (items[slot] == null) {
							slot++;
							continue;
						}
						Block block = Block.getBlockById(Item.getIdFromItem(items[slot].getItem()));
						if (block != null && items[slot].stackSize > 0) {
							world.setBlock(aint[0], aint[1], aint[2], block, items[slot].getItemDamage() % 16, 3);
							if (!player.capabilities.isCreativeMode) {
								items[slot].stackSize--;
								if (items[slot].stackSize == 0) {
									items[slot] = null;
									slot++;
								}
							}
							break;
						} else {
							slot++;
							continue;
						}
					}
				}
			}

			NBTTagList tagList = new NBTTagList();
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					NBTTagCompound compound = new NBTTagCompound();
					compound.setByte("Slot", (byte) i);
					items[i].writeToNBT(compound);
					tagList.appendTag(compound);
				}
			}
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setTag("Items", tagList);
		}
	}

	public boolean drawHighLightBox(ItemStack itemstack, EntityPlayer player, double partialTicks, MovingObjectPosition mop) {
		NBTTagCompound nbt = getTagCompound(itemstack);
		if (mop == null)
			return false;
		Vec3 set = mop.hitVec;

		RenderHighLight rh = RenderHighLight.INSTANCE;
		if (nbt.getBoolean("activated")) {
			Vec3 end = Vec3.createVectorHelper(nbt.getDouble("setX"), nbt.getDouble("setY"), nbt.getDouble("setZ"));
			List<int[]> list = MitoUtil.getBlocksOnLine(player.worldObj, end, set, null, 0.98);
			for (int n = 0; n < list.size(); n++) {
				int[] aint = list.get(n);
				Vec3 v = Vec3.createVectorHelper(0.5 + aint[0], 0.5 + aint[1], 0.5 + aint[2]);
				rh.drawBox(player, v, 0.95, partialTicks);
			}
		} else {
			return false;
		}

		return true;

	}

	public boolean isDamageable() {
		return false;
	}

	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer player, int i) {

		NBTTagCompound nbt = itemstack.getTagCompound();
		if (world.isRemote) {
			MovingObjectPosition mop = this.getMovingOPWithKey(itemstack, world, player, Main.proxy.getKey(), Minecraft.getMinecraft().objectMouseOver, 0);
			PacketHandler.INSTANCE.sendToServer(new ItemUsePacketProcessor(Main.proxy.getKey(), player.inventory.currentItem, mop.hitVec));
		}
		player.clearItemInUse();

	}

	public NBTTagCompound getNBT(ItemStack itemstack) {
		NBTTagCompound nbt = itemstack.getTagCompound();

		if (nbt == null) {
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
			this.nbtInit(nbt, itemstack);
		}

		return nbt;
	}

	public NBTTagCompound getTagCompound(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		return nbt;
	}
}
