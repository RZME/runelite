package net.runelite.cache.item;

class Rasterizer2D
{


	public int[] graphicsPixels;


	public int graphicsPixelsWidth;


	public int graphicsPixelsHeight;


	public int drawingAreaTop;


	public int drawingAreaBottom;


	public int draw_region_x;


	protected int drawingAreaRight;

//	static
//	{
//		drawingAreaTop = 0;
//		drawingAreaBottom = 0;
//		draw_region_x = 0;
//		drawingAreaRight = 0;
//	}


	public void setRasterBuffer(int[] var0, int var1, int var2)
	{
		graphicsPixels = var0;
		graphicsPixelsWidth = var1;
		graphicsPixelsHeight = var2;
		setDrawRegion(0, 0, var1, var2);
	}


	public void setDrawRegion(int var0, int var1, int var2, int var3)
	{
		if (var0 < 0)
		{
			var0 = 0;
		}

		if (var1 < 0)
		{
			var1 = 0;
		}

		if (var2 > graphicsPixelsWidth)
		{
			var2 = graphicsPixelsWidth;
		}

		if (var3 > graphicsPixelsHeight)
		{
			var3 = graphicsPixelsHeight;
		}

		draw_region_x = var0;
		drawingAreaTop = var1;
		drawingAreaRight = var2;
		drawingAreaBottom = var3;
	}


//	public void copyDrawRegion(int[] var0)
//	{
//		var0[0] = draw_region_x;
//		var0[1] = drawingAreaTop;
//		var0[2] = drawingAreaRight;
//		var0[3] = drawingAreaBottom;
//	}
//
//
//	public void setDrawRegion(int[] var0)
//	{
//		draw_region_x = var0[0];
//		drawingAreaTop = var0[1];
//		drawingAreaRight = var0[2];
//		drawingAreaBottom = var0[3];
//	}


	public void reset()
	{
		int var0 = 0;

		int var1;
		for (var1 = graphicsPixelsWidth * graphicsPixelsHeight - 7; var0 < var1; graphicsPixels[var0++] = 0)
		{
			graphicsPixels[var0++] = 0;
			graphicsPixels[var0++] = 0;
			graphicsPixels[var0++] = 0;
			graphicsPixels[var0++] = 0;
			graphicsPixels[var0++] = 0;
			graphicsPixels[var0++] = 0;
			graphicsPixels[var0++] = 0;
		}

		for (var1 += 7; var0 < var1; graphicsPixels[var0++] = 0)
		{
			;
		}

	}


	public void method5718(int var0, int var1, int var2, int var3)
	{
		if (var1 >= drawingAreaTop && var1 < drawingAreaBottom)
		{
			if (var0 < draw_region_x)
			{
				var2 -= draw_region_x - var0;
				var0 = draw_region_x;
			}

			if (var0 + var2 > drawingAreaRight)
			{
				var2 = drawingAreaRight - var0;
			}

			int var4 = var0 + graphicsPixelsWidth * var1;

			for (int var5 = 0; var5 < var2; ++var5)
			{
				graphicsPixels[var4 + var5] = var3;
			}

		}
	}


//	public static void method5707(int var0, int var1, int var2, int var3)
//	{
//		if (var0 >= draw_region_x && var0 < drawingAreaRight)
//		{
//			if (var1 < drawingAreaTop)
//			{
//				var2 -= drawingAreaTop - var1;
//				var1 = drawingAreaTop;
//			}
//
//			if (var2 + var1 > drawingAreaBottom)
//			{
//				var2 = drawingAreaBottom - var1;
//			}
//
//			int var4 = var0 + graphicsPixelsWidth * var1;
//
//			for (int var5 = 0; var5 < var2; ++var5)
//			{
//				graphicsPixels[var4 + var5 * graphicsPixelsWidth] = var3;
//			}
//
//		}
//	}


}