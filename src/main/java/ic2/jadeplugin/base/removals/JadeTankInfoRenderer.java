package ic2.jadeplugin.base.removals;

import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.utils.helpers.StackUtil;
import ic2.jadeplugin.JadeTags;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

public class JadeTankInfoRenderer implements IBlockComponentProvider {

    public static final JadeTankInfoRenderer INSTANCE = new JadeTankInfoRenderer();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (StackUtil.hasHotbarItems(blockAccessor.getPlayer(), SpecialFilters.EU_READER) || blockAccessor.getPlayer().isCreative()) {
            if (blockAccessor.getServerData().contains(JadeTags.TAG_TANKS)) {
                iTooltip.remove(Identifiers.UNIVERSAL_FLUID_STORAGE);
                iTooltip.remove(Identifiers.UNIVERSAL_FLUID_STORAGE_DETAILED);
            }
        }
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL;
    }

    @Override
    public ResourceLocation getUid() {
        return JadeTags.TANK_RENDER;
    }
}
