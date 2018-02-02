package com.lirfu.lirfugraph;

import com.lirfu.lirfugraph.graphs.LinearGraph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import java.util.LinkedList;

public class BarGraph extends GraphTemplate {
    private String title;
    public LinkedList<Double> values;
    private LinkedList<String> names;
    private Double mMaxY;

    public BarGraph(String title) {
        values = new LinkedList<>();
        names = new LinkedList<>();
        this.title = title;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Point l = template.getLocation();
        Dimension size = getAdjustedSize();

        drawTitleAndFrame(g, title);

        // Display empty message if no points are available.
        if (values.size() == 0) {
            g.setColor(super.primaryColor);
            g.drawString("EMPTY!", l.x + template.getWidth() / 4, l.y + template.getHeight() / 2);
            return;
        }

        int rectWidth = size.width / values.size() / 2;

        double max;
        if (mMaxY == null)
            max = Collections.max(values);
        else
            max = mMaxY;

        // Draw max y value.
        g.drawString("Max: " + max, l.x + template.getWidth() / 2, l.y + padding - 3);

        if (rectWidth > 7) {

            AffineTransform old = g2.getTransform();

            for (int i = 0; i < values.size(); i++) {
                g2.rotate(-Math.PI / 2);

                // Draw the label
                g.setColor(interfaceColor);
                g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Config.FONT_SIZE));
                g2.drawString(names.get(i) + " = " + Tools.round(values.get(i), 3), -(padding - 5 + l.y + size.height), (int) (rectWidth * (2 * i + 1) + padding + l.x - 12));

                g2.setTransform(old);

                // Draw the bar.
                g.setColor(colorPalette[i % colorPalette.length]);
                g.fillRect(rectWidth * (2 * i + 1) + padding + l.x, (int) ((1 - values.get(i) / max) * size.height) + padding + l.y, rectWidth, (int) (values.get(i) / max * size.height));

            }
        } else {
            LinearGraph linear = new LinearGraph("");
            linear.setSize(template.getSize());
            linear.template.setLocation(template.getLocation());
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
    }

    public void clear() {
        values = new LinkedList<>();
        names = new LinkedList<>();
    }

    public BarGraph setMaxY(Double maxY) {
        this.mMaxY = maxY;
        return this;
    }
}
