package com.lirfu.lirfugraph;

import java.awt.*;

/**
 * Created by lirfu on 24.06.17..
 */
public abstract class Component {
    protected Dimension fixedSize = null;
    protected Integer horizontalWeight = null;
    protected Integer verticalWeight = null;
    protected RepaintManager repaintManager;

    public void setFixedSize(Dimension size) {
        fixedSize = size;
    }

    public void repaint() {
        repaintManager.requestRepaint();
    }

    abstract void setRepaintManager(RepaintManager manager);

    abstract java.awt.Component getComponent();
}
