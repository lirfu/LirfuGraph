package com.lirfu.lirfugraph.components;

import com.lirfu.lirfugraph.GraphTemplate;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class DrawGestureComponent extends GraphTemplate {
    private ArrayList<Point> points = new ArrayList<>();
    private String title;

    private int pixelSize;

    public DrawGestureComponent(String title) {
        this(title, 1);
    }

    public DrawGestureComponent(String title, int pixelSize) {
        this.title = title;
        this.pixelSize = pixelSize;

        template.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {

                Point p = mouseEvent.getPoint();
                Point l = template.getLocation();
                Dimension size = template.getSize();

                // Check bounds
                if (p.x < l.x + padding || p.y < l.y + padding || p.x > l.x + size.width - padding || p.y > l.y + size.height - padding)
                    return;

                points = new ArrayList<>(); // Reset the canvas.

                // Pixelize!
                p.x = p.x / pixelSize * pixelSize;
                p.y = p.y / pixelSize * pixelSize;

                points.add(p);
                DrawGestureComponent.this.repaint();
            }
        });
        template.addMouseMotionListener(new MouseMotionAdapter() {
            private int counter = 0;

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();
                Point l = template.getLocation();
                Dimension size = template.getSize();

                // Check bounds
                if (p.x < l.x + padding || p.y < l.y + padding || p.x > l.x + size.width - padding || p.y > l.y + size.height - padding)
                    return;

                // Pixelize!
                p.x = p.x / pixelSize * pixelSize;
                p.y = p.y / pixelSize * pixelSize;

                if (!points.contains(p))
                    points.add(p);
                repaintManager.requestRepaint(p.x, p.y, pixelSize);
                repaintManager.requestRepaint(template.getX() + template.getWidth() - 2 * padding - 70, template.getY(), 100);

//                if (++counter % 100 == 0) // TODO Optimize if it gets too slow
//                    optimize();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        drawTitleAndFrame(g, title);

        g.setColor(primaryColor);
        for (Point p : points)
            g.fillRect(p.x, p.y, pixelSize, pixelSize);

        g.drawString("Points: " + points.size(), template.getX() + template.getWidth() - 2 * padding - 70, template.getY() + g.getFont().getSize() / 2);
    }

    public void add(Point p) {
        points.add(p);
    }

    public void add(Point... ps) {
        for (Point p : ps) points.add(p);
    }

    public void clear() {
        points = new ArrayList<>();
    }

    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //    public void optimize() {
//        Point l = template.getLocation();
//        Dimension size = getAdjustedSize();
//
//        for (Point p : points) {
//            p.x = l.x + padding + (p.x - l.x - padding) / pixelSize * size.width;
//            p.y = l.y + padding + (p.y - l.y - padding) / pixelSize * size.height;
//        }
//
//        ArrayList<Point> unique = new ArrayList<>();
//        for (Point p : points)
//            if (!unique.contains(p))
//                unique.add(p);
//        points = unique;
//    }
}
