package dev.crossvas.jadexic2c.providers;

import dev.crossvas.jadexic2c.base.interfaces.IInfoProvider;
import dev.crossvas.jadexic2c.base.interfaces.IJadeHelper;
import dev.crossvas.jadexic2c.utils.TextFormatter;
import ic2.core.block.base.tile.TileEntityBlock;
import ic2.core.block.base.util.info.misc.IWrench;
import ic2.core.inventory.filters.IFilter;
import ic2.core.util.obj.IWrenchableTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class WrenchableInfo implements IInfoProvider {

    public static final WrenchableInfo THIS = new WrenchableInfo();

    @Override
    public void addInfo(IJadeHelper helper, TileEntity blockEntity, EntityPlayer player) {
        if (blockEntity instanceof IWrenchableTile) {
            IWrenchableTile wrenchableTile = (IWrenchableTile) blockEntity;
            int actualRate = (int) (wrenchableTile.getWrenchDropRate() * 100);
            ItemStack handItem = player.getHeldItemMainhand();
            boolean show;
            if (wrenchableTile instanceof TileEntityBlock) {
                TileEntityBlock machines = (TileEntityBlock) wrenchableTile;
                show = machines.canRemoveBlockProbe(player);
            } else {
                show = actualRate > 0;
            }
            if (show) {
                if (handItem.getItem() instanceof IWrench) {
                    textCentered(helper, TextFormatter.GRAY.translate("probe.wrenchable.drop_chance.info", TextFormatter.AQUA.literal(actualRate + "%")));
                } else {
                    textCentered(helper, TextFormatter.GOLD.translate("probe.wrenchable.info"));
                }
            }
        }
    }

    @Override
    public IFilter getFilter() {
        return ALWAYS;
    }
}
