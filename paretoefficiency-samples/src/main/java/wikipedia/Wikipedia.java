package wikipedia;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.File;
import java.util.Collection;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.paretoefficiency.ParetoComparator;
import org.paretoefficiency.ParetoHelper;

import common.Canvas;

/**
 * This sample take the example of the Pareto frontier described on <a
 * href="http://en.wikipedia.org/wiki/Pareto_efficiency#Pareto_frontier"
 * >Wikipedia</a>. The SVG is parsed in order to get the points.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
@SuppressWarnings("serial")
public class Wikipedia extends JFrame {

	public static void main(String[] args) {
		new Wikipedia().setVisible(true);
	}

	public Wikipedia() {
		ParetoComparator<Integer, Point> comparator = new ParetoComparator<Integer, Point>();
		comparator.setDimensionComparator(0, new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				return Integer.valueOf(o1.x).compareTo(Integer.valueOf(o2.x));
			}
		});
		comparator.setDimensionComparator(1, new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				/*
				 * the comparison is reversed because the graphical Y is in the
				 * opposite sense of the mathematical Y.
				 */
				return -Integer.valueOf(o1.y).compareTo(Integer.valueOf(o2.y));
			}
		});

		File file = new File("Front_pareto.svg");
		Collection<Point> points = SVGHelper.getPointsFrom(file);

		Canvas canvas = new Canvas();
		canvas.setPoints(points);
		canvas.setFrontier(ParetoHelper.<Point> getMinimalFrontierOf(points,
				comparator));
		canvas.setPreferredSize(new Dimension(500, 500));
		setLayout(new GridLayout(1, 1));
		getContentPane().add(canvas);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Wikipedia Sample");
		pack();
	}
}
