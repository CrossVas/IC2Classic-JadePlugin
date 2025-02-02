package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.JadeHelper;
import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import ic2.api.energy.EnergyNet;
import ic2.core.block.machines.tiles.ev.PlasmafierTileEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PlasmafierInfo implements IInfoProvider {

    public static final PlasmafierInfo THIS = new PlasmafierInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof PlasmafierTileEntity plasmafier) {
            helper.maxIn(plasmafier.getMaxInput());
            helper.usage(10240);

            int plasma = plasmafier.getPumpProgress();
            int maxPlasma = plasmafier.getPumpMaxProgress();
            int uuMatter = plasmafier.uuMatter;
            if (plasma > 0) {
                helper.bar(plasma, maxPlasma, Component.translatable("ic2.probe.plasma.name", plasma, maxPlasma), -5829955);
            }
            if (uuMatter > 0) {
                helper.bar(uuMatter, 100, Component.translatable("ic2.probe.matter.name", uuMatter), -5829955);
            }
        }
    }
}
