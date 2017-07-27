package com.lirfu.lirfugraph.graphs;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;

public class DualLinearGraph extends AbstractGraph {
	private int maxDrawnPoints;
	private final int numberOfHorizontals = 5;

	private ArrayList<Double> points1;
	private ArrayList<Double> points2;

	private int iterations;
	private boolean iterationsSet = false;
	private String title;

	public DualLinearGraph(String title) {

		this.title = title;
		this.points1 = new ArrayList<>();
		this.points2 = new ArrayList<>();
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
		this.iterationsSet = true;
	}

	@Override
	public void paint(Graphics g) {
		setDirty(false);

		ArrayList<Double> list1 = null;
		ArrayList<Double> list2 = null;
		synchronized (points1) {
			list1 = new ArrayList<>(points1);
		}
		synchronized (points2) {
			list2 = new ArrayList<>(points2);
		}
		Dimension size = getAdjustedSize();
		Point l = graph.getLocation();

		if (!iterationsSet)
			iterations = list1.size();

		if (iterations == 0) {
			g.setColor(primaryColor);
			g.drawString("EMPTY!", l.x + graph.getWidth() / 4, l.y + graph.getHeight() / 2);
			return;
		}

		double max = Math.max(Collections.max(list1), Collections.max(list2));
		double min = Math.min(Collections.min(list1), Collections.min(list2));

		if (max == 0) {
			g.setColor(primaryColor);
			g.drawString("Max is 0!", l.x + graph.getWidth() / 6, l.y + graph.getHeight() / 2);
			return;
		}

		double delta = (double) size.width / (iterations - 1);
		double zoom = 1;
		if (max != 0)
			zoom = size.height / (max - min);

		// Draw interface horizontals.
		g.setColor(interfaceColor);
		for (int i = 1; i <= numberOfHorizontals; i++) {
			double y = size.height * (1 - i / (double) (numberOfHorizontals + 1)) + padding;
			g.drawLine(l.x + padding, l.y + (int) y, l.x + size.width + padding, l.y + (int) y);
			g.drawString("" + ((max - min) * i / (numberOfHorizontals + 1) + min), l.x + padding + 3, l.y + (int) y - 3);
		}

		drawTitleAndFrame(g, title);

		g.drawString("" + iterations, l.x + padding + size.width - 10, l.y + size.height - 10 + 2 * padding); // max iteration
		g.drawString("Max: " + max, l.x + graph.getWidth() / 2, l.y + padding - 3);
		g.drawString("Min: " + min, l.x + graph.getWidth() / 2, l.y + size.height + padding + 13);

		// First value
		double lastValue1 = list1.get(0);
		double lastValue2 = list2.get(0);

				// The first value
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform old = g2.getTransform();
		g2.rotate(-Math.PI / 2);
		g2.setColor(primaryColor);
		g2.drawString("" + lastValue1, -l.y - graph.getHeight() + padding, l.x + padding - 5);
		g2.setColor(secondaryColor);
		g2.drawString("" + lastValue2, -l.y - graph.getHeight() + padding, l.x + padding - 16);
		g2.setTransform(old);

		// Draw the curve
		double currentx = padding;
		for (int i = 1; i < list1.size(); i++) {
			double val = list1.get(i);
			g.setColor(primaryColor);
			g.drawLine(l.x + (int) currentx, l.y + (int) (size.height - (lastValue1 - min) * zoom) + padding, l.x + (int) (currentx + delta), l.y + (int) (size.height - (val - min) * zoom) + padding);
			lastValue1 = val;

			val = list2.get(i);
			g.setColor(secondaryColor);
			g.drawLine(l.x + (int) currentx, l.y + (int) (size.height - (lastValue2 - min) * zoom) + padding, l.x + (int) (currentx + delta), l.y + (int) (size.height - (val - min) * zoom) + padding);
			lastValue2 = val;

			currentx += delta;
		}

				// The last value
		g2.rotate(Math.PI / 2);
		g2.setColor(primaryColor);
		g2.drawString("" + lastValue1, l.y + padding, -l.x + padding - 5 - graph.getWidth());
		g2.setColor(secondaryColor);
		g2.drawString("" + lastValue2, l.y + padding, -l.x + padding - 16 - graph.getWidth());
		g2.setTransform(old);
	}

	public void add(double value1, double value2) {
		points1.add(value1);
		points2.add(value2);

		setDirty(true);
		//		synchronized (points1 ) {
		//			//			System.out.println("Size " + points.size() + " max " + maxDrawnPoints);
		//			if (points.size() > 0 && maxDrawnPoints > 0 && points.size() > maxDrawnPoints)
		//				for (int i = 0; i < points.size(); i += 2)
		//					points.remove(i);
		//		}
	}

	public void setSize(Dimension d) {
		graph.setSize(d);
		maxDrawnPoints = d.width - 2 * padding;
	}

	public void setSize(int width, int height) {
		graph.setSize(width, height);
		maxDrawnPoints = width - 2 * padding;
		//		System.out.println("Size " + maxDrawnPoints);
	}
}
