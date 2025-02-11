package ic2.jadeplugin.providers;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.api.reactor.ISteamReactor;
import ic2.core.block.generators.tiles.ElectricNuclearReactorTileEntity;
import ic2.core.block.generators.tiles.SteamReactorChamberTileEntity;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.ColorUtils;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import ic2.jadeplugin.helpers.Formatter;
import ic2.jadeplugin.helpers.TextFormatter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NuclearInfo implements IInfoProvider {

    public static final NuclearInfo THIS = new NuclearInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof IReactor) {
            addTooltips(helper, blockEntity, player);
        }
        if (blockEntity instanceof IReactorChamber chamber) {
            addTooltips(helper, (BlockEntity) chamber.getReactor(), player);
        }
        if (blockEntity instanceof SteamReactorChamberTileEntity steamChamber) {
            helper.addTankInfo(steamChamber);
        }
    }

    public void addTooltips(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof IReactor reactor) {
            if (blockEntity instanceof ElectricNuclearReactorTileEntity nuclearReactor) {
                helper.defaultText("ic2.probe.eu.output.current.name", TextFormatter.GREEN.literal(Formatter.formatNumber(nuclearReactor.getProvidedEnergy(), 3)));
                helper.defaultText("ic2.probe.reactor.breeding.name", TextFormatter.GREEN.literal(reactor.getHeat() / 3000 + 1 + ""));
            } else if (blockEntity instanceof ISteamReactor steamReactor) {
                helper.defaultText("ic2.probe.steam.output.name", TextFormatter.GREEN.literal(Formatter.THERMAL_GEN.format(steamReactor.getEnergyOutput() * 3.200000047683716)));
                helper.defaultText("ic2.probe.water.consumption.name", TextFormatter.GREEN.literal(Formatter.THERMAL_GEN.format(steamReactor.getEnergyOutput() / 50.0)));
                helper.defaultText("ic2.probe.pump.pressure", TextFormatter.GREEN.literal(100 + ""));
                helper.defaultText("ic2.probe.pump.amount", TextFormatter.GREEN.literal(Formatters.EU_FORMAT.format(20000)));
                helper.addTankInfo(blockEntity);
            }

            if (StackUtil.hasHotbarItems(player, SpecialFilters.THERMOMETER) || player.isCreative()) {
                helper.bar(reactor.getHeat(), reactor.getMaxHeat(), Component.translatable("ic2.probe.reactor.heat.name",
                        Formatter.formatNumber(reactor.getHeat(), 4), Formatter.formatNumber(reactor.getMaxHeat(), 2)), getReactorColor(reactor.getHeat(), reactor.getMaxHeat()));
            }
        }
    }

    public static int getReactorColor(int current, int max) {
        float progress = (float) current / max;
        if ((double) progress < 0.25) {
            return ColorUtils.GREEN;
        } else if ((double) progress < 0.5) {
            return -1189115;
        } else {
            return (double) progress < 0.75 ? -1203707 : ColorUtils.RED;
        }
    }
}
