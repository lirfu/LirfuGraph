package com.lirfu.demo;

import com.lirfu.lirfugraph.*;
import com.lirfu.lirfugraph.graphs.BarGraph;
import com.lirfu.lirfugraph.graphs.DualLinearGraph;
import com.lirfu.lirfugraph.graphs.LinearGraph;
import com.lirfu.lirfugraph.layouts.Row;
import com.lirfu.lirfugraph.layouts.VerticalContainer;

/**
 * Created by lirfu on 16.03.17..
 */
public class Demo {
    public static void main(String[] args) {
        // Define a container type.
        VerticalContainer container = new VerticalContainer();

        Window window = new Window(container, true, true);
        window.setTitle("Showcase");

        // Define and add graphs.
        DualLinearGraph d = new DualLinearGraph("Dual linear graph");
        container.addRow(new Row(d)); // Add the graph wrapped in a row (one graph in row).

        LinearGraph l = new LinearGraph("LinearGraph");
        container.addRow(new Row(l));

//        BarGraph b = new BarGraph("BarGraph");
//        container.addRow(new Row(l,b)); // Add the graphs wrapped in a row (multiple graphs in a row).


        Thread thread1 = new Thread(() -> {
            // Insert the values for display.
            for (double i = 0; i < 1e4; i++) {
                l.add(i % 1000 + 200 * Math.sin(i));
//                d.add(i * i, i * i * Math.sin(0.01 * i));

                window.redraw();
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            // Insert the values for display.
            for (double i = 0; i < 1e3; i++) {
                d.add(i * i, i * i * Math.sin(0.01 * i));

                window.redraw();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

//        b.add("2015.", 100);
//        b.add("2016.", 170);
//        b.add("2017.", 90);

        thread1.start();
        thread2.start();
    }
}
