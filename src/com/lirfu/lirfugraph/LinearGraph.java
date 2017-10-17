package com.lirfu.lirfugraph;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class LinearGraph extends GraphTemplate {
    private final ArrayList<Double> points = new ArrayList<>();

    private int maxDrawnPoints;
    private Double maxY;
    private Double minY;
    private String title;

    private boolean showDots = true;
    private boolean showValues = true;
    private final int dotSize = 6;

    public LinearGraph(String title) {
        this.title = title;
    }

    @Override
    public void paint(Graphics g) {
        ArrayList<Double> list = points;

        // Area parameters for drawing.
        Dimension size = new Dimension(template.getWidth() - padding * 2, template.getHeight() - padding * 2);
        Point l = template.getLocation();

        // Display empty message if no points are available.
        if (list.size() == 0) {
            g.setColor(super.primaryColor);
            g.drawString("EMPTY!", l.x + template.getWidth() / 4, l.y + template.getHeight() / 2);
            return;
        }

        // Find y extremes.
        double max;
        if (maxY != null)
            max = maxY;
        else
            max = Collections.max(list);
        double min;
        if (minY != null)
            min = minY;
        else
            min = Collections.min(list);

        // Pixel distance between points.
        double delta = (double) size.width / (points.size() - 1);
        // Factor for scaling the point values .
        double zoom = size.height / (max - min);

        drawTitleAndFrame(g, title);

        // Draw max and min y value.
        g.drawString("Min: " + min + "   Max: " + max, l.x + size.width / 3, l.y + padding - 3);

        // First value.
        g.setColor(super.primaryColor);
        double lastValue = list.get(0);

        // Draw the curve.
        double currentx = padding;
        for (int i = 1; i < list.size(); i++) {
            double val = list.get(i);
            g.drawLine(l.x + (int) currentx, l.y + (int) (size.height - (lastValue - min) * zoom) + padding, l.x + (int) (currentx + delta), l.y + (int) (size.height - (val - min) * zoom) + padding);
            if (showDots)
                g.fillOval(l.x + (int) currentx - dotSize / 2, l.y + (int) (size.height - (lastValue - min) * zoom) + padding - dotSize / 2, dotSize, dotSize);
            if (showValues)
                g.drawString(lastValue + "", l.x + (int) currentx - dotSize / 2, l.y + template.getHeight() - 8);
            lastValue = val;
            currentx += delta;
        }
        if (showDots)
            g.fillOval(l.x + (int) currentx - dotSize / 2, l.y + (int) (size.height - (lastValue - min) * zoom) + padding - dotSize / 2, dotSize, dotSize);
        if (showValues)
            g.drawString(lastValue + "", l.x + (int) currentx - dotSize / 2, l.y + template.getHeight() - 8);
    }

    public void add(double value) {
        points.add(value);
        synchronized (points) {
            //			System.out.println("Size " + points.size() + " max " + maxDrawnPoints);
            if (points.size() > 0 && maxDrawnPoints > 0 && points.size() > maxDrawnPoints)
                for (int i = 0; i < points.size(); i += 2)
                    points.remove(i);
        }
    }

    public LinearGraph setMaxY(double max) {
        maxY = max;
        return this;
    }

    public LinearGraph setMinY(double min) {
        minY = min;
        return this;
    }

    public LinearGraph setShowDots(boolean showDots) {
        this.showDots = showDots;
        return this;
    }

    public LinearGraph setShowValues(boolean showValues) {
        this.showValues = showValues;
        return this;
    }

    public void setSize(Dimension d) {
        template.setSize(d);
        maxDrawnPoints = d.width - 2 * padding;
    }
}
