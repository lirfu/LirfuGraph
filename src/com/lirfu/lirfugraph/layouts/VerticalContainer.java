package com.lirfu.lirfugraph.layouts;

import com.lirfu.lirfugraph.*;

import java.awt.*;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class VerticalContainer implements com.lirfu.lirfugraph.Component {
    private JPanel container;
    private LinkedList<Row> rows;

    public VerticalContainer() {
        this.container = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Point l = getLocation();
                int rowHeight = getHeight() / rows.size();
                int counter = 0;

                for (Row r : rows) {
                    r.getComponent().setSize(getWidth(), rowHeight);
                    r.getComponent().setLocation(l.x, counter * rowHeight + l.y);
                    r.getComponent().paint(g);

                    counter++;
                }
            }
        };

        this.rows = new LinkedList<>();
    }

    public VerticalContainer(Row... rows) {
        this();

        for (Row r : rows)
            addRow(r);
    }

    public VerticalContainer addRow(Row row) {
        rows.add(row);

        for (MouseListener l : row.getComponent().getMouseListeners())
            container.addMouseListener(l);
        for (MouseMotionListener l : row.getComponent().getMouseMotionListeners())
            container.addMouseMotionListener(l);
        for (KeyListener l : row.getComponent().getKeyListeners())
            container.addKeyListener(l);

        return this;
    }

    @Override
    public java.awt.Component getComponent() {
        return container;
    }

    @Override
    public boolean needRedraw() {
        for (com.lirfu.lirfugraph.Component r : rows)
            if (r.needRedraw())
                return true;
        return false;
    }

    @Override
    public Rectangle getArea() {
        int minx = 0, maxx = 0;
        int miny = 0, maxy = 0;

        Rectangle r;
        for (com.lirfu.lirfugraph.Component c : rows) {
            r = c.getArea();

            if (minx > r.x)
                minx = r.x;
            if (maxx < r.x + r.width)
                maxx = r.x + r.width;
            if (miny > r.y)
                miny = r.y;
            if (maxy < r.y + r.height)
                maxy = r.y + r.height;
        }

        return new Rectangle(minx, miny, maxx - minx, maxy - miny);
    }
}
