package com.lirfu.demo;

import com.lirfu.lirfugraph.*;

/**
 * Created by lirfu on 16.03.17..
 */
public class Demo {
    public static void main(String[] args) {
        // Define a container type.
        VerticalContainer container = new VerticalContainer();


        // Define and add graphs.
        DualLinearGraph d = new DualLinearGraph("Dual linear graph");
        container.addRow(new Row(d)); // Add the graph wrapped in a row (one graph in row).

        LinearGraph l = new LinearGraph("LinearGraph");
        BarGraph b = new BarGraph("BarGraph");
        container.addRow(new Row(l,b)); // Add the graphs wrapped in a row (multiple graphs in a row).


        // Insert the values for display.
        for (double i = 0; i < 1e4; i++) {
            l.add(i % 1000 + 2 * i / 1000);
            d.add(i * i, i * i * Math.sin(0.01 * i));
        }
        b.add("2015.", 100);
        b.add("2016.", 170);
        b.add("2017.", 90);

        new Window(container, true, true).setTitle("Showcase");
    }
}
