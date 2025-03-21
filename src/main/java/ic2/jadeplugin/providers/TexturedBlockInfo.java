package ic2.jadeplugin.providers;

import ic2.core.block.misc.textured.TexturedBlockBlock;
import ic2.core.block.misc.textured.TexturedSlabBlock;
import ic2.core.block.misc.textured.TexturedStairsBlock;
import ic2.core.block.misc.textured.TexturedWallBlock;
import ic2.jadeplugin.JadeTags;
import ic2.jadeplugin.base.removals.ModNameRender;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec2;
import snownee.jade.Jade;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.Identifiers;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.ui.TextElement;
import snownee.jade.util.ModIdentification;

import javax.annotation.Nullable;

public class TexturedBlockInfo implements IBlockComponentProvider {

    public static final TexturedBlockInfo INSTANCE = new TexturedBlockInfo();

    @Override
    public @Nullable IElement getIcon(BlockAccessor blockAccessor, IPluginConfig config, IElement currentIcon) {
        ItemStack icon = ItemStack.EMPTY;
        Block block = blockAccessor.getBlock();
        if (block instanceof TexturedBlockBlock || block instanceof TexturedSlabBlock ||
                block instanceof TexturedWallBlock || block instanceof TexturedStairsBlock) {
            icon = getFakeIcon(block, blockAccessor);
        }
        return IElementHelper.get().item(icon);
    }

    public static ItemStack getFakeIcon(Block block, BlockAccessor blockAccessor) {
        return block.getCloneItemStack(blockAccessor.getBlockState(), blockAccessor.getHitResult(), blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getPlayer());
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        Block block = blockAccessor.getBlock();
        if (block instanceof TexturedBlockBlock || block instanceof TexturedSlabBlock ||
                block instanceof TexturedWallBlock || block instanceof TexturedStairsBlock) {
            addFakeInfo(block, iTooltip, blockAccessor);
        }
    }

    public static void addFakeInfo(Block texturedBlock, ITooltip iTooltip, BlockAccessor blockAccessor) {
        ItemStack fakeStack = getFakeIcon(texturedBlock, blockAccessor);
        iTooltip.remove(Identifiers.CORE_OBJECT_NAME); // remove block name
        iTooltip.remove(Identifiers.CORE_MOD_NAME); // remove mod name
        iTooltip.remove(ModNameRender.RELOCATE); // disable relocator
        Component fakeNameComponent = fakeStack.getHoverName().copy().withStyle(ChatFormatting.WHITE);
        String fakeModName = ModIdentification.getModName(fakeStack);
        String fakeModNameFormatted = String.format(Jade.CONFIG.get().getFormatting().getModName(), fakeModName);
        // add fake block name
        iTooltip.add(new TextElement(fakeNameComponent).size(new Vec2(Minecraft.getInstance().font.width(fakeNameComponent.getString()) + 16, Minecraft.getInstance().font.lineHeight)));
        // add fake block modid
        iTooltip.add(Component.literal(fakeModNameFormatted));
    }

    @Override
    public ResourceLocation getUid() {
        return JadeTags.INFO_RENDERER;
    }
}
