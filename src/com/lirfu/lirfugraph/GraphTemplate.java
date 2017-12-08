package com.lirfu.lirfugraph;

import javax.swing.JPanel;
import java.awt.*;

public abstract class GraphTemplate implements Component {
    protected static int padding = 25;
    protected Color interfaceColor = Color.decode("0xb0b0b0");
    protected Color primaryColor = Color.decode("0x2292df");
    protected Color secondaryColor = Color.decode("0xdf2222");
    protected Color[] colorPalette = {primaryColor, secondaryColor, Color.green, Color.orange, Color.pink, Color.white, Color.cyan, Color.magenta};


    protected JPanel template;

    protected GraphTemplate() {
        template = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Config.FONT_SIZE));
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
