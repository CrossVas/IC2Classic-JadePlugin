package dev.crossvas.jadexic2c.base.tooltiprenderers;

import dev.crossvas.jadexic2c.JadeIC2Classic;
import dev.crossvas.jadexic2c.utils.ColorUtils;
import dev.crossvas.jadexic2c.utils.RenderHelper;
import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaTooltipRenderer;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.config.OverlayConfig;
import mcp.mobius.waila.overlay.RayTracing;
import mcp.mobius.waila.overlay.Tooltip;
import mcp.mobius.waila.utils.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

import java.awt.Dimension;
import java.util.*;


public class BaseProgressBarRenderer implements IWailaTooltipRenderer {

    private final Map<List<String>, Tooltip> subTooltips = new HashMap<>();

    int offset = 4;
    Map<List<String>, Integer> cachedWidths = new HashMap<>();

    @Override
    public Dimension getSize(String[] strings, IWailaCommonAccessor accessor) {
        if (strings == null || strings.length == 0) return new Dimension();

        List<String> key = Arrays.asList(strings);
        Tooltip tooltip = this.subTooltips.get(key);
        if (tooltip == null) {
            List<String> list = new ArrayList<>(key);
            tooltip = new Tooltip(list, RayTracing.instance().getTargetStack());
            this.subTooltips.put(key, tooltip);
        }

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;

        // calculate line width
        int maxLineWidth = 0;
        for (String line : strings) {
            int width = font.getStringWidth(line);
            if (width > maxLineWidth) maxLineWidth = width;
        }

        // Cache per-instance width
        this.cachedWidths.put(key, maxLineWidth);

        boolean isStringOnly = strings.length > 4 && "1".equals(strings[4]);
        int height = isStringOnly ? 10 : 11;
        return new Dimension(maxLineWidth + offset, height);
    }

    /**
     * Tooltip Builder
     * param 0 - current
     * param 1 - max
     * param 2 - color
     * param 3 - text
     * param 4 - string only, 0 - false, 1 - true
     * param 5 - centered, 0 - false, 1 - true
     * param 6 - fluid id
     * */

    @Override
    public void draw(String[] strings, IWailaCommonAccessor accessor) {
        if (strings == null || strings.length < 7) return;

        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer font = mc.fontRenderer;
        int current = Integer.parseInt(strings[0]);
        int max = Integer.parseInt(strings[1]);
        int color = Integer.parseInt(strings[2]);
        String text = strings[3];
        boolean isStringOnly = "1".equals(strings[4]);
        boolean centered = "1".equals(strings[5]);
        String fluidStringId = strings[6];
        Fluid fluid = FluidRegistry.getFluid(fluidStringId);

        List<String> key = Arrays.asList(strings);
        int maxLineWidth = this.cachedWidths.getOrDefault(key, JadeIC2Classic.DEFAULT_BAR_WIDTH);
        maxLineWidth = Math.max(maxLineWidth + offset, JadeIC2Classic.DEFAULT_BAR_WIDTH);

        int barHeight = 11;
        int textWidth = font.getStringWidth(text);

        ScaledResolution resolution = new ScaledResolution(mc);
        int x = ((int) (resolution.getScaledWidth() / OverlayConfig.scale) - maxLineWidth - 1) *
                ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_POSX, 0) / 10000;
        int y = ((int) (resolution.getScaledHeight() / OverlayConfig.scale) - barHeight - 1) *
                ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_POSY, 0) / 10000;
        int textX = x;

        GL11.glTranslated(-x, -y, 0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (!isStringOnly) {
            if (fluid == null) {
                RenderHelper.THIS.render(current, max, x, y, maxLineWidth, barHeight + 1, color);
            } else {
                RenderHelper.THIS.renderFluidBar(current, max, x, y, maxLineWidth, barHeight + 1, color, fluid);
            }
            textX += (maxLineWidth - textWidth) / 2 + 1;
        } else if (centered) {
            textX += (maxLineWidth - textWidth) / 2 + 1;
        }

        font.drawStringWithShadow(text, textX, y + 2, ColorUtils.WHITE);
    }
}