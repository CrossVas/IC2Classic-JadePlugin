package dev.crossvas.jadexic2c.utils;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ScreenRectangle {
    int minX;
    int minY;
    int maxX;
    int maxY;

    public ScreenRectangle(int x, int y, int width, int height) {
        this.minX = x;
        this.minY = y;
        this.maxX = x + width;
        this.maxY = y + height;
    }

    public void limit(ScreenRectangle rect) {
        minX = Math.max(rect.minX, minX);
        minY = Math.max(rect.minY, minY);
        maxX = Math.min(rect.maxX, maxX);
        maxY = Math.min(rect.maxY, maxY);
    }

    public int getX() {
        return minX;
    }

    public int getY() {
        return minY;
    }

    public int getWidth() {
        return maxX - minX;
    }

    public int getHeight() {
        return maxY - minY;
    }
}
