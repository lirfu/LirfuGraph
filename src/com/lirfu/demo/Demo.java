package com.lirfu.demo;

import com.lirfu.lirfugraph.BarGraph;
import com.lirfu.lirfugraph.Window;
import com.lirfu.lirfugraph.Row;
import com.lirfu.lirfugraph.VerticalContainer;
import com.lirfu.lirfugraph.graphs.*;

import java.awt.*;
import java.util.Random;

/**
 * Created by lirfu on 16.03.17..
 */
public class Demo {
    public static void main(String[] args) {
        // Define a container type.
        VerticalContainer container = new VerticalContainer();

        // Define and add graphs.
//        DualLinearGraph d = new DualLinearGraph("Dual linear graph");
        MultiLinearGraph d = new MultiLinearGraph("MultiLinearGraph", 4, "Positive square-Sinusoid-Cosinusoid-Negative square".split("-"));
        d.setShowDots(false);
        container.addRow(new Row(d)); // Add the graph wrapped in a row (one graph in row).

        LinearGraph l = new LinearGraph("LinearGraph");
        l.setShowDots(false).setShowValues(false);
        BarGraph b = new BarGraph("BarGraph");
        container.addRow(new Row(l, b)); // Add the graphs wrapped in a row (multiple graphs in a row).


        // Insert the values for display.
        for (double i = 0; i < 1e4; i++) {
            l.add(i % 1000 + 2 * i / 1000);
            d.add(i * i, i * i * Math.sin(0.01 * i), i * i * Math.cos(0.01 * i), -i * i);
        }
        b.add("2015.", 100);
        b.add("2016.", 170);
        b.add("2017.", 90);

        ScatterGraph sc = new ScatterGraph("ScatterGraph", "t1", "t2");
        Surface2DGraph surf = new Surface2DGraph("Surface2D", input -> Math.sin(.1 * input.x) * Math.cos(2 * input.y), new Surface2DGraph.Bounds(-5, 5, -5, 5));
        surf.setMinZ(0.);
        container.addRow(new Row(sc, surf));

        Random r = new Random(42);
        Point p1 = new Point(60, 50);
        Point p2 = new Point(20, 10);
        for (int i = 0; i < 50; i++) {
            sc.add(0, r.nextGaussian() * 30 + p1.x, r.nextGaussian() * 10 + p1.y);
            sc.add(1, r.nextGaussian() * 30 + p2.x, r.nextGaussian() * 30 + p2.y);
        }

        new Window(container, true, true).setTitle("Showcase").setSize(new Dimension(800, 800));
    }
}
