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

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup(
	keyName = "tithefarmplugin",
	name = "Tithe Farm plugin",
	description = "Configuration for the Tithe Farm plugin"
)
public interface TitheFarmPluginConfig extends Config
{
	@ConfigItem(
			position = 0,
			keyName = "enabled",
			name = "Enabled",
			description = "Configures whether or not the Tithe Farm plugin is enabled"
	)
	default boolean enabled()
	{
		return true;
	}

	@ConfigItem(
			position = 1,
			keyName = "showWateringCanOverlay",
			name = "Show watering can overlay",
			description = "Configures whether or not the watering can overlay is displayed"
	)
	default boolean showWateringCanOverlay(){return true;}

	@ConfigItem(
			position = 2,
			keyName = "showSack",
			name = "Show fruit sack",
			description = "Configures whether or not the fruit sack is displayed"
	)
	default boolean showSack()
	{
		return true;
	}

	@ConfigItem(
		position = 3,
		keyName = "hexColorUnwatered",
		name = "Unwatered plant",
		description = "Color of unwatered plant timer"
	)
	default Color getColorUnwatered()
	{
		return Color.YELLOW;
	}

	@ConfigItem(
		position = 4,
		keyName = "hexColorWatered",
		name = "Watered plant",
		description = "Color of watered plant timer"
	)
	default Color getColorWatered()
	{
		return Color.BLUE;
	}

	@ConfigItem(
		position = 5,
		keyName = "hexColorGrown",
		name = "Grown plant",
		description = "Color of grown plant timer"
	)
	default Color getColorGrown()
	{
		return Color.GREEN;
	}
}
