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

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameObject;
import net.runelite.api.Point;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.GameObjectsChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;

import javax.inject.Inject;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@PluginDescriptor(
	name = "Tithe Farm plugin"
)
public class TitheFarmPlugin extends Plugin
{

	@Inject
	private TitheFarmPluginConfig config;

	@Inject
	private TitheFarmOverlay titheFarmOverlay;

	@Getter
	private final Set<TitheFarmPlant> plants = new HashSet<>();

	@Getter
	private Instant lastActionTime = Instant.ofEpochMilli(0);

	@Provides
	TitheFarmPluginConfig getConfig(ConfigManager configManager){return configManager.getConfig(TitheFarmPluginConfig.class);}

	@Override
	protected void startUp() throws Exception
	{
		titheFarmOverlay.updateColors();
	}

	@Override
	public Overlay getOverlay() {return titheFarmOverlay;}

	@Subscribe
	public void onGameObjectsChanged(GameObjectsChanged event)
	{
		if (!config.enabled())
		{
			return;
		}

		GameObject gameObject = event.getGameObject();

		TitheFarmPlant newPlant = new TitheFarmPlant(gameObject);
		TitheFarmPlant oldPlant = getPlantFromCollection(gameObject);

		if (oldPlant == null && newPlant.getType() != TitheFarmPlantType.EMPTY && newPlant.getType() != null)
		{
			log.debug("Added plant");
			plants.add(newPlant);
		}
		else if (oldPlant != null && newPlant.getType() == TitheFarmPlantType.EMPTY)
		{
			log.debug("Removed plant");
			plants.remove(oldPlant);
		}
		else if (oldPlant != null && newPlant.getType() != TitheFarmPlantType.EMPTY && newPlant.getType() != null
				&& oldPlant.getGameObject().getWorldLocation().equals(newPlant.getGameObject().getWorldLocation())
				&& oldPlant.getState() != newPlant.getState())
		{
			if (newPlant.getState() == TitheFarmPlantState.WATERED)
			{
				log.debug("Updated plant (watered)");
				newPlant.setPlanted(oldPlant.getPlanted());
				plants.remove(oldPlant);
				plants.add(newPlant);
			}
			else
			{
				log.debug("Updated plant");
				plants.remove(oldPlant);
				plants.add(newPlant);
			}
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("tithefarmplugin"))
		{
			titheFarmOverlay.updateColors();
		}
	}

	private TitheFarmPlant getPlantFromCollection(GameObject gameObject)
	{
		Point gameObjectLocation = gameObject.getWorldLocation();
		for (TitheFarmPlant plant : plants)
		{
			if (gameObjectLocation.equals(plant.getGameObject().getWorldLocation()))
			{
				return plant;
			}
		}
		return null;
	}
}
