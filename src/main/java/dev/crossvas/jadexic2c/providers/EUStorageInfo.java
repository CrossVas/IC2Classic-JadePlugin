package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import dev.crossvas.jadexic2c.utils.ColorUtils;
import dev.crossvas.jadexic2c.utils.Formatter;
import ic2.api.classic.tile.machine.IEUStorage;
import ic2.core.block.base.tile.TileEntityBlock;
import ic2.core.block.machine.high.TileEntityMassFabricator;
import ic2.core.block.machine.high.TileEntityTeleporter;
import ic2.core.block.machine.low.TileEntityElectrolyzer;
import ic2.core.block.machine.med.TileEntityChargedElectrolyzer;
import ic2.core.block.personal.tile.TileEntityPersonalEnergyStorage;
import ic2.core.block.wiring.tile.TileEntityCreativeEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class EUStorageInfo implements IInfoProvider {

    public static final EUStorageInfo THIS = new EUStorageInfo();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityBlock) {
            TileEntityBlock machine = (TileEntityBlock) blockEntity;
            if (machine instanceof TileEntityCreativeEnergyStorage) {
                bar(helper, 1, 1, translatable("probe.energy.storage.name", "Infinite"), ColorUtils.RED);
            } else if (machine instanceof IEUStorage && !(machine instanceof TileEntityElectrolyzer || machine instanceof TileEntityChargedElectrolyzer || machine instanceof TileEntityMassFabricator)) {
                IEUStorage storage = (IEUStorage) blockEntity;
                int stored = storage.getStoredEU();
                int max = storage.getMaxEU();
                if (storage instanceof TileEntityPersonalEnergyStorage) {
                    stored = ((TileEntityPersonalEnergyStorage) storage).storage.stored;
                    max = ((TileEntityPersonalEnergyStorage) storage).storage.maxEnergy;
                }
                bar(helper, stored, max, translatable("probe.energy.storage.full.name", Formatter.formatInt(stored, 4), Formatter.formatInt(max, 4)), ColorUtils.RED);
            } else if (machine instanceof TileEntityTeleporter) {
                TileEntityTeleporter tp = (TileEntityTeleporter) machine;
                bar(helper, tp.getAvailableEnergy(), tp.getAvailableEnergy(), translatable("probe.energy.storage.name", Formatter.formatInt(tp.getAvailableEnergy(), 4)), ColorUtils.RED);
            }
        }
    }
}
