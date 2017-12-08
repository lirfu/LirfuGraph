package com.lirfu.lirfugraph;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScatterGraph extends GraphTemplate {
    private final ArrayList<Dot>[] dots;

    private Double maxX;
    private Double minX;
    private Double maxY;
    private Double minY;
    private String[] titles;

    private int dotSize = 6;

    public ScatterGraph(String... titles) {
        this.titles = titles;

        dots = new ArrayList[titles.length];
        for (int i = 0; i < titles.length; i++)
            dots[i] = new ArrayList<>();

    }

    @Override
    public void paint(Graphics g) {
        // Area parameters for drawing.
        Dimension size = new Dimension(template.getWidth() - padding * 2, template.getHeight() - padding * 2);
        Point l = template.getLocation();

        // Display empty message if no points are available.
        if (empty()) {
            g.setColor(super.primaryColor);
            g.drawString("EMPTY!", l.x + template.getWidth() / 4, l.y + template.getHeight() / 2);
            return;
        }

        // Find y extremes.
        if (minX == null || minY == null) {
            Dot min = min();
            if (minX == null)
                minX = min.x;
            if (minY == null)
                minY = min.y;
        }
        if (maxX == null || maxY == null) {
            Dot max = max();
            if (maxX == null)
                maxX = max.x;
            if (maxY == null)
                maxY = max.y;
        }

        // Factor for scaling the point values.
        double zoomX = size.width / (maxX - minX);
        double zoomY = size.height / (maxY - minY);

        drawTitleAndFrame(g, "");
        // Draw titles in corresponding colorPalette.
        int titleOffset = 70;
        for (int i = 0; i < titles.length; i++) {
            g.setColor(colorPalette[i % colorPalette.length]);
            g.drawString(titles[i], l.x + titleOffset, l.y + padding - 6);
            titleOffset += (1 + titles[i].length()) * g.getFontMetrics().charWidth('a');
        }

        // Draw max and min y value.
        g.setColor(super.interfaceColor);
        g.drawString("Min: (" + minX + ", " + minY + ")   Max: (" + maxX + ", " + maxY + ")", l.x + size.width / 3, l.y + padding - 3);

        // Draw dots.
        g.setColor(super.primaryColor);
        for (int i = 0; i < dots.length; i++) {
            g.setColor(colorPalette[i % colorPalette.length]);
            for (Dot d : dots[i])
                g.fillOval((int) (l.x + padding + (d.x - minX) * zoomX - dotSize / 2), (int) (l.y + padding + (maxY - d.y) * zoomY - dotSize / 2), dotSize, dotSize);
        }
    }

    private boolean empty() {
        for (ArrayList<Dot> list : dots)
            if (list.size() > 0)
                return false;
        return true;
    }

    private Dot max() {
        Dot m = new Dot(dots[0].get(0));
        for (ArrayList<Dot> list : dots)
            for (Dot d : list) {
                if (m.x < d.x) m.x = d.x;
                if (m.y < d.y) m.y = d.y;
            }
        return m;
    }

    private Dot min() {
        Dot m = new Dot(dots[0].get(0));
        for (ArrayList<Dot> list : dots)
            for (Dot d : list) {
                if (m.x > d.x) m.x = d.x;
                if (m.y > d.y) m.y = d.y;
            }
        return m;
    }


    public void add(int graphIndex, double x, double y) {
        add(graphIndex, new Dot(x, y));
    }

    public void add(int graphIndex, Dot point) {
        dots[graphIndex].add(point);
    }

    public ScatterGraph setDotSize(int diameter) {
        dotSize = diameter;
        return this;
    }

    public ScatterGraph setMaxX(double max) {
        maxX = max;
        return this;
    }

    public ScatterGraph setMinX(double min) {
        minX = min;
        return this;
    }

    public ScatterGraph setMaxY(double max) {
        maxY = max;
        return this;
    }

    public ScatterGraph setMinY(double min) {
        minY = min;
        return this;
    }

    public static class Dot {
        double x;
        double y;

        public Dot(Dot d) {
            this.x = d.x;
            this.y = d.y;
        }

        public Dot(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return '(' + x + ", " + y + ')';
        }
    }
}
