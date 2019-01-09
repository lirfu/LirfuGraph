package com.lirfu.lirfugraph;

import com.lirfu.lirfugraph.themes.DarkTheme;
import com.lirfu.lirfugraph.themes.LightTheme;

import java.awt.*;

/**
 * Created by lirfu on 24.06.17..
 */
public abstract class Component {
    protected Dimension fixedSize = null;
    protected Integer horizontalWeight = null;
    protected Integer verticalWeight = null;
    protected RepaintManager repaintManager;

    protected static Color backgroundColor;
    protected static Color interfaceColor;
    protected static Color primaryColor;
    protected static Color secondaryColor;
    protected static Color[] colorPalette;
    protected static Theme theme;

    protected Component() {
        setTheme(new DarkTheme());
    }

    public void setTheme(Theme t) {
        theme = t;
        backgroundColor = t.getBackgroundColor();
        interfaceColor = t.getInterfaceColor();
        primaryColor = t.getPrimaryColor();
        secondaryColor = t.getSecondaryColor();
        colorPalette = t.getColorPalette();
    }

    public static Theme getTheme() {
        return theme;
    }

    public Component setFixedSize(Dimension size) {
        fixedSize = size;
        return this;
    }

    public void repaint() {
        repaintManager.requestRepaint();
    }

    abstract void setRepaintManager(RepaintManager manager);

    protected abstract void calculate();

    abstract java.awt.Component getComponent();
}
