package com.lirfu.lirfugraph;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Row extends JPanel {
    private LinkedList<GraphTemplate> graphs;

    public Row() {
        this.graphs = new LinkedList<>();
    }

    public Row(GraphTemplate... graphs) {
        this.graphs = new LinkedList<>();

        Collections.addAll(this.graphs, graphs);
    }

    public Row addGraph(GraphTemplate g) {
        graphs.add(g);
        return this;
    }

    @Override
    public void paint(Graphics g) {
        Point l = getLocation();

        int singleWidth = getWidth() / graphs.size();
        int counter = 0;

        for (GraphTemplate gr : graphs) {
            gr.setSize(singleWidth, getHeight());
            gr.setLocation(counter * singleWidth + l.x, l.y);
            gr.paint(g);

            counter++;
        }
    }
}
