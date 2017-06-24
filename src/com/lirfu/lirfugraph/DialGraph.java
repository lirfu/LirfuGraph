package com.lirfu.lirfugraph;

import java.awt.*;

/**
 * Created by lirfu on 16.03.17..
 */
public class DialGraph extends GraphTemplate {
    private double angle;
    private String title;

    public DialGraph(String title, double angle) {
        this.angle = angle;
        this.title = title;
    }

    @Override
    public void paint(Graphics g) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();

        drawTitleAndFrame(g, title);

        double diameter = Math.min(size.width, size.height);
        Point center = new Point((int) (l.x + size.width / 2. + padding), (int) (l.y + size.height / 2. + padding));

        // Draw the dial background.
        g.setColor(interfaceColor);
        g.fillOval((int) (center.x - diameter / 2.), (int) (center.y - diameter / 2.), (int) diameter, (int) diameter);

        // Draw the dial fence.
        g.setColor(primaryColor);
        g.drawOval((int) (center.x - diameter / 2.), (int) (center.y - diameter / 2.), (int) diameter, (int) diameter);

        // Draw the dial.
        g.setColor(secondaryColor);
        g.drawLine(center.x, center.y, (int) (center.x + Math.cos(angle) * diameter / 2), (int) (center.y + Math.sin(angle) * diameter / 2));
    }
}
