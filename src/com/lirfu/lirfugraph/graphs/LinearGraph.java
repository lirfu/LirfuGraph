package com.lirfu.lirfugraph.graphs;

import com.lirfu.lirfugraph.GraphTemplate;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class LinearGraph extends GraphTemplate {
    private final ArrayList<Double> points = new ArrayList<>();

    private int maxDrawnPoints;
    private Double maxX;
    private Double minX;
    private Double maxY;
    private Double minY;
    private String title;

    private boolean showDots = false;
    private boolean showValues = false;
    private boolean showAxes = true;
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
        if (maxX == null)
            maxX = (double) list.size();
        if (minX == null)
            minX = 1.;

        // Pixel distance between points.
        double delta = (double) size.width / (points.size() - 1);
        // Factor for scaling the point values .
        double zoom = size.height / (max - min);

        drawTitleAndFrame(g, title);
        if (showAxes)
            drawAxes(g, minX, maxX, min, max, zoom);
        drawBoundValues(g, minX, maxX, min, max);

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
//                g.drawString(lastValue + "", l.x + (int) currentx - dotSize / 2, l.y + template.getHeight() - 8);
                g.drawString((minX + i - 1) + "", l.x + (int) currentx - dotSize / 2, l.y + template.getHeight() - 8);
            lastValue = val;
            currentx += delta;
        }
        if (showDots)
            g.fillOval(l.x + (int) currentx - dotSize / 2, l.y + (int) (size.height - (lastValue - min) * zoom) + padding - dotSize / 2, dotSize, dotSize);
        if (showValues)
//            g.drawString(lastValue + "", l.x + (int) currentx - dotSize / 2, l.y + template.getHeight() - 8);
            g.drawString(maxX + "", l.x + (int) currentx - dotSize / 2, l.y + template.getHeight() - 8);
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

    public LinearGraph setMaxX(double max) {
        maxX = max;
        return this;
    }

    public LinearGraph setMinX(double min) {
        minX = min;
        return this;
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

    public LinearGraph setShowAxes(boolean showAxes) {
        this.showAxes = showAxes;
        return this;
    }

    public void setSize(Dimension d) {
        template.setSize(d);
        maxDrawnPoints = d.width - 2 * padding;
    }

    @Override
    protected void calculate() {
        //TODO
    }
}
