package com.lirfu.lirfugraph;

import java.awt.*;

public class Label extends GraphTemplate {
    private static final int lineSeparation = 2;

    protected String title;
    protected String[] contentLines;

    public Label(String title) {
        this.title = title;
    }

    @Override
    public void paint(Graphics g) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();

        g.setColor(interfaceColor);
        g.drawString(title, l.x + (int) (Config.FONT_SIZE * 0.39), l.y + Config.FONT_SIZE);

        g.setColor(primaryColor);
        g.drawRect(l.x, l.y, size.width + 2 * padding, size.height + 2 * padding);

        if (contentLines == null) return;

        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, Config.BUTTON_FONT_SIZE));
        int lineHeight = (size.height - 2 * Config.FONT_SIZE) / contentLines.length;
        int heightIndex = l.y + 1 + 2 * Config.FONT_SIZE;
        for (String content : contentLines) {
            g.drawString(content, l.x + padding + size.width / 2 - (int) (content.length() * Config.BUTTON_FONT_SIZE * 0.3), heightIndex);
            heightIndex += Config.FONT_SIZE + lineSeparation;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.contentLines = content.split("\n");
    }
}
