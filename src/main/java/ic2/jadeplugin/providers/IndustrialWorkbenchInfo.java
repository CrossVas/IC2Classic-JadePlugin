package ic2.jadeplugin.providers;

import ic2.core.block.machines.logic.crafter.CraftRecipe;
import ic2.core.block.machines.tiles.nv.IndustrialWorkbenchTileEntity;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;

public class IndustrialWorkbenchInfo implements IInfoProvider {

    public static final IndustrialWorkbenchInfo THIS = new IndustrialWorkbenchInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof IndustrialWorkbenchTileEntity workbench) {
            List<ItemStack> recipeOutputs = new ArrayList<>();
            for (int i = 0; i < 27; i++) {
                CraftRecipe recipe = workbench.recipes.getRecipe(i);
                if (recipe != null && recipe.isValid()) {
                    recipeOutputs.add(recipe.getDisplayItem());
                }
            }
            if (!recipeOutputs.isEmpty()) {
                helper.addGrid(recipeOutputs, translate("ic2.probe.memory_expansion.can_craft.name").withStyle(ChatFormatting.YELLOW));
            }
        }

    }
}
