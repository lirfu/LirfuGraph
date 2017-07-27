package com.lirfu.lirfugraph.layouts;

import com.lirfu.lirfugraph.Component;
import com.lirfu.lirfugraph.graphs.AbstractGraph;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Row implements com.lirfu.lirfugraph.Component {
    private JPanel panel;
    private LinkedList<AbstractGraph> graphs;

    public Row() {
        this.panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Point l = getLocation();

                int singleWidth = getWidth() / graphs.size();
                int counter = 0;

                for (AbstractGraph gr : graphs) {
                    gr.getComponent().setSize(singleWidth, getHeight());
                    gr.getComponent().setLocation(counter * singleWidth + l.x, l.y);
                    gr.getComponent().paint(g);

                    counter++;
                }
            }
        };

        this.graphs = new LinkedList<>();
    }

    public Row(AbstractGraph... graphs) {
        this();

        for (AbstractGraph g : graphs)
            addGraph(g);
    }

    public Row addGraph(AbstractGraph g) {
        graphs.add(g);

        for (MouseListener l : g.getComponent().getMouseListeners())
            panel.addMouseListener(l);
        for (MouseMotionListener l : g.getComponent().getMouseMotionListeners())
            panel.addMouseMotionListener(l);
        for (KeyListener l : g.getComponent().getKeyListeners())
            panel.addKeyListener(l);

        return this;
    }

    @Override
    public java.awt.Component getComponent() {
        return panel;
    }

    @Override
    public boolean needRedraw() {
        for (Component c : graphs)
            if (c.needRedraw())
                return true;

        return false;
    }

    @Override
    public Rectangle getArea() {
        int minx = 0, maxx = 0;
        int miny = 0, maxy = 0;

        Rectangle r;
        for (Component c : graphs) {
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
