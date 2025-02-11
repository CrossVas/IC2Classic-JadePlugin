package ic2.jadeplugin.providers;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.impls.BaseEnergyStorageTileEntity;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import ic2.jadeplugin.helpers.EnergyContainer;
import ic2.jadeplugin.helpers.TextFormatter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BaseEnergyStorageInfo implements IInfoProvider {

    public static final BaseEnergyStorageInfo THIS = new BaseEnergyStorageInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof BaseEnergyStorageTileEntity energyStorage) {
            helper.tier(energyStorage.getSourceTier());
            helper.defaultText("info.energy.io", TextFormatter.GREEN.literal(EnergyNet.INSTANCE.getPowerFromTier(energyStorage.getSourceTier()) + ""));

            EnergyContainer container = EnergyContainer.getContainer(energyStorage);
            helper.addAveragesFull(container);
        }
    }
}
