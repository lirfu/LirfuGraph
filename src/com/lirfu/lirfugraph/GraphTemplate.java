package com.lirfu.lirfugraph;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GraphTemplate extends Component {
    protected int padding = 25;

    protected boolean isDirty = true;

    protected Canvas template;

    protected GraphTemplate() {
        template = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Config.FONT_SIZE));
                GraphTemplate.this.paint(g);
                g.setColor(interfaceColor);
            }
        };
//        template.createBufferStrategy(2);
    }

    protected Dimension getAdjustedSize() {
        return new Dimension(template.getWidth() - padding * 2, template.getHeight() - padding * 2);
    }

    protected void getAdjustedSize(Dimension dim) {
        dim.width = template.getWidth() - padding * 2;
        dim.height = template.getHeight() - padding * 2;
    }

    protected void drawTitleAndFrame(Graphics g, String title) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();
        g.setColor(interfaceColor);

        g.drawLine(l.x + padding, l.y + padding, l.x + size.width + padding, l.y + padding); //horizontal top
        g.drawLine(l.x + padding, l.y + size.height + padding, l.x + size.width + padding, l.y + size.height + padding); //horizontal base
        g.drawLine(l.x + padding, l.y + padding, l.x + padding, l.y + padding + size.height); // first vertical
        g.drawLine(l.x + padding + size.width, l.y + padding, l.x + padding + size.width, l.y + padding + size.height); // last vertical

        g.drawString(title, l.x, l.y + padding - Config.FONT_SIZE / 2);
    }

    protected void drawAxes(Graphics g, double minX, double maxX, double minY, double maxY, double zoom) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();
        g.setColor(interfaceColor);

        if (minY < 0 && maxY > 0) { // If x axis is visible.
            int pos = (int) (maxY * zoom);
            g.drawLine(l.x + padding, l.y + padding + pos, l.x + size.width + padding, l.y + padding + pos); //horizontal top
        }
        if (minX < 0 && maxX > 0) { // If y axis is visible.
            int pos = (int) (-minX * size.width / (maxX - minX));
            g.drawLine(l.x + padding + pos, l.y + padding, l.x + padding + pos, l.y + padding + size.height); // first vertical
        }
    }

    protected void drawBoundValues(Graphics g, double minX, double maxX, double minY, double maxY) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();
        g.setColor(interfaceColor);

        String sminx = "Min: " + minX;
        String smaxx = "Max: " + maxX;
        String sminy = "Min: " + minY;
        String smaxy = "Max: " + maxY;

        // Y bounds.
        g.drawString(smaxy, l.x + padding + size.width - g.getFontMetrics().stringWidth(smaxy), l.y + padding - 6);
        g.drawString(sminy, l.x + padding + size.width - g.getFontMetrics().stringWidth(sminy),
                l.y + padding + size.height + Config.FONT_SIZE + 1);

        // X bounds.
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform trans = g2.getTransform();
        g2.rotate(Math.PI / 2);
        g2.drawString(sminx, l.y + size.height + padding - g.getFontMetrics().stringWidth(sminx),
                -(int) (l.x + padding - Config.FONT_SIZE * 0.5 - 6));
        g2.drawString(smaxx, l.y + size.height + padding - g.getFontMetrics().stringWidth(smaxx),
                -(l.x + padding + size.width + 6));
        g2.setTransform(trans);
    }

    public GraphTemplate setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public java.awt.Component getComponent() {
        return template;
    }

    public abstract void paint(Graphics g);

    @Override
    void setRepaintManager(RepaintManager manager) {
        repaintManager = manager;
    }

    @Override
    public void repaint() {
        repaintManager.requestRepaint(template.getX(), template.getY(), template.getWidth(), template.getHeight());
    }
}
