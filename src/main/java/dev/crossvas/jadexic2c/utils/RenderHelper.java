package dev.crossvas.jadexic2c.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

public class RenderHelper {

    public static final RenderHelper THIS = new RenderHelper();

    private static final ScissorsStack STACK = new ScissorsStack();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawScrollingString(FontRenderer font, String text, float centerX, float centerY, float width, float height, int color) {
        int textWidth = font.getStringWidth(text);
        float drawY = centerY - (font.FONT_HEIGHT / 2F);

        float boxX = centerX - (width / 2F);
        float boxY = centerY - (height / 2F);
        int clipX = MathHelper.floor(boxX);
        int clipY = MathHelper.floor(boxY);
        int clipW = MathHelper.ceil(width);
        int clipH = MathHelper.ceil(height);

        if (textWidth > width) {
            float scrollOffset = getScrollOffset(width, textWidth);
            float alignedX = boxX + 2 - scrollOffset;

            pushScissors(clipX, clipY, clipW, clipH);
            font.drawStringWithShadow(text, (int) alignedX, (int) drawY, color);
            popScissors();
        } else {
            float drawX = centerX - (textWidth / 2F);
            font.drawStringWithShadow(text, (int) drawX, (int) drawY, color);
        }
    }

    public static void pushScissors(int x, int y, int width, int height) {
        pushScissors(new ScreenRectangle(x, y, width, height));
    }

    public static void pushScissors(ScreenRectangle rect) {
        STACK.push(rect);
        applyScissors(rect);
    }

    public static void popScissors() {
        applyScissors(STACK.pop());
    }

    private static void applyScissors(ScreenRectangle rect) {
        if (rect == null) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            return;
        }

        ScaledResolution resolution = new ScaledResolution(mc);
        double scaleX = (double) mc.displayWidth / resolution.getScaledWidth();
        double scaleY = (double) mc.displayHeight / resolution.getScaledHeight();

        int x = (int) (rect.getX() * scaleX);
        int y = (int) ((resolution.getScaledHeight() - rect.getY() - rect.getHeight()) * scaleY);
        int w = (int) (rect.getWidth() * scaleX);
        int h = (int) (rect.getHeight() * scaleY);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x, y, w, h);
    }

    private static float getScrollOffset(float width, int textWidth) {
        float scrollRange = textWidth - width + 4F;
        float scrollSpeed = 20F;

        long now = System.currentTimeMillis();
        float totalScrollTime = scrollRange / scrollSpeed;
        float cycleTime = totalScrollTime * 2F;

        float timeInCycle = (now % (long) (cycleTime * 1000L)) / 1000F;
        boolean reverse = timeInCycle >= totalScrollTime;
        float timeInDirection = reverse ? (timeInCycle - totalScrollTime) : timeInCycle;

        float offset = timeInDirection * scrollSpeed;
        return reverse ? (scrollRange - offset) : offset;
    }

    public void renderFluidBar(long current, long max, int x, int y, int w, int h, int mainColor, Fluid fluid) {
        drawThickBeveledBox(x, y, x + w + 1, y + h, 1, ColorUtils.WHITE, ColorUtils.WHITE, ColorUtils.doubleDarker(mainColor));
        renderTank(x, y, w, h, mainColor, (int) current, (int) max, fluid.getStill().toString());
    }

    /**
     * Modified copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/apiimpl/client/ElementProgressRender.java#L15">ElementProgressRender#render</a>
     */
    public void render(long current, long max, int x, int y, int w, int h, int mainColor) {
        drawThickBeveledBox(x, y, x + w + 1, y + h, 1, ColorUtils.WHITE, ColorUtils.WHITE, ColorUtils.doubleDarker(mainColor));
        if (current > 0 && max > 0) {
            int dx = (int) Math.min((current * (w - 2) / max), w - 2);
            int secondColor = ColorUtils.darker(mainColor);
            if (mainColor == secondColor) {
                if (dx > 0) {
                    drawThickBeveledBox(x + 1, y + 1, x + dx + 1, y + h - 1, 1, mainColor, mainColor, mainColor);
                }
            } else {
                for (int xx = x + 1; xx <= x + dx + 1; xx++) {
                    int color = (xx & 1) == 0 ? mainColor : secondColor;
                    drawVerticalLine(xx, y + 1, y + h - 1, color);
                }
            }
        }
    }

    /**
     * Copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/rendering/RenderHelper.java#L209">RenderHelper#drawVerticalLine</a>
     * */
    public void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }

    /**
     * Copy of: <a href="https://github.com/McJtyMods/TheOneProbe/blob/f4797f1a7f1349ab71ac85e667517117a8a8d51a/src/main/java/mcjty/theoneprobe/rendering/RenderHelper.java#L275">RenderHelper#drawThickBeveledBox</a>
     * */
    public void drawThickBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        Gui.drawRect(x1, y1, x2 - 1, y1 + thickness, topleftcolor);
        Gui.drawRect(x1, y1, x1 + thickness, y2 - 1, topleftcolor);
        Gui.drawRect(x2 - thickness, y1, x2, y2 - 1, botrightcolor);
        Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
    }

    public static void renderTank(int x, int y, int width, int height, int color, int current, int capacity, String fluidIcon) {
        TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        int start = 1;
        width -= 1;
        TextureAtlasSprite liquidIcon = map.getAtlasSprite(fluidIcon);
        int lvl = (int) (((double) current / capacity) * width);
        GlStateManager.color((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F);
        while (lvl > 0) {
            int maxX = Math.min(16, lvl);
            lvl -= maxX;
            drawTexturedModalRect(x + start, y + 1, liquidIcon, maxX, height - 2);
            start += maxX;
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawTexturedModalRect(int x, int y, TextureAtlasSprite sprite, int width, int height) {
        float zLevel = 0.01f;
        float u1 = sprite.getMinU();
        float v1 = sprite.getMinV();
        float u2 = sprite.getMaxU();
        float v2 = sprite.getMaxV();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((x), (y + height), zLevel).tex(u1, v1).endVertex();
        buffer.pos((x + width), (y + height), zLevel).tex(u1, v2).endVertex();
        buffer.pos((x + width), (y), zLevel).tex(u2, v2).endVertex();
        buffer.pos((x), (y), zLevel).tex(u2, v1).endVertex();
        tessellator.draw();
    }
}
