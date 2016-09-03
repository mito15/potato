package com.mito.potatobag.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class RenderHighLight {

	public static RenderHighLight INSTANCE = new RenderHighLight();


	public void drawBox(EntityPlayer player, Vec3 set, double size, double partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(-(player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks),
				-(player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks),
				-(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks));
		GL11.glTranslated(set.xCoord, set.yCoord, set.zCoord);
		this.renderBox(size);
		GL11.glPopMatrix();
	}

	public void drawBox(EntityPlayer player, Vec3 set, Vec3 end, double partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(-(player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks),
				-(player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks),
				-(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks));
		this.renderBox(set, end);
		GL11.glPopMatrix();
	}

	public void renderBox(double s, boolean b) {
		double size = s / 2.0;
		this.renderBox(size, size, size, b);
	}

	public void renderBox(double s) {
		double size = s / 2.0;
		this.renderBox(size, size, size);
	}

	private void renderBox(Vec3 set, Vec3 end) {
		GL11.glTranslated((set.xCoord + end.xCoord) / 2, (set.yCoord + end.yCoord) / 2, (set.zCoord + end.zCoord) / 2);

		this.renderBox(Math.abs((set.xCoord - end.xCoord)) / 2 + 0.01, Math.abs((set.yCoord - end.yCoord)) / 2 + 0.01, Math.abs((set.zCoord - end.zCoord)) / 2 + 0.01, false);
	}

	private void renderBox(double x, double y, double z) {
		renderBox(x, y, z, true);
	}

	private void renderBox(double x, double y, double z, boolean b) {

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		if (b) {
			GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ZERO);
		}
		GL11.glLineWidth(2.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBegin(1);

		GL11.glVertex3d(x, y, z);
		GL11.glVertex3d(x, -y, z);
		GL11.glVertex3d(x, y, -z);
		GL11.glVertex3d(x, -y, -z);
		GL11.glVertex3d(-x, y, z);
		GL11.glVertex3d(-x, -y, z);
		GL11.glVertex3d(-x, y, -z);
		GL11.glVertex3d(-x, -y, -z);

		GL11.glVertex3d(x, y, z);
		GL11.glVertex3d(x, y, -z);
		GL11.glVertex3d(x, -y, z);
		GL11.glVertex3d(x, -y, -z);
		GL11.glVertex3d(-x, y, z);
		GL11.glVertex3d(-x, y, -z);
		GL11.glVertex3d(-x, -y, z);
		GL11.glVertex3d(-x, -y, -z);

		GL11.glVertex3d(x, y, z);
		GL11.glVertex3d(-x, y, z);
		GL11.glVertex3d(x, y, -z);
		GL11.glVertex3d(-x, y, -z);
		GL11.glVertex3d(x, -y, z);
		GL11.glVertex3d(-x, -y, z);
		GL11.glVertex3d(x, -y, -z);
		GL11.glVertex3d(-x, -y, -z);

		GL11.glEnd();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

	}
}
