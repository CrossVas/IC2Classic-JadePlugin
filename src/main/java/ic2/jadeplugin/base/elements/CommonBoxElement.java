package ic2.jadeplugin.base.elements;

import ic2.jadeplugin.JadeTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class CommonBoxElement extends CommonElement {

    List<ItemStack> GRID_STACKS;
    int SIZE;

    public CommonBoxElement(List<ItemStack> gridStacks, int size) {
        super(Vec2.ZERO, "LEFT");
        this.GRID_STACKS = gridStacks;
        this.SIZE = size;
    }

    public List<ItemStack> getGridStacks() {
        return this.GRID_STACKS;
    }

    public int getSize() {
        return this.SIZE;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (ItemStack stack : this.GRID_STACKS) {
            CompoundTag stackTag = new CompoundTag();
            stackTag.put("stack", stack.save(new CompoundTag()));
            stackTag.putInt("count", stack.getCount());
            list.add(stackTag);
        }
        if (!list.isEmpty()) tag.put("GridStacks", list);
        tag.putInt("size", this.SIZE);
        return tag;
    }

    public static CommonBoxElement load(CompoundTag tag) {
        List<ItemStack> gridStacks = new ObjectArrayList<>();
        ListTag list = tag.getList("GridStacks", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag stackTag = list.getCompound(i);
            ItemStack stack = ItemStack.of(stackTag.getCompound("stack"));
            int count = stackTag.getInt("count");
            stack.setCount(count);
            gridStacks.add(stack);
        }
        int size = tag.getInt("size");
        return new CommonBoxElement(gridStacks, size);
    }

    @Override
    public String getTagId() {
        return JadeTags.JADE_ADDON_BOX_TAG;
    }
}
