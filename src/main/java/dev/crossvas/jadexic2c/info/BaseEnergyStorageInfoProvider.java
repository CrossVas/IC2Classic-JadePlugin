package dev.crossvas.jadexic2c.info;

import dev.crossvas.jadexic2c.IHelper;
import dev.crossvas.jadexic2c.JadeIC2CPluginHandler;
import dev.crossvas.jadexic2c.utils.Helpers;
import ic2.api.energy.EnergyNet;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.base.tiles.impls.BaseEnergyStorageTileEntity;
import ic2.core.utils.helpers.Formatters;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum BaseEnergyStorageInfoProvider implements IHelper {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (!canHandle(blockAccessor.getPlayer())) {
            return;
        }

        if (!blockAccessor.getServerData().contains("EnergyStorageInfo")) {
            return;
        }

        CompoundTag tag = blockAccessor.getServerData().getCompound("EnergyStorageInfo");
        if (blockAccessor.getBlockEntity() instanceof BaseInventoryTileEntity tile) {
            if (tile instanceof BaseEnergyStorageTileEntity energyStorage) {
                Helpers.text(iTooltip, "ic2.probe.eu.tier.name", EnergyNet.INSTANCE.getDisplayTier(energyStorage.getSourceTier()));
                Helpers.text(iTooltip, "ic2.probe.eu.output.name", tag.getInt("providedEnergy"));

                long averageIn = tag.getLong("averageIn");
                long averageOut = tag.getLong("averageOut");
                long packetsIn = tag.getLong("packetsIn");
                long packetsOut = tag.getLong("packetsOut");



                if (averageIn > 0 || averageOut > 0) {
                    if (averageIn > 0) {
                        Helpers.text(iTooltip, Component.translatable("tooltip.item.ic2.eu_reader.cable_flow_in", Formatters.EU_FORMAT.format((long)averageIn)).withStyle(ChatFormatting.AQUA));
                    }

                    if (averageOut > 0) {
                        Helpers.text(iTooltip, Component.translatable("tooltip.item.ic2.eu_reader.cable_flow_out", Formatters.EU_FORMAT.format((long)averageOut)).withStyle(ChatFormatting.AQUA));
                    }

                    if (packetsIn > 0) {
                        Helpers.text(iTooltip, Component.translatable("tooltip.item.ic2.eu_reader.packet_flow_in", Formatters.EU_READER_FORMAT.format((long)packetsIn)).withStyle(ChatFormatting.AQUA));
                    }

                    if (packetsOut > 0) {
                        Helpers.text(iTooltip, Component.translatable("tooltip.item.ic2.eu_reader.packet_flow_out", Formatters.EU_READER_FORMAT.format((long)packetsOut)).withStyle(ChatFormatting.AQUA));
                    }
                }
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        CompoundTag tag = new CompoundTag();
        if (blockEntity instanceof BaseInventoryTileEntity tile) {
            if (tile instanceof BaseEnergyStorageTileEntity energyStorage) {
                tag.putInt("providedEnergy", energyStorage.getProvidedEnergy());

                CableInfoProvider.EnergyContainer result = CableInfoProvider.getContainer(energyStorage);
                tag.putLong("averageIn", result.getAverageIn());
                tag.putLong("averageOut", result.getAverageOut());
                tag.putLong("packetsIn", result.getPacketsIn());
                tag.putLong("packetsOut", result.getPacketsOut());
            }
        }
        compoundTag.put("EnergyStorageInfo", tag);
    }

    @Override
    public ResourceLocation getUid() {
        return JadeIC2CPluginHandler.EU_READER_INFO;
    }

}
