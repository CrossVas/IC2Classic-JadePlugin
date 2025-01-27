package dev.crossvas.jadexic2c.providers.transport;

import dev.crossvas.jadexic2c.base.JadeHelper;
import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.helpers.PluginHelper;
import dev.crossvas.jadexic2c.helpers.TextFormatter;
import ic2.core.block.transport.item.tubes.FilterTubeTileEntity;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterTubeInfo implements IInfoProvider {

    public static final FilterTubeInfo THIS = new FilterTubeInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof FilterTubeTileEntity filterTube) {
            boolean invPriority = filterTube.invPriority;
            helper.text(TextFormatter.GOLD.translate("info.tube.inv.prio", status(invPriority)));

            List<FilterTubeTileEntity.FilterEntry> filterEntries = filterTube.stacks;
            Object2ObjectOpenHashMap<Component, List<FilterTubeTileEntity.FilterEntry>> mappedFilter = new Object2ObjectOpenHashMap<>();
            if (!filterEntries.isEmpty()) {
                helper.paddingY(3);
                for (FilterTubeTileEntity.FilterEntry entry : filterEntries) {
                    Component side = PluginHelper.getSides(entry.getSides());
                    if (mappedFilter.containsKey(side)) {
                        List<FilterTubeTileEntity.FilterEntry> existing = new ArrayList<>(mappedFilter.get(side));
                        existing.add(entry);
                        mappedFilter.put(side, existing);
                    } else {
                        mappedFilter.put(side, Collections.singletonList(entry));
                    }
                }

                mappedFilter.keySet().forEach(side -> {
                    helper.text(TextFormatter.GOLD.translate("info.tube.filter"));
                    for (FilterTubeTileEntity.FilterEntry entry : mappedFilter.get(side)) {
                        helper.appendPaddingX(2);
                        helper.appendItem(entry.getStack());
                    }
                    helper.appendPaddingX(3);
                    helper.appendText(TextFormatter.WHITE.literal("→ "));
                    helper.appendText(side);
                });
            }

        }
    }
}
