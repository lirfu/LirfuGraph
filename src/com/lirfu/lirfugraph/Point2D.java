package com.lirfu.lirfugraph;

/**
 * Created by lirfu on 19.08.17..
 */
public class Point2D {
    public double x, y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(Point2D p) {
        this.x = p.x;
        this.y = p.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point2D add(Point2D p) {
        x += p.x;
        y += p.y;
        return this;
    }

    public Point2D sub(Point2D p) {
        x -= p.x;
        y -= p.y;
        return this;
    }

    public Point2D multiply(double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public double distanceFrom(Point2D p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
