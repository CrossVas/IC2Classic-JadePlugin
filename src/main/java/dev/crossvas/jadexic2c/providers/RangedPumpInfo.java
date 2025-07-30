package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.JadeCommonHandler;
import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import ic2.core.block.machine.low.TileEntityPump;
import ic2.core.block.machine.med.TileEntityRangedPump;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class RangedPumpInfo implements IInfoProvider {

    public static final RangedPumpInfo THIS = new RangedPumpInfo();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityPump) {
            TileEntityPump pump = (TileEntityPump) blockEntity;
            text(helper, usage(pump.getEnergyUsage()));
        }
        if (blockEntity instanceof TileEntityRangedPump) {
            TileEntityRangedPump pump = (TileEntityRangedPump) blockEntity;
            text(helper, usage(10));

            text(helper, translatable(pump.isOperating() ? "probe.miner.mining" : "probe.miner.retracting"));
            int y = pump.getPipeTip().getY();
            bar(helper, y, pump.getPos().getY(), translatable("probe.pump.progress", y), -10996205);
            JadeCommonHandler.addTankInfo(helper, pump);
        }
    }
}
