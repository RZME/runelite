/*
 * Copyright (c) 2018, Morgan Lewis <https://github.com/MESLewis>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.ui.overlay.worldmap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.RenderOverview;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

@Singleton
@Slf4j
public class WorldMapOverlay extends Overlay
{
	private static final Color TOOLTIP_BACKGROUND = new Color(255, 255, 160);
	private static final int TOOLTIP_OFFSET_HEIGHT = 25;
	private static final int TOOLTIP_OFFSET_WIDTH = 5;
	private static final int TOOLTIP_PADDING_HEIGHT = 1;
	private static final int TOOLTIP_PADDING_WIDTH = 2;

	private final WorldMapPointManager worldMapPointManager;
	private final Provider<Client> clientProvider;
	private final WorldMapOverlayMouseListener worldMapOverlayMouseListener;

	@Inject
	private WorldMapOverlay(Provider<Client> clientProvider, WorldMapPointManager worldMapPointManager, WorldMapOverlayMouseListener worldMapOverlayMouseListener)
	{
		this.clientProvider = clientProvider;
		this.worldMapPointManager = worldMapPointManager;
		this.worldMapOverlayMouseListener = worldMapOverlayMouseListener;
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.HIGH);
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
	}

	private Point mapWorldPointToGraphicsPoint(WorldPoint worldPoint)
	{
		RenderOverview ro = clientProvider.get().getRenderOverview();
		Float pixelsPerTile = ro.getWorldMapZoom();

		Point worldMapPosition = ro.getWorldMapPosition();
		int xWorldDiff = worldPoint.getX() - worldMapPosition.getX();
		int yWorldDiff = worldPoint.getY() - worldMapPosition.getY();
		yWorldDiff = yWorldDiff * -1;

		Widget map = clientProvider.get().getWidget(WidgetInfo.WORLD_MAP_VIEW);
		if (map != null)
		{
			Rectangle worldMapRect = map.getBounds();
			int xGraphDiff = (int) (xWorldDiff * pixelsPerTile + worldMapRect.getWidth() / 2 + worldMapRect.getX());
			int yGraphDiff = (int) (yWorldDiff * pixelsPerTile + worldMapRect.getHeight() / 2 + worldMapRect.getY());

			return new Point(xGraphDiff, yGraphDiff);
		}
		return new Point(0, 0);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		final List<WorldMapPoint> points = worldMapPointManager.getWorldMapPoints();

		if (points.isEmpty())
		{
			return null;
		}

		final Client client = clientProvider.get();

		Widget widget = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);
		if (widget == null)
		{
			return null;
		}

		final Rectangle worldMapRectangle = widget.getBounds();
		WorldMapPoint tooltipPoint = null;

		for (WorldMapPoint worldPoint : points)
		{
			if (worldPoint.getImage() != null && worldPoint.getWorldPoint() != null)
			{
				Point drawPoint = mapWorldPointToGraphicsPoint(worldPoint.getWorldPoint());

				if (worldPoint.isSnapToEdge())
				{
					graphics.setClip(0, 0, client.getCanvas().getWidth(), client.getCanvas().getHeight());

					if (worldMapRectangle.contains(drawPoint.getX(), drawPoint.getY()))
					{
						if (worldPoint.isCurrentlyEdgeSnapped())
						{
							worldPoint.setCurrentlyEdgeSnapped(false);
							worldPoint.onEdgeUnsnap();
						}
					}
					else
					{
						drawPoint = clipToRectangle(drawPoint, worldMapRectangle);
						if (!worldPoint.isCurrentlyEdgeSnapped())
						{
							worldPoint.setCurrentlyEdgeSnapped(true);
							worldPoint.onEdgeSnap();
						}
					}
				}
				else
				{
					graphics.setClip(worldMapRectangle);
				}

				int drawX = drawPoint.getX();
				int drawY = drawPoint.getY();

				if (worldPoint.getImagePoint() == null)
				{
					drawX -= worldPoint.getImage().getWidth() / 2;
					drawY -= worldPoint.getImage().getHeight() / 2;
				}
				else
				{
					drawX -= worldPoint.getImagePoint().getX();
					drawY -= worldPoint.getImagePoint().getY();

				}

				graphics.drawImage(worldPoint.getImage(), drawX, drawY, null);
				Rectangle clickbox = new Rectangle(drawX, drawY, worldPoint.getImage().getWidth(), worldPoint.getImage().getHeight());
				worldPoint.setClickbox(clickbox);

				if (worldPoint.isTooltipVisible())
				{
					tooltipPoint = worldPoint;
				}
			}

		}

		if (tooltipPoint != null)
		{
			drawTooltip(graphics, tooltipPoint);
		}

		return null;
	}

	private void drawTooltip(Graphics2D graphics, WorldMapPoint worldPoint)
	{
		String tooltip = worldPoint.getTooltip();
		if (tooltip == null || tooltip.length() <= 0)
		{
			return;
		}

		Point drawPoint = mapWorldPointToGraphicsPoint(worldPoint.getWorldPoint());
		drawPoint = new Point(drawPoint.getX() + TOOLTIP_OFFSET_WIDTH, drawPoint.getY() + TOOLTIP_OFFSET_HEIGHT);

		graphics.setClip(0, 0, clientProvider.get().getCanvas().getWidth(), clientProvider.get().getCanvas().getHeight());
		graphics.setColor(TOOLTIP_BACKGROUND);
		graphics.setFont(FontManager.getRunescapeFont());
		FontMetrics fm = graphics.getFontMetrics();
		int width = fm.stringWidth(tooltip);
		int height = fm.getHeight();

		Rectangle tooltipRect = new Rectangle(drawPoint.getX() - TOOLTIP_PADDING_WIDTH, drawPoint.getY() - TOOLTIP_PADDING_HEIGHT, width + TOOLTIP_PADDING_WIDTH * 2, height + TOOLTIP_PADDING_HEIGHT * 2);
		graphics.fillRect((int) tooltipRect.getX(), (int) tooltipRect.getY(), (int) tooltipRect.getWidth(), (int) tooltipRect.getHeight());

		graphics.setColor(Color.black);
		graphics.drawRect((int) tooltipRect.getX(), (int) tooltipRect.getY(), (int) tooltipRect.getWidth(), (int) tooltipRect.getHeight());
		graphics.drawString(tooltip, drawPoint.getX(), drawPoint.getY() + height);
	}

	private Point clipToRectangle(Point drawPoint, Rectangle mapDisplayRectangle)
	{
		int clippedX = drawPoint.getX();

		if (drawPoint.getX() < mapDisplayRectangle.getX())
		{
			clippedX = (int) mapDisplayRectangle.getX();
		}

		if (drawPoint.getX() > mapDisplayRectangle.getX() + mapDisplayRectangle.getWidth())
		{
			clippedX = (int) (mapDisplayRectangle.getX() + mapDisplayRectangle.getWidth());
		}

		int clippedY = drawPoint.getY();

		if (drawPoint.getY() < mapDisplayRectangle.getY())
		{
			clippedY = (int) mapDisplayRectangle.getY();
		}

		if (drawPoint.getY() > mapDisplayRectangle.getY() + mapDisplayRectangle.getHeight())
		{
			clippedY = (int) (mapDisplayRectangle.getY() + mapDisplayRectangle.getHeight());
		}

		return new Point(clippedX, clippedY);
	}
}
