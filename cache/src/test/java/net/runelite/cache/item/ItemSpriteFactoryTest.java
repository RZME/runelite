package net.runelite.cache.item;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.runelite.cache.IndexType;
import net.runelite.cache.ItemManager;
import net.runelite.cache.SpriteManager;
import net.runelite.cache.StoreLocation;
import net.runelite.cache.TextureManager;
import net.runelite.cache.definitions.ModelDefinition;
import net.runelite.cache.definitions.loaders.ModelLoader;
import net.runelite.cache.definitions.providers.ModelProvider;
import net.runelite.cache.fs.Archive;
import net.runelite.cache.fs.Index;
import net.runelite.cache.fs.Store;
import org.junit.Test;

public class ItemSpriteFactoryTest
{
	@Test
	public void test() throws IOException
	{
		File base = StoreLocation.LOCATION;
//			outDir = folder.newFolder();

//		int count = 0;

		try (Store store = new Store(base))
		{
			store.load();

			ItemManager itemManager = new ItemManager(store);
			itemManager.load();

			int itemID = 995;

			ModelProvider modelProvider = new ModelProvider()
			{
				@Override
				public ModelDefinition provide(int modelId) throws IOException
				{
					Index models = store.getIndex(IndexType.MODELS);
					Archive archive = models.getArchive(modelId);

					byte[] data = archive.decompress(store.getStorage().loadArchive(archive));
					ModelDefinition inventoryModel = new ModelLoader().load(modelId, data);
					return inventoryModel;
				}
			};

			SpriteManager spriteManager = new SpriteManager(store);
			spriteManager.load();

			TextureManager textureManager = new TextureManager(store);
			textureManager.load();


			BufferedImage sprite = ItemSpriteFactory.createSprite(itemManager, modelProvider, spriteManager, textureManager,
				itemID, 1, 1, 3153952, 0, false);

			File out = new File("D:\\rs\\07\\temp\\" + itemID + ".png");
			BufferedImage img = sprite;
			ImageIO.write(img, "PNG", out);
		}
	}
}