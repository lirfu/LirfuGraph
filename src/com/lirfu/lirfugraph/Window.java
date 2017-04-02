package com.lirfu.lirfugraph;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
    private int padding = 10;
    private JPanel container;

    public Window(JPanel container, boolean exitOnClose) {
        this.container = container;
        for (MouseListener l : container.getMouseListeners())
            addMouseListener(l);
        for (MouseMotionListener l : container.getMouseMotionListeners())
            addMouseMotionListener(l);

        setLocation(0, 0);
        setSize(1000, 400);

        setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : JFrame.DO_NOTHING_ON_CLOSE);

        setFocusable(true);
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                Window.this.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
    }

    public Window(JPanel container, boolean exitOnClose, boolean visibility) {
        this(container, exitOnClose);
        for (MouseListener l : container.getMouseListeners())
            addMouseListener(l);
        for (MouseMotionListener l : container.getMouseMotionListeners())
            addMouseMotionListener(l);
        setVisible(visibility);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        container.setLocation(padding, padding);
        container.setSize(getWidth() - padding * 2, getHeight() - padding * 2);
        container.paint(g);
    }
}
