package ic2.jadeplugin.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.ui.Element;
import snownee.jade.api.ui.IBoxElement;
import snownee.jade.api.ui.IBoxStyle;
import snownee.jade.api.ui.ITooltipRenderer;
import snownee.jade.impl.Tooltip;
import snownee.jade.overlay.DisplayHelper;
import snownee.jade.overlay.TooltipRenderer;

import java.util.List;

public class CustomBoxElement extends Element implements IBoxElement {

    TooltipRenderer TOOLTIP;
    IBoxStyle BOX;
    List<ItemStack> STACKS;
    int ROW_SIZE;

    public CustomBoxElement(Tooltip tooltip, IBoxStyle box, List<ItemStack> stacks, int rowSize) {
        this.TOOLTIP = new TooltipRenderer(tooltip, false);
        this.BOX = box;
        this.STACKS = stacks;
        this.ROW_SIZE = rowSize;
    }

    @Override
    public ITooltipRenderer getTooltipRenderer() {
        return this.TOOLTIP;
    }

    @Override
    public Vec2 getSize() {
        if (this.STACKS.isEmpty()) return Vec2.ZERO;

        int columns = this.ROW_SIZE;
        int rows = (int) Math.ceil((double) this.STACKS.size() / columns);

        int width = 18 * Math.min(this.STACKS.size(), columns) + 2;
        int height = 18 * rows + 2;
        return new Vec2(width, height);
    }



    @Override
    public void render(PoseStack matrixStack, float x, float y, float maxX, float maxY) {
        if (this.STACKS.isEmpty()) return;
        RenderSystem.enableBlend();
        matrixStack.pushPose();
        matrixStack.translate(x, y, 0.0F);
//        this.BOX.render(matrixStack, 0.0F, 0.0F, maxX - x, maxY - y - 2.0F);
        // Get grid size
        Vec2 size = this.getSize();
        float width = size.x;
        float height = size.y;

        // Render the box based on the grid size
        this.BOX.render(matrixStack, 0.0F, 0.0F, width, height - 2);
        int rowSize = this.ROW_SIZE;

        for (int index = 0; index < this.STACKS.size(); index++) {
            int xPos = (index % rowSize) * 18;
            int yPos = (index / rowSize) * 18;

            ItemStack stack = this.STACKS.get(index);
            DisplayHelper.INSTANCE.drawItem(matrixStack, 1 + xPos, 1 + yPos, stack, 1, null);
        }
        matrixStack.popPose();
    }
}
