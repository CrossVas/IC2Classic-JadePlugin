package ic2.jadeplugin.providers;

import ic2.core.block.machines.tiles.nv.StoneBasicMachineTileEntity;
import ic2.core.block.machines.tiles.nv.StoneCannerTileEntity;
import ic2.core.block.machines.tiles.nv.StoneWoodGassifierTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.ColorUtils;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import ic2.jadeplugin.helpers.TextFormatter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class StoneMachineInfo implements IInfoProvider {

    public static final StoneMachineInfo THIS = new StoneMachineInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof StoneBasicMachineTileEntity stoneMachine) {
            if (stoneMachine.getFuel() > 0) {
                helper.bar(stoneMachine.getFuel(), stoneMachine.getMaxFuel(), Component.translatable("ic2.probe.fuel.storage.name").append(String.valueOf(stoneMachine.getFuel())), ColorUtils.DARK_GRAY);
            }
            if (stoneMachine.getProgress() > 0) {
                helper.bar((int) stoneMachine.getProgress(), (int) stoneMachine.getMaxProgress(), Component.translatable("ic2.probe.progress.full.name", (int) stoneMachine.getProgress(), (int) stoneMachine.getMaxProgress()).append("t").withStyle(ChatFormatting.WHITE), -16733185);
            }
        }

        if (blockEntity instanceof StoneWoodGassifierTileEntity woodGassifierTile) {
            helper.defaultText("ic2.probe.pump.pressure", TextFormatter.GREEN.literal(25 + ""));
            helper.defaultText("ic2.probe.pump.amount", TextFormatter.GREEN.literal(Formatters.EU_FORMAT.format(900)));
            if (woodGassifierTile.getFuel() > 0) {
                helper.bar(woodGassifierTile.getFuel(), woodGassifierTile.getMaxFuel(), Component.translatable("ic2.probe.fuel.storage.name").append(String.valueOf(woodGassifierTile.getFuel())), ColorUtils.DARK_GRAY);
            }
            if (woodGassifierTile.getProgress() > 0) {
                helper.bar((int) woodGassifierTile.getProgress(), (int) woodGassifierTile.getMaxProgress(), Component.translatable("ic2.probe.progress.full.name", (int) woodGassifierTile.getProgress(), (int) woodGassifierTile.getMaxProgress()).append("t").withStyle(ChatFormatting.WHITE), -16733185);
            }
            helper.addTankInfo(woodGassifierTile);
        }
        if (blockEntity instanceof StoneCannerTileEntity stoneCannerTile) {
            if (stoneCannerTile.getFuel() > 0) {
                helper.bar(stoneCannerTile.getFuel(), stoneCannerTile.getMaxFuel(), Component.translatable("ic2.probe.fuel.storage.name").append(String.valueOf(stoneCannerTile.getFuel())), ColorUtils.DARK_GRAY);
            }
            if (stoneCannerTile.getProgress() > 0) {
                helper.bar((int) stoneCannerTile.getProgress(), (int) stoneCannerTile.getMaxProgress(), Component.translatable("ic2.probe.progress.full.name", (int) stoneCannerTile.getProgress(), (int) stoneCannerTile.getMaxProgress()).append("t").withStyle(ChatFormatting.WHITE), -16733185);
            }
        }
    }
}
