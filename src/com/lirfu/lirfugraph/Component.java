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

    protected Component() {
        setTheme(new DarkTheme());
    }

    public void setTheme(Theme theme) {
        backgroundColor = theme.getBackgroundColor();
        interfaceColor = theme.getInterfaceColor();
        primaryColor = theme.getPrimaryColor();
        secondaryColor = theme.getSecondaryColor();
        colorPalette = theme.getColorPalette();
    }

    public void setFixedSize(Dimension size) {
        fixedSize = size;
    }

    public void repaint() {
        repaintManager.requestRepaint();
    }

    abstract void setRepaintManager(RepaintManager manager);

    protected abstract void calculate();

    abstract java.awt.Component getComponent();
}
