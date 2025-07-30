package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import dev.crossvas.jadexic2c.utils.EnergyContainer;
import dev.crossvas.jadexic2c.utils.TextFormatter;
import ic2.core.block.base.tile.TileEntityTransformer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TransformerInfo implements IInfoProvider {

    public static final TransformerInfo THIS = new TransformerInfo();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof TileEntityTransformer) {
            TileEntityTransformer transformer = (TileEntityTransformer) blockEntity;
            text(helper, TextFormatter.GOLD.translate("probe.transformer.inverted", status(transformer.isActive)));
            text(helper, maxIn(transformer.isActive ? transformer.lowOutput : transformer.highOutput));
            text(helper, translatable("probe.energy.output.max", transformer.isActive ? transformer.highOutput : transformer.lowOutput));
            text(helper, translatable("probe.packet.tick", transformer.isActive ? 1 : 4));
            EnergyContainer container = EnergyContainer.getContainer(transformer);
            addCableOut(helper, container);
        }
    }
}
