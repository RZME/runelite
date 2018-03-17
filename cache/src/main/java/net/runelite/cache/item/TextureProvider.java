package net.runelite.cache.item;

import net.runelite.cache.definitions.TextureDefinition;
import net.runelite.cache.definitions.providers.SpriteProvider;
import net.runelite.cache.definitions.providers.TextureProvider2;

class TextureProvider
{
	private final SpriteProvider spriteProvider;
	TextureDefinition[] textures;
	int maxSize;
	int size;
	double brightness;
	int width;

	public TextureProvider(TextureProvider2 textureProvider, SpriteProvider spriteProvider)
	{
		this.spriteProvider = spriteProvider;
		this.size = 0;
		this.brightness = 1.0D;
		this.width = 128;
		this.maxSize = 20;
		this.size = this.maxSize;
		this.brightness = 0.8D;
		this.width = 128;

		int max = -1;
		for (TextureDefinition textureDefinition : textureProvider.provide())
		{
			if (textureDefinition.getId() > max)
			{
				max = textureDefinition.getId();
			}
		}

		textures = new TextureDefinition[max + 1];
		for (TextureDefinition textureDefinition : textureProvider.provide())
		{
			textures[textureDefinition.getId()] = textureDefinition;
		}
	}

	public int[] load(int var1)
	{
		TextureDefinition var2 = this.textures[var1];
		if (var2 != null)
		{
			if (var2.pixels != null)
			{
				return var2.pixels;
			}

			boolean var3 = var2.method2680(this.brightness, this.width, spriteProvider);
			return var2.pixels;
		}

		return null;
	}


	public int getAverageTextureRGB(int var1)
	{
		return this.textures[var1] != null ? this.textures[var1].field1777 : 0;
	}


	public boolean vmethod3057(int var1)
	{
		return this.textures[var1].field1778;
	}


	public boolean vmethod3066(int var1)
	{
		return this.width == 64;
	}
}
