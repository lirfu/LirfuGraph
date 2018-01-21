package com.lirfu.lirfugraph;

public class Utils {
    public static double round(double value, int precision) {
        return Math.round(value * Math.pow(10, precision)) / Math.pow(10, precision);
    }
}
