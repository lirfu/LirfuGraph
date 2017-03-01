package com.lirfu.lirfugraph;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JPanel;

public class VerticalContainer extends JPanel {
	private LinkedList<Row> rows;

	public VerticalContainer() {
		this.rows = new LinkedList<>();
	}

	public VerticalContainer(Row... rows){
		this();
		Collections.addAll(this.rows, rows);
	}

	public VerticalContainer addRow(Row row) {
		rows.add(row);
		return this;
	}

	@Override
	public void paint(Graphics g) {
		Point l = getLocation();
		int rowHeight = getHeight() / rows.size();
		int counter = 0;

		for (Row r : rows) {
			r.setSize(getWidth(), rowHeight);
			r.setLocation(l.x, counter * rowHeight + l.y);
			r.paint(g);

			counter++;
		}
	}

}
