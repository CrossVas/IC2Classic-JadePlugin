package ic2.jadeplugin.providers;

import ic2.core.block.machines.tiles.mv.RangedPumpTileEntity;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import ic2.jadeplugin.helpers.TextFormatter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RangedPumpInfo implements IInfoProvider {

    public static final RangedPumpInfo THIS = new RangedPumpInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof RangedPumpTileEntity ranged) {
            helper.maxIn(ranged.getMaxInput());
            helper.usage(10);

            helper.defaultText(TextFormatter.WHITE.translate(ranged.isOperating() ? "ic2.probe.miner.mining.name" : "ic2.probe.miner.retracting.name"));
            int y = ranged.getPipeTip().getY();
            helper.bar(y, ranged.getPosition().getY(), TextFormatter.WHITE.translate("ic2.probe.pump.progress.name", y), -10996205);
            helper.addTankInfo(ranged);
        }
    }
}
