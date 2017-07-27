package com.lirfu.lirfugraph.graphs;

import javax.swing.JPanel;
import java.awt.*;

public abstract class AbstractGraph implements com.lirfu.lirfugraph.Component {
    protected static int padding = 25;
    protected Color interfaceColor = Color.decode("0x999999");
    protected Color primaryColor = Color.decode("0x0000ff");
    protected Color secondaryColor = Color.decode("0xff0000");

    protected JPanel graph;
    private boolean dataDirty = false;

    protected AbstractGraph() {
        graph = new JPanel() {
            @Override
            public void paint(Graphics g) {
                AbstractGraph.this.paint(g);
            }
        };
    }

    protected Dimension getAdjustedSize() {
        return new Dimension(graph.getWidth() - padding * 2, graph.getHeight() - padding * 2);
    }

    protected void drawTitleAndFrame(Graphics g, String title) {
        Point l = graph.getLocation();
        Dimension size = getAdjustedSize();
        g.setColor(interfaceColor);

        g.drawLine(l.x + padding, l.y + padding, l.x + size.width + padding, l.y + padding); //horizontal top
        g.drawLine(l.x + padding, l.y + size.height + padding, l.x + size.width + padding, l.y + size.height + padding); //horizontal base
        g.drawLine(l.x + padding, l.y + padding, l.x + padding, l.y + padding + size.height); // first vertical
        g.drawLine(l.x + padding + size.width, l.y + padding, l.x + padding + size.width, l.y + padding + size.height); // last vertical

        g.drawString(title, l.x + 3, l.y + 13);
    }

    public AbstractGraph setPadding(int padding) {
        AbstractGraph.padding = padding;

        return this;
    }

    @Override
    public java.awt.Component getComponent() {
        return graph;
    }

    public abstract void paint(Graphics g);

    @Override
    public boolean needRedraw() {
        return dataDirty;
    }

    @Override
    public Rectangle getArea() {
        return graph.getBounds();
    }

    protected void setDirty(boolean dirty) {
        dataDirty = dirty;
    }
}
