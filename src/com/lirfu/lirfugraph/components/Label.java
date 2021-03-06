package com.lirfu.lirfugraph.components;

import com.lirfu.lirfugraph.Config;
import com.lirfu.lirfugraph.GraphTemplate;

import java.awt.*;

public class Label extends GraphTemplate {
    private static final int lineSeparation = 2;

    protected String title;
    protected String[] contentLines;

    public Label(String title) {
        this.title = title;
    }

    public Label(String title, String content){
        this(title);
        setContent(content);
    }

    @Override
    public void paint(Graphics g) {
        Point l = template.getLocation();
        Dimension size = getAdjustedSize();

        g.setColor(primaryColor);
        g.drawString(title, l.x + (int) (Config.FONT_SIZE * 0.39), l.y + Config.FONT_SIZE);

        g.setColor(interfaceColor);
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

    public Label setContent(String content) {
        this.contentLines = content.split("\n");
        return this;
    }

    @Override
    protected void calculate() {
        //TODO
    }
}
