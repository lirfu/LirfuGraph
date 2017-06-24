package com.lirfu.lirfugraph;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class VerticalContainer implements Component {
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
}
