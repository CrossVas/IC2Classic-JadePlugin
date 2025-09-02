package ic2.jadeplugin.base.elements;

import ic2.jadeplugin.JadeTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

public class CommonWikiElement extends CommonElement {

    Component TEXT;

    public CommonWikiElement(Component text) {
        super(new Vec2(0, 0), "LEFT");
        this.TEXT = text;
    }

    public Component getText() {
        return TEXT;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putString("text", Component.Serializer.toJson(TEXT));
        return tag;
    }

    public static CommonWikiElement load(CompoundTag tag) {
        Component text = Component.Serializer.fromJson(tag.getString("text"));
        return new CommonWikiElement(text);
    }

    @Override
    public String getTagId() {
        return JadeTags.JADE_ADDON_WIKI_TAG;
    }
}
