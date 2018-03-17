package net.runelite.cache.definitions.providers;

import net.runelite.cache.definitions.ItemDefinition;

public interface ItemProvider
{
	ItemDefinition provide(int itemId);
}
