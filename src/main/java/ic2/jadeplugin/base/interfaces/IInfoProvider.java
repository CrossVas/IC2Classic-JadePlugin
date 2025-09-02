package ic2.jadeplugin.base.interfaces;

import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.item.tool.WikiItem;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.helpers.TextFormatter;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public interface IInfoProvider extends ILangHelper {

    IFilter READER = SpecialFilters.EU_READER;
    IFilter THERMOMETER = SpecialFilters.THERMOMETER;
    IFilter CROP_ANALYZER = SpecialFilters.CROP_SCANNER;
    IFilter ALWAYS = SpecialFilters.ALWAYS_TRUE;
    IFilter WIKI = itemStack -> itemStack.getItem() instanceof WikiItem;

    void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player);

    default IFilter getFilter() {
        return READER;
    }

    default boolean canHandle(Player player) {
        return StackUtil.hasHotbarItems(player, getFilter()) || player.isCreative();
    }

    default MutableComponent status(boolean status) {
        return status ? TextFormatter.GREEN.literal(true + "") : TextFormatter.RED.literal(false + "");
    }

    default boolean has(Player player, IFilter filter) {
        return StackUtil.hasHotbarItems(player, filter);
    }

    default void addWikiComponent(JadeHelper helper, Player player, BlockState blockState) {
        ResourceLocation location = ForgeRegistries.BLOCKS.getKey(blockState.getBlock());
        Objects.requireNonNull(location);
        String translatableWikiEntry = "wiki.ic2.preview." + location.getPath() + ".desc";
        if (has(player, WIKI) && Language.getInstance().has(translatableWikiEntry)) {
            if (player.isCrouching()) {
                helper.centered(TextFormatter.GOLD.translate("info.wiki"));
                helper.addWiki(TextFormatter.GRAY.translate(translatableWikiEntry));
            } else {
                helper.centered(TextFormatter.AQUA.translate("info.wiki.sneak"));
            }
        }
    }

    default void addWikiComponent(JadeHelper helper, Player player, String wikiEntry) {
        String translatable = "wiki.ic2.preview." + wikiEntry + ".desc";
        if (has(player, WIKI) && Language.getInstance().has(translatable)) {
            if (player.isCrouching()) {
                helper.centered(TextFormatter.GOLD.translate("info.wiki"));
                helper.addWiki(TextFormatter.GRAY.translate(translatable));
            }  else {
                helper.centered(TextFormatter.AQUA.translate("info.wiki.sneak"));
            }
        }
    }
}
