package dev.crossvas.waila.ic2;

import dev.crossvas.waila.ic2.base.WailaTooltipRenderer;
import dev.crossvas.waila.ic2.base.tooltiprenderers.BaseProgressBarRenderer;
import dev.crossvas.waila.ic2.providers.BarrelInfo;
import dev.crossvas.waila.ic2.providers.CropInfo;
import ic2.core.block.TileEntityBarrel;
import ic2.core.block.TileEntityCrop;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class WailaPluginHandler {

    public static void register(IWailaRegistrar registration) {
        registration.registerStackProvider(CropInfo.CropIconProvider.THIS, TileEntityCrop.class);
        registration.registerHeadProvider(BarrelInfo.BarrelNameProvider.THIS, TileEntityBarrel.class);
        registration.registerBodyProvider(WailaTooltipRenderer.THIS, Block.class);
        registration.registerNBTProvider(WailaTooltipRenderer.THIS, TileEntity.class);
        registration.registerTooltipRenderer("jade.progress", new BaseProgressBarRenderer());
    }
}
