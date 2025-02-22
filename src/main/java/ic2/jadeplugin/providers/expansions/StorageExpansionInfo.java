package ic2.jadeplugin.providers.expansions;

import ic2.core.block.machines.tiles.nv.BufferStorageExpansionTileEntity;
import ic2.core.block.machines.tiles.nv.StorageExpansionTileEntity;
import ic2.jadeplugin.base.JadeHelper;
import ic2.jadeplugin.base.interfaces.IInfoProvider;
import ic2.jadeplugin.helpers.TextFormatter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class StorageExpansionInfo implements IInfoProvider {

    public static final StorageExpansionInfo THIS = new StorageExpansionInfo();

    @Override
    public void addInfo(JadeHelper helper, BlockEntity blockEntity, Player player) {
        if (blockEntity instanceof BufferStorageExpansionTileEntity buffer) {
            List<ItemStack> stacks = new ObjectArrayList<>();
            int slotCount = buffer.inventory.getSlotCount();
            for (int i = 0; i < slotCount; i++) {
                ItemStack stack = buffer.inventory.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    stacks.add(stack);
                }
            }
            if (!stacks.isEmpty()) {
                helper.grid(stacks, TextFormatter.YELLOW.translate("info.storage.storage"), 9);
            }
        }
        if (blockEntity instanceof StorageExpansionTileEntity storage) {
            List<ItemStack> stacks = new ObjectArrayList<>();
            int slotCount = storage.inventory.getSlotCount();
            for (int i = 0; i < slotCount; i++) {
                ItemStack stack = storage.inventory.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    stacks.add(stack);
                }
            }
            if (!stacks.isEmpty()) {
                helper.grid(stacks, TextFormatter.YELLOW.translate("info.storage.storage"));
            }
        }
    }
}
