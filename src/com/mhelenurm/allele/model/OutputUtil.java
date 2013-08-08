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

import java.io.File;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 25, 2013
 */
public class OutputUtil {

	private static final Logger LOG = Logger.getLogger(OutputUtil.class.getName());

	/**
	 * Writes a string to a filename.
	 *
	 * @param filename The name of the file to write to.
	 * @param data The data to write to the file.
	 * @return Whether it succeeded writing.
	 */
	public static void writeToFile(String data) {
		boolean returnval = true;
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				if (!file.getName().endsWith(".csv")) {
					file.renameTo(new File(file.getAbsolutePath() + ".csv"));
				}
				if (!file.isDirectory()) {
					if (file.exists()) {
						int canoverwrite = JOptionPane.showConfirmDialog(fc, "Are you sure it's okay to overwrite " + file.getAbsolutePath() + "?", "Overwrite?", JOptionPane.YES_NO_OPTION);
						if (canoverwrite == JOptionPane.YES_OPTION) {
							returnval = writeToFile(file, data);
						}
					} else {
						returnval = writeToFile(file, data);
					}
				}

			} else {
				returnval = false;
			}
		} catch (Exception e) {
			returnval = false;
		}
	}

	public static boolean writeToFile(File outputFile, String data) {
		PrintWriter fileWriter = null;

		try {
			fileWriter = new PrintWriter(outputFile);
			fileWriter.write(data);
			fileWriter.close();
		} catch (Exception e) {
			if (fileWriter != null) {
				fileWriter.close();
			}
			return false;
		}
		return true;
	}

	private OutputUtil() {
	}
}
