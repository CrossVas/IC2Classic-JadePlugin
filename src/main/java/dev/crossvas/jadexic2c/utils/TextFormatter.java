package dev.crossvas.jadexic2c.utils;

import net.minecraft.util.text.*;

public enum TextFormatter {
    BLACK(TextFormatting.BLACK),
    DARK_BLUE(TextFormatting.DARK_BLUE),
    DARK_GREEN(TextFormatting.DARK_GREEN),
    DARK_AQUA(TextFormatting.DARK_AQUA),
    DARK_RED(TextFormatting.DARK_RED),
    DARK_PURPLE(TextFormatting.DARK_PURPLE),
    GOLD(TextFormatting.GOLD),
    GRAY(TextFormatting.GRAY),
    DARK_GRAY(TextFormatting.DARK_GRAY),
    BLUE(TextFormatting.BLUE),
    GREEN(TextFormatting.GREEN),
    AQUA(TextFormatting.AQUA),
    RED(TextFormatting.RED),
    LIGHT_PURPLE(TextFormatting.LIGHT_PURPLE),
    YELLOW(TextFormatting.YELLOW),
    WHITE(TextFormatting.WHITE),
    OBFUSCATED(TextFormatting.OBFUSCATED),
    BOLD(TextFormatting.BOLD),
    STRIKETHROUGH(TextFormatting.STRIKETHROUGH),
    UNDERLINE(TextFormatting.UNDERLINE),
    ITALIC(TextFormatting.ITALIC),
    EMPTY(TextFormatting.RESET);

    final TextFormatting FORMAT;

    TextFormatter(TextFormatting formatting) {
        this.FORMAT = formatting;
    }

    public ITextComponent translate(String translatable) {
        return new TextComponentTranslation(translatable).setStyle(new Style().setColor(this.FORMAT));
    }

    public ITextComponent component(ITextComponent component) {
        return component.setStyle(new Style().setColor(this.FORMAT));
    }

    public ITextComponent translate(String translatable, Object... args) {
        return new TextComponentTranslation(translatable, args).setStyle(new Style().setColor(this.FORMAT));
    }

    public ITextComponent literal(String literal) {
        return new TextComponentString(literal).setStyle(new Style().setColor(this.FORMAT));
    }
}
