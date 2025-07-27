package dev.crossvas.waila.ic2.providers;

import dev.crossvas.waila.ic2.base.WailaCommonHandler;
import dev.crossvas.waila.ic2.base.interfaces.IInfoProvider;
import dev.crossvas.waila.ic2.base.interfaces.IWailaHelper;
import dev.crossvas.waila.ic2.utils.ColorUtils;
import dev.crossvas.waila.ic2.utils.Formatter;
import ic2.core.IC2;
import ic2.core.block.generator.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class BaseGeneratorInfo implements IInfoProvider {

    public static final BaseGeneratorInfo THIS = new BaseGeneratorInfo();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityBaseGenerator) {
            TileEntityBaseGenerator generator = (TileEntityBaseGenerator) blockEntity;
            int euProduction = generator instanceof TileEntityWindGenerator ? (int) ((TileEntityWindGenerator) generator).subproduction : generator.isConverting() ? generator.production : Math.min(generator.production, generator.storage);
            int maxOutput = Math.max(0, generator.production);
            if (generator instanceof TileEntityGenerator) {
                maxOutput = IC2.energyGeneratorBase;
            }
            if (generator instanceof TileEntityGeoGenerator) {
                maxOutput = IC2.energyGeneratorGeo;
            }
            if (generator instanceof TileEntityWaterLV) {
                maxOutput = 8;
            } else if (generator instanceof TileEntityWaterMV) {
                maxOutput = 64;
            } else if (generator instanceof TileEntityWaterHV) {
                maxOutput = 500;
            } else if (generator instanceof TileEntityWaterGenerator) {
                maxOutput = 2;
            }
            text(helper, tier(generator.getSourceTier()));
            text(helper, translate("probe.energy.output", Formatter.formatNumber(euProduction, 2)));
            text(helper, translate("probe.energy.output.max", Formatter.formatNumber(maxOutput, 2)));

            int fuel = generator.fuel;
            int maxFuel = generator.storage;

            if (generator instanceof TileEntityGeoGenerator) {
                fuel = 0;
                WailaCommonHandler.addTankInfo(helper, generator);
            }
            if (fuel > 0) {
                bar(helper, fuel, maxFuel, translate("probe.storage.fuel", fuel), ColorUtils.DARK_GRAY);
            }
        }
    }
}
