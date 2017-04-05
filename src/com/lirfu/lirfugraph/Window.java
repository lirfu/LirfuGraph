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
        for (KeyListener l : container.getKeyListeners())
            addKeyListener(l);

        setLocation(0, 0);
        setSize(1000, 400);

        setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : JFrame.DO_NOTHING_ON_CLOSE);

        setFocusable(true);
    }

    public Window(JPanel container, boolean exitOnClose, boolean visibility) {
        this(container, exitOnClose);
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
