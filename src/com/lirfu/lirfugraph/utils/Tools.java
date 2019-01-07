package com.lirfu.lirfugraph.utils;

public class Tools {
    public static double round(double value, int precision) {
        return Math.round(value * Math.pow(10, precision)) / Math.pow(10, precision);
    }
}
