# LirfuGraph
An extensional library for data presentation in Java.

## What can it do?
It has a built-in simple window builder for basic graph tiling.

The `VerticalContainer` accepts `Row`s and stacks them one under another.<br>
The `Row` class represents a row of graphs stacking them from left to right.<br>
The current graphs are, **and should be** scalable depending on the window size.

Graphs currenty supported:
* `BarGraph`
  * Presenting each added name-value pair as a bar.
  * When the bar gets too slim, it turns into a LinearGraph.

* `LinearGraph`
  * Adding a line connecting values as they are added.
  * Displaying the value of the first and the last added value.

* `DualLinearGraph`
  * A `LinearGraph` with two simultaneous lines of different colors.

## How to use it?
### The long way:

The first thing you need is a `VerticalContainer` to hold the `Row`s:
```java
VerticalContainer container = new VerticalContainer();
```

Now you can create and add rows to the container:
```java
Row row = new Row();
container.addRow(row);
```

Next add some graphs to the row:
```java
GraphTemplate graph = new BarGraph("Graph name");
row.addGraph(graph);
```

Now populate the graph with some data:
```java
for(int i = 0; i <= 10; i++)
  graph.add("Value" + i, (i-5)(i-5));
```

Finally, build and show the window:
```java
new Window(container, true, true);
```

### The short way:
First build the graph:
```java
GraphTemplate graph = new DualLinearGraph("Graph name");
for(int i = 0; i <= 10; i++)
  graph.add((i-5), (i-5)(i-5));
```

Then build the container:
```java
VerticalContainer container = new VerticalContainer();
container.addRow(new Row().addGraph(graph));
```

And finally, build and show the window:
```java
new Window(container, true, true);
```

## How to build your own graph?
Each graph extends the `GraphTemplate`, which containsfollowing standardised values:
* padding
* interface color
* primary graph color
* secondary graph color (if a graph has two data sets)

It contains the `getAdjustedSize` method which returns your actual graph dimensions, subtracting the padding value.<br>
The `drawTitleAndFrame` method should be called to draw the standardised graph frame and graph title.

The rest is up to you!  :smile:<br>
All you need to implement is:
* A method for adding data and a structure to store it
* Override the `paint(Graphics g)` method and draw (present) the stored data
* Get the graph location in window with `getLocation()` and correct drawing positions with it

## Want to contribute?
If you like this project you're welcome to contribute with code or suggestions.<br>
The project was created in IntelliJ IDEA 2016.1 (Community Edition).
