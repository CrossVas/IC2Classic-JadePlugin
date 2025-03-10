package ic2.jadeplugin.providers;

import ic2.core.block.base.tiles.impls.machine.multi.BaseAdvMultiMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BaseColossalMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BaseMultiMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BasicMultiMachineTileEntity;
import ic2.core.utils.math.ColorUtils;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.text.DecimalFormat;

public class BaseMultiBlockMachineInfo implements IInfoProvider {

    public static final BaseMultiBlockMachineInfo THIS = new BaseMultiBlockMachineInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof BaseMultiMachineTileEntity multiMachine) {
            helper.maxIn(multiMachine.getMaxInput());
            helper.usage(multiMachine.getEnergyPerTick());
            if (multiMachine instanceof BaseAdvMultiMachineTileEntity adv) {
                int speed = adv.getSpeed();
                int maxSpeed = adv.getMaxSpeed();
                Component speedName = adv.getSpeedName();
                double scaledProgress = (double) speed / maxSpeed;
                if (speed > 0) {
                    helper.bar(speed, maxSpeed, speedName.plainCopy().append(": " + new DecimalFormat().format(scaledProgress * 100.0) + "%"), -295680);
                }
            }

            if (!multiMachine.isValid) {
                long time = multiMachine.clockTime(512);
                helper.bar((int) time, 512, translate("ic2.multiblock.reform.next", 512 - time), ColorUtils.GRAY);
            }

            if (multiMachine instanceof BasicMultiMachineTileEntity machineTile) {
                if (multiMachine.isMachineWorking() || machineTile.getProgress() > 0) {
                    helper.bar((int) machineTile.getProgress(), (int) machineTile.getMaxProgress(), translate("ic2.probe.progress.full.name", (int) machineTile.getProgress() / 1000, (int) machineTile.getMaxProgress() / 1000).append("t"), -16733185);
                }
            }

            if (multiMachine instanceof BaseColossalMachineTileEntity colossalMachine) {
                IntList maxProgressFilter = new IntArrayList();
                IntList progressFilter = new IntArrayList();
                IntIterator activeSlotsIterator = colossalMachine.getActiveSlots();
                label38:
                while (true) {
                    int progress;
                    int maxProgress;
                    int maxIndex;
                    int index;
                    do {
                        if (!activeSlotsIterator.hasNext()) {
                            for (int i = 0; i < progressFilter.size(); ++i) {
                                helper.bar(progressFilter.getInt(i), maxProgressFilter.getInt(i), translate("ic2.probe.progress.full.name", progressFilter.getInt(i) / 1000, maxProgressFilter.getInt(i) / 1000).append("t"), -16733185);
                            }
                            break label38;
                        }

                        int slot = activeSlotsIterator.nextInt();
                        progress = (int) colossalMachine.getProgress(slot);
                        maxProgress = (int) colossalMachine.getMaxProgress(slot);
                        maxIndex = maxProgressFilter.indexOf(maxProgress);
                        index = progressFilter.indexOf(progress);
                    } while (maxIndex == index && index != -1);

                    maxProgressFilter.add(maxProgress);
                    progressFilter.add(progress);
                }
            }
            helper.addTankInfo(multiMachine);
        }
    }
}
