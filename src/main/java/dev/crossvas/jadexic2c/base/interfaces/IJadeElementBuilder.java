package dev.crossvas.jadexic2c.base.interfaces;

import ic2.core.util.math.Vec2i;
import net.minecraft.nbt.NBTTagCompound;

public interface IJadeElementBuilder {

    /**
     * @param side left, right
     *
     * */
    IJadeElementBuilder align(String side);

    IJadeElementBuilder translate(Vec2i translation);

    NBTTagCompound save(NBTTagCompound tag);

    String getTagId();
}
