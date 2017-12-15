package com.lirfu.lirfugraph;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

public class Window extends Component {
    private JFrame frame;

    private int padding = 10;
    private Color background = Color.decode("0x000000");
    private Component container;
    private RepaintManager repaintManager;

    public Window(Component container, boolean exitOnClose) {
        this.frame = new JFrame("LirfuGraph") {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(background);
                g.fillRect(0, 0, getWidth(), getHeight());

                container.getComponent().setLocation(padding, padding);
                container.getComponent().setSize(getWidth() - padding * 2, getHeight() - padding * 2);
                container.getComponent().paint(g);
            }
        };

        this.container = container;

        repaintManager = new RepaintManager() {
            @Override
            public void requestRepaint() {
                frame.repaint();
            }

            @Override
            public void requestRepaint(int x, int y, int width, int height) {
                frame.repaint(x, y, width, height);
            }
        };
        container.setRepaintManager(repaintManager);

        for (MouseListener l : container.getComponent().getMouseListeners())
            frame.addMouseListener(l);
        for (MouseMotionListener l : container.getComponent().getMouseMotionListeners())
            frame.addMouseMotionListener(l);
        for (KeyListener l : container.getComponent().getKeyListeners())
            frame.addKeyListener(l);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                frame.repaint();
            }
        });

        frame.setLocation(0, 0);
        frame.setSize(1000, 600);

        frame.setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
        frame.setFocusable(true);
    }

    public Window(Component container, boolean exitOnClose, boolean visibility) {
        this(container, exitOnClose);
        frame.setVisible(visibility);
    }

    public Window setTitle(String title) {
        frame.setTitle(title);
        return this;
    }

    public Window setSize(Dimension size) {
        frame.setSize(size);
        return this;
    }

    public Window setLocation(Point p) {
        frame.setLocation(p);
        return this;
    }

    public void repaint() {
        frame.repaint();
    }

    @Override
    void setRepaintManager(RepaintManager manager) {
        // do nothing
    }

    public void setVisibility(boolean visibility) {
        frame.setVisible(visibility);
    }

    @Override
    public java.awt.Component getComponent() {
        throw new IllegalStateException("Window can't be a child!");
    }
}
