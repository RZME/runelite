package net.runelite.rs.api;

import net.runelite.api.WorldMapManager;
import net.runelite.mapping.Import;

public interface RSWorldMapManager extends WorldMapManager
{
	@Import("loaded")
	@Override
	boolean isLoaded();
}
