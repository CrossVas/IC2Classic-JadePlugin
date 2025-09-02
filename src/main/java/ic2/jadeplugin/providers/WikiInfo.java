package ic2.jadeplugin.providers;

import ic2.api.reactor.IReactorChamber;
import ic2.core.block.base.tiles.impls.*;
import ic2.core.block.generators.tiles.SolarPanelTileEntity;
import ic2.core.block.generators.tiles.WaterMillTileEntity;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class WikiInfo implements IInfoProvider {

    public static WikiInfo THIS = new WikiInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof SolarPanelTileEntity.LVSolarPanelTileEntity ||
            blockEntity instanceof SolarPanelTileEntity.MVSolarPanelTileEntity ||
            blockEntity instanceof SolarPanelTileEntity.HVSolarPanelTileEntity) {
            addWikiComponent(helper, player, "compact_solar_panel");
        } else if (blockEntity instanceof WaterMillTileEntity.LVWaterMillTileEntity ||
            blockEntity instanceof WaterMillTileEntity.MVWaterMillTileEntity ||
            blockEntity instanceof WaterMillTileEntity.HVWaterMillTileEntity) {
            addWikiComponent(helper, player, "compact_watermill");
        } else if (blockEntity instanceof IReactorChamber chamber) {
            addWikiComponent(helper, player, ((BlockEntity) chamber.getReactor()).getBlockState());
        } else if (blockEntity instanceof BaseEnergyStorageTileEntity) {
            addWikiComponent(helper, player, "energy_storage");
        } else if (blockEntity instanceof BaseChargingBenchTileEntity) {
            addWikiComponent(helper, player, "charging_bench");
        } else if (blockEntity instanceof BaseBatteryStationTileEntity) {
            addWikiComponent(helper, player, "battery_station");
        } else if (blockEntity instanceof BaseChargePadTileEntity) {
            addWikiComponent(helper, player, "chargepad");
        } else if (blockEntity instanceof BaseFluxGeneratorTileEntity) {
            addWikiComponent(helper, player, "flux_generator");
        } else {
            addWikiComponent(helper, player, blockEntity.getBlockState());
        }
    }
}
