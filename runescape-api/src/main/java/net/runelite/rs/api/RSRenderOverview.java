package net.runelite.rs.api;

import net.runelite.api.RenderOverview;
import net.runelite.api.WorldMapData;
import net.runelite.mapping.Import;

public interface RSRenderOverview extends RenderOverview
{
	@Import("worldMapManager")
	@Override
	RSWorldMapManager getWorldMapManager();

	@Import("initializeWorldMap")
	@Override
	void initializeWorldMap(WorldMapData var1);

	@Import("worldMapData")
	@Override
	RSWorldMapData getWorldMapData();
}
