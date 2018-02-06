package com.lirfu.lirfugraph.themes;

import com.lirfu.lirfugraph.Theme;

import java.awt.*;

public class DarkTheme extends Theme {

    @Override
    public Color getBackgroundColor() {
        return Color.BLACK;
    }

    @Override
    public Color getInterfaceColor() {
        return Color.decode("0xb0b0b0");
    }

    @Override
    public Color getPrimaryColor() {
        return Color.decode("0x2292df");
    }

    @Override
    public Color getSecondaryColor() {
        return Color.decode("0xdf2222");
    }

    @Override
    public Color[] getColorPalette() {
        return new Color[]{getPrimaryColor(), getSecondaryColor(), Color.green, Color.orange, Color.pink, Color.white, Color.cyan, Color.magenta};
    }
}
