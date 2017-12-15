package com.lirfu.lirfugraph;

import java.awt.*;
import java.awt.event.*;

public class Button extends GraphTemplate {
    private final Color HIGHLIGHT_COLOR = Color.decode("0x44b2ef"); // TODO onClick

    private String title;
    private boolean enabled = true;
    private Color currentColor = primaryColor;

    public Button(String title, ActionListener listener) {
        padding = 5;
        this.title = title;
        template.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!enabled) return;
                Point l = template.getLocation();
                Dimension size = getAdjustedSize();
                Point p = mouseEvent.getPoint();

                // Check bounds
                if (p.x < l.x + padding || p.y < l.y + padding || p.x > l.x + size.width - padding || p.y > l.y + size.height - padding)
                    return;

                listener.actionPerformed(new ActionEvent(Button.this, 137, "onClick"));
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();
        g.setColor(enabled ? currentColor : Color.LIGHT_GRAY);
        g.fillRoundRect(l.x + padding, l.y + padding, size.width, size.height, 10, 10);
        g.setColor(backgroundColor);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, Config.BUTTON_FONT_SIZE));
        g.drawString(title, l.x + padding + size.width / 2 - (int) (title.length() * Config.BUTTON_FONT_SIZE * 0.3), l.y + padding + size.height / 2);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
