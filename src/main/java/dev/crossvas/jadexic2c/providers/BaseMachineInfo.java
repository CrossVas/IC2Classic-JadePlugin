package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import dev.crossvas.jadexic2c.utils.ColorUtils;
import dev.crossvas.jadexic2c.utils.Formatter;
import ic2.core.block.base.tile.TileEntityAdvancedMachine;
import ic2.core.block.base.tile.TileEntityBasicElectricMachine;
import ic2.core.block.base.tile.TileEntityElecMachine;
import ic2.core.block.machine.low.TileEntityCropAnalyzer;
import ic2.core.block.machine.low.TileEntityRareEarthExtractor;
import ic2.core.block.machine.med.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;

import java.text.DecimalFormat;

public class BaseMachineInfo implements IInfoProvider {

    public static final BaseMachineInfo THIS = new BaseMachineInfo();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityElecMachine) {
            TileEntityElecMachine machine = (TileEntityElecMachine) blockEntity;
            ITextComponent tier = tier(machine.getTier());
            ITextComponent maxIn = maxIn(machine.maxInput);
            ITextComponent usage = null;
            float progress = 0;
            float maxProgress = 0;
            float progressPerTick = 0;

            int advProgress = 0;
            int advMaxProgress = 0;

            if (machine instanceof TileEntityRareEarthExtractor) {
                TileEntityRareEarthExtractor extractor = (TileEntityRareEarthExtractor) machine;
                usage = usage(1);
                bar(helper, (int) extractor.getSecondaryProgress(), (int) extractor.getMaxSecondaryProgress(), translatable("progress.material.name", Formatter.formatNumber(extractor.getSecondaryProgress(), 4), (int) extractor.getMaxSecondaryProgress()), -10996205);
            }
            if (machine instanceof TileEntityCropAnalyzer) {
                usage = usage(((TileEntityCropAnalyzer) machine).energyUsage);
            }

            if (machine instanceof TileEntityBasicElectricMachine) {
                TileEntityBasicElectricMachine electricMachine = (TileEntityBasicElectricMachine) machine;
                usage = usage(electricMachine.energyConsume);
                progress = electricMachine.getProgress();
                maxProgress = electricMachine.getMaxProgress();
                progressPerTick = electricMachine.progressPerTick;
            }

            float speed = 0;
            float maxSpeed = 0;
            String name = "";
            double scaledProgress = 0;

            if (machine instanceof TileEntityAdvancedMachine) {
                TileEntityAdvancedMachine advMachine = (TileEntityAdvancedMachine) machine;
                progress = advMachine.progress;
                usage = usage(advMachine.getEnergyUsage());
                speed = advMachine.speed;
                maxSpeed = advMachine.getMaxSpeed();
                scaledProgress = speed / maxSpeed;
                if (advMachine instanceof TileEntityInductionFurnace) {
                    name = "probe.speed.heat";
                } else if (advMachine instanceof TileEntityRotaryMacerator) {
                    name = "probe.speed.rotation";
                } else if (advMachine instanceof TileEntitySingularityCompressor) {
                    name = "probe.speed.pressure";
                } else if (advMachine instanceof TileEntityCentrifugalExtractor || advMachine instanceof TileEntityCompactingRecycler) {
                    name = "probe.speed.speed";
                }
                int operationsPerTick = advMachine.speed / 30;
                advProgress = (int) Math.min(6.0E7F, progress / operationsPerTick);
                advMaxProgress = (int) Math.min(6.0E7F, (float) 4000 / operationsPerTick);
            }
            if (machine instanceof TileEntityVacuumCanner) {
                TileEntityVacuumCanner canner = (TileEntityVacuumCanner) machine;
                usage = usage(canner.energyConsume);
                name = "probe.speed.vacuum";
                speed = canner.getSpeed();
                maxSpeed = canner.getMaxSpeed();
                scaledProgress = (double) speed / maxSpeed;
                int operationsPerTick = canner.speed / 30;
                advProgress = (int) Math.min(6.0E7F, progress / operationsPerTick);
                advMaxProgress = (int) Math.min(6.0E7F, (float) 4000 / operationsPerTick);
            }
            // tier
            if (tier != null) text(helper, tier);
            // maxIn
            if (maxIn != null) text(helper, maxIn);
            // usage
            if (usage != null) text(helper, usage);
            if (speed > 0) {
                bar(helper, (int) speed, (int) maxSpeed, translatable(name, new DecimalFormat().format(scaledProgress * 100.0)), ColorUtils.SPEED);
            }

            if (progress > 0 && advProgress == 0) {
                int scaledOp = (int) Math.min(6.0E7F, progress / progressPerTick);
                int scaledMaxOp = (int) Math.min(6.0E7F, maxProgress / progressPerTick);
                addProgressBar(helper, scaledOp, scaledMaxOp);
            }
            if (advProgress > 0) {
                addProgressBar(helper, advProgress, advMaxProgress);
            }
        }
    }

    public void addProgressBar(IJadeHelper helper, int current, int max) {
        bar(helper, current, max, translatable("probe.progress.full.name", current, max), ColorUtils.PROGRESS);
    }
}
