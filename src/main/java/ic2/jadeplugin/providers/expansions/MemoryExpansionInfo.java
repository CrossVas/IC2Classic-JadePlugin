package ic2.jadeplugin.providers.expansions;

import ic2.core.block.machines.tiles.nv.MemoryExpansionTileEntity;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class MemoryExpansionInfo implements IInfoProvider {

    public static final MemoryExpansionInfo THIS = new MemoryExpansionInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof MemoryExpansionTileEntity memoryExpansion) {
            List<ItemStack> common = new ObjectArrayList<>();
            int j = memoryExpansion.crafting.getSlotCount();
            for (int i = 0; i < j; i++) {
                ItemStack recipeOutput = memoryExpansion.crafting.getStackInSlot(i);
                if (!recipeOutput.isEmpty()) {
                    common.add(recipeOutput);
                }
            }

            if (!common.isEmpty()) {
                helper.bar(common.size(), 18, translate("info.memory.recipes", common.size(), 18), -16733185);
                helper.addGrid(common, translate("ic2.probe.memory_expansion.can_craft.name").withStyle(ChatFormatting.YELLOW));
            }
        }
    }
}
