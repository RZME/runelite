package net.runelite.cache.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.runelite.cache.definitions.ItemDefinition;
import net.runelite.cache.definitions.ModelDefinition;
import net.runelite.cache.definitions.providers.ModelProvider;
import net.runelite.cache.definitions.providers.SpriteProvider;
import net.runelite.cache.definitions.providers.TextureProvider2;
import net.runelite.cache.models.FaceNormal;
import net.runelite.cache.models.VertexNormal;

public class ItemSpriteFactory
{
	public static final BufferedImage createSprite(ModelProvider modelProvider, SpriteProvider spriteProvider, TextureProvider2 textureProvider2, ItemDefinition item, int quantity, int border, int shadowColor, int stackable, boolean noted) throws IOException
	{
		if (quantity == -1)
		{
			stackable = 0;
		}
		else if (stackable == 2 && quantity != 1)
		{
			stackable = 1;
		}


		if (quantity > 1 && item.countObj != null)
		{
			int stackItemID = -1;

			for (int i = 0; i < 10; ++i)
			{
				if (quantity >= item.countCo[i] && item.countCo[i] != 0)
				{
					stackItemID = item.countObj[i];
				}
			}
//
//			if(stackItemID != -1) {
//				item = class81.getItemDefinition(stackItemID);
//			}
		}

		Model itemModel = getModel(modelProvider, item, 1); //item.getModel(1);
		if (itemModel == null)
		{
			return null;
		}
		else
		{
			SpritePixels auxSpritePixels = null;
//			if(item.notedTemplate != -1) {
//				auxSpritePixels = createSprite(item.notedID, 10, 1, 0, 0, true);
//				if(auxSpritePixels == null) {
//					return null;
//				}
			//AKA notedID
//			} else if(item.boughtTemplateId != -1) {
//				auxSpritePixels = createSprite(item.boughtId, quantity, border, shadowColor, 0, false);
//				if(auxSpritePixels == null) {
//					return null;
//				}
//			} else if(item.placeholderTemplateId != -1) {
//				auxSpritePixels = createSprite(item.placeholderId, quantity, 0, 0, 0, false);
//				if(auxSpritePixels == null) {
//					return null;
//				}
//			}

			TextureProvider textureProvider = new TextureProvider(textureProvider2, spriteProvider);

			SpritePixels spritePixels = new SpritePixels(36, 32);
			Graphics3D graphics = new Graphics3D(textureProvider);
			graphics.setBrightness(0.6d);
			graphics.setRasterBuffer(spritePixels.pixels, 36, 32);
			graphics.reset();
			graphics.setRasterClipping();
			graphics.setOffset(16, 16);
			graphics.rasterGouraudLowRes = false;
			if (item.placeholderTemplateId != -1)
			{
				auxSpritePixels.drawAt(0, 0);
			}

			int zoom2d = item.zoom2d;
			if (noted)
			{
				zoom2d = (int) ((double) zoom2d * 1.5D);
			}
			else if (border == 2)
			{
				zoom2d = (int) ((double) zoom2d * 1.04D);
			}

			int var17 = zoom2d * Graphics3D.SINE[item.xan2d] >> 16;
			int var18 = zoom2d * Graphics3D.COSINE[item.xan2d] >> 16;

			itemModel.calculateBoundsCylinder();
			itemModel.rotateAndProject(graphics, 0,
					item.yan2d,
					item.zan2d,
					item.xan2d,
					item.xOffset2d,
					itemModel.modelHeight / 2 + var17 + item.yOffset2d,
					var18 + item.yOffset2d);
			if (item.boughtTemplateId != -1)
			{
				auxSpritePixels.drawAt(0, 0);
			}

			if (border >= 1)
			{
				spritePixels.drawBorder(1);
			}

			if (border >= 2)
			{
				spritePixels.drawBorder(0xffffff);
			}

			if (shadowColor != 0)
			{
				spritePixels.drawShadow(shadowColor);
			}

			graphics.setRasterBuffer(spritePixels.pixels, 36, 32);
			if (item.notedTemplate != -1)
			{
				auxSpritePixels.drawAt(0, 0);
			}

//			if(stackable == 1 || stackable == 2 && item.stackable == 1) {
//				Resampler.field1590.method5500(Client.method1645(quantity), 0, 9, 16776960, 1);
//			}

//			if(!noted) {
//				ItemComposition.itemSpriteCache.put(spritePixels, var6);
//			}

			graphics.setRasterBuffer(graphics.graphicsPixels,
				graphics.graphicsPixelsWidth,
				graphics.graphicsPixelsHeight);

			graphics.setRasterClipping();
			graphics.rasterGouraudLowRes = true;
			return spritePixels.toBufferedImage();
		}
	}

	private static Model getModel(ModelProvider modelProvider, ItemDefinition item, int quantity) throws IOException
	{
//		if(item.countObj != null && quantity > 1) {
//			int itemModelID = -1;
//
//			for(int i = 0; i < 10; ++i) {
//				if(quantity >= item.countCo[i] && item.countCo[i] != 0) {
//					itemModelID = item.countObj[i];
//				}
//			}
//
//			if(itemModelID != -1) {
//				return class81.getItemDefinition(itemModelID).getModel(1);
//			}
//		}
//
//		Model itemModel = (Model)itemModelCache.get((long)this.id);
//		if(itemModel != null) {
//			return itemModel;
//		} else {
//		Index models = store.getIndex(IndexType.MODELS);
//		Archive archive = models.getArchive(item.inventoryModel);
//
//		byte[] data = archive.decompress(store.getStorage().loadArchive(archive));

		Model itemModel;
		ModelDefinition inventoryModel = modelProvider.provide(item.inventoryModel);
//		ModelDefinition inventoryModel = new ModelLoader().load(item.inventoryModel, data);
//			ModelData inventoryModel = ModelData.method2594(ItemDefinition_modelIndexCache, item.inventoryModel, 0);
		if (inventoryModel == null)
		{
			return null;
		}
		else
		{
			if (item.resizeX != 128 || item.resizeY != 128 || item.resizeZ != 128)
			{
				inventoryModel.resize(item.resizeX, item.resizeY, item.resizeZ);
			}

			if (item.colorFind != null)
			{
				for (int i = 0; i < item.colorFind.length; ++i)
				{
					inventoryModel.recolor(item.colorFind[i], item.colorReplace[i]);
				}
			}

//				if(item.textureFind != null) {
//					for(int i = 0; i < item.textureFind.length; ++i) {
//						inventoryModel.method2609(item.textureFind[i], item.textureReplace[i]);
//					}
//				}

			itemModel = light(inventoryModel, item.ambient + 64, item.contrast + 768, -50, -10, -50);
			itemModel.isItemModel = true;
			//itemModelCache.put(itemModel, (long)this.id);
			return itemModel;
		}
		//}
	}

	public static Model light(ModelDefinition def, int ambient, int contrast, int x, int y, int z)
	{
		def.computeNormals();
		int somethingMagnitude = (int) Math.sqrt((double) (z * z + x * x + y * y));
		int var7 = somethingMagnitude * contrast >> 8;
		Model litModel = new Model();
		litModel.field1856 = new int[def.faceCount];
		litModel.field1854 = new int[def.faceCount];
		litModel.field1823 = new int[def.faceCount];
		if (def.textureTriangleCount > 0 && def.textureCoordinates != null)
		{
			int[] var9 = new int[def.textureTriangleCount];

			int var10;
			for (var10 = 0; var10 < def.faceCount; ++var10)
			{
				if (def.textureCoordinates[var10] != -1)
				{
					++var9[def.textureCoordinates[var10] & 255];
				}
			}

			litModel.field1852 = 0;

			for (var10 = 0; var10 < def.textureTriangleCount; ++var10)
			{
				if (var9[var10] > 0 && def.textureRenderTypes[var10] == 0)
				{
					++litModel.field1852;
				}
			}

			litModel.field1844 = new int[litModel.field1852];
			litModel.field1865 = new int[litModel.field1852];
			litModel.field1846 = new int[litModel.field1852];
			var10 = 0;


			for (int i = 0; i < def.textureTriangleCount; ++i)
			{
				if (var9[i] > 0 && def.textureRenderTypes[i] == 0)
				{
					litModel.field1844[var10] = def.textureTriangleVertexIndices1[i] & '\uffff';
					litModel.field1865[var10] = def.textureTriangleVertexIndices2[i] & '\uffff';
					litModel.field1846[var10] = def.textureTriangleVertexIndices3[i] & '\uffff';
					var9[i] = var10++;
				}
				else
				{
					var9[i] = -1;
				}
			}

			litModel.field1840 = new byte[def.faceCount];

			for (int i = 0; i < def.faceCount; ++i)
			{
				if (def.textureCoordinates[i] != -1)
				{
					litModel.field1840[i] = (byte) var9[def.textureCoordinates[i] & 255];
				}
				else
				{
					litModel.field1840[i] = -1;
				}
			}
		}

		for (int faceIdx = 0; faceIdx < def.faceCount; ++faceIdx)
		{
			byte faceType;
			if (def.faceRenderTypes == null)
			{
				faceType = 0;
			}
			else
			{
				faceType = def.faceRenderTypes[faceIdx];
			}

			byte faceAlpha;
			if (def.faceAlphas == null)
			{
				faceAlpha = 0;
			}
			else
			{
				faceAlpha = def.faceAlphas[faceIdx];
			}

			short faceTexture;
			if (def.faceTextures == null)
			{
				faceTexture = -1;
			}
			else
			{
				faceTexture = def.faceTextures[faceIdx];
			}

			if (faceAlpha == -2)
			{
				faceType = 3;
			}

			if (faceAlpha == -1)
			{
				faceType = 2;
			}

			VertexNormal vertexNormal;
			int tmp;
			FaceNormal faceNormal;
			if (faceTexture == -1)
			{
				if (faceType != 0)
				{
					if (faceType == 1)
					{
						faceNormal = def.faceNormals[faceIdx];
						tmp = (y * faceNormal.y + z * faceNormal.z + x * faceNormal.x) / (var7 / 2 + var7) + ambient;
						litModel.field1856[faceIdx] = method2608(def.faceColors[faceIdx] & '\uffff', tmp);
						litModel.field1823[faceIdx] = -1;
					}
					else if (faceType == 3)
					{
						litModel.field1856[faceIdx] = 128;
						litModel.field1823[faceIdx] = -1;
					}
					else
					{
						litModel.field1823[faceIdx] = -2;
					}
				}
				else
				{
					int var15 = def.faceColors[faceIdx] & '\uffff';
//					if(def.field1741 != null && def.field1741[def.faceVertexIndices1[faceIdx]] != null) {
//						vertexNormal = def.field1741[def.faceVertexIndices1[faceIdx]];
//					} else {
					vertexNormal = def.vertexNormals[def.faceVertexIndices1[faceIdx]];
					//}

					tmp = (y * vertexNormal.y + z * vertexNormal.z + x * vertexNormal.x) / (var7 * vertexNormal.magnitude) + ambient;
					litModel.field1856[faceIdx] = method2608(var15, tmp);
//					if(def.field1741 != null && def.field1741[def.faceVertexIndices2[faceIdx]] != null) {
//						vertexNormal = def.field1741[def.faceVertexIndices2[faceIdx]];
//					} else {
					vertexNormal = def.vertexNormals[def.faceVertexIndices2[faceIdx]];
					//	}

					tmp = (y * vertexNormal.y + z * vertexNormal.z + x * vertexNormal.x) / (var7 * vertexNormal.magnitude) + ambient;
					litModel.field1854[faceIdx] = method2608(var15, tmp);
//					if(def.field1741 != null && def.field1741[def.faceVertexIndices3[faceIdx]] != null) {
//						vertexNormal = def.field1741[def.faceVertexIndices3[faceIdx]];
//					} else {
					vertexNormal = def.vertexNormals[def.faceVertexIndices3[faceIdx]];
					//}

					tmp = (y * vertexNormal.y + z * vertexNormal.z + x * vertexNormal.x) / (var7 * vertexNormal.magnitude) + ambient;
					litModel.field1823[faceIdx] = method2608(var15, tmp);
				}
			}
			else if (faceType != 0)
			{
				if (faceType == 1)
				{
					faceNormal = def.faceNormals[faceIdx];
					tmp = (y * faceNormal.y + z * faceNormal.z + x * faceNormal.x) / (var7 / 2 + var7) + ambient;
					litModel.field1856[faceIdx] = bound2to126(tmp);
					litModel.field1823[faceIdx] = -1;
				}
				else
				{
					litModel.field1823[faceIdx] = -2;
				}
			}
			else
			{
//				if(def.field1741 != null && def.field1741[def.faceVertexIndices1[faceIdx]] != null) {
//					vertexNormal = def.field1741[def.faceVertexIndices1[faceIdx]];
//				} else {
				vertexNormal = def.vertexNormals[def.faceVertexIndices1[faceIdx]];
				//}

				tmp = (y * vertexNormal.y + z * vertexNormal.z + x * vertexNormal.x) / (var7 * vertexNormal.magnitude) + ambient;
				litModel.field1856[faceIdx] = bound2to126(tmp);
//				if(def.field1741 != null && def.field1741[def.faceVertexIndices2[faceIdx]] != null) {
//					vertexNormal = def.field1741[def.faceVertexIndices2[faceIdx]];
//				} else {
				vertexNormal = def.vertexNormals[def.faceVertexIndices2[faceIdx]];
				//}

				tmp = (y * vertexNormal.y + z * vertexNormal.z + x * vertexNormal.x) / (var7 * vertexNormal.magnitude) + ambient;
				litModel.field1854[faceIdx] = bound2to126(tmp);
//				if(def.field1741 != null && def.field1741[def.faceVertexIndices3[faceIdx]] != null) {
//					vertexNormal = def.field1741[def.faceVertexIndices3[faceIdx]];
//				} else {
				vertexNormal = def.vertexNormals[def.faceVertexIndices3[faceIdx]];
				//	}

				tmp = (y * vertexNormal.y + z * vertexNormal.z + x * vertexNormal.x) / (var7 * vertexNormal.magnitude) + ambient;
				litModel.field1823[faceIdx] = bound2to126(tmp);
			}
		}

//		def.computeAnimationTables();
		litModel.verticesCount = def.vertexCount;
		litModel.verticesX = def.vertexPositionsX;
		litModel.verticesY = def.vertexPositionsY;
		litModel.verticesZ = def.vertexPositionsZ;
		litModel.indicesCount = def.faceCount;
		litModel.indices1 = def.faceVertexIndices1;
		litModel.indices2 = def.faceVertexIndices2;
		litModel.indices3 = def.faceVertexIndices3;
		litModel.field1838 = def.faceRenderPriorities;
		litModel.field1882 = def.faceAlphas;
		litModel.field1842 = def.priority;
//		litModel.field1847 = def.field1725;
//		litModel.field1848 = def.field1726;
		litModel.field1841 = def.faceTextures;
		return litModel;
	}

	static final int method2608(int var0, int var1)
	{

		var1 = ((var0 & 127) * var1) >> 7;
		var1 = bound2to126(var1);

		return (var0 & 65408) + var1;
	}

	static final int bound2to126(int var0)
	{
		if (var0 < 2)
		{
			var0 = 2;
		}
		else if (var0 > 126)
		{
			var0 = 126;
		}

		return var0;
	}
}
