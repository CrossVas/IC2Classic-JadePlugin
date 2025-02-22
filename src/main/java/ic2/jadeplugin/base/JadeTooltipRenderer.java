package ic2.jadeplugin.base;

import com.google.gson.JsonObject;
import ic2.core.utils.math.ColorUtils;
import ic2.jadeplugin.JadeTags;
import ic2.jadeplugin.base.elements.*;
import ic2.jadeplugin.elements.CustomBoxElement;
import ic2.jadeplugin.elements.CustomBoxStyle;
import ic2.jadeplugin.elements.CustomProgressStyle;
import ic2.jadeplugin.elements.CustomTextElement;
import ic2.jadeplugin.helpers.Formatter;
import ic2.jadeplugin.helpers.TextFormatter;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.fluids.FluidStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.*;
import snownee.jade.impl.ui.ProgressStyle;

import java.util.List;
import java.util.Locale;

import static ic2.jadeplugin.JadeTags.*;

@SuppressWarnings("all")
public class JadeTooltipRenderer implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {

    public static final JadeTooltipRenderer INSTANCE = new JadeTooltipRenderer();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        appendTooltips(iTooltip, blockAccessor, iPluginConfig);
    }

    private void appendTooltips(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        boolean forceTOPStyle = config.get(TOP_STYLE);
        boolean showIC2Tanks = config.get(TANK_RENDER);
        TextFormatter defaultFormat = forceTOPStyle ? TextFormatter.WHITE : TextFormatter.GRAY;
        CompoundTag serverData = accessor.getServerData();
        IElementHelper helper = tooltip.getElementHelper();
        if (serverData.contains(TAG_DATA, Tag.TAG_LIST)) {
            ListTag tagList = serverData.getList(TAG_DATA, Tag.TAG_COMPOUND);
            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag serverTag = tagList.getCompound(i);
                // padding
                if (serverTag.contains(JADE_ADDON_PADDING_TAG)) {
                    CompoundTag elementTag = serverTag.getCompound(JADE_ADDON_PADDING_TAG);
                    CommonPaddingElement paddingElement = CommonPaddingElement.load(elementTag);
                    int x = paddingElement.getX();
                    int y = paddingElement.getY();
                    IElement jadeElement = tooltip.getElementHelper().spacer(x, y);
                    addElement(tooltip, jadeElement, elementTag);
                }
                // text
                if (serverTag.contains(JADE_ADDON_TEXT_TAG)) {
                    CompoundTag elementTag = serverTag.getCompound(JADE_ADDON_TEXT_TAG);
                    CommonTextElement textElement = CommonTextElement.load(elementTag);
                    boolean centered = textElement.isCentered();
                    IElement jadeElement = new CustomTextElement(format(textElement.getText(), defaultFormat)).centered(centered).translate(textElement.getTranslation()).align(IElement.Align.valueOf(textElement.getSide()));
                    addElement(tooltip, jadeElement, elementTag);
                }
                // bar
                if (serverTag.contains(JADE_ADDON_BAR_TAG)) {
                    CompoundTag elementTag = serverTag.getCompound(JADE_ADDON_BAR_TAG);
                    CommonBarElement barElement = CommonBarElement.load(elementTag);
                    int color = barElement.getColor();
                    int current = barElement.getCurrent();
                    int max = barElement.getMax();
                    BoxStyle boxStyle = forceTOPStyle ? new CustomBoxStyle(ColorUtils.doubleDarker(color)) : BoxStyle.DEFAULT;
                    IProgressStyle progressStyle = forceTOPStyle ? new CustomProgressStyle().color(color, ColorUtils.darker(color)) : new ProgressStyle().color(color, ColorUtils.darker(color));
                    Component label = barElement.getText();
                    IElement jadeElement = helper.progress((float) current / max, label, progressStyle, boxStyle, true);
                    addElement(tooltip, jadeElement, elementTag);
                }
                // item
                if (serverTag.contains(JADE_ADDON_ITEM_TAG)) {
                    CompoundTag elementTag = serverTag.getCompound(JADE_ADDON_ITEM_TAG);
                    CommonItemStackElement stackElement = CommonItemStackElement.load(elementTag);
                    ItemStack stack = stackElement.getStack();
                    IElement jadeElement = tooltip.getElementHelper().item(stack).size(new Vec2(16, 16)).translate(stackElement.getTranslation()).align(IElement.Align.valueOf(stackElement.getSide()));
                    addElement(tooltip, jadeElement, elementTag);
                }
                // fluid
                if (serverTag.contains(JADE_ADDON_FLUID_TAG) && showIC2Tanks) {
                    CompoundTag elementTag = serverTag.getCompound(JADE_ADDON_FLUID_TAG);
                    CommonFluidBarElement fluidElement = CommonFluidBarElement.load(elementTag);
                    FluidStack fluid = fluidElement.getFluid();
                    int fluidAmount = fluid.getAmount();
                    int max = fluidElement.getMax();
                    boolean ignoreCapacity = fluidElement.ignoreCapacity();
                    if (fluidAmount > 0) {
                        if (forceTOPStyle) {
                            Component fluidComp = ignoreCapacity ? defaultFormat.component(fluid.getDisplayName()) :
                                    defaultFormat.translate("ic2.barrel.info.fluid", fluid.getDisplayName(), Formatter.formatNumber(fluidAmount, String.valueOf(fluidAmount).length() - 1), Formatter.formatNumber(max, String.valueOf(max).length() - 1));
                            IProgressStyle progressStyle = helper.progressStyle().overlay(helper.fluid(fluid));
                            tooltip.add(helper.progress((float) fluid.getAmount() / max, fluidComp, progressStyle,
                                    new CustomBoxStyle(ColorUtils.doubleDarker(JadeHelper.getColorForFluid(fluid))), true));
                        } else {
                            String current = IDisplayHelper.get().humanReadableNumber(fluid.getAmount(), "B", true);
                            String maxS = IDisplayHelper.get().humanReadableNumber(max, "B", true);
                            Component text;
                            if (ignoreCapacity) {
                                text = fluid.getDisplayName();
                            } else {
                                if (accessor.showDetails()) {
                                    text = Component.translatable("jade.fluid2", IDisplayHelper.get().stripColor(fluid.getDisplayName()).withStyle(ChatFormatting.WHITE), Component.literal(current).withStyle(ChatFormatting.WHITE), maxS).withStyle(ChatFormatting.GRAY);
                                } else {
                                    text = Component.translatable("jade.fluid", IDisplayHelper.get().stripColor(fluid.getDisplayName()), current);
                                }
                            }
                            IProgressStyle progressStyle = helper.progressStyle().overlay(helper.fluid(fluid));
                            tooltip.add(helper.progress((float) fluidAmount / max, text, progressStyle, BoxStyle.DEFAULT, true));
                        }
                    }
                }
                // box
                if (serverTag.contains(JADE_ADDON_BOX_TAG)) {
                    CompoundTag elementTag = serverTag.getCompound(JADE_ADDON_BOX_TAG);
                    CommonBoxElement boxElement = CommonBoxElement.load(elementTag);
                    List<ItemStack> gridStacks = boxElement.getGridStacks();
                    int size = boxElement.getSize();
                    CustomBoxElement box = new CustomBoxElement(forceTOPStyle ? new CustomBoxStyle() : BoxStyle.DEFAULT, gridStacks, size);
                    tooltip.add(box);
                }
            }
        }
    }

    public void addElement(ITooltip iTooltip, IElement jadeElement, CompoundTag elementTag) {
        boolean add = elementTag.getBoolean(JadeHelper.ADD_TAG);
        boolean append = elementTag.getBoolean(JadeHelper.APPEND_TAG);
        if (add) {
            iTooltip.add(jadeElement);
        }
        if (append) {
            iTooltip.append(jadeElement);
        }
    }

    public static Component format(Component component, TextFormatter defaultFormatting) {
        TextFormatter formatting = defaultFormatting;
        JsonObject json = Component.Serializer.toJsonTree(component).getAsJsonObject();
        if (json.has("color")) {
            String color = json.get("color").getAsString();
            if (!color.isEmpty()) {
                TextFormatter existing = TextFormatter.valueOf(color.toUpperCase(Locale.ROOT));
                if (existing != TextFormatter.WHITE) { // every other formatting color
                    formatting = existing;
                }
            }
        }
        return formatting.component(component);
    }

    @Override
    public ResourceLocation getUid() {
        return JadeTags.INFO_RENDERER;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        JadeHelper helper = new JadeHelper();
        JadeCommonHandler.THIS.addInfo(helper, blockEntity, serverPlayer);
        helper.transferData(compoundTag);
    }
}
