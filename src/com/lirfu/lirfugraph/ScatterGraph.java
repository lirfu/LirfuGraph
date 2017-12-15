package com.lirfu.lirfugraph;

import java.awt.*;
import java.util.ArrayList;

public class ScatterGraph extends GraphTemplate {
    private ArrayList<Point2D>[] points;

    private Double maxX;
    private Double minX;
    private Double maxY;
    private Double minY;
    private String[] titles;
    private boolean invertY = false;

    private int pointSize = 6;

    public ScatterGraph(String... titles) {
        this.titles = titles;

        points = new ArrayList[titles.length];
        for (int i = 0; i < titles.length; i++)
            points[i] = new ArrayList<>();

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
            Point2D min = min();
            if (minX == null)
                minX = min.x;
            if (minY == null)
                minY = min.y;
        }
        if (maxX == null || maxY == null) {
            Point2D max = max();
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

        // Draw points.
        g.setColor(super.primaryColor);
        for (int i = 0; i < points.length; i++) {
            g.setColor(colorPalette[i % colorPalette.length]);
            for (Point2D d : points[i])
                if (invertY)
                    g.fillOval((int) (l.x + padding + (d.x - minX) * zoomX - pointSize / 2), (int) (l.y + padding + (maxY - d.y) * zoomY - pointSize / 2), pointSize, pointSize);
                else
                    g.fillOval((int) (l.x + padding + (d.x - minX) * zoomX - pointSize / 2), (int) (l.y + padding + (d.y - minY) * zoomY - pointSize / 2), pointSize, pointSize);
        }
    }

    private boolean empty() {
        for (ArrayList<Point2D> list : points)
            if (list.size() > 0)
                return false;
        return true;
    }

    private Point2D max() {
        Point2D m = new Point2D(points[0].get(0));
        for (ArrayList<Point2D> list : points)
            for (Point2D d : list) {
                if (m.x < d.x) m.x = d.x;
                if (m.y < d.y) m.y = d.y;
            }
        return m;
    }

    private Point2D min() {
        Point2D m = new Point2D(points[0].get(0));
        for (ArrayList<Point2D> list : points)
            for (Point2D d : list) {
                if (m.x > d.x) m.x = d.x;
                if (m.y > d.y) m.y = d.y;
            }
        return m;
    }


    public void add(int graphIndex, double x, double y) {
        add(graphIndex, new Point2D(x, y));
    }

    public void add(int graphIndex, Point2D point) {
        points[graphIndex].add(point);
    }

    public ScatterGraph setPointSize(int diameter) {
        pointSize = diameter;
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

    public void clear() {
        points = new ArrayList[titles.length];
        for (int i = 0; i < titles.length; i++)
            points[i] = new ArrayList<>();
    }

    public void setInvertY(boolean invertY) {
        this.invertY = invertY;
    }

    public void setTitles(String... titles) {
        this.titles = titles;
    }
}
