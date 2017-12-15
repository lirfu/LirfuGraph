package com.lirfu.lirfugraph;

import java.awt.*;

public class Label extends GraphTemplate {
    protected String title;
    protected String content;

    public Label(String title) {
        this.title = title;
    }

    @Override
    public void paint(Graphics g) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();

        g.setColor(interfaceColor);
        g.drawString(title, l.x + padding, l.y + padding-5);

        g.setColor(primaryColor);
        g.drawRect(l.x + padding, l.y + padding, size.width, size.height);

        if (content == null) return;

        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, Config.BUTTON_FONT_SIZE));
        g.drawString(content, l.x + padding + size.width / 2 - (int) (content.length() * Config.BUTTON_FONT_SIZE * 0.3), l.y + padding + size.height / 2 + Config.BUTTON_FONT_SIZE / 2);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
