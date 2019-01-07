package com.lirfu.lirfugraph.graphs;

import com.lirfu.lirfugraph.GraphTemplate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;

public class MultiLinearGraph extends GraphTemplate {
    private ArrayList<Double>[] points;
    private String name;
    private String[] titles;

    private Double maxX;
    private Double minX;
    private Double maxY;
    private Double minY;
    private int dotSize = 6;
    private boolean showDots = true;

    private BufferedImage image;

    public MultiLinearGraph(int numberOfGraphs, String... titles) {
        this("", numberOfGraphs, titles);
    }

    public MultiLinearGraph(String name, int numberOfGraphs, String... titles) {
        this.name = name;
        this.titles = titles;
        this.points = new ArrayList[numberOfGraphs];
        for (int i = 0; i < numberOfGraphs; i++)
            points[i] = new ArrayList<>();
    }

    @Override
    protected void calculate() {
        try {
            image = new BufferedImage(template.getWidth() <= 0 ? 1 : template.getWidth(), template.getHeight() <= 0 ? 1 : template.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            Dimension size = getAdjustedSize();
            Point l = template.getLocation();

            int iterations = points[0].size();

            // Draw only frame.
            drawTitleAndFrame(g, name);

            if (iterations == 0) {
                g.setColor(primaryColor);
                g.drawString("EMPTY!", l.x + padding + size.width / 2 - (int) (3 * g.getFont().getSize() * 0.4), l.y + padding + size.height / 2);
                return;
            }

            double max;
            if (maxY != null)
                max = maxY;
            else {
                ArrayList<Double> maxes = new ArrayList<>();
                for (ArrayList<Double> point : points)
                    maxes.add(Collections.max(point));
                max = Collections.max(maxes);
            }

            double min;
            if (minY != null)
                min = minY;
            else {
                ArrayList<Double> mins = new ArrayList<>();
                for (ArrayList<Double> point : points)
                    mins.add(Collections.min(point));
                min = Collections.min(mins);
            }
            if (maxX == null) {
                maxX = 0.;
                for (ArrayList a : points)
                    if (maxX < a.size())
                        maxX = (double) a.size();
            }
            if (minX == null)
                minX = 1.;

            double delta = (double) size.width / (iterations - 1);
            double zoom = 1;
            if (max != 0)
                zoom = size.height / (max - min);

            // Draw titles in corresponding colorPalette.
            int titleOffset = 70;
            for (int i = 0; i < titles.length; i++) {
                g.setColor(colorPalette[i % colorPalette.length]);
                g.drawString(titles[i], l.x + titleOffset, l.y + padding - 6);
                titleOffset += (1 + titles[i].length()) * g.getFontMetrics().charWidth('a');
            }

            g.setColor(interfaceColor);
            g.drawString("Min: " + min + "   Max: " + max, l.x + size.width * 2 / 3, l.y + padding - 6);
//        g.drawString("Max: " + max, l.x + template.getWidth() / 2, l.y + padding - 3);
//        g.drawString("Min: " + min, l.x + template.getWidth() / 2, l.y + size.height + padding + 13);

            // First value
            double[] lastValues = new double[points.length];
            for (int i = 0; i < points.length; i++)
                lastValues[i] = points[i].get(0);

            // Draw the curve
            double currentx = padding;
            for (int i = 1; i < points[0].size(); i++) {
                for (int j = 0; j < points.length; j++) {
                    double val = points[j].get(i);
                    g.setColor(colorPalette[j % colorPalette.length]);
                    g.drawLine(l.x + (int) currentx, l.y + (int) (size.height - (lastValues[j] - min) * zoom) + padding, l.x + (int) (currentx + delta), l.y + (int) (size.height - (val - min) * zoom) + padding);
                    if (showDots)
                        g.fillOval(l.x + (int) currentx - dotSize / 2, l.y + (int) (size.height - (lastValues[j] - min) * zoom) + padding - dotSize / 2, dotSize, dotSize);
                    lastValues[j] = val;
                }

                currentx += delta;
            }

            g.drawString("" + minX, l.x, l.y + size.height + 2 * padding);
            g.drawString("" + maxX, l.x + size.width, l.y + size.height + 2 * padding);
        } catch (ConcurrentModificationException ignore) {
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public void add(double... values) {
        if (values.length != points.length)
            throw new IllegalArgumentException("Wrong number of parameters: " + values.length + " != " + points.length);

        for (int i = 0; i < values.length; i++)
            points[i].add(values[i]);
    }

    public MultiLinearGraph setMaxX(double max) {
        maxX = max;
        return this;
    }

    public MultiLinearGraph setMinX(double min) {
        minX = min;
        return this;
    }

    public MultiLinearGraph setMaxY(double max) {
        maxY = max;
        return this;
    }

    public MultiLinearGraph setMinY(double min) {
        minY = min;
        return this;
    }

    public MultiLinearGraph setShowDots(boolean showDots) {
        this.showDots = showDots;
        return this;
    }

    public MultiLinearGraph setName(String name) {
        this.name = name;
        return this;
    }

    public void clear() {
        this.points = new ArrayList[points.length];
        for (int i = 0; i < points.length; i++)
            points[i] = new ArrayList<>();
    }
}
