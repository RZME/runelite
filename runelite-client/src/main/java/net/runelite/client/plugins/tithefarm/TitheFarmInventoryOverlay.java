package net.runelite.client.plugins.tithefarm;

import net.runelite.api.Query;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.util.QueryRunner;

import javax.inject.Inject;
import java.awt.*;

class TitheFarmInventoryOverlay extends Overlay
{
    private final QueryRunner queryRunner;
    private final TitheFarmPluginConfig config;

    @Inject
    TitheFarmInventoryOverlay(QueryRunner queryRunner, TitheFarmPluginConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        this.queryRunner = queryRunner;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics, Point parent)
    {
        if (!config.enabled())
        {
            return null;
        }
        graphics.setFont(FontManager.getRunescapeSmallFont());

        Query inventoryQuery = new InventoryWidgetItemQuery();
        WidgetItem[] inventoryItems = queryRunner.runQuery(inventoryQuery);

        for (WidgetItem item : inventoryItems)
        {
            WateringCan wateringCan = WateringCan.getWateringCan(item.getId());
            if (wateringCan == null)
            {
                continue;
            }

            final Rectangle bounds = item.getCanvasBounds();
            final net.runelite.client.ui.overlay.components.TextComponent textComponent = new TextComponent();
            textComponent.setPosition(new Point(bounds.x, bounds.y + 16));
            textComponent.setColor(wateringCan.getColor());
            textComponent.setText(String.valueOf(wateringCan.getCharges()));
            textComponent.render(graphics, parent);
        }
        return null;
    }
}
