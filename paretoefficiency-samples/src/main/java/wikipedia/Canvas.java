package wikipedia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel {
	private Collection<Point> points = null;
	private Collection<Point> frontier = null;

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());

		if (getPoints() == null) {
			throw new IllegalStateException("No points given.");
		}
		if (getFrontier() != null && !getPoints().containsAll(getFrontier())) {
			Collection<Point> delta = new HashSet<Point>(getFrontier());
			delta.removeAll(getPoints());
			throw new IllegalStateException(
					"Some frontier points are unknown : " + delta);
		}

		int xMax = 0;
		int xMin = 0;
		int yMax = 0;
		int yMin = 0;
		for (Point coords : getPoints()) {
			xMax = Math.max(xMax, coords.x);
			xMin = Math.min(xMin, coords.x);
			yMax = Math.max(yMax, coords.y);
			yMin = Math.min(yMin, coords.y);
		}
		System.out.println("Draw in [" + xMin + "," + yMin + "]->[" + xMax
				+ "," + yMax + "]");
		double xRate = (double) getWidth() / (xMax - xMin);
		double yRate = (double) getHeight() / (yMax - yMin);
		int width = 10;
		int height = 10;

		int counter = 0;
		for (Point coords : getPoints()) {
			int xDraw = (int) Math.floor(xRate * coords.x);
			int yDraw = (int) Math.floor(yRate * coords.y);

			if (getFrontier().contains(coords)) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.BLUE);
			}
			g.fillRect(xDraw - width / 2, yDraw - height / 2, width, height);
			System.out.println("draw " + (++counter) + " : " + coords);
		}
	}

	public Collection<Point> getPoints() {
		return points;
	}

	public void setPoints(Collection<Point> points) {
		this.points = points;
	}

	public Collection<Point> getFrontier() {
		return frontier;
	}

	public void setFrontier(Collection<Point> frontier) {
		this.frontier = frontier;
	}
}
