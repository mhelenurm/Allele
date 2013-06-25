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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * This class handle the display of a histogram.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 24, 2013
 */
public class MHHistogram extends JPanel {

	private static final long serialVersionUID = 31415679000L;
	private static final Logger LOG = Logger.getLogger(MHHistogram.class.getName());
	private double minamount;
	private double maxamount;
	private int divisions;
	private int counts[];
	private double total;
	private int maxhits;
	private String graphTitle;
	private String axisTitle;
	private boolean lowerBoundSeparate;
	private boolean upperBoundSeparate;

	private MHHistogram() {
	}

	/**
	 * Initializes the histogram.
	 *
	 * @param minamount The minimum amount.
	 * @param maxamount The maximum amount.
	 * @param divisions The number of divisions.
	 * @param maxhits The maximum number of hits.
	 * @param graphTitle The title of the graph.
	 * @param axisTitle The title of the axis.
	 * @param counts The data for the histogram.
	 */
	public MHHistogram(double minamount, double maxamount, int divisions, int maxhits, String graphTitle, String axisTitle, int counts[]) {
		this.minamount = minamount;
		this.maxamount = maxamount;
		this.divisions = divisions;
		this.maxhits = maxhits;
		this.graphTitle = graphTitle;
		this.axisTitle = axisTitle;
		this.counts = counts;
		this.total = 0;
	}

	/**
	 * Initializes the histogram.
	 *
	 * @param minamount The minimum amount.
	 * @param maxamount The maximum amount.
	 * @param divisions The number of divisions.
	 * @param maxhits The maximum number of hits.
	 * @param hitsdivisions The number of hits divisions.
	 * @param graphTitle The title of the graph.
	 * @param axisTitle The title of the axis.
	 */
	public MHHistogram(double minamount, double maxamount, int divisions, int maxhits, int hitsdivisions, String graphTitle, String axisTitle) {
		this(minamount, maxamount, divisions, maxhits, graphTitle, axisTitle, new int[hitsdivisions]);
	}

	/**
	 * Initializes the histogram.
	 *
	 * @param minamount The minimum amount.
	 * @param maxamount The maximum amount.
	 * @param divisions The number of divisions.
	 * @param maxhits The maximum number of hits.
	 * @param counts The data for the histogram.
	 */
	public MHHistogram(double minamount, double maxamount, int divisions, int maxhits, int counts[]) {
		this(minamount, maxamount, divisions, maxhits, "", "", counts);
	}

	/**
	 * Initializes the histogram.
	 *
	 * @param minamount The minimum amount.
	 * @param maxamount The maximum amount.
	 * @param divisions The number of divisions.
	 * @param maxhits The maximum number of hits.
	 * @param hitsdivisions The number of hits divisions.
	 */
	public MHHistogram(double minamount, double maxamount, int divisions, int maxhits, int hitsdivisions) {
		this(minamount, maxamount, divisions, maxhits, new int[hitsdivisions]);
	}

	/**
	 * Sets the maximum number of hits.
	 *
	 * @param hits The maximum number of hits.
	 */
	public void setMaxHits(int hits) {
		maxhits = hits;
	}

	/**
	 * Sets the title of the histogram.
	 *
	 * @param title The title of the histogram.
	 */
	public void setGraphTitle(String title) {
		graphTitle = title;
	}

	/**
	 * Sets the title of the y axis.
	 *
	 * @param title The title of the y axis.
	 */
	public void setAxisTitle(String title) {
		axisTitle = title;
	}

	/**
	 * Sets whether the lower bound is a separate category.
	 *
	 * @param b Whether the lower bound is a separate category.
	 */
	public void setLowerBoundSeparate(boolean b) {
		lowerBoundSeparate = b;
	}

	/**
	 * Sets whether the upper bound is a separate category.
	 *
	 * @param b Whether the upper bound is a separate category.
	 */
	public void setUpperBoundSeparate(boolean b) {
		upperBoundSeparate = b;
	}

	/**
	 * Gets the minimum bound.
	 *
	 * @return The minimum bound.
	 */
	public double getMinAmount() {
		return minamount;
	}

	/**
	 * Gets the maximum bound.
	 *
	 * @return The maximum bound.
	 */
	public double getMaxAmount() {
		return maxamount;
	}

	/**
	 * Gets the number of axis divisions.
	 *
	 * @return The number of axis divisions.
	 */
	public double getAxisDivisions() {
		return divisions;
	}

	/**
	 * Gets the histogram data.
	 *
	 * @return The histogram data.
	 */
	public int[] getHits() {
		return counts;
	}

	/**
	 * Gets the maximum number of hits displayed on the chart.
	 *
	 * @return The maximum number of hits diaplayed on the chart.
	 */
	public int getMaxHits() {
		return maxhits;
	}

	/**
	 * Gets the graph title.
	 *
	 * @return The graph title.
	 */
	public String getGraphTitle() {
		return graphTitle;
	}

	/**
	 * Gets the axis title.
	 *
	 * @return The axis title.
	 */
	public String getAxisTitle() {
		return axisTitle;
	}

	/**
	 * Gets whether the lower bound is separate.
	 *
	 * @return Whether the lower bound is separate.
	 */
	public boolean getLowerBoundSeparate() {
		return lowerBoundSeparate;
	}

	/**
	 * Gets whether the upper bound is separate.
	 *
	 * @return Whether the upper bound is separate.
	 */
	public boolean getUpperBoundSeparate() {
		return upperBoundSeparate;
	}

	/**
	 * Adds a piece of data to the histogram.
	 *
	 * @param data The piece of data to add.
	 */
	public void addData(double data) {
		total++; //TAG
		for (int i = 0; i < divisions; i++) {
			if (counts[i] > (int) (.9 * (double) maxhits)) {
				maxhits *= 2;
				break;
			}
		}

		if (data < minamount || data > maxamount) {
			return;
		}
		if (data == minamount && lowerBoundSeparate) {
			counts[0]++;
			return;
		}
		if (data == maxamount && upperBoundSeparate) {
			counts[divisions - 1]++;
			return;
		}
		int seps = ((lowerBoundSeparate) ? 1 : 0) + ((upperBoundSeparate) ? 1 : 0);
		for (int i = 0; i < divisions - seps; i++) {

			double upperbound = minamount + (maxamount - minamount) * ((double) (i + 1) / (double) (divisions - seps));
			if (data <= upperbound) {
				counts[i + ((lowerBoundSeparate) ? 1 : 0)]++;
				return;
			}
		}

	}

	/**
	 * Handles the drawing of the histogram.
	 *
	 * @param g The graphics context on which to draw the histogram.
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);

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
		g.drawString(axisTitle, (int) (marginx - g.getFontMetrics().stringWidth(axisTitle) / 2), (int) (marginy - g.getFontMetrics().getHeight()));

		for (int i = 0; i <= counts.length; i++) {

			int yheight1 = (int) (getHeight() - marginy + tickradius);
			int yheight2 = (int) (getHeight() - marginy - tickradius);
			int xheight = (int) ((double) i / (double) counts.length * graphwidth + marginx);
			g.drawLine(xheight, yheight1, xheight, yheight2);

			String label = String.format("%d", (maxhits * i / counts.length));
			g.drawString(label, xheight - g.getFontMetrics().stringWidth(label) / 2, yheight1 + g.getFontMetrics().getHeight());
		}

		double bars = (double) divisions;
		double spaces = bars + 1;
		double spacesspace = graphheight * .1; //they occupy a total of 20% of the space
		double spaceperspace = spacesspace / spaces;

		double barheight = graphheight * .9 / bars;

		for (int i = 0; i < divisions; i++) {
			int seps = ((lowerBoundSeparate) ? 1 : 0) + ((upperBoundSeparate) ? 1 : 0);


			double lowerbound = minamount + (maxamount - minamount) * ((double) (i + ((lowerBoundSeparate) ? -1 : 0)) / (double) (divisions - seps));
			double upperbound = minamount + (maxamount - minamount) * ((double) (i + 1 + ((lowerBoundSeparate) ? -1 : 0)) / (double) (divisions - seps));

			String label = String.format("%.1f-%.1f", lowerbound, upperbound);
			if (i == 0 && lowerBoundSeparate) {
				label = "" + minamount;
			}
			if (i == divisions - 1 && upperBoundSeparate) {
				label = "" + maxamount;
			}

			double topleftx = marginx;
			double toplefty = (getHeight() - ((double) (i + 1) * spaceperspace + (double) (i + 1) * barheight + marginy));

			double barwidth = ((double) counts[i] / (double) maxhits * graphwidth);
			g.fillRect((int) topleftx, (int) toplefty, (int) barwidth, (int) barheight);

			g.drawString(label, (int) (topleftx - g.getFontMetrics().stringWidth(label) - tickradius), (int) ((toplefty + toplefty + barheight) / 2));

			if (total != 0) //TAG
			{
				String percentlabel = String.format("(%.1f%c)", (double) counts[i] / total * 100.0, '%'); //TAG
				g.drawString(percentlabel, (int) (topleftx + barwidth + tickradius), (int) (toplefty + barheight / 2.0)); //TAG
			} //TAG
		}
	}

	/**
	 * Clears all the data in the histogram.
	 */
	public void clear() {
		for (int i = 0; i < divisions; i++) {
			counts[i] = 0;
		}
		total = 0;
	}
}
