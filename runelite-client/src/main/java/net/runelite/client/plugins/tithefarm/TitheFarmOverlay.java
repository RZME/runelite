/*
 * Copyright (c) 2018, Unmoon <https://github.com/Unmoon>
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
package net.runelite.client.plugins.tithefarm;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Arc2D;

public class TitheFarmOverlay extends Overlay
{
	private static final int TIMER_SIZE = 25;
	private static final int TIMER_BORDER_WIDTH = 1;

	private final Client client;
	private final TitheFarmPlugin plugin;
	private final TitheFarmPluginConfig config;

	private Color colorUnwatered;
	private Color colorWatered;
	private Color colorGrown;

	@Inject
	TitheFarmOverlay(@Nullable Client client, TitheFarmPlugin plugin, TitheFarmPluginConfig config)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.UNDER_WIDGETS);
		this.plugin = plugin;
		this.config = config;
		this.client = client;
	}

	@Override
	public Dimension render(Graphics2D graphics, Point parent)
	{
		if (config.enabled())
		{
			drawPlants(graphics);
		}
		return null;
	}

	public void updateColors()
	{
		colorGrown = config.getColorGrown();
		colorWatered = config.getColorWatered();
		colorUnwatered = config.getColorUnwatered();
	}

	private void drawPlants(Graphics2D graphics)
	{
		Widget viewport = client.getViewportWidget();
		for (TitheFarmPlant plant : plugin.getPlants())
		{
			if (viewport != null && plant.getGameObject().getCanvasLocation() != null && viewport.contains(plant.getGameObject().getCanvasLocation()))
			{
				switch (plant.getState())
				{
					case UNWATERED:
						drawTimerOnPlant(graphics, plant, colorUnwatered);
						break;
					case WATERED:
						drawTimerOnPlant(graphics, plant, colorWatered);
						break;
					case GROWN:
						drawTimerOnPlant(graphics, plant, colorGrown);
						break;
				}
			}
		}
	}
	private void drawTimerOnPlant(Graphics2D graphics, TitheFarmPlant plant, Color color)
	{
		net.runelite.api.Point loc = plant.getGameObject().getCanvasLocation();

		//Construct the arc
		Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
		arc.setAngleStart(90);
		double timeLeft = 1 - plant.getPlantTimeRelative();
		arc.setAngleExtent(timeLeft * 360);
		arc.setFrame(loc.getX() - TIMER_SIZE / 2, loc.getY() - TIMER_SIZE / 2, TIMER_SIZE, TIMER_SIZE);

		//Draw the inside of the arc
		graphics.setColor(color);
		graphics.fill(arc);

		//Draw the outlines of the arc
		graphics.setStroke(new BasicStroke(TIMER_BORDER_WIDTH));
		graphics.drawOval(loc.getX() - TIMER_SIZE / 2, loc.getY() - TIMER_SIZE / 2, TIMER_SIZE, TIMER_SIZE);
	}
}
