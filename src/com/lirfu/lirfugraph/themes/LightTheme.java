package com.lirfu.lirfugraph.themes;

import com.lirfu.lirfugraph.Theme;

import java.awt.*;

public class LightTheme extends Theme {
    @Override
    public Color getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public Color getInterfaceColor() {
        return Color.BLACK;
    }

    @Override
    public Color getPrimaryColor() {
        return Color.decode("0x1a6eba");
    }

    @Override
    public Color getSecondaryColor() {
        return Color.decode("0xdf2222");
    }

    @Override
    public Color[] getColorPalette() {
        return new Color[]{getPrimaryColor(), getSecondaryColor(), Color.decode("0x00bb00"),  Color.decode("0xdfbf00"), Color.pink, Color.white, Color.cyan, Color.magenta};
    }
}
