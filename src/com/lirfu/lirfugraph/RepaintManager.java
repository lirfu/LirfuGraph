package com.lirfu.lirfugraph;

public abstract class RepaintManager {
    public void requestRepaint(int centerX, int centerY, int halfSize) {
        requestRepaint(centerX - halfSize, centerY - halfSize, centerX + halfSize, centerY + halfSize);
    }

    abstract void requestRepaint(int x, int y, int width, int height);

    abstract void requestRepaint();
}
