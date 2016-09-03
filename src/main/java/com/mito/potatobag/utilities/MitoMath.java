package com.mito.potatobag.utilities;

import net.minecraft.util.Vec3;

public final class MitoMath {

	public static Vec3 vectorRatio(Vec3 set, Vec3 end, double r) {
		Vec3 ret = vectorPul(vectorMul(end, r), vectorMul(set, 1 - r));
		return ret;
	}

	public static double[] crossProduct(double[] d, double[] m) {
		double[] ret = { d[1] * m[2] - d[2] * m[1], d[2] * m[0] - d[0] * m[2], d[0] * m[1] - d[1] * m[0] };
		return ret;
	}

	public static double subAbs(double[] v1, double[] v2) {
		double ret = MitoMath.abs(v1[0] - v2[0], v1[1] - v2[1], v1[2] - v2[2]);
		return ret;
	}

	public static double subAbs(double[] v1, Vec3 v2) {
		double ret = MitoMath.abs(v1[0] - v2.xCoord, v1[1] - v2.yCoord, v1[2] - v2.zCoord);
		return ret;
	}

	public static double subAbs(Vec3 v1, Vec3 v2) {
		double ret = MitoMath.abs(v1.xCoord - v2.xCoord, v1.yCoord - v2.yCoord, v1.zCoord - v2.zCoord);
		return ret;
	}

	public static double subAbs2(Vec3 v1, Vec3 v2) {
		double ret = MitoMath.abs2(v1.xCoord - v2.xCoord, v1.yCoord - v2.yCoord, v1.zCoord - v2.zCoord);
		return ret;
	}

	public static Vec3 vectorMul(Vec3 d, double m) {
		Vec3 ret = Vec3.createVectorHelper(d.xCoord * m, d.yCoord * m, d.zCoord * m);
		return ret;
	}

	public static Vec3 vectorDiv(Vec3 d, double m) {

		if (m == 0) {
			return d;
		}

		Vec3 ret = Vec3.createVectorHelper(d.xCoord / m, d.yCoord / m, d.zCoord / m);
		return ret;
	}

	public static Vec3 vectorPul(Vec3... d){
	double x = 0;
	double y = 0;
	double z = 0;

	for (int n = 0; n < d.length; n++) {
			x = x + d[n].xCoord;
			y = y + d[n].yCoord;
			z = z + d[n].zCoord;
		}

		Vec3 ret = Vec3.createVectorHelper(x, y, z);
		return ret;
	}

	public static Vec3 vectorSub(Vec3 d1, Vec3 d2) {
		if (d1 == null || d2 == null) {
			return Vec3.createVectorHelper(0, 0, 0);
		}
		Vec3 ret = Vec3.createVectorHelper(d1.xCoord - d2.xCoord, d1.yCoord - d2.yCoord, d1.zCoord - d2.zCoord);
		return ret;
	}

	public static Vec3 vectorBezier(Vec3 d1, Vec3 d2, Vec3 d3, Vec3 d4, double r) {
		Vec3 ret = vectorPul(vectorMul(d1, Math.pow(r, 3)), vectorMul(d2, 3 * Math.pow(r, 2) * (1 - r)), vectorMul(d3, 3 * Math.pow((1 - r), 2) * r), vectorMul(d4, Math.pow((1 - r), 3)));
		return ret;
	}

	public static Vec3 normalBezier(Vec3 d1, Vec3 d2, Vec3 d3, Vec3 d4, double r) {
		Vec3 ret = vectorPul(vectorMul(d1, 3 * Math.pow(r, 2)), vectorMul(d2, 3 * r * (2 - 3 * r)), vectorMul(d3, 3 * (3 * r - 1) * (r - 1)), vectorMul(d4, -3 * Math.pow((1 - r), 2)));
		return ret.normalize();
	}

	public static Vec3 normalBezier(Vec3 d1, Vec3 d2, Vec3 d3, double r) {
		Vec3 ret = vectorPul(vectorMul(d1, 2 * r), vectorMul(d2, -(4 * r) + 2), vectorMul(d3, 2 * r - 2));
		return ret.normalize();
	}

	public static double abs(double x, double y, double z) {
		double ret = Math.sqrt(x * x + y * y + z * z);

		return ret;
	}

	public static double abs2(double x, double y, double z) {
		double ret = x * x + y * y + z * z;

		return ret;
	}

	public static Vec3 copyVec3(Vec3 v) {
		return Vec3.createVectorHelper(v.xCoord, v.yCoord, v.zCoord);
	}

	public static double abs(Vec3 v) {
		double ret = Math.sqrt(v.xCoord * v.xCoord + v.yCoord * v.yCoord + v.zCoord * v.zCoord);

		return ret;
	}

	public static double abs2(Vec3 v) {
		double ret = Math.pow(v.xCoord, 2) + Math.pow(v.yCoord, 2) + Math.pow(v.zCoord, 2);

		return ret;
	}

	public static Vec3 unitVector(Vec3 v) {
		Vec3 ret = vectorDiv(v, abs(v));

		return ret;
	}

	public static double setLimExp(double x, double max) {
		double ret;

		if (x > 0) {

			ret = max - max * Math.exp(-x / max);

		} else {

			ret = -max + max * Math.exp(x / max);

		}

		return ret;
	}

	public static Vec3 getNearPoint(Vec3 s, Vec3 e, Vec3 p) {
		Vec3 ret;

		double d1 = abs2(vectorSub(s, p));
		double d2 = abs2(vectorSub(e, p));
		double l = abs2(vectorSub(s, e));

		double k = (d1 - d2 + l) / (2 * l);
		k = k >= 1 ? 1 : (k <= 0 ? 0 : k);
		ret = vectorPul(vectorMul(vectorSub(e, s), k), s);

		return ret;
	}

	public static double distancePointPlane(Vec3 plane, Vec3 normal, Vec3 p) {
		return normal.dotProduct(vectorSub(plane, p));
	}

	public static double distancePointPlane(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4) {
		return distancePointPlane(v1, v2.crossProduct(v3).normalize(), v4);
	}

	public static Vec3 getIntersectPlaneLine(Vec3 plane, Vec3 normal, Vec3 s, Vec3 e) {
		double ls = normal.dotProduct(vectorSub(s, plane));
		double le = normal.dotProduct(vectorSub(e, plane));
		double r = ls / (ls + le);
		if (r < 0 || r > 1) {
			return null;
		}
		Vec3 l = vectorSub(e, s);
		Vec3 ret = vectorPul(vectorMul(l, r), s);

		return ret;
	}

	public static boolean onLine(Vec3 p, Vec3 set, Vec3 end) {
		return subAbs2(getNearPoint(set, end, p), p) < 0.001;
	}

	public static Vec3 crossX(Vec3 side1) {
		return Vec3.createVectorHelper(0, side1.zCoord, -side1.yCoord);
	}

	public static Vec3 crossY(Vec3 side1) {
		return Vec3.createVectorHelper(-side1.zCoord, 0, side1.xCoord);
	}

	public static Vec3 crossZ(Vec3 side1) {
		return Vec3.createVectorHelper(side1.yCoord, -side1.xCoord, 0);
	}

	public static Vec3 rot(Vec3 v1, double r, double p, double y) {
		return rotY(rotX(rotZ(v1, r), p), y);
	}

	public static Vec3 rotX(Vec3 v, double t) {
		double rot = t * 2 * Math.PI / 360;
		double d1 = v.yCoord * Math.cos(rot) - v.zCoord * Math.sin(rot);
		double d2 = v.yCoord * Math.sin(rot) + v.zCoord * Math.cos(rot);
		return Vec3.createVectorHelper(v.xCoord, d1, d2);
	}

	public static Vec3 rotY(Vec3 v, double t) {
		double rot = t * 2 * Math.PI / 360;
		double d1 = v.xCoord * Math.cos(rot) + v.zCoord * Math.sin(rot);
		double d2 = -v.xCoord * Math.sin(rot) + v.zCoord * Math.cos(rot);
		return Vec3.createVectorHelper(d1, v.yCoord, d2);
	}

	public static Vec3 rotZ(Vec3 v, double t) {
		double rot = t * 2 * Math.PI / 360;
		double d1 = v.xCoord * Math.cos(rot) - v.yCoord * Math.sin(rot);
		double d2 = v.xCoord * Math.sin(rot) + v.yCoord * Math.cos(rot);
		return Vec3.createVectorHelper(d1, d2, v.zCoord);
	}

	public static double getYaw(Vec3 v) {

		if (v.xCoord == 0 && v.zCoord == 0) {
			return 0;
		}
		double yaw;
		double zabs = v.zCoord;
		yaw = Math.atan(v.xCoord / zabs) / Math.PI * 180;
		if (0 > v.zCoord) {
			if (0 < v.xCoord) {
				yaw = yaw + 180;
			} else {
				yaw = yaw - 180;
			}
		}
		if (Double.isNaN(yaw)) {
			return 0.0;
		}
		return yaw;
	}

	public static double getYaw(Vec3 v1, Vec3 v2) {
		return getYaw(vectorSub(v2, v1));
	}

	public static double getPitch(Vec3 v) {
		double pitch;
		double xzabs = Math.sqrt(Math.pow(v.xCoord, 2) + Math.pow(v.zCoord, 2));
		pitch = -Math.atan(v.yCoord / xzabs) / Math.PI * 180;
		if (Double.isNaN(pitch)) {
			return 0.0;
		}
		return pitch;
	}

	public static double getPitch(Vec3 v1, Vec3 v2) {
		return getPitch(vectorSub(v2, v1));
	}

	public static Vec3 getNormal(Vec3 v1, Vec3 v2, Vec3 v3) {
		return vectorSub(v2, v1).crossProduct(vectorSub(v3, v1)).normalize();
	}

}
