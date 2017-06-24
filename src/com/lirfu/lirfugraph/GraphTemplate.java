package com.lirfu.lirfugraph;

import javax.swing.JPanel;
import java.awt.*;

public abstract class GraphTemplate implements Component {
    protected static int padding = 25;
    protected Color interfaceColor = Color.decode("0x999999");
    protected Color primaryColor = Color.decode("0x0000ff");
    protected Color secondaryColor = Color.decode("0xff0000");

    protected JPanel template;

    protected GraphTemplate() {
        template = new JPanel() {
            @Override
            public void paint(Graphics g) {
                GraphTemplate.this.paint(g);
            }
        };
    }

    protected Dimension getAdjustedSize() {
        return new Dimension(template.getWidth() - padding * 2, template.getHeight() - padding * 2);
    }

    protected void drawTitleAndFrame(Graphics g, String title) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();
        g.setColor(interfaceColor);

        g.drawLine(l.x + padding, l.y + padding, l.x + size.width + padding, l.y + padding); //horizontal top
        g.drawLine(l.x + padding, l.y + size.height + padding, l.x + size.width + padding, l.y + size.height + padding); //horizontal base
        g.drawLine(l.x + padding, l.y + padding, l.x + padding, l.y + padding + size.height); // first vertical
        g.drawLine(l.x + padding + size.width, l.y + padding, l.x + padding + size.width, l.y + padding + size.height); // last vertical

        g.drawString(title, l.x + 3, l.y + 13);
    }

    public GraphTemplate setPadding(int padding) {
        GraphTemplate.padding = padding;

        return this;
    }

    @Override
    public java.awt.Component getComponent() {
        return template;
    }

    public abstract void paint(Graphics g);
}
