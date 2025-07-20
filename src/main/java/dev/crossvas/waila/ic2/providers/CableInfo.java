package dev.crossvas.waila.ic2.providers;

import dev.crossvas.waila.ic2.base.interfaces.IInfoProvider;
import dev.crossvas.waila.ic2.base.interfaces.IWailaHelper;
import dev.crossvas.waila.ic2.utils.Formatter;
import ic2.core.block.wiring.TileEntityCable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class CableInfo implements IInfoProvider {

    public static final CableInfo THIS = new CableInfo();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityCable) {
            TileEntityCable cable = (TileEntityCable) blockEntity;
            text(helper, translate("probe.energy.limit", (int) cable.getConductorBreakdownEnergy() - 1));
            text(helper, translate("probe.energy.loss", Formatter.CABLE_LOSS_FORMAT.format(cable.getConductionLoss())));
        }
    }
}
