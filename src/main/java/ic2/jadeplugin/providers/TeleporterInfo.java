package ic2.jadeplugin.providers;

import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.block.machines.tiles.hv.TeleporterTileEntity;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.SanityHelper;
import ic2.core.utils.helpers.TeleportUtil;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import ic2.jadeplugin.helpers.Formatter;
import ic2.jadeplugin.helpers.TextFormatter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TeleporterInfo implements IInfoProvider {

    public static final TeleporterInfo THIS = new TeleporterInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof TeleporterTileEntity teleport) {
            helper.defaultText(translate("ic2.probe.teleporter.type", translate("ic2.probe.teleporter.type." + teleport.getProbeSendType().name().toLowerCase())));
            TeleporterTarget target = teleport.target;
            int cost = teleport.getBaseCost();
            long availableEnergy = teleport.getAvailableEnergy();
            if (target == null) {
                helper.defaultText("ic2.probe.teleporter.no_target");
            } else if (!teleport.hasMatchingType()) {
                helper.defaultText("ic2.probe.teleporter.invalid_target");
            } else {
                helper.defaultText("ic2.probe.teleporter.target", SanityHelper.toPascalCase(target.getDimension().location().getPath()), target.getTargetPosition().getX(), target.getTargetPosition().getY(), target.getTargetPosition().getZ());
            }
            int displayCost = cost;
            switch (teleport.getProbeSendType()) {
                case ENTITY:
                    displayCost = TeleportUtil.getWeightOfEntity(player, IC2.CONFIG.teleporterKeepItems.get()) * cost * 5;
                    break;
                case FLUID:
                    displayCapacity(helper, "ic2.probe.teleporter.capacity.fluid", (double) availableEnergy / cost * 10);
                    break;
                case ITEM:
                    displayCapacity(helper, "ic2.probe.teleporter.capacity.item", (double) availableEnergy / cost / 100 * 64);
                    break;
                case SPAWNER:
                    displayCost = cost * 25000;
            }
            helper.defaultText("ic2.probe.teleporter.cost", TextFormatter.GREEN.literal(Formatters.EU_FORMAT.format(displayCost)));
        }
    }

    private void displayCapacity(JadeHelper helper, String translationKey, double value) {
        helper.defaultText("ic2.probe.teleporter.capacity", translate(translationKey, Formatter.formatNumber(value, 6)));
    }
}
