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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * This class represents a java GUI JTextField that controls input for only-numbers input and
 * rejects numbers that are outside of a specified range.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 25, 2013
 */
public class MHRangedField extends JTextField implements ActionListener {

	private static final Logger LOG = Logger.getLogger(MHRangedField.class.getName());
	private static final long serialVersionUID = 1L;
	private static Color BACKGROUND_DEFAULT;
	private static final Color BACKGROUND_RED = Color.RED;
	private static final int ANIMATION_DELAY = 200;
	private double min;
	private double max;
	private double value;
	private String error;
	private Timer animationTimer;
	private boolean isRed;
	private int animationCount;

	private MHRangedField() {
	}

	/**
	 * Calls super(defaultvalue) for the overridden text field and sets the variables.
	 *
	 * @param min The minimum accepted input value.
	 * @param max The maximum accepted input value.
	 * @param defaultvalue The default input value.
	 */
	public MHRangedField(double min, double max, double defaultvalue) {
		super(defaultvalue + "");
		this.min = min;
		this.max = max;
		this.value = defaultvalue;
		BACKGROUND_DEFAULT = super.getBackground();
	}

	/**
	 * Calls the default MHRangedField constructor with the default value as the average of the min
	 * and max.
	 *
	 * @param min The minimum accepted input value.
	 * @param max The maximum accepted input value.
	 */
	public MHRangedField(double min, double max) {
		this(min, max, (min + max) / 2);
	}

	/**
	 * Returns the last text that would pass verify().
	 *
	 * @return The value in string format.
	 */
	@Override
	public String getText() {
		return value + "";
	}

	/**
	 * Gets the value.
	 *
	 * @return The value.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Verifies the current text; if verify fails, then return the last value. If verify succeeds,
	 * set internal value to it and return the value. Verify also starts the animation if the
	 * current value exceeds the bounds.
	 */
	private void verifyValue() {
		String text = super.getText();
		try {
			value = Double.parseDouble(text);
			//if the value is outside the bounds, start the animation
			if (value < min) {
				error = "Value is below the minimum";
			} else if (value > max) {
				error = "Value is above the maximum";
			}

			if (value < min || value > max) {
				primeAnimation();
			}
		} catch (Exception e) {
		}
		super.setText(value + "");
		repaint();
	}

	/**
	 * Primes the animation and runs it.
	 */
	private void primeAnimation() {
		isRed = false;
		animationTimer = new Timer(ANIMATION_DELAY, this);
		animationCount = 0;
		animationTimer.start();
	}

	/**
	 * Stops the animation.
	 */
	private void stopAnimation() {
		animationTimer.stop();
		this.setBackground(BACKGROUND_DEFAULT);
	}

	/**
	 * Determines whether the value is in the range or not; if not, the animation begins.
	 *
	 * @return Whether the value of the text field is in range of
	 */
	public boolean inRange() {
		verifyValue();
		return value >= min && value <= max;
	}

	/**
	 * This is where the animation logic is handled.
	 *
	 * @param ae The actionevent that is sent.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		animationCount++;
		if (animationCount == 6) {
			stopAnimation();
		}
		isRed = !isRed;
		if (isRed) {
			setBackground(BACKGROUND_RED);
		} else {
			setBackground(BACKGROUND_DEFAULT);
		}
	}
}