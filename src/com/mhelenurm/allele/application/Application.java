package com.mhelenurm.allele.application;
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
import java.awt.EventQueue;
import java.util.logging.Logger;

/**
 * This class wraps the JFrame into a safe-executing application.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 25, 2013
 */
public class Application {
	private static final Logger LOG = Logger.getLogger(Application.class.getName());

	/**
	 * Runs the main program.
	 *
	 * @param args The arguments to the application.
	 */
	public static void main(String[] args) {
		final AlleleFrame mainFrame = new AlleleFrame();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainFrame.setVisible(true);
			}
		});
	}
}