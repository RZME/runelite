package net.runelite.client.plugins.tithefarm;

import lombok.Getter;
import net.runelite.api.ItemID;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum WateringCan
{
	WATERING_CAN0(ItemID.WATERING_CAN, 0, Color.RED),
	WATERING_CAN1(ItemID.WATERING_CAN1, 1, Color.ORANGE),
	WATERING_CAN2(ItemID.WATERING_CAN2, 2, Color.YELLOW),
	WATERING_CAN3(ItemID.WATERING_CAN3, 3, Color.WHITE),
	WATERING_CAN4(ItemID.WATERING_CAN4, 4, Color.WHITE),
	WATERING_CAN5(ItemID.WATERING_CAN5, 5, Color.WHITE),
	WATERING_CAN6(ItemID.WATERING_CAN6, 6, Color.WHITE),
	WATERING_CAN7(ItemID.WATERING_CAN7, 7, Color.WHITE),
	WATERING_CAN8(ItemID.WATERING_CAN8, 8, Color.WHITE);

	@Getter
	private final int id;
	@Getter
	private final int charges;
	@Getter
	private final Color color;

	private static final Map<Integer, WateringCan> wateringCans = new HashMap<>();

	static
	{
		for (WateringCan can : values())
		{
			wateringCans.put(can.getId(), can);
		}
	}

	WateringCan(int id, int charges, Color color)
	{
		this.id = id;
		this.charges = charges;
		this.color = color;
	}

	public static WateringCan getWateringCan(int itemId)
	{
		return wateringCans.get(itemId);
	}
}
