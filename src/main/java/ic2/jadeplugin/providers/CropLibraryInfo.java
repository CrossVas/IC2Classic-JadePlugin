package ic2.jadeplugin.providers;

import ic2.api.energy.tile.IEnergySink;
import ic2.core.block.base.tiles.impls.BaseCropLibraryTileEntity;
import ic2.core.block.machines.tiles.ev.UUCropLibraryTileEntity;
import ic2.core.utils.helpers.StackUtil;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class CropLibraryInfo implements IInfoProvider {

    public static final CropLibraryInfo THIS = new CropLibraryInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof BaseCropLibraryTileEntity baseCropLibrary) {
            if (baseCropLibrary instanceof IEnergySink sink) {
                helper.maxInFromTier(sink.getSinkTier());
                helper.usage(1);
            }

            if (baseCropLibrary instanceof UUCropLibraryTileEntity uum) {
                helper.bar(uum.uu_matter, 512, string("UU Matter: " + uum.uu_matter + " / " + 512), -5829955);
            }

            int cropCount = baseCropLibrary.syncer.getCropCount();
            int statCount = baseCropLibrary.syncer.getStatCount();
            int sizeLimit = baseCropLibrary.storage.getSizeLimit();
            int typeLimit = baseCropLibrary.storage.getTypeLimit();
            int statLimit = baseCropLibrary.storage.getStatLimit();

            if (typeLimit != -1) {
                helper.defaultText("ic2.probe.crop_library.type.name", cropCount, typeLimit);
                helper.defaultText("ic2.probe.crop_library.stat.name", statCount, statLimit * typeLimit);
                helper.defaultText("ic2.probe.crop_library.size.name", sizeLimit);
            }

            List<ItemStack> stackList = StackUtil.copyNonEmpty(baseCropLibrary.storage.getTypes());
            if (!stackList.isEmpty())
                helper.grid(stackList, translate("ic2.probe.crop_library.name").withStyle(ChatFormatting.YELLOW));
        }
    }
}
