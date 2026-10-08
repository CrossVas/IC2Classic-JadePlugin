package ic2.jadeplugin.base;

import ic2.core.IC2;
import ic2.core.utils.plugins.IRegistryProvider;
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

public final class JadeCommonHandler {

    public static final JadeCommonHandler THIS = new JadeCommonHandler();

    private final List<IInfoProvider> INFO_PROVIDERS = new ObjectArrayList<>();
    private boolean initialized;

    private JadeCommonHandler() {}

    public void init() {
        if (initialized) {
            return;
        }

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
                BasicTubeInfo.THIS,
                WikiInfo.THIS
        );
        initialized = true;
    }

    public void registerProviders(IInfoProvider... providers) {
        INFO_PROVIDERS.addAll(List.of(providers));
    }

    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity == null || !(blockEntity.getBlockState().getBlock() instanceof IRegistryProvider provider) || !provider.getRegistryName().getNamespace().equals(IC2.MOD_ID)) {
            return; // early exit
        }

        for (IInfoProvider infoProvider : INFO_PROVIDERS) {
            if (infoProvider.canHandle(player)) {
                infoProvider.addInfo(helper, blockEntity, player);
            }
        }
    }
}
