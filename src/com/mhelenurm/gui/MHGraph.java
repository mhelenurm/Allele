/*
 * Copyright © 2013 Mark Helenurm
 * 
 * This code is copyrighted by Mark Helenurm.
 * Do not steal this code under the threat of legal
 * prosecution.
 * 
 * If you have suggestions, comments, or requests to
 * borrow code, email me at <mhelenurm@gmail.com>
 */
package com.mhelenurm.gui;

import com.mhelenurm.allele.model.CSVExport;
import com.mhelenurm.allele.model.DataPoint;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * This class handles the display of a graph.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 24, 2013
 */
public class MHGraph extends JPanel implements CSVExport {

	private static final long serialVersionUID = 31415678999L;
	/**
	 * The ID for whether the graph is a line graph.
	 */
	public static final int GRAPH_LINE = 0;
	/**
	 * The ID for whether the graph is a point graph.
	 */
	public static final int GRAPH_POINTS = 1;
	private static final Logger LOG = Logger.getLogger(MHGraph.class.getName());
	private double xmin, xmax;
	private double ymin, ymax;
	private int xdivisions, ydivisions;
	private String xtitle, ytitle;
	private String graphTitle;
	private int mode;
	private ArrayList<DataPoint> datapoints;
	private final Color[] COLORS = new Color[]{Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW.darker().darker(), Color.ORANGE, Color.GRAY, Color.CYAN, Color.MAGENTA, Color.PINK};

	private MHGraph() {
	}

	/**
	 * Initializes MHGraph
	 *
	 * @param mode The mode of the graph.
	 * @param xmin The minimum x value.
	 * @param xmax The maximum x value.
	 * @param ymin The minimum y value.
	 * @param ymax The maximum y value.
	 * @param xdivisions The number of x divisions.
	 * @param ydivisions The number of y divisions.
	 * @param xtitle The title of the x axis.
	 * @param ytitle The title of the y axis.
	 * @param graphTitle The title of the graph.
	 * @param points The initial points data.
	 */
	public MHGraph(int mode, double xmin, double xmax, double ymin, double ymax, int xdivisions, int ydivisions, String xtitle, String ytitle, String graphtitle, DataPoint points[]) {
		this.mode = mode;
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
		this.xdivisions = xdivisions;
		this.ydivisions = ydivisions;
		this.xtitle = xtitle;
		this.ytitle = ytitle;
		this.graphTitle = graphtitle;

		datapoints = new ArrayList<DataPoint>((points.length == 0) ? points.length : 10);
		datapoints.addAll(Arrays.asList(points));
	}

	/**
	 * Initializes MHGraph
	 *
	 * @param mode The mode of the graph.
	 * @param xmin The minimum x value.
	 * @param xmax The maximum x value.
	 * @param ymin The minimum y value.
	 * @param ymax The maximum y value.
	 * @param xdivisions The number of x divisions.
	 * @param ydivisions The number of y divisions.
	 * @param xtitle The title of the x axis.
	 * @param ytitle The title of the y axis.
	 * @param graphTitle The title of the graph.
	 */
	public MHGraph(int mode, double xmin, double xmax, double ymin, double ymax, int xdivisions, int ydivisions, String xtitle, String ytitle, String graphtitle) {
		this(mode, xmin, xmax, ymin, ymax, xdivisions, ydivisions, xtitle, ytitle, graphtitle, new DataPoint[]{});
	}

	/**
	 * Initializes MHGraph
	 *
	 * @param mode The mode of the graph.
	 * @param xmin The minimum x value.
	 * @param xmax The maximum x value.
	 * @param ymin The minimum y value.
	 * @param ymax The maximum y value.
	 * @param xdivisions The number of x divisions.
	 * @param ydivisions The number of y divisions.
	 * @param points The initial points data.
	 */
	public MHGraph(int mode, double xmin, double xmax, double ymin, double ymax, int xdivisions, int ydivisions, DataPoint points[]) {
		this(mode, xmin, xmax, ymin, ymax, xdivisions, ydivisions, "", "", "", new DataPoint[]{});
	}

	/**
	 * Initializes MHGraph
	 *
	 * @param mode The mode of the graph.
	 * @param xmin The minimum x value.
	 * @param xmax The maximum x value.
	 * @param ymin The minimum y value.
	 * @param ymax The maximum y value.
	 * @param xdivisions The number of x divisions.
	 * @param ydivisions The number of y divisions.
	 */
	public MHGraph(int mode, double xmin, double xmax, double ymin, double ymax, int xdivisions, int ydivisions) {
		this(mode, xmin, xmax, ymin, ymax, xdivisions, ydivisions, "", "", "", new DataPoint[]{});
	}

	/**
	 * Adds a data point to the graph.
	 *
	 * @param d The data point to add.
	 */
	public void addDataPoint(DataPoint d) {
		datapoints.add(d);
	}

	/**
	 * Adds all the data points from a datapoint array.
	 *
	 * @param d The array of datapoints.
	 */
	public void addDataPoints(DataPoint d[]) {
		datapoints.addAll(Arrays.asList(d));
	}

	/**
	 * Gets the minimum x axis value.
	 *
	 * @return The minimum x axis value.
	 */
	public double getXmin() {
		return xmin;
	}

	/**
	 * Sets the minimum x axis value.
	 *
	 * @param xmin The minimum x axis value.
	 */
	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	/**
	 * Sets the maximum x axis value.
	 *
	 * @return The maximum x axis value.
	 */
	public double getXmax() {
		return xmax;
	}

	/**
	 * Sets the maximum x axis value.
	 *
	 * @param xmax The maximum x axis value.
	 */
	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	/**
	 * Gets the minimum y axis value.
	 *
	 * @return The minimum y axis value.
	 */
	public double getYmin() {
		return ymin;
	}

	/**
	 * Sets the minimum y axis value.
	 *
	 * @param ymin The minimum y axis value.
	 */
	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	/**
	 * Gets the maximum y axis value.
	 *
	 * @return The maximum y axis value.
	 */
	public double getYmax() {
		return ymax;
	}

	/**
	 * Sets the maximum y axis value.
	 *
	 * @param ymax The maximum y axis value.
	 */
	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

	/**
	 * Gets the number of x axis divisions.
	 *
	 * @return The number of x axis divisions.
	 */
	public int getXdivisions() {
		return xdivisions;
	}

	/**
	 * Sets the number of x axis divisions.
	 *
	 * @param xdivisions The number of x axis divisions.
	 */
	public void setXdivisions(int xdivisions) {
		this.xdivisions = xdivisions;
	}

	/**
	 * Gets the number of y axis divisions.
	 *
	 * @return The number of y axis divisions.
	 */
	public int getYdivisions() {
		return ydivisions;
	}

	/**
	 * Sets the number of y axis divisions.
	 *
	 * @param ydivisions The number of y axis divisions.
	 */
	public void setYdivisions(int ydivisions) {
		this.ydivisions = ydivisions;
	}

	/**
	 * Gets the x axis graph title.
	 *
	 * @return The x axis title.
	 */
	public String getXtitle() {
		return xtitle;
	}

	/**
	 * Sets the x axis graph title.
	 *
	 * @param xtitle The x axis title.
	 */
	public void setXtitle(String xtitle) {
		this.xtitle = xtitle;
	}

	/**
	 * Gets the y axis graph title.
	 *
	 * @return The title for the y axis.
	 */
	public String getYtitle() {
		return ytitle;
	}

	/**
	 * Sets the y axis title of the graph.
	 *
	 * @param ytitle The y axis title for the graph.
	 */
	public void setYtitle(String ytitle) {
		this.ytitle = ytitle;
	}

	/**
	 * Gets the graph title.
	 *
	 * @return The graph title.
	 */
	public String getGraphtitle() {
		return graphTitle;
	}

	/**
	 * Sets the graph title.
	 *
	 * @param graphTitle The graph title.
	 */
	public void setGraphtitle(String graphtitle) {
		this.graphTitle = graphtitle;
	}

	/**
	 * Gets the graph mode.
	 *
	 * @return The graph mode.
	 */
	public int getmode() {
		return mode;
	}

	/**
	 * Sets the graph mode.
	 *
	 * @param mode The graph mode.
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * Clears the data points of a certain type.
	 *
	 * @param mode
	 */
	public void clearPoints(int mode) {
		if (mode == -1) {
			datapoints.clear();
		} else {
			for (int i = datapoints.size() - 1; i >= 0; i--) {
				if (datapoints.get(i).getId() == mode) {
					datapoints.remove(i);
				}
			}
		}
	}

	/**
	 * Handles the drawing of the graph component.
	 *
	 * @param g The graphics object to be drawn on.
	 */
	@Override
	public void paintComponent(Graphics g) {
		setBackground(Color.WHITE);
		g.clearRect(0, 0, getWidth(), getHeight());

		double marginx = .15 * (double) getWidth();
		double marginy = .15 * (double) getHeight();
		double graphheight = .7 * (double) getHeight();
		double graphwidth = .7 * (double) getWidth();
		double tickradius = .01 * (double) getWidth();

		g.setColor(Color.BLACK);
		g.drawLine((int) marginx, (int) (getHeight() - (marginy)), (int) (marginx + graphwidth), (int) (getHeight() - (marginy)));
		g.drawLine((int) marginx, (int) (getHeight() - (marginy)), (int) (marginx), (int) (getHeight() - (marginy + graphheight)));

		g.setFont(new Font(g.getFont().getName(), Font.BOLD, g.getFont().getSize() + 3));

		g.drawString(graphTitle, getWidth() / 2 - g.getFontMetrics().stringWidth(graphTitle) / 2, (int) (marginy - g.getFontMetrics().getAscent()));
		g.setFont(new Font(g.getFont().getName(), Font.PLAIN, g.getFont().getSize() - 3));
		g.drawString(xtitle, getWidth() / 2 - g.getFontMetrics().stringWidth(xtitle) / 2, (int) (getHeight() * .975));
		g.drawString(ytitle, (int) (marginx - g.getFontMetrics().stringWidth(ytitle) / 2), (int) (marginy - g.getFontMetrics().getHeight()));

		for (int i = 0; i <= xdivisions; i++) {

			int yheight1 = (int) (getHeight() - marginy + tickradius);
			int yheight2 = (int) (getHeight() - marginy - tickradius);
			int xheight = (int) ((double) i / (double) xdivisions * graphwidth + marginx);
			g.drawLine(xheight, yheight1, xheight, yheight2);

			String label = String.format("%.2f", xmin + (xmax - xmin) * ((double) i / (double) xdivisions));
			double test = (xmax - xmin) / (double) xdivisions;
			if (test == (int) test) {
				label = String.format("%d", (int) (xmin + (xmax - xmin) * ((double) i / (double) xdivisions)));
			}
			g.drawString(label, xheight - g.getFontMetrics().stringWidth(label) / 2, yheight1 + g.getFontMetrics().getHeight());
		}
		for (int i = 0; i <= ydivisions; i++) {
			int xheight1 = (int) (marginx - tickradius);
			int xheight2 = (int) (marginx + tickradius);
			int yheight = (int) (getHeight() - ((double) i / (double) ydivisions * graphheight + marginy));
			g.drawLine(xheight1, yheight, xheight2, yheight);

			String label = String.format("%.2f", ymin + (ymax - ymin) * ((double) i / (double) ydivisions));
			double test = (ymax - ymin) / (double) ydivisions;
			if (test == (int) test) {
				label = String.format("%d", (int) (ymin + (ymax - ymin) * ((double) i / (double) ydivisions)));
			}
			g.drawString(label, (int) (xheight1 - g.getFontMetrics().stringWidth(label) * 1.1), yheight + g.getFontMetrics().getAscent() / 2);
		}
		//lines are drawn in

		//TEXT DRAWN HERE!!

		if (mode == GRAPH_LINE) {
			for (int i = 0; i < 10; i++) {
				g.setColor(COLORS[i]);
				int lastx = -1;
				int lasty = -1;
				for (DataPoint p : datapoints) {
					if (p.getX() >= xmin && p.getX() <= xmax && p.getY() >= ymin && p.getY() <= ymax && p.getId() == i) {
						int xcoord = (int) (marginx + graphwidth * (p.getX() - xmin) / (xmax - xmin));
						int ycoord = (int) (getHeight() - (marginy + graphheight * (p.getY() - ymin) / (ymax - ymin)));
						if (lastx != -1 && lasty != -1) {
							g.drawLine(lastx, lasty, xcoord, ycoord);
						}
						lastx = xcoord;
						lasty = ycoord;
					}
				}
			}
		} else if (mode == GRAPH_POINTS) {
			for (DataPoint p : datapoints) {
				if (p.getX() >= xmin && p.getX() <= xmax && p.getY() >= ymin && p.getY() <= ymax) {
					int xcoord = (int) (marginx + graphwidth * (p.getX() - xmin) / (xmax - xmin));
					int ycoord = (int) (getHeight() - (marginy + graphheight * (p.getY() - ymin) / (ymax - ymin)));
					g.setColor(COLORS[p.getId()]);
					g.drawLine(xcoord + (int) tickradius / 2, ycoord + (int) tickradius / 2, xcoord - (int) tickradius / 2, ycoord - (int) tickradius / 2);
					g.drawLine(xcoord + (int) tickradius / 2, ycoord - (int) tickradius / 2, xcoord - (int) tickradius / 2, ycoord + (int) tickradius / 2);
				}
			}
		}
	}

	@Override
	public String getExportName() {
		return graphTitle;
	}

	@Override
	public StringBuilder exportCSV() {
		StringBuilder ss = new StringBuilder(1024);
		final StringBuilder ln = new StringBuilder("\n");
		for (int i = 0; i < 10; i++) {
			StringBuilder linex = new StringBuilder(128);
			StringBuilder liney = new StringBuilder(128);
			linex.append("type ").append(i).append(" x");
			liney.append("type ").append(i).append(" y");
			boolean hasData = false;
			for (DataPoint p : datapoints) {
				if (p.getId() == i) {
					hasData = true;
					linex.append(",").append(p.getX());
					liney.append(",").append(p.getY());
				}
			}
			if (hasData) {
				ss.append(linex).append(ln);
				ss.append(liney).append(ln);
			}
		}
		return ss;
	}
}
