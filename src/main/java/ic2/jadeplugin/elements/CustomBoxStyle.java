package ic2.jadeplugin.elements;

import snownee.jade.api.ui.BoxStyle;

@SuppressWarnings("all")
public class CustomBoxStyle extends BoxStyle {

    public CustomBoxStyle(int backColor) {
        this.borderWidth = 1.0F;
        this.borderColor = 0xFFFFFFFF;
        this.bgColor = backColor;
    }
}
