package com.lirfu.lirfugraph;

import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JFrame;

public class Window {
    private JFrame frame;

    private int padding = 10;
    private Component container;

    public Window(Component container, boolean exitOnClose) {
        this.frame=new JFrame("LirfuGraph"){
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                container.getComponent().setLocation(padding, padding);
                container.getComponent().setSize(getWidth() - padding * 2, getHeight() - padding * 2);
                container.getComponent().paint(g);
            }
        };
        this.container = container;

        for (MouseListener l : container.getComponent().getMouseListeners())
            frame.addMouseListener(l);
        for (MouseMotionListener l : container.getComponent().getMouseMotionListeners())
            frame.addMouseMotionListener(l);
        for (KeyListener l : container.getComponent().getKeyListeners())
            frame.addKeyListener(l);

        frame.setLocation(0, 0);
        frame.setSize(1000, 400);

        frame.setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : JFrame.DO_NOTHING_ON_CLOSE);

        frame.setFocusable(true);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                frame.repaint();
            }
        });
    }

    public Window(Component container, boolean exitOnClose, boolean visibility) {
        this(container, exitOnClose);
        frame.setVisible(visibility);
    }

    public void setTitle(String title){
        frame.setTitle(title);
    }
}
