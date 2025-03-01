package ic2.jadeplugin.base;

import ic2.jadeplugin.base.interfaces.IInfoProvider;
import ic2.jadeplugin.providers.*;
import ic2.jadeplugin.providers.expansions.FluidExpansionInfo;
import ic2.jadeplugin.providers.expansions.MemoryExpansionInfo;
import ic2.jadeplugin.providers.expansions.StorageExpansionInfo;
import ic2.jadeplugin.providers.expansions.UUMExpansionInfo;
import ic2.jadeplugin.providers.transport.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class JadeCommonHandler {

    public static final JadeCommonHandler THIS = new JadeCommonHandler();

    protected List<IInfoProvider> INFO_PROVIDERS = new ObjectArrayList<>();

    public void init() {
        registerProviders(
                EUStorageInfo.THIS,
                AdjustableTransformerInfo.THIS,
                BarrelInfo.THIS,
                BaseEnergyStorageInfo.THIS,
                BaseGeneratorInfo.THIS,
                BaseMachineInfo.THIS,
                BaseMultiBlockMachineInfo.THIS,
                BaseTeleporterInfo.THIS,
                BasicPipeInfo.THIS,
                BatteryStationInfo.THIS,
                CableInfo.THIS,
                ChargePadInfo.THIS,
                ChargingBenchInfo.THIS,
                ColorFilterTubeInfo.THIS,
                CropInfo.THIS,
                CropLibraryInfo.THIS,
                DirectionalTubeInfo.THIS,
                DynamicTankInfo.THIS,
                ElectricBlockInfo.THIS,
                ElectricFisherInfo.THIS,
                ElectricLoaderInfo.THIS,
                ElectricUnloaderInfo.THIS,
                ElectricWoodGassifierInfo.THIS,
                ElectrolyzerInfo.THIS,
                ExtractionTubeInfo.THIS,
                FilterTubeInfo.THIS,
                FilteredExtractionTubeInfo.THIS,
                FluidExpansionInfo.THIS,
                FluidOMatInfo.THIS,
                FuelBoilerInfo.THIS,
                InsertionTubeInfo.THIS,
                LimiterTubeInfo.THIS,
                LuminatorInfo.THIS,
                MemoryExpansionInfo.THIS,
                MinerInfo.THIS,
                NuclearInfo.THIS,
                OceanGenInfo.THIS,
                OreScannerInfo.THIS,
                PersonalInfo.THIS,
                PickupTubeInfo.THIS,
                PipePumpInfo.THIS,
                PlasmafierInfo.THIS,
                ProviderTubeInfo.THIS,
                PumpInfo.THIS,
                PushingValveInfo.THIS,
                RangedPumpInfo.THIS,
                RedirectorMasterInfo.THIS,
                RedirectorSlaveInfo.THIS,
                RequestTubeInfo.THIS,
                RoundRobinTubeInfo.THIS,
                SolarPanelInfo.THIS,
                StackingTubeInfo.THIS,
                SteamTunnelInfo.THIS,
                SteamTurbineInfo.THIS,
                StoneMachineInfo.THIS,
                StorageExpansionInfo.THIS,
                TeleportTubeInfo.THIS,
                TeleporterInfo.THIS,
                ThermonuclearReactorInfo.THIS,
                TransformerInfo.THIS,
                UUMExpansionInfo.THIS,
                UraniumEnricherInfo.THIS,
                VillagerOMatInfo.THIS,
                WaveGenInfo.THIS,
                WindmillGenInfo.THIS,
                IndustrialWorkbenchInfo.THIS,
                BasicTubeInfo.THIS
        );
    }

    public void registerProviders(IInfoProvider... providers) {
        INFO_PROVIDERS.addAll(List.of(providers));
    }

    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity != null) {
            INFO_PROVIDERS.forEach(infoProvider -> {
                if (infoProvider.canHandle(player)) {
                    infoProvider.addInfo(helper, blockEntity, player);
                }
            });
        }
    }
}
