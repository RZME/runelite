package net.runelite.cache.item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Model extends Renderable
{
	boolean[] field1887 = new boolean[4700];
	boolean[] field1885 = new boolean[4700];
	int[] modelViewportYs = new int[4700];
	int[] modelViewportXs = new int[4700];
	int[] modelViewportZs = new int[4700];
	int[] yViewportBuffer = new int[4700];
	int[] field1839 = new int[4700];
	int[] field1869 = new int[4700];
	int[] field1871 = new int[1600];
	int[][] field1868 = new int[1600][512];
	int[] field1872 = new int[12];
	int[][] field1874 = new int[12][2000];
	int[] field1857 = new int[2000];
	int[] field1863 = new int[2000];
	int[] field1877 = new int[12];
	int[] field1831 = new int[10];
	int[] field1837 = new int[10];
	int[] xViewportBuffer = new int[10];
	static int[] Model_sine;
	static int[] Model_cosine;
	static int[] field1890;
	int verticesCount;
	int[] verticesX;
	int[] verticesY;
	int[] verticesZ;
	int indicesCount;
	int[] indices1;
	int[] indices2;
	int[] indices3;
	int[] field1856;
	int[] field1854;
	int[] field1823;
	byte[] field1838;
	byte[] field1882;
	byte[] field1840;
	short[] field1841;
	byte field1842;
	int field1852;
	int[] field1844;
	int[] field1865;
	int[] field1846;
	public boolean isItemModel;
	int boundsType;
	int bottomY;
	int XYZMag;
	int diameter;
	int radius;
	public int extremeX;
	public int extremeY;
	public int extremeZ;

	static
	{
		Model_sine = Graphics3D.SINE;
		Model_cosine = Graphics3D.COSINE;
		field1890 = Graphics3D.field1933;
	}

	Model()
	{
		this.verticesCount = 0;
		this.indicesCount = 0;
		this.field1842 = 0;
		this.field1852 = 0;
		this.isItemModel = false;
		this.extremeX = -1;
		this.extremeY = -1;
		this.extremeZ = -1;
	}

	public void calculateBoundsCylinder()
	{
		if (this.boundsType != 1)
		{
			this.boundsType = 1;
			super.modelHeight = 0;
			this.bottomY = 0;
			this.XYZMag = 0;

			for (int var1 = 0; var1 < this.verticesCount; ++var1)
			{
				int var2 = this.verticesX[var1];
				int var3 = this.verticesY[var1];
				int var4 = this.verticesZ[var1];
				if (-var3 > super.modelHeight)
				{
					super.modelHeight = -var3;
				}

				if (var3 > this.bottomY)
				{
					this.bottomY = var3;
				}

				int var5 = var2 * var2 + var4 * var4;
				if (var5 > this.XYZMag)
				{
					this.XYZMag = var5;
				}
			}

			this.XYZMag = (int) (Math.sqrt((double) this.XYZMag) + 0.99D);
			this.radius = (int) (Math.sqrt((double) (this.XYZMag * this.XYZMag + super.modelHeight * super.modelHeight)) + 0.99D);
			this.diameter = this.radius + (int) (Math.sqrt((double) (this.XYZMag * this.XYZMag + this.bottomY * this.bottomY)) + 0.99D);
		}
	}


//	void method2759(int var1)
//	{
//		if (this.extremeX == -1)
//		{
//			int var2 = 0;
//			int var3 = 0;
//			int var4 = 0;
//			int var5 = 0;
//			int var6 = 0;
//			int var7 = 0;
//			int var8 = Model_cosine[var1];
//			int var9 = Model_sine[var1];
//
//			for (int var10 = 0; var10 < this.verticesCount; ++var10)
//			{
//				int var11 = Graphics3D.method2795(this.verticesX[var10], this.verticesZ[var10], var8, var9);
//				int var12 = this.verticesY[var10];
//				int var13 = Graphics3D.method2815(this.verticesX[var10], this.verticesZ[var10], var8, var9);
//				if (var11 < var2)
//				{
//					var2 = var11;
//				}
//
//				if (var11 > var5)
//				{
//					var5 = var11;
//				}
//
//				if (var12 < var3)
//				{
//					var3 = var12;
//				}
//
//				if (var12 > var6)
//				{
//					var6 = var12;
//				}
//
//				if (var13 < var4)
//				{
//					var4 = var13;
//				}
//
//				if (var13 > var7)
//				{
//					var7 = var13;
//				}
//			}
//
//			this.centerX = (var5 + var2) / 2;
//			this.centerY = (var6 + var3) / 2;
//			this.centerZ = (var7 + var4) / 2;
//			this.extremeX = (var5 - var2 + 1) / 2;
//			this.extremeY = (var6 - var3 + 1) / 2;
//			this.extremeZ = (var7 - var4 + 1) / 2;
//			if (this.extremeX < 32)
//			{
//				this.extremeX = 32;
//			}
//
//			if (this.extremeZ < 32)
//			{
//				this.extremeZ = 32;
//			}
//
//			if (this.isItemModel)
//			{
//				this.extremeX += 8;
//				this.extremeZ += 8;
//			}
//
//		}
//	}
//
//
//	void resetBounds()
//	{
//		this.boundsType = 0;
//		this.extremeX = -1;
//	}


//   public void method2746(Frames var1, int var2) {
//      if(this.field1847 != null) {
//         if(var2 != -1) {
//            Frame var3 = var1.skeletons[var2];
//            FrameMap var4 = var3.skin;
//            field1881 = 0;
//            field1845 = 0;
//            field1883 = 0;
//
//            for(int var5 = 0; var5 < var3.field1793; ++var5) {
//               int var6 = var3.field1790[var5];
//               this.method2695(var4.types[var6], var4.list[var6], var3.translator_x[var5], var3.translator_y[var5], var3.translator_z[var5]);
//            }
//
//            this.resetBounds();
//         }
//      }
//   }
//
//   public void method2712(Frames var1, int var2, Frames var3, int var4, int[] var5) {
//      if(var2 != -1) {
//         if(var5 != null && var4 != -1) {
//            Frame var6 = var1.skeletons[var2];
//            Frame var7 = var3.skeletons[var4];
//            FrameMap var8 = var6.skin;
//            field1881 = 0;
//            field1845 = 0;
//            field1883 = 0;
//            byte var9 = 0;
//            int var13 = var9 + 1;
//            int var10 = var5[var9];
//
//            int var11;
//            int var12;
//            for(var11 = 0; var11 < var6.field1793; ++var11) {
//               for(var12 = var6.field1790[var11]; var12 > var10; var10 = var5[var13++]) {
//                  ;
//               }
//
//               if(var12 != var10 || var8.types[var12] == 0) {
//                  this.method2695(var8.types[var12], var8.list[var12], var6.translator_x[var11], var6.translator_y[var11], var6.translator_z[var11]);
//               }
//            }
//
//            field1881 = 0;
//            field1845 = 0;
//            field1883 = 0;
//            var9 = 0;
//            var13 = var9 + 1;
//            var10 = var5[var9];
//
//            for(var11 = 0; var11 < var7.field1793; ++var11) {
//               for(var12 = var7.field1790[var11]; var12 > var10; var10 = var5[var13++]) {
//                  ;
//               }
//
//               if(var12 == var10 || var8.types[var12] == 0) {
//                  this.method2695(var8.types[var12], var8.list[var12], var7.translator_x[var11], var7.translator_y[var11], var7.translator_z[var11]);
//               }
//            }
//
//            this.resetBounds();
//         } else {
//            this.method2746(var1, var2);
//         }
//      }
//   }


//	void method2695(int var1, int[] var2, int var3, int var4, int var5)
//	{
//		int var6 = var2.length;
//		int var7;
//		int var8;
//		int var11;
//		int var12;
//		if (var1 == 0)
//		{
//			var7 = 0;
//			field1881 = 0;
//			field1845 = 0;
//			field1883 = 0;
//
//			for (var8 = 0; var8 < var6; ++var8)
//			{
//				int var9 = var2[var8];
//				if (var9 < this.field1847.length)
//				{
//					int[] var10 = this.field1847[var9];
//
//					for (var11 = 0; var11 < var10.length; ++var11)
//					{
//						var12 = var10[var11];
//						field1881 += this.verticesX[var12];
//						field1845 += this.verticesY[var12];
//						field1883 += this.verticesZ[var12];
//						++var7;
//					}
//				}
//			}
//
//			if (var7 > 0)
//			{
//				field1881 = var3 + field1881 / var7;
//				field1845 = var4 + field1845 / var7;
//				field1883 = var5 + field1883 / var7;
//			}
//			else
//			{
//				field1881 = var3;
//				field1845 = var4;
//				field1883 = var5;
//			}
//
//		}
//		else
//		{
//			int[] var18;
//			int var19;
//			if (var1 == 1)
//			{
//				for (var7 = 0; var7 < var6; ++var7)
//				{
//					var8 = var2[var7];
//					if (var8 < this.field1847.length)
//					{
//						var18 = this.field1847[var8];
//
//						for (var19 = 0; var19 < var18.length; ++var19)
//						{
//							var11 = var18[var19];
//							this.verticesX[var11] += var3;
//							this.verticesY[var11] += var4;
//							this.verticesZ[var11] += var5;
//						}
//					}
//				}
//
//			}
//			else if (var1 == 2)
//			{
//				for (var7 = 0; var7 < var6; ++var7)
//				{
//					var8 = var2[var7];
//					if (var8 < this.field1847.length)
//					{
//						var18 = this.field1847[var8];
//
//						for (var19 = 0; var19 < var18.length; ++var19)
//						{
//							var11 = var18[var19];
//							this.verticesX[var11] -= field1881;
//							this.verticesY[var11] -= field1845;
//							this.verticesZ[var11] -= field1883;
//							var12 = (var3 & 255) * 8;
//							int var13 = (var4 & 255) * 8;
//							int var14 = (var5 & 255) * 8;
//							int var15;
//							int var16;
//							int var17;
//							if (var14 != 0)
//							{
//								var15 = Model_sine[var14];
//								var16 = Model_cosine[var14];
//								var17 = var15 * this.verticesY[var11] + var16 * this.verticesX[var11] >> 16;
//								this.verticesY[var11] = var16 * this.verticesY[var11] - var15 * this.verticesX[var11] >> 16;
//								this.verticesX[var11] = var17;
//							}
//
//							if (var12 != 0)
//							{
//								var15 = Model_sine[var12];
//								var16 = Model_cosine[var12];
//								var17 = var16 * this.verticesY[var11] - var15 * this.verticesZ[var11] >> 16;
//								this.verticesZ[var11] = var15 * this.verticesY[var11] + var16 * this.verticesZ[var11] >> 16;
//								this.verticesY[var11] = var17;
//							}
//
//							if (var13 != 0)
//							{
//								var15 = Model_sine[var13];
//								var16 = Model_cosine[var13];
//								var17 = var15 * this.verticesZ[var11] + var16 * this.verticesX[var11] >> 16;
//								this.verticesZ[var11] = var16 * this.verticesZ[var11] - var15 * this.verticesX[var11] >> 16;
//								this.verticesX[var11] = var17;
//							}
//
//							this.verticesX[var11] += field1881;
//							this.verticesY[var11] += field1845;
//							this.verticesZ[var11] += field1883;
//						}
//					}
//				}
//
//			}
//			else if (var1 == 3)
//			{
//				for (var7 = 0; var7 < var6; ++var7)
//				{
//					var8 = var2[var7];
//					if (var8 < this.field1847.length)
//					{
//						var18 = this.field1847[var8];
//
//						for (var19 = 0; var19 < var18.length; ++var19)
//						{
//							var11 = var18[var19];
//							this.verticesX[var11] -= field1881;
//							this.verticesY[var11] -= field1845;
//							this.verticesZ[var11] -= field1883;
//							this.verticesX[var11] = var3 * this.verticesX[var11] / 128;
//							this.verticesY[var11] = var4 * this.verticesY[var11] / 128;
//							this.verticesZ[var11] = var5 * this.verticesZ[var11] / 128;
//							this.verticesX[var11] += field1881;
//							this.verticesY[var11] += field1845;
//							this.verticesZ[var11] += field1883;
//						}
//					}
//				}
//
//			}
//			else if (var1 == 5)
//			{
//				if (this.field1848 != null && this.field1882 != null)
//				{
//					for (var7 = 0; var7 < var6; ++var7)
//					{
//						var8 = var2[var7];
//						if (var8 < this.field1848.length)
//						{
//							var18 = this.field1848[var8];
//
//							for (var19 = 0; var19 < var18.length; ++var19)
//							{
//								var11 = var18[var19];
//								var12 = (this.field1882[var11] & 255) + var3 * 8;
//								if (var12 < 0)
//								{
//									var12 = 0;
//								}
//								else if (var12 > 255)
//								{
//									var12 = 255;
//								}
//
//								this.field1882[var11] = (byte) var12;
//							}
//						}
//					}
//				}
//
//			}
//		}
//	}
//

	public final void rotateAndProject(Graphics3D graphics, int rotation_1, int yRotation, int zRotation, int xRotation, int xOffset, int yOffset, int zOffset)
	{
		field1871[0] = -1;
		// (re?)Calculate magnitude as necessary
		if (this.boundsType != 2 && this.boundsType != 1)
		{
			this.boundsType = 2;
			this.XYZMag = 0;

			for (int var1 = 0; var1 < this.verticesCount; ++var1)
			{
				int x = this.verticesX[var1];
				int y = this.verticesY[var1];
				int z = this.verticesZ[var1];
				int magnitude_squared = x * x + z * z + y * y;
				if (magnitude_squared > this.XYZMag)
				{
					this.XYZMag = magnitude_squared;
				}
			}

			this.XYZMag = (int) (Math.sqrt((double) this.XYZMag) + 0.99D);
			this.radius = this.XYZMag;
			this.diameter = this.XYZMag + this.XYZMag;
		}

		// rotate + perspective transform
		int sinX = Model_sine[xRotation];
		int cosX = Model_cosine[xRotation];
		int zRelatedVariable = sinX * yOffset + cosX * zOffset >> 16;

		for (int i = 0; i < this.verticesCount; ++i)
		{
			int x = this.verticesX[i];
			int y = this.verticesY[i];
			int z = this.verticesZ[i];
			if (zRotation != 0)
			{
				int sinZ = Model_sine[zRotation];
				int cosZ = Model_cosine[zRotation];
				int tmp;
				tmp = y * sinZ + x * cosZ >> 16;
				y = y * cosZ - x * sinZ >> 16;
				x = tmp;
			}

			if (rotation_1 != 0)
			{
				int sinR1 = Model_sine[rotation_1];
				int cosR1 = Model_cosine[rotation_1];
				int tmp;
				tmp = y * cosR1 - z * sinR1 >> 16;
				z = y * sinR1 + z * cosR1 >> 16;
				y = tmp;
			}

			if (yRotation != 0)
			{
				int sinY = Model_sine[yRotation];
				int cosY = Model_cosine[yRotation];
				int tmp;
				tmp = z * sinY + x * cosY >> 16;
				z = z * cosY - x * sinY >> 16;
				x = tmp;
			}

			x += xOffset;
			y += yOffset;
			z += zOffset;
			int tmp = y * cosX - z * sinX >> 16;
			z = y * sinX + z * cosX >> 16;
			modelViewportZs[i] = z - zRelatedVariable;
			modelViewportYs[i] = x * graphics.Rasterizer3D_zoom / z + graphics.centerX;
			modelViewportXs[i] = tmp * graphics.Rasterizer3D_zoom / z + graphics.centerY;
			if (this.field1852 > 0)
			{
				yViewportBuffer[i] = x;
				field1839[i] = tmp;
				field1869[i] = z;
			}
		}

		try
		{
			this.method0(graphics, false, false, false, 0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}


	private void method0(Graphics3D graphics, boolean var1, boolean var2, boolean var3, int var4)
	{
		if (this.diameter < 1600)
		{
			int var5;
			for (var5 = 0; var5 < this.diameter; ++var5)
			{
				field1871[var5] = 0;
			}

			var5 = var3 ? 20 : 5;
			int var7;
			int var9;
			int var10;
			int var11;
			int var12;
			int var13;
			int var14;
			int var15;
			int var16;
			int var17;
			int var18;
			int var19;
			int var20;
			int var27;
//         if(class7.drawObjectGeometry2D && var2) {
//            Model var6 = this;
//
//            for(var7 = 0; var7 < var6.indicesCount; ++var7) {
//               if(var6.field1823[var7] != -2) {
//                  var27 = var6.indices1[var7];
//                  var9 = var6.indices2[var7];
//                  var10 = var6.indices3[var7];
//                  var11 = modelViewportYs[var27];
//                  var12 = modelViewportYs[var9];
//                  var13 = modelViewportYs[var10];
//                  var14 = modelViewportXs[var27];
//                  var15 = modelViewportXs[var9];
//                  var16 = modelViewportXs[var10];
//                  var17 = Math.min(var11, Math.min(var12, var13)) - var5;
//                  var18 = Math.max(var11, Math.max(var12, var13)) + var5;
//                  var19 = Math.min(var14, Math.min(var15, var16)) - var5;
//                  var20 = Math.max(var14, Math.max(var15, var16)) + var5;
////                  GrandExchangeEvents.method77(var17, var19, var18, var20, -49088);
//               }
//            }
//         }

			int var26;
			for (var26 = 0; var26 < this.indicesCount; ++var26)
			{
				if (this.field1823[var26] != -2)
				{
					var7 = this.indices1[var26];
					var27 = this.indices2[var26];
					var9 = this.indices3[var26];
					var10 = modelViewportYs[var7];
					var11 = modelViewportYs[var27];
					var12 = modelViewportYs[var9];
					if (var1 && (var10 == -5000 || var11 == -5000 || var12 == -5000))
					{
						var13 = yViewportBuffer[var7];
						var14 = yViewportBuffer[var27];
						var15 = yViewportBuffer[var9];
						var16 = field1839[var7];
						var17 = field1839[var27];
						var18 = field1839[var9];
						var19 = field1869[var7];
						var20 = field1869[var27];
						int var21 = field1869[var9];
						var13 -= var14;
						var15 -= var14;
						var16 -= var17;
						var18 -= var17;
						var19 -= var20;
						var21 -= var20;
						int var22 = var16 * var21 - var19 * var18;
						int var23 = var19 * var15 - var13 * var21;
						int var24 = var13 * var18 - var16 * var15;
						if (var14 * var22 + var17 * var23 + var20 * var24 > 0)
						{
							field1885[var26] = true;
							int var25 = (modelViewportZs[var7] + modelViewportZs[var27] + modelViewportZs[var9]) / 3 + this.radius;
							field1868[var25][field1871[var25]++] = var26;
						}
					}
					else
					{
						if (var2)
						{
							var14 = modelViewportXs[var7];
							var15 = modelViewportXs[var27];
							var16 = modelViewportXs[var9];
//                     var17 = var5 + class132.Viewport_mouseY;
//                     boolean var32;
//                     if(var17 < var14 && var17 < var15 && var17 < var16) {
//                        var32 = false;
//                     } else {
//                        var17 = class132.Viewport_mouseY - var5;
//                        if(var17 > var14 && var17 > var15 && var17 > var16) {
//                           var32 = false;
//                        } else {
//                           var17 = var5 + class132.Viewport_mouseX;
//                           if(var17 < var10 && var17 < var11 && var17 < var12) {
//                              var32 = false;
//                           } else {
//                              var17 = class132.Viewport_mouseX - var5;
//                              if(var17 > var10 && var17 > var11 && var17 > var12) {
//                                 var32 = false;
//                              } else {
//                                 var32 = true;
//                              }
//                           }
//                        }
//                     }
//
//                     if(var32) {
//                        BoundingBox.method49(var4);
//                        var2 = false;
//                     }
						}

						if ((var10 - var11) * (modelViewportXs[var9] - modelViewportXs[var27]) - (var12 - var11) * (modelViewportXs[var7] - modelViewportXs[var27]) > 0)
						{
							field1885[var26] = false;
							if (var10 >= 0 && var11 >= 0 && var12 >= 0 && var10 <= graphics.rasterClipX && var11 <= graphics.rasterClipX && var12 <= graphics.rasterClipX)
							{
								field1887[var26] = false;
							}
							else
							{
								field1887[var26] = true;
							}

							var13 = (modelViewportZs[var7] + modelViewportZs[var27] + modelViewportZs[var9]) / 3 + this.radius;
							field1868[var13][field1871[var13]++] = var26;
						}
					}
				}
			}

			int[] var8;
			if (this.field1838 == null)
			{
				for (var26 = this.diameter - 1; var26 >= 0; --var26)
				{
					var7 = field1871[var26];
					if (var7 > 0)
					{
						var8 = field1868[var26];

						for (var9 = 0; var9 < var7; ++var9)
						{
							this.method2706(graphics, var8[var9]);
						}
					}
				}

			}
			else
			{
				for (var26 = 0; var26 < 12; ++var26)
				{
					field1872[var26] = 0;
					field1877[var26] = 0;
				}

				for (var26 = this.diameter - 1; var26 >= 0; --var26)
				{
					var7 = field1871[var26];
					if (var7 > 0)
					{
						var8 = field1868[var26];

						for (var9 = 0; var9 < var7; ++var9)
						{
							var10 = var8[var9];
							byte var31 = this.field1838[var10];
							var12 = field1872[var31]++;
							field1874[var31][var12] = var10;
							if (var31 < 10)
							{
								field1877[var31] += var26;
							}
							else if (var31 == 10)
							{
								field1857[var12] = var26;
							}
							else
							{
								field1863[var12] = var26;
							}
						}
					}
				}

				var26 = 0;
				if (field1872[1] > 0 || field1872[2] > 0)
				{
					var26 = (field1877[1] + field1877[2]) / (field1872[1] + field1872[2]);
				}

				var7 = 0;
				if (field1872[3] > 0 || field1872[4] > 0)
				{
					var7 = (field1877[3] + field1877[4]) / (field1872[3] + field1872[4]);
				}

				var27 = 0;
				if (field1872[6] > 0 || field1872[8] > 0)
				{
					var27 = (field1877[8] + field1877[6]) / (field1872[8] + field1872[6]);
				}

				var10 = 0;
				var11 = field1872[10];
				int[] var28 = field1874[10];
				int[] var29 = field1857;
				if (var10 == var11)
				{
					var10 = 0;
					var11 = field1872[11];
					var28 = field1874[11];
					var29 = field1863;
				}

				if (var10 < var11)
				{
					var9 = var29[var10];
				}
				else
				{
					var9 = -1000;
				}

				for (var14 = 0; var14 < 10; ++var14)
				{
					while (var14 == 0 && var9 > var26)
					{
						this.method2706(graphics, var28[var10++]);
						if (var10 == var11 && var28 != field1874[11])
						{
							var10 = 0;
							var11 = field1872[11];
							var28 = field1874[11];
							var29 = field1863;
						}

						if (var10 < var11)
						{
							var9 = var29[var10];
						}
						else
						{
							var9 = -1000;
						}
					}

					while (var14 == 3 && var9 > var7)
					{
						this.method2706(graphics, var28[var10++]);
						if (var10 == var11 && var28 != field1874[11])
						{
							var10 = 0;
							var11 = field1872[11];
							var28 = field1874[11];
							var29 = field1863;
						}

						if (var10 < var11)
						{
							var9 = var29[var10];
						}
						else
						{
							var9 = -1000;
						}
					}

					while (var14 == 5 && var9 > var27)
					{
						this.method2706(graphics, var28[var10++]);
						if (var10 == var11 && var28 != field1874[11])
						{
							var10 = 0;
							var11 = field1872[11];
							var28 = field1874[11];
							var29 = field1863;
						}

						if (var10 < var11)
						{
							var9 = var29[var10];
						}
						else
						{
							var9 = -1000;
						}
					}

					var15 = field1872[var14];
					int[] var30 = field1874[var14];

					for (var17 = 0; var17 < var15; ++var17)
					{
						this.method2706(graphics, var30[var17]);
					}
				}

				while (var9 != -1000)
				{
					this.method2706(graphics, var28[var10++]);
					if (var10 == var11 && var28 != field1874[11])
					{
						var10 = 0;
						var28 = field1874[11];
						var11 = field1872[11];
						var29 = field1863;
					}

					if (var10 < var11)
					{
						var9 = var29[var10];
					}
					else
					{
						var9 = -1000;
					}
				}

			}
		}
	}


	private void method2706(Graphics3D graphics, int var1)
	{
		if (field1885[var1])
		{
			this.method2707(graphics, var1);
		}
		else
		{
			int var2 = this.indices1[var1];
			int var3 = this.indices2[var1];
			int var4 = this.indices3[var1];
			graphics.rasterClipEnable = field1887[var1];
			if (this.field1882 == null)
			{
				graphics.rasterAlpha = 0;
			}
			else
			{
				graphics.rasterAlpha = this.field1882[var1] & 255;
			}

			if (this.field1841 != null && this.field1841[var1] != -1)
			{
				int var5;
				int var6;
				int var7;
				if (this.field1840 != null && this.field1840[var1] != -1)
				{
					int var8 = this.field1840[var1] & 255;
					var5 = this.field1844[var8];
					var6 = this.field1865[var8];
					var7 = this.field1846[var8];
				}
				else
				{
					var5 = var2;
					var6 = var3;
					var7 = var4;
				}

				if (this.field1823[var1] == -1)
				{
					graphics.rasterTextureAffine(modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], this.field1856[var1], this.field1856[var1], this.field1856[var1], yViewportBuffer[var5], yViewportBuffer[var6], yViewportBuffer[var7], field1839[var5], field1839[var6], field1839[var7], field1869[var5], field1869[var6], field1869[var7], this.field1841[var1]);
				}
				else
				{
					graphics.rasterTextureAffine(modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], this.field1856[var1], this.field1854[var1], this.field1823[var1], yViewportBuffer[var5], yViewportBuffer[var6], yViewportBuffer[var7], field1839[var5], field1839[var6], field1839[var7], field1869[var5], field1869[var6], field1869[var7], this.field1841[var1]);
				}
			}
			else if (this.field1823[var1] == -1)
			{
				int[] field1889 = graphics.colorPalette;
				graphics.rasterFlat(modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], field1889[this.field1856[var1]]);
			}
			else
			{
				graphics.rasterGouraud(modelViewportXs[var2], modelViewportXs[var3], modelViewportXs[var4], modelViewportYs[var2], modelViewportYs[var3], modelViewportYs[var4], this.field1856[var1], this.field1854[var1], this.field1823[var1]);
			}

		}
	}


	private void method2707(Graphics3D graphics, int var1)
	{
		int var2 = graphics.centerX;
		int var3 = graphics.centerY;
		int var4 = 0;
		int var5 = this.indices1[var1];
		int var6 = this.indices2[var1];
		int var7 = this.indices3[var1];
		int var8 = field1869[var5];
		int var9 = field1869[var6];
		int var10 = field1869[var7];
		if (this.field1882 == null)
		{
			graphics.rasterAlpha = 0;
		}
		else
		{
			graphics.rasterAlpha = this.field1882[var1] & 255;
		}

		int var11;
		int var12;
		int var13;
		int var14;
		if (var8 >= 50)
		{
			field1831[var4] = modelViewportYs[var5];
			field1837[var4] = modelViewportXs[var5];
			xViewportBuffer[var4++] = this.field1856[var1];
		}
		else
		{
			var11 = yViewportBuffer[var5];
			var12 = field1839[var5];
			var13 = this.field1856[var1];
			if (var10 >= 50)
			{
				var14 = field1890[var10 - var8] * (50 - var8);
				field1831[var4] = var2 + graphics.Rasterizer3D_zoom * (var11 + ((yViewportBuffer[var7] - var11) * var14 >> 16)) / 50;
				field1837[var4] = var3 + graphics.Rasterizer3D_zoom * (var12 + ((field1839[var7] - var12) * var14 >> 16)) / 50;
				xViewportBuffer[var4++] = var13 + ((this.field1823[var1] - var13) * var14 >> 16);
			}

			if (var9 >= 50)
			{
				var14 = field1890[var9 - var8] * (50 - var8);
				field1831[var4] = var2 + graphics.Rasterizer3D_zoom * (var11 + ((yViewportBuffer[var6] - var11) * var14 >> 16)) / 50;
				field1837[var4] = var3 + graphics.Rasterizer3D_zoom * (var12 + ((field1839[var6] - var12) * var14 >> 16)) / 50;
				xViewportBuffer[var4++] = var13 + ((this.field1854[var1] - var13) * var14 >> 16);
			}
		}

		if (var9 >= 50)
		{
			field1831[var4] = modelViewportYs[var6];
			field1837[var4] = modelViewportXs[var6];
			xViewportBuffer[var4++] = this.field1854[var1];
		}
		else
		{
			var11 = yViewportBuffer[var6];
			var12 = field1839[var6];
			var13 = this.field1854[var1];
			if (var8 >= 50)
			{
				var14 = field1890[var8 - var9] * (50 - var9);
				field1831[var4] = var2 + graphics.Rasterizer3D_zoom * (var11 + ((yViewportBuffer[var5] - var11) * var14 >> 16)) / 50;
				field1837[var4] = var3 + graphics.Rasterizer3D_zoom * (var12 + ((field1839[var5] - var12) * var14 >> 16)) / 50;
				xViewportBuffer[var4++] = var13 + ((this.field1856[var1] - var13) * var14 >> 16);
			}

			if (var10 >= 50)
			{
				var14 = field1890[var10 - var9] * (50 - var9);
				field1831[var4] = var2 + graphics.Rasterizer3D_zoom * (var11 + ((yViewportBuffer[var7] - var11) * var14 >> 16)) / 50;
				field1837[var4] = var3 + graphics.Rasterizer3D_zoom * (var12 + ((field1839[var7] - var12) * var14 >> 16)) / 50;
				xViewportBuffer[var4++] = var13 + ((this.field1823[var1] - var13) * var14 >> 16);
			}
		}

		if (var10 >= 50)
		{
			field1831[var4] = modelViewportYs[var7];
			field1837[var4] = modelViewportXs[var7];
			xViewportBuffer[var4++] = this.field1823[var1];
		}
		else
		{
			var11 = yViewportBuffer[var7];
			var12 = field1839[var7];
			var13 = this.field1823[var1];
			if (var9 >= 50)
			{
				var14 = field1890[var9 - var10] * (50 - var10);
				field1831[var4] = var2 + graphics.Rasterizer3D_zoom * (var11 + ((yViewportBuffer[var6] - var11) * var14 >> 16)) / 50;
				field1837[var4] = var3 + graphics.Rasterizer3D_zoom * (var12 + ((field1839[var6] - var12) * var14 >> 16)) / 50;
				xViewportBuffer[var4++] = var13 + ((this.field1854[var1] - var13) * var14 >> 16);
			}

			if (var8 >= 50)
			{
				var14 = field1890[var8 - var10] * (50 - var10);
				field1831[var4] = var2 + graphics.Rasterizer3D_zoom * (var11 + ((yViewportBuffer[var5] - var11) * var14 >> 16)) / 50;
				field1837[var4] = var3 + graphics.Rasterizer3D_zoom * (var12 + ((field1839[var5] - var12) * var14 >> 16)) / 50;
				xViewportBuffer[var4++] = var13 + ((this.field1856[var1] - var13) * var14 >> 16);
			}
		}

		var11 = field1831[0];
		var12 = field1831[1];
		var13 = field1831[2];
		var14 = field1837[0];
		int var15 = field1837[1];
		int var16 = field1837[2];
		graphics.rasterClipEnable = false;
		int var17;
		int var18;
		int var19;
		int var20;
		if (var4 == 3)
		{
			if (var11 < 0 || var12 < 0 || var13 < 0 || var11 > graphics.rasterClipX || var12 > graphics.rasterClipX || var13 > graphics.rasterClipX)
			{
				graphics.rasterClipEnable = true;
			}

			if (this.field1841 != null && this.field1841[var1] != -1)
			{
				if (this.field1840 != null && this.field1840[var1] != -1)
				{
					var20 = this.field1840[var1] & 255;
					var17 = this.field1844[var20];
					var18 = this.field1865[var20];
					var19 = this.field1846[var20];
				}
				else
				{
					var17 = var5;
					var18 = var6;
					var19 = var7;
				}

				if (this.field1823[var1] == -1)
				{
					graphics.rasterTextureAffine(var14, var15, var16, var11, var12, var13, this.field1856[var1], this.field1856[var1], this.field1856[var1], yViewportBuffer[var17], yViewportBuffer[var18], yViewportBuffer[var19], field1839[var17], field1839[var18], field1839[var19], field1869[var17], field1869[var18], field1869[var19], this.field1841[var1]);
				}
				else
				{
					graphics.rasterTextureAffine(var14, var15, var16, var11, var12, var13, xViewportBuffer[0], xViewportBuffer[1], xViewportBuffer[2], yViewportBuffer[var17], yViewportBuffer[var18], yViewportBuffer[var19], field1839[var17], field1839[var18], field1839[var19], field1869[var17], field1869[var18], field1869[var19], this.field1841[var1]);
				}
			}
			else if (this.field1823[var1] == -1)
			{
				int[] field1889 = graphics.colorPalette;
				graphics.rasterFlat(var14, var15, var16, var11, var12, var13, field1889[this.field1856[var1]]);
			}
			else
			{
				graphics.rasterGouraud(var14, var15, var16, var11, var12, var13, xViewportBuffer[0], xViewportBuffer[1], xViewportBuffer[2]);
			}
		}

		if (var4 == 4)
		{
			if (var11 < 0 || var12 < 0 || var13 < 0 || var11 > graphics.rasterClipX || var12 > graphics.rasterClipX || var13 > graphics.rasterClipX || field1831[3] < 0 || field1831[3] > graphics.rasterClipX)
			{
				graphics.rasterClipEnable = true;
			}

			if (this.field1841 != null && this.field1841[var1] != -1)
			{
				if (this.field1840 != null && this.field1840[var1] != -1)
				{
					var20 = this.field1840[var1] & 255;
					var17 = this.field1844[var20];
					var18 = this.field1865[var20];
					var19 = this.field1846[var20];
				}
				else
				{
					var17 = var5;
					var18 = var6;
					var19 = var7;
				}

				short var21 = this.field1841[var1];
				if (this.field1823[var1] == -1)
				{
					graphics.rasterTextureAffine(var14, var15, var16, var11, var12, var13, this.field1856[var1], this.field1856[var1], this.field1856[var1], yViewportBuffer[var17], yViewportBuffer[var18], yViewportBuffer[var19], field1839[var17], field1839[var18], field1839[var19], field1869[var17], field1869[var18], field1869[var19], var21);
					graphics.rasterTextureAffine(var14, var16, field1837[3], var11, var13, field1831[3], this.field1856[var1], this.field1856[var1], this.field1856[var1], yViewportBuffer[var17], yViewportBuffer[var18], yViewportBuffer[var19], field1839[var17], field1839[var18], field1839[var19], field1869[var17], field1869[var18], field1869[var19], var21);
				}
				else
				{
					graphics.rasterTextureAffine(var14, var15, var16, var11, var12, var13, xViewportBuffer[0], xViewportBuffer[1], xViewportBuffer[2], yViewportBuffer[var17], yViewportBuffer[var18], yViewportBuffer[var19], field1839[var17], field1839[var18], field1839[var19], field1869[var17], field1869[var18], field1869[var19], var21);
					graphics.rasterTextureAffine(var14, var16, field1837[3], var11, var13, field1831[3], xViewportBuffer[0], xViewportBuffer[2], xViewportBuffer[3], yViewportBuffer[var17], yViewportBuffer[var18], yViewportBuffer[var19], field1839[var17], field1839[var18], field1839[var19], field1869[var17], field1869[var18], field1869[var19], var21);
				}
			}
			else if (this.field1823[var1] == -1)
			{
				int[] field1889 = graphics.colorPalette;
				var17 = field1889[this.field1856[var1]];
				graphics.rasterFlat(var14, var15, var16, var11, var12, var13, var17);
				graphics.rasterFlat(var14, var16, field1837[3], var11, var13, field1831[3], var17);
			}
			else
			{
				graphics.rasterGouraud(var14, var15, var16, var11, var12, var13, xViewportBuffer[0], xViewportBuffer[1], xViewportBuffer[2]);
				graphics.rasterGouraud(var14, var16, field1837[3], var11, var13, field1831[3], xViewportBuffer[0], xViewportBuffer[2], xViewportBuffer[3]);
			}
		}

	}


//
//   void draw(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
//      field1871[0] = -1;
//      if(this.boundsType != 1) {
//         this.calculateBoundsCylinder();
//      }
//
//      this.method2759(var1);
//      int var10 = var5 * var8 - var4 * var6 >> 16;
//      int var11 = var2 * var7 + var3 * var10 >> 16;
//      int var12 = var3 * this.XYZMag >> 16;
//      int var13 = var11 + var12;
//      if(var13 > 50 && var11 < 3500) {
//         int var14 = var8 * var4 + var5 * var6 >> 16;
//         int var15 = (var14 - this.XYZMag) * Graphics3D.Rasterizer3D_zoom;
//         if(var15 / var13 < Graphics3D.Rasterizer3D_clipMidX2) {
//            int var16 = (var14 + this.XYZMag) * Graphics3D.Rasterizer3D_zoom;
//            if(var16 / var13 > Graphics3D.Rasterizer3D_clipNegativeMidX) {
//               int var17 = var3 * var7 - var10 * var2 >> 16;
//               int var18 = var2 * this.XYZMag >> 16;
//               int var19 = (var17 + var18) * Graphics3D.Rasterizer3D_zoom;
//               if(var19 / var13 > Graphics3D.Rasterizer3D_clipNegativeMidY) {
//                  int var20 = (var3 * super.modelHeight >> 16) + var18;
//                  int var21 = (var17 - var20) * Graphics3D.Rasterizer3D_zoom;
//                  if(var21 / var13 < Graphics3D.Rasterizer3D_clipMidY2) {
//                     int var22 = var12 + (var2 * super.modelHeight >> 16);
//                     boolean var23 = false;
//                     boolean var24 = false;
//                     if(var11 - var22 <= 50) {
//                        var24 = true;
//                     }
//
//                     boolean var25 = var24 || this.field1852 > 0;
//                     int var26 = class132.Viewport_mouseX;
//                     int var28 = class132.Viewport_mouseY;
//                     boolean var30 = class132.Viewport_containsMouse;
//                     if(class7.drawBoundingBoxes3D && var9 > 0) {
//                        class163.method3207(this, var6, var7, var8);
//                     }
//
//                     int var33;
//                     int var34;
//                     int var35;
//                     int var36;
//                     int var37;
//                     int var38;
//                     int var39;
//                     if(class7.drawBoundingBoxes2D && var9 > 0) {
//                        int var32 = var11 - var12;
//                        if(var32 <= 50) {
//                           var32 = 50;
//                        }
//
//                        if(var14 > 0) {
//                           var33 = var15 / var13;
//                           var34 = var16 / var32;
//                        } else {
//                           var34 = var16 / var13;
//                           var33 = var15 / var32;
//                        }
//
//                        if(var17 > 0) {
//                           var35 = var21 / var13;
//                           var36 = var19 / var32;
//                        } else {
//                           var36 = var19 / var13;
//                           var35 = var21 / var32;
//                        }
//
//                        var37 = -8355840;
//                        var38 = var26 - Graphics3D.centerX;
//                        var39 = var28 - Graphics3D.centerY;
//                        if(var38 > var33 && var38 < var34 && var39 > var35 && var39 < var36) {
//                           var37 = -256;
//                        }
//
//                        GrandExchangeEvents.method77(var33 + Graphics3D.centerX, var35 + Graphics3D.centerY, var34 + Graphics3D.centerX, var36 + Graphics3D.centerY, var37);
//                     }
//
//                     boolean var44 = false;
//                     if(var9 > 0 && var30) {
//                        boolean var45 = false;
//                        if(useBoundingBoxes3D) {
//                           var45 = class71.boundingBox3DContainsMouse(this, var6, var7, var8);
//                        } else {
//                           var34 = var11 - var12;
//                           if(var34 <= 50) {
//                              var34 = 50;
//                           }
//
//                           if(var14 > 0) {
//                              var15 /= var13;
//                              var16 /= var34;
//                           } else {
//                              var16 /= var13;
//                              var15 /= var34;
//                           }
//
//                           if(var17 > 0) {
//                              var21 /= var13;
//                              var19 /= var34;
//                           } else {
//                              var19 /= var13;
//                              var21 /= var34;
//                           }
//
//                           var35 = var26 - Graphics3D.centerX;
//                           var36 = var28 - Graphics3D.centerY;
//                           if(var35 > var15 && var35 < var16 && var36 > var21 && var36 < var19) {
//                              var45 = true;
//                           }
//                        }
//
//                        if(var45) {
//                           if(this.isItemModel) {
//                              BoundingBox.method49(var9);
//                           } else {
//                              var44 = true;
//                           }
//                        }
//                     }
//
//                     var33 = Graphics3D.centerX;
//                     var34 = Graphics3D.centerY;
//                     var35 = 0;
//                     var36 = 0;
//                     if(var1 != 0) {
//                        var35 = Model_sine[var1];
//                        var36 = Model_cosine[var1];
//                     }
//
//                     for(var37 = 0; var37 < this.verticesCount; ++var37) {
//                        var38 = this.verticesX[var37];
//                        var39 = this.verticesY[var37];
//                        int var40 = this.verticesZ[var37];
//                        int var41;
//                        if(var1 != 0) {
//                           var41 = var40 * var35 + var38 * var36 >> 16;
//                           var40 = var40 * var36 - var38 * var35 >> 16;
//                           var38 = var41;
//                        }
//
//                        var38 += var6;
//                        var39 += var7;
//                        var40 += var8;
//                        var41 = var40 * var4 + var5 * var38 >> 16;
//                        var40 = var5 * var40 - var38 * var4 >> 16;
//                        var38 = var41;
//                        var41 = var3 * var39 - var40 * var2 >> 16;
//                        var40 = var39 * var2 + var3 * var40 >> 16;
//                        modelViewportZs[var37] = var40 - var11;
//                        if(var40 >= 50) {
//                           modelViewportYs[var37] = var38 * Graphics3D.Rasterizer3D_zoom / var40 + var33;
//                           modelViewportXs[var37] = var41 * Graphics3D.Rasterizer3D_zoom / var40 + var34;
//                        } else {
//                           modelViewportYs[var37] = -5000;
//                           var23 = true;
//                        }
//
//                        if(var25) {
//                           yViewportBuffer[var37] = var38;
//                           field1839[var37] = var41;
//                           field1869[var37] = var40;
//                        }
//                     }
//
//                     try {
//                        this.method0(var23, var44, this.isItemModel, var9);
//                     } catch (Exception var43) {
//                        ;
//                     }
//
//                  }
//               }
//            }
//         }
//      }
//   }
}