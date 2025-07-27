package dev.crossvas.waila.ic2.providers;

import dev.crossvas.waila.ic2.base.interfaces.IInfoProvider;
import dev.crossvas.waila.ic2.base.interfaces.IWailaHelper;
import dev.crossvas.waila.ic2.utils.ColorUtils;
import ic2.core.block.machine.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;

import java.text.DecimalFormat;

public class BaseMachineInfo implements IInfoProvider {

    public static final BaseMachineInfo THIS = new BaseMachineInfo();

    @Override
    public void addInfo(IWailaHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityElecMachine) {
            TileEntityElecMachine machine = (TileEntityElecMachine) blockEntity;
            IChatComponent tier = tier(machine.tier);
            IChatComponent maxIn = maxIn(machine.maxInput);
            IChatComponent usage = usage(machine.getEnergyUsage());
            if (machine instanceof TileEntityMiner ||
                machine instanceof TileEntityMatter ||
                machine instanceof TileEntityTerra ||
                machine instanceof TileEntityOreScanner ||
                machine instanceof TileEntityUraniumEnricher) {
                usage = null;
            }
            int progress = 0;
            int maxProgress = 0;

            int advProgress = 0;
            int advMaxProgress = 0;

            if (machine instanceof TileEntityElectricMachine) {
                TileEntityElectricMachine electricMachine = (TileEntityElectricMachine) machine;
                progress = electricMachine.progress;
                maxProgress = electricMachine.operationLength;
            }

            float speed = 0;
            float maxSpeed = 0;
            String name = "";
            double scaledProgress = 0;

            if (machine instanceof TileEntityAdvancedMachine) {
                TileEntityAdvancedMachine advMachine = (TileEntityAdvancedMachine) machine;
                progress = advMachine.progress;
                speed = advMachine.speed;
                maxSpeed = advMachine.MaxSpeed;
                scaledProgress = speed / maxSpeed;
                if (advMachine instanceof TileEntityInduction) {
                    name = "probe.speed.heat";
                } else if (advMachine instanceof TileEntityRotary) {
                    name = "probe.speed.rotation";
                } else if (advMachine instanceof TileEntitySingularity) {
                    name = "probe.speed.pressure";
                } else if (advMachine instanceof TileEntityCentrifuge || advMachine instanceof TileEntityCompacting) {
                    name = "probe.speed.speed";
                } else if (advMachine instanceof TileEntityVacuumCanner) {
                    name = "probe.speed.vacuum";
                }
                int operationsPerTick = advMachine.speed / 30;
                advProgress = (int) Math.min(6.0E7F, (float) progress / operationsPerTick);
                advMaxProgress = (int) Math.min(6.0E7F, (float) 4000 / operationsPerTick);
            }

            // tier
            if (tier != null) text(helper, tier);
            // maxIn
            if (maxIn != null) text(helper, maxIn);
            // usage
            if (usage != null) text(helper, usage);
            if (speed > 0) {
                bar(helper, (int) speed, (int) maxSpeed, translate(name, new DecimalFormat().format(scaledProgress * 100.0)), ColorUtils.SPEED);
            }
            if (progress > 0 && advProgress == 0) {
                addProgressBar(helper, progress, maxProgress);
            }
            if (advProgress > 0) {
                addProgressBar(helper, advProgress, advMaxProgress);
            }
        }
    }

    public void addProgressBar(IWailaHelper helper, int current, int max) {
        bar(helper, current, max, translate("probe.progress.full.name", current, max), ColorUtils.PROGRESS);
    }
}
