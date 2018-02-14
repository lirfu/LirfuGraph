package com.lirfu.lirfugraph.graphs;


import com.lirfu.lirfugraph.GraphTemplate;
import com.lirfu.lirfugraph.Point2D;
import com.lirfu.lirfugraph.utils.Tools;

import java.awt.*;

public class Surface2DGraph extends GraphTemplate {
    private final Function function;

    private double maxX;
    private double minX;
    private double maxY;
    private double minY;
    private Double maxZ;
    private Double minZ;
    private String title;

    private Canvas cnv;

    public Surface2DGraph(String title, Function function, Bounds bounds) {
        this.title = title;
        this.function = function;
        this.minX = bounds.minX;
        this.maxX = bounds.maxX;
        this.minY = bounds.minY;
        this.maxY = bounds.maxY;
    }

    private void redrawCanvas() {
        cnv = new Canvas() {
            @Override
            public void paint(Graphics g) {
                // Area parameters for drawing.
                Dimension size = new Dimension(template.getWidth() - padding * 2, template.getHeight() - padding * 2);
                Point l = template.getLocation();

                // Find extremes.
                double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
                for (double x = minX; x <= maxX; x += (maxX - minX) / (double) size.width) {
                    for (double y = minY; y <= maxY; y += (maxY - minY) / (double) size.height) {
                        float value = (float) function.calculate(new Point2D(x, y));
                        if (minZ == null && min > value)
                            min = value;
                        if (maxZ == null && max < value)
                            max = value;
                    }
                }

                if (minZ != null)
                    min = minZ;
                if (maxZ != null)
                    max = maxZ;

                drawTitleAndFrame(g, title);

                g.drawString("Max: " + Tools.round(max, 2), l.x + padding + size.width / 2, l.y + padding - 3);
                g.drawString("Min: " + Tools.round(min, 2), l.x + padding + size.width / 2, l.y + padding + size.height + 13);

                // First value.
                for (int x = 0; x < size.width; x++) {
                    for (int y = 0; y < size.height; y++) {
                        Point2D p = new Point2D(
                                minX + x * (maxX - minX) / size.width,
                                minY + y * (maxY - minY) / size.height
                        );
                        double value = function.calculate(p);
                        setColor(g, min, max, value);
                        g.fillRect(l.x + padding + x, l.y + padding + size.height - y, 1, 1);
                    }
                }
            }
        };
        isDirty = false;
    }

    @Override
    public void paint(Graphics g) {
        if (isDirty)
            redrawCanvas();

        cnv.paint(g);
    }

    private void setColor(Graphics g, double min, double max, double value) {
        float percentage = (float) ((value - min) / (max - min));

        if (percentage > 1f)
            percentage = 1f;
        else if (percentage < -1f)
            percentage = -1f;

        if (percentage > 0f)
            g.setColor(new Color(1, 1, 1, percentage));
        else
            g.setColor(new Color(0, 0, 1, -percentage));
    }

    public Surface2DGraph setMaxX(double max) {
        maxX = max;
        isDirty = true;
        return this;
    }

    public Surface2DGraph setMinX(double min) {
        minX = min;
        isDirty = true;
        return this;
    }

    public Surface2DGraph setMaxY(double max) {
        maxY = max;
        isDirty = true;
        return this;
    }

    public Surface2DGraph setMinY(double min) {
        minY = min;
        isDirty = true;
        return this;
    }

    public Surface2DGraph setMaxZ(Double maxZ) {
        this.maxZ = maxZ;
        isDirty = true;
        return this;
    }

    public Surface2DGraph setMinZ(Double minZ) {
        this.minZ = minZ;
        isDirty = true;
        return this;
    }

    public Surface2DGraph setSize(Dimension d) {
        template.setSize(d);
        isDirty = true;
        return this;
    }

    public interface Function {
        public double calculate(Point2D input);
    }

    public static class Bounds {
        public final double minX;
        public final double maxX;
        public final double minY;
        public final double maxY;

        public Bounds(double minX, double maxX, double minY, double maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
    }
}
