package com.lirfu.lirfugraph;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Row implements Component {
    private JPanel panel;
    private LinkedList<GraphTemplate> graphs;

    public Row() {
        this.panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Point l = getLocation();

                int singleWidth = getWidth() / graphs.size();
                int counter = 0;

                for (GraphTemplate gr : graphs) {
                    gr.getComponent().setSize(singleWidth, getHeight());
                    gr.getComponent().setLocation(counter * singleWidth + l.x, l.y);
                    gr.getComponent().paint(g);

                    counter++;
                }
            }
        };

        this.graphs = new LinkedList<>();
    }

    public Row(GraphTemplate... graphs) {
        this();

        for (GraphTemplate g : graphs)
            addGraph(g);
    }

    public Row addGraph(GraphTemplate g) {
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
}
