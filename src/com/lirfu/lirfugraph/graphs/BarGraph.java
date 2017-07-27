package com.lirfu.lirfugraph.graphs;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import java.util.LinkedList;

public class BarGraph extends AbstractGraph {
    private String title;
    public LinkedList<Double> values;
    private LinkedList<String> names;

    public BarGraph(String title) {
        values = new LinkedList<>();
        names = new LinkedList<>();
        this.title = title;
    }

    @Override
    public void paint(Graphics g) {
        setDirty(false);

        Graphics2D g2 = (Graphics2D) g;

        Point l = graph.getLocation();
        Dimension size = getAdjustedSize();

        drawTitleAndFrame(g, title);

        int rectWidth = size.width / values.size() / 2;
        double max = Collections.max(values);

        if (rectWidth > 7) {

            AffineTransform old = g2.getTransform();

            for (int i = 0; i < values.size(); i++) {
                g2.rotate(-Math.PI / 2);

                // Draw the label
                g.setColor(interfaceColor);
                g2.drawString(names.get(i) + " = ", -(padding - 5 + l.y + size.height),(int) (rectWidth * (2 * i+1) + padding + l.x - 12));
                g.setColor(secondaryColor);
                g2.drawString(values.get(i) + "", -(padding - 5 + l.y + size.height), (int)(rectWidth * (2 * i+1) + padding + l.x - 2));

                g2.setTransform(old);

                // Draw the bar.
                g.setColor(primaryColor);
                g.fillRect(rectWidth * (2 * i + 1) + padding + l.x, (int) ((1 - values.get(i) / max) * size.height) + padding + l.y, rectWidth, (int) (values.get(i) / max * size.height));

            }
        } else {
            LinearGraph linear = new LinearGraph("");
            linear.setSize(graph.getSize());
//            linear.setLocation(graph.getLocation());
            for (double val : values)
                linear.add(val);
            linear.paint(g);
            //			g.drawString("Too many values (" + values.size() + ")", (int) (l.x + getWidth() / 4), (int) (l.y + getHeight() / 2));
        }
    }

    ;

    public void add(String name, double value) {
        values.add(value);
        names.add(name);
        setDirty(true);
    }
}
