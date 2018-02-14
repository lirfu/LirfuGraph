package com.lirfu.lirfugraph.graphs;


import com.lirfu.lirfugraph.Config;
import com.lirfu.lirfugraph.GraphTemplate;
import com.lirfu.lirfugraph.Point2D;
import com.lirfu.lirfugraph.utils.Tools;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Classification2DGraph extends GraphTemplate {
    private ArrayList<Point2D> points = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();

    private Double mMaxX;
    private Double mMinX;
    private Double mMaxY;
    private Double mMinY;
    private String mTitle;

    private Canvas mCanvas;
    private int circleSize = 5;
    private Bounds bounds;
    private boolean keepAspectRatio = false;

    public Classification2DGraph(String title) {
        this.mTitle = title;
        padding = 44;

        template.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();
                Point l = template.getLocation();
                Dimension size = new Dimension(template.getWidth() - padding * 2, template.getHeight() - padding * 2);

                double x = (p.x - l.x - padding) / (double) (size.width - circleSize) * (bounds.maxX - bounds.minX) + bounds.minX;
                double y = (1 - (p.y - l.y - padding) / (double) (size.height - circleSize)) * (bounds.maxY - bounds.minY) + bounds.minY;

                System.out.println("x: " + x + "   y: " + y);
            }
        });
    }

    private void redrawCanvas() {
        mCanvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                // Area parameters for drawing.
                Dimension size = new Dimension(template.getWidth() - padding * 2, template.getHeight() - padding * 2);
                Point l = template.getLocation();

                bounds = Bounds.calculate(Classification2DGraph.this);

                if (keepAspectRatio) {
                    if (bounds.maxX - bounds.minX >= bounds.maxY - bounds.minY)
                        bounds.maxY = bounds.minY + bounds.maxX - bounds.minX;
                    else
                        bounds.maxX = bounds.minX + bounds.maxY - bounds.minY;
                }


                drawTitleAndFrame(g, mTitle);

                for (int i = 0; i < points.size(); i++) {
                    Point2D p = points.get(i);
                    g.setColor(colors.get(i));
                    g.fillRect(
                            (int) ((p.x - bounds.minX) / (bounds.maxX - bounds.minX) * (size.width - circleSize) + l.x + padding),
                            (int) ((1 - (p.y - bounds.minY) / (bounds.maxY - bounds.minY)) * (size.height - circleSize) + l.y + padding),
                            circleSize, circleSize
                    );
                }

                g.setColor(interfaceColor);
                Graphics2D g2 = (Graphics2D) g;
                g2.drawString(Tools.round(bounds.minX, 4) + "", l.x + padding, l.y + padding + size.height + Config.FONT_SIZE);
                g2.drawString(Tools.round(bounds.maxX, 4) + "", l.x + padding + size.width - 4 * Config.FONT_SIZE, l.y + padding + size.height + Config.FONT_SIZE);
                g2.drawString(Tools.round(bounds.minY, 4) + "", l.x, l.y + padding + size.height);
                g2.drawString(Tools.round(bounds.maxY, 4) + "", l.x, l.y + padding);
            }
        };
        isDirty = false;
    }

    @Override
    public void paint(Graphics g) {
        if (isDirty)
            redrawCanvas();

        mCanvas.paint(g);
    }

    public void add(double x, double y, Color typeColor) {
        points.add(new Point2D(x, y));
        colors.add(typeColor);
    }

    public Classification2DGraph setMaxX(double max) {
        mMaxX = max;
        isDirty = true;
        return this;
    }

    public Classification2DGraph setMinX(double min) {
        mMinX = min;
        isDirty = true;
        return this;
    }

    public Classification2DGraph setMaxY(double max) {
        mMaxY = max;
        isDirty = true;
        return this;
    }

    public Classification2DGraph setMinY(double min) {
        mMinY = min;
        isDirty = true;
        return this;
    }

    public Classification2DGraph keepAspectRatio(boolean keep) {
        keepAspectRatio = keep;
        isDirty = true;
        return this;
    }

    public Classification2DGraph setSize(Dimension d) {
        template.setSize(d);
        isDirty = true;
        return this;
    }

    public static class Bounds {
        public double minX;
        public double maxX;
        public double minY;
        public double maxY;

        public Bounds(double minX, double maxX, double minY, double maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }

        public static Bounds calculate(Classification2DGraph graph) {
            // Find y extremes.
            double minX = Double.MAX_VALUE;
            double minY = Double.MAX_VALUE;
            double maxX = Double.MIN_VALUE;
            double maxY = Double.MIN_VALUE;

            for (Point2D p : graph.points) {
                if (p.x < minX) minX = p.x;
                if (p.x > maxX) maxX = p.x;
                if (p.y < minY) minY = p.y;
                if (p.y > maxY) maxY = p.y;
            }

            if (graph.mMinX != null) minX = graph.mMinX;
            if (graph.mMaxX != null) maxX = graph.mMaxX;
            if (graph.mMinY != null) minY = graph.mMinY;
            if (graph.mMaxY != null) maxY = graph.mMaxY;

            return new Bounds(minX, maxX, minY, maxY);
        }
    }
}
