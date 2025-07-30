package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import dev.crossvas.jadexic2c.utils.EnergyContainer;
import dev.crossvas.jadexic2c.utils.Formatter;
import ic2.core.block.wiring.tile.TileEntityCable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class CableInfo implements IInfoProvider {

    public static final CableInfo THIS = new CableInfo();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityCable) {
            TileEntityCable cable = (TileEntityCable) blockEntity;
            text(helper, translatable("probe.energy.limit", cable.getConductorBreakdownEnergy() - 1));
            text(helper, translatable("probe.energy.loss", Formatter.CABLE_LOSS_FORMAT.format(cable.getConductionLoss())));
            EnergyContainer container = EnergyContainer.getContainer(cable);
            addCableOut(helper, container);
        }
    }
}
