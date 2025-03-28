package ic2.jadeplugin.providers;

import ic2.core.block.misc.TreeTapAndBucketBlock;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.ColorUtils;
import ic2.jadeplugin.JadeTags;
import ic2.jadeplugin.elements.CustomBoxStyle;
import ic2.jadeplugin.elements.CustomProgressStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class TreetapAndBucketInfo implements IBlockComponentProvider {

    public static final TreetapAndBucketInfo THIS = new TreetapAndBucketInfo();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (StackUtil.hasHotbarItems(blockAccessor.getPlayer(), SpecialFilters.EU_READER) || blockAccessor.getPlayer().isCreative()) {
            if (blockAccessor.getBlock() instanceof TreeTapAndBucketBlock) {
                BlockState state = blockAccessor.getBlockState();
                int current = state.getValue(TreeTapAndBucketBlock.FILL_STAGE);
                if (current > 0) {
                    iTooltip.add(iTooltip.getElementHelper().progress((float) current / 5, Component.translatable("ic2.probe.progress.full.name", current, 5).withStyle(ChatFormatting.WHITE),
                            new CustomProgressStyle().color(-10996205, ColorUtils.darker(-10996205)), new CustomBoxStyle(ColorUtils.doubleDarker(-10996205)), true));
                }
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return JadeTags.INFO_RENDERER;
    }
}
