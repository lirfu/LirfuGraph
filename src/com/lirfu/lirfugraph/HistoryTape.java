package com.lirfu.lirfugraph;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lirfu on 27.07.17..
 */
public class HistoryTape<T> {
    private ArrayList<T> datapoints;
    private int size;
    private int startIndex;
    private int endIndex;

    public HistoryTape(int size) {
        datapoints = new ArrayList<T>();
        this.size = size;
        startIndex = endIndex = 0;
    }

    public void add(T point) {
        datapoints.add(point);

        endIndex++;
        if (endIndex > size)
            startIndex++;
    }

    public int size() {
        return size;
    }

    public ArrayList<T> getSegment() {
        return new ArrayList<>(datapoints.subList(startIndex, endIndex));
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = startIndex;

            @Override
            public boolean hasNext() {
                return index < endIndex;
            }

            @Override
            public T next() {
                return datapoints.get(index++);
            }
        };
    }
}
