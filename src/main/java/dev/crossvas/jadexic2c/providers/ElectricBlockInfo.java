package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.JadeCommonHandler;
import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import dev.crossvas.jadexic2c.utils.ColorUtils;
import dev.crossvas.jadexic2c.utils.Formatter;
import dev.crossvas.jadexic2c.utils.TextFormatter;
import ic2.core.block.base.tile.TileEntityElecMachine;
import ic2.core.block.base.tile.TileEntityRFProducer;
import ic2.core.block.machine.high.TileEntityElectricEnchanter;
import ic2.core.block.machine.high.TileEntityMassFabricator;
import ic2.core.block.machine.low.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class ElectricBlockInfo implements IInfoProvider {

    public static final ElectricBlockInfo THIS = new ElectricBlockInfo();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityElecMachine) {
            TileEntityElecMachine baseTile = (TileEntityElecMachine) blockEntity;

            if (baseTile instanceof TileEntityMachineTank || baseTile instanceof TileEntityCropmatron || baseTile instanceof TileEntityElectricWoodGasser) {
                JadeCommonHandler.addTankInfo(helper, baseTile);
            }

            if (baseTile instanceof TileEntityElectricEnchanter) {
                TileEntityElectricEnchanter enchanter = (TileEntityElectricEnchanter) baseTile;
                text(helper, usage(enchanter.getEnergyUsage()));
                int storedXP = enchanter.storedExp;
                float progress = enchanter.getProgress();
                float maxProgress = enchanter.getMaxProgress();
                if (storedXP <= 0) {
                    text(helper, TextFormatter.RED.translate("probe.enchanter.missing"));
                } else {
                    bar(helper, storedXP, 1000, translatable("probe.machine.xp", storedXP), ColorUtils.GREEN);
                }
                if (progress > 0) {
                    bar(helper, (int) progress, (int) maxProgress, translatable("probe.progress.full.name", progress, maxProgress).appendText("t"), -16733185);

                }
            }
            if (baseTile instanceof TileEntityMassFabricator) {
                TileEntityMassFabricator massFab = (TileEntityMassFabricator) baseTile;
                int progress = (int) massFab.getProgress();
                int maxProgress = (int) massFab.getMaxProgress();
                if (progress > 0) {
                    double finalProgress = progress / massFab.getMaxProgress() * 100.0;
                    if (finalProgress > 100) finalProgress = 100;
                    bar(helper, progress, maxProgress, translatable("probe.speed.massFab",
                            Formatter.THERMAL_GEN.format(finalProgress)), -4441721);
                }
                if (massFab.scrap > 0) {
                    bar(helper, massFab.scrap, massFab.lastScrap * 2, translatable("probe.matter.amplifier.name",
                            massFab.scrap), -10996205);
                }
            }
        }
        if (blockEntity instanceof TileEntityRFProducer) {
            TileEntityRFProducer fluxGenerator = (TileEntityRFProducer) blockEntity;
            text(helper, tier(fluxGenerator.inputTier));
            text(helper, maxIn(fluxGenerator.maxInput));
        }
    }
}
