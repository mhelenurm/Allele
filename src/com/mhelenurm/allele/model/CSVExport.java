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

/**
 * This class acts as an interface for objects that are exportable as CSV files.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 25, 2013
 */
public interface CSVExport {

	/**
	 * Gets the name that is used as an export identifier.
	 *
	 * @return A string name identifier.
	 */
	public String getExportName();

	/**
	 * Exports data in CSV format to an input stream.
	 *
	 * @return The outputstream that controls the export.
	 */
	public StringBuilder exportCSV();
}