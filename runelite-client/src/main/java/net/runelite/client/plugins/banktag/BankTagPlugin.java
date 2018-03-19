package net.runelite.client.plugins.banktag;

import com.google.common.eventbus.Subscribe;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ScriptEvent;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Bank Tags"
)
@Slf4j
public class BankTagPlugin extends Plugin
{
	@Inject
	private Client client;

	@Subscribe
	public void onScriptEvent(ScriptEvent event)
	{
		if (event.getEventName().equals("itemNameCompare") == false)
		{
			return;
		}

		int[] intStack = client.getIntStack();
		String[] stringStack = client.getStringStack();
		int intStackSize = client.getIntStackSize();
		int stringStackSize = client.getStringStackSize();
		int itemId = intStack[intStackSize - 1];
		String search = stringStack[stringStackSize - 1];

		log.debug("Checking item {} search: {}", itemId, search);

		if (itemId == 1329 && search.contains("moo"))
		{
			intStack[intStackSize - 2] = 1;
		}
	}

}
