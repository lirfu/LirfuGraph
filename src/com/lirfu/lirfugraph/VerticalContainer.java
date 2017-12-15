package com.lirfu.lirfugraph;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class VerticalContainer extends Component {
    private JPanel container;
    private LinkedList<Row> rows;

    public VerticalContainer() {
        this.container = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Point l = getLocation();

                int availableHeight = getHeight();
                int scalableRows = rows.size();
                for (Row r : rows)
                    if (r.fixedSize != null) {
                        availableHeight -= r.fixedSize.height;
                        scalableRows--;

                        if (getWidth() < r.fixedSize.width)
                            fixedSize = new Dimension(r.fixedSize.width, getHeight());
                    }

                int singleHeight = scalableRows > 0 ? availableHeight / scalableRows : 0;
                int width = fixedSize == null ? getWidth() : fixedSize.width;

                int heightCounter = l.y;
                for (Row r : rows) {
                    r.getComponent().setLocation(l.x, heightCounter);
                    if (r.fixedSize != null) {
                        r.getComponent().setSize(r.fixedSize);
                        heightCounter += r.fixedSize.height;
                    } else {
                        r.getComponent().setSize(width, singleHeight);
                        heightCounter += singleHeight;
                    }
                    r.getComponent().paint(g);
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

        row.setRepaintManager(repaintManager);

        return this;
    }

    @Override
    void setRepaintManager(RepaintManager manager) {
        repaintManager = manager;
        for (Row r : rows)
            r.setRepaintManager(manager);
    }

    @Override
    public java.awt.Component getComponent() {
        return container;
    }

}
