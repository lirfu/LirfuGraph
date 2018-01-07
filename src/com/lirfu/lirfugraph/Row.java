package com.lirfu.lirfugraph;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Row extends Component {
    private JPanel panel;
    private LinkedList<Component> graphs;
    private String title;

    public Row() {
        this.panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Point l = getLocation();

                int availableWidth = getWidth();
                int scalableGraphs = graphs.size();
                for (Component gr : graphs)
                    if (gr.fixedSize != null) {
                        availableWidth -= gr.fixedSize.width;
                        scalableGraphs--;

                        if (getHeight() < gr.fixedSize.height)
                            fixedSize = new Dimension(getWidth(), gr.fixedSize.height);
                    }

                int singleWidth = scalableGraphs > 0 ? availableWidth / scalableGraphs : 0;
                int height = fixedSize == null ? getHeight() : fixedSize.height;

                int widthCounter = l.x;
                for (Component gr : graphs) {
                    gr.getComponent().setLocation(widthCounter, l.y);
                    if (gr.fixedSize != null) {
                        gr.getComponent().setSize(gr.fixedSize);
                        widthCounter += gr.fixedSize.width;
                    } else {
                        gr.getComponent().setSize(singleWidth, height);
                        widthCounter += singleWidth;
                    }
                    gr.getComponent().paint(g);
                }

                if (title != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    double rotation = Math.toRadians(90);
                    g2.rotate(-rotation);
                    g2.drawString(title, -(l.y + (title.length() * Config.FONT_SIZE + getHeight()) / 2f), l.x + 8);
                    g2.rotate(rotation);
                }
            }
        };

        this.graphs = new LinkedList<>();
    }

    public Row(Component... graphs) {
        this();

        for (Component g : graphs)
            addComponent(g);
    }

    public Row addComponent(Component g) {
        graphs.add(g);

        for (MouseListener l : g.getComponent().getMouseListeners())
            panel.addMouseListener(l);
        for (MouseMotionListener l : g.getComponent().getMouseMotionListeners())
            panel.addMouseMotionListener(l);
        for (KeyListener l : g.getComponent().getKeyListeners())
            panel.addKeyListener(l);

        g.setRepaintManager(repaintManager);

        return this;
    }

    @Override
    public java.awt.Component getComponent() {
        return panel;
    }

    @Override
    void setRepaintManager(RepaintManager manager) {
        repaintManager = manager;
        for (Component g : graphs)
            g.setRepaintManager(manager);
    }

    public Row setTitle(String title) {
        this.title = title;
        return this;
    }
}
