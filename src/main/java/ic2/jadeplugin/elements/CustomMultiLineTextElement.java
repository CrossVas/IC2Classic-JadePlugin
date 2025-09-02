package ic2.jadeplugin.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec2;
import snownee.jade.impl.ui.TextElement;
import snownee.jade.overlay.OverlayRenderer;

import java.util.List;

public class CustomMultiLineTextElement extends TextElement {

    private float scale = 1;

    public CustomMultiLineTextElement(Component component) {
        super(component);
    }

    @Override
    public Vec2 getSize() {
        Font font = Minecraft.getInstance().font;
        List<FormattedCharSequence> wrappedLines = font.split(text, (int)(135 / scale));
        float height = wrappedLines.size() * font.lineHeight * scale;
        return new Vec2(135, height + 4);
    }

    @Override
    public void render(PoseStack matrixStack, float x, float y, float maxX, float maxY) {
        matrixStack.pushPose();
        Font font = Minecraft.getInstance().font;
        // Split the text into wrapped lines
        List<FormattedCharSequence> wrappedLines = font.split(text, (int)(135 / scale));

        matrixStack.translate(x, y + scale, 0);
        matrixStack.scale(scale, scale, 1);

        int color = OverlayRenderer.normalTextColorRaw;

        for (int i = 0; i < wrappedLines.size(); i++) {
            FormattedCharSequence line = wrappedLines.get(i);
            font.draw(matrixStack, line, 0, i * font.lineHeight, color);
        }
        matrixStack.popPose();
    }
}
