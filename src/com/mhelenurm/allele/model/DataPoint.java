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
package com.mhelenurm.allele.model;

import java.util.logging.Logger;

/**
 * This class represents a datapoint on a graph. It supports an id tag for differentiating various
 * types of points.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 24, 2013
 */
public class DataPoint extends Object {

	private static final Logger LOG = Logger.getLogger(DataPoint.class.getName());
	private final double x;
	private final double y;
	private final char id;

	/**
	 * Initializes a DataPoint object with a location and a type.
	 *
	 * @param x The x location of the datapoint.
	 * @param y The y location of the datapoint.
	 * @param id The id of the datapoint (for color-coding stuff).
	 */
	public DataPoint(double x, double y, char id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	/**
	 * Initializes a DataPoint object with a location.
	 *
	 * @param x The x location of the datapoint.
	 * @param y The y location of the datapoint.
	 */
	public DataPoint(double x, double y) {
		this.x = x;
		this.y = y;
		this.id = 0;
	}

	/**
	 * Gets the x value.
	 *
	 * @return The x value.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the y value.
	 *
	 * @return The y value.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Gets the id.
	 *
	 * @return The id.
	 */
	public char getId() {
		return id;
	}
}