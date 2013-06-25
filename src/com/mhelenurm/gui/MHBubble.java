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
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * This class controls the display of a bubble who's filled area corresponds to the percent of the
 * input value.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 24, 2013
 */
public class MHBubble extends JPanel {

	private static final long serialVersionUID = 31415679001L;
	private static final Logger LOG = Logger.getLogger(MHBubble.class.getName());
	private double level;
	private String title;

	/**
	 * Initializes MHBubble.
	 *
	 * @param level The initial level.
	 * @param title The title.
	 */
	public MHBubble(double level, String title) {
		this.level = level;
		this.title = title;
	}

	/**
	 * Initializes MHBubble.
	 *
	 * @param level The initial level.
	 */
	public MHBubble(double level) {
		this(level, "");
	}

	/**
	 * Initializes MHBubble.
	 *
	 * @param title The title.
	 */
	public MHBubble(String title) {
		this(0, title);
	}

	/**
	 * Initializes MHBubble.
	 */
	public MHBubble() {
		this(0, "");
	}

	/**
	 * Sets the title for the bubble.
	 *
	 * @param title The title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the title for the bubble.
	 *
	 * @return The title for the bubble.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the level for the bubble.
	 *
	 * @param level The level for the bubble.
	 */
	public void setLevel(double level) {
		this.level = level;
	}

	/**
	 * Gets the level for the bubble.
	 *
	 * @return The level for the bubble.
	 */
	public double getLevel() {
		return level;
	}

	/**
	 * Handles the drawing of the bubble.
	 * @param g2 The graphics object to draw upon.
	 */
	@Override
	public void paintComponent(Graphics g2) {
		g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, 16));

		Graphics2D g = (Graphics2D) g2;

		Ellipse2D d = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
		Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
		Area a = new Area(rect);
		a.subtract(new Area(d));

		g.clearRect(0, 0, getWidth(), getHeight());
		Color c = getBackground().brighter();
		g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 200));
		g.fillOval(0, 0, getWidth(), getHeight());


		double areafraction = level;

		double lo = 0.0, hi = 1.0, mid = (lo + hi) / 2.0;
		while (Math.abs(areaFunction(mid) - areafraction) > .001) {
			mid = (hi + lo) / 2.0;
			double newp = areaFunction(mid);
			if (newp > areafraction) {
				hi = mid;
			} else {
				lo = mid;
			}
		}
		double rectheightpercent = mid;

		g.setColor(Color.CYAN);
		g.fillRect(0, (int) (getHeight() - (getHeight() * rectheightpercent)), getWidth(), getHeight());

		g.setColor(g.getBackground());
		g.fill(a);

		String levelstr = String.format("%.3f", level);
		g.setColor(Color.BLACK);
		g.drawString(title, getWidth() / 2 - g.getFontMetrics().stringWidth(title) / 2, getHeight() / 2 - g.getFontMetrics().getHeight());
		g.drawString(levelstr, getWidth() / 2 - g.getFontMetrics().stringWidth(levelstr) / 2, getHeight() / 2 + g.getFontMetrics().getHeight());

	}

	private double areaFunction(double level) //returns the percent of area under a circle for level height being filled
	{
		double area;
		if (level > .5) {
			double x = (level - .5) * 2;
			area = Math.PI / 4 + (.5 * x * Math.sqrt(1.0 - x * x) + .5 * Math.asin(x));
		} else {
			double x = 2 * (0.5 - level);
			area = Math.PI / 4 - (.5 * x * Math.sqrt(1.0 - x * x) + .5 * Math.asin(x));
		}
		return area * 2.0 / Math.PI;
	}
}
