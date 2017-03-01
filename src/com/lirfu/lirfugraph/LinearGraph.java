package com.lirfu.lirfugraph;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import java.util.LinkedList;

public class LinearGraph extends GraphTemplate {
    private int maxDrawnPoints;
    private final int numberOfHorizontals = 5;
    private LinkedList<Double> points;
    private int iterations;
    private boolean iterationsSet = false;
    private String title;

    public LinearGraph(String title) {

        this.title = title;
        this.points = new LinkedList<>();
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
        this.iterationsSet = true;
    }

    @Override
    public void paint(Graphics g) {
        LinkedList<Double> list = null;
        synchronized (points) {
            list = new LinkedList<>(points);
        }
        Dimension size = new Dimension(getWidth() - padding * 2, getHeight() - padding * 2);
        Point l = getLocation();

        if (!iterationsSet)
            iterations = points.size();

        if (iterations == 0) {
            g.setColor(super.primaryColor);
            g.drawString("EMPTY!", l.x + getWidth() / 4, l.y + getHeight() / 2);
            return;
        }

        double max = Collections.max(list);
        double min = Collections.min(list);

        if (max == 0) {
            g.setColor(super.primaryColor);
            g.drawString("Max is 0!", l.x + getWidth() / 6, l.y + getHeight() / 2);
            return;
        }

        double delta = (double) size.width / (iterations - 1);
        double zoom = 1;
        if (max != 0)
            zoom = size.height / (max - min);

        // Draw interface horizontals.
        g.setColor(super.interfaceColor);
        for (int i = 1; i <= numberOfHorizontals; i++) {
            double y = size.height * (1 - i / (double) (numberOfHorizontals + 1)) + padding;
            g.drawLine(l.x + padding, l.y + (int) y, l.x + size.width + padding, l.y + (int) y);
            g.drawString("" + ((max - min) * i / (numberOfHorizontals + 1) + min), l.x + padding + 3, l.y + (int) y - 3);
        }

        drawTitleAndFrame(g, title);

        g.drawString("" + iterations, l.x + padding + size.width - 10, l.y + size.height - 10 + 2 * padding); // max iteration
        g.drawString("Max: " + max, l.x + getWidth() / 2, l.y + padding - 3);
        g.drawString("Min: " + min, l.x + getWidth() / 2, l.y + size.height + padding + 13);

        // First value
        g.setColor(super.primaryColor);
        double lastValue = list.get(0);

        // The last value
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();
        g2.rotate(-Math.PI / 2);
        g2.drawString("" + lastValue, -l.y - getHeight() + padding, l.x + padding - 5);
        g2.setTransform(old);

        // Draw the curve
        double currentx = padding;
        for (int i = 1; i < list.size(); i++) {
            double val = list.get(i);
            g.drawLine(l.x + (int) currentx, l.y + (int) (size.height - (lastValue - min) * zoom) + padding, l.x + (int) (currentx + delta), l.y + (int) (size.height - (val - min) * zoom) + padding);
            lastValue = val;
            currentx += delta;
        }

        // The last value
        g2.rotate(Math.PI / 2);
        g2.drawString("" + lastValue, l.y + padding, -l.x + padding - 5 - getWidth());
        g2.setTransform(old);
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

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        maxDrawnPoints = d.width - 2 * padding;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        maxDrawnPoints = width - 2 * padding;
        //		System.out.println("Size " + maxDrawnPoints);
    }
}
