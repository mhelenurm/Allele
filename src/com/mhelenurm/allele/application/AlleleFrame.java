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
package com.mhelenurm.allele.application;

import com.mhelenurm.allele.model.DataPoint;
import com.mhelenurm.allele.model.Population;
import com.mhelenurm.allele.model.RunType;
import com.mhelenurm.gui.MHBubble;
import com.mhelenurm.gui.MHGraph;
import com.mhelenurm.gui.MHHistogram;
import com.mhelenurm.gui.MHRangedField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * This class is the main entry point for the application launch.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 24, 2013
 */
public class AlleleFrame extends JFrame implements ActionListener {

	private static final Logger LOG = Logger.getLogger(AlleleFrame.class.getName());
	private static final String multiRunName = "Multi-Run";
	private static final String singleRunName = "Single Run";
	private static int singleRunGenerationCount = 50;
	private static final int singleRunDelay = 100;
	private static final int multiRunDelay = 20;
	private static int multiRunCount = 100;
	private static final long serialVersionUID = 1L;

	private final int initialFrameWidth = 900;
	private final int initialFrameHeight = 621;
	private int multiRunGenCount = 0;
	private int multiRunTotalCount = 0;
	private int multiRunPopulation;
	private int multiRunGenerations;
	private double multiRunFrequency;
	private MHGraph singleRunGraph;
	private MHGraph singleRunHetGraph;
	private MHBubble singleRunBubble;
	private JLabel singleRunPopLabel;
	private MHRangedField populationField;
	private JLabel singleRunFreqLabel;
	private MHRangedField frequencyField;
	private JLabel singleRunGenLabel;
	private MHRangedField generationField;
	private JComboBox runTypeComboBox;
	private MHHistogram multiRunHist;
	private MHHistogram multiRunHetHist;
	private JLabel multiRunCtLabel;
	private JButton resetButton;
	private Population singleRunPopulation;
	private Timer animationTimer;
	private RunType runningMode;
	private boolean inRun;
	private JButton runButton;
	private char currentDataFlavor;

	/**
	 * Initializes AlleleFrame.
	 */
	public AlleleFrame() {
		super("Allele Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();

		setSize(initialFrameWidth, initialFrameHeight);
		setResizable(false);
		setLocation(tk.getScreenSize().width / 2 - initialFrameWidth / 2, tk.getScreenSize().height / 2 - initialFrameHeight / 2);
		getContentPane().setLayout(null);

		runningMode = RunType.SINGLE_RUN;
		inRun = false;

		singleRunGraph = new MHGraph(MHGraph.GRAPH_LINE, 0, singleRunGenerationCount, 0, 1.0, 10, 10, "Generation", "Allele Frequency", "Allele Frequency Over Time");
		singleRunGraph.setSize(600, 300);
		singleRunGraph.setLocation(300, 0);
		getContentPane().add(singleRunGraph);

		multiRunHist = new MHHistogram(0.0, 1.0, 7, 100, 10, "Allele Frequency Histogram", "Allele Frequency");
		multiRunHist.setUpperBoundSeparate(true);
		multiRunHist.setLowerBoundSeparate(true);
		multiRunHist.setSize(600, 300);
		multiRunHist.setLocation(300, 0);

		singleRunHetGraph = new MHGraph(MHGraph.GRAPH_LINE, 0, singleRunGenerationCount, 0, 0.5, 5, 10, "Generation", "Heterozygosity", "Heterozygosity Over Time");
		singleRunHetGraph.setSize(600, 300);
		singleRunHetGraph.setLocation(300, 300);
		getContentPane().add(singleRunHetGraph);

		multiRunHetHist = new MHHistogram(0.0, 0.5, 6, 100, 10, "Heterozygosity Histogram", "Heterozygosity");
		multiRunHetHist.setLowerBoundSeparate(true);
		multiRunHetHist.setSize(600, 300);
		multiRunHetHist.setLocation(300, 300);

		multiRunCtLabel = new JLabel("Number of Runs: 0");
		multiRunCtLabel.setSize(280, 50);
		multiRunCtLabel.setLocation(10, 10);
		multiRunCtLabel.setHorizontalTextPosition(JLabel.CENTER);

		singleRunBubble = new MHBubble(.5, "Allele Frequency");
		singleRunBubble.setSize(210, 210);
		singleRunBubble.setLocation(45, 45);
		getContentPane().add(singleRunBubble);

		runButton = new JButton("Run!");
		runButton.setSize(280, 50);
		runButton.setLocation(10, 310);
		runButton.setFocusable(false);
		runButton.setToolTipText("Programmed By Mark Helenurm");
		getContentPane().add(runButton);

		resetButton = new JButton("Reset");
		resetButton.setSize(280, 50);
		resetButton.setLocation(10, 370);
		resetButton.setFocusable(false);
		getContentPane().add(resetButton);

		singleRunPopLabel = new JLabel("Population Size: ");
		singleRunPopLabel.setSize(140, 30);
		singleRunPopLabel.setLocation(10, 430);
		getContentPane().add(singleRunPopLabel);

		populationField = new MHRangedField(1, 10000, 100);
		populationField.setSize(80, 30);
		populationField.setLocation(210, 430);
		getContentPane().add(populationField);

		singleRunFreqLabel = new JLabel("Allele Frequency: ");
		singleRunFreqLabel.setSize(140, 30);
		singleRunFreqLabel.setLocation(10, 470);
		getContentPane().add(singleRunFreqLabel);

		frequencyField = new MHRangedField(0, 1, .5);
		frequencyField.setSize(80, 30);
		frequencyField.setLocation(210, 470);
		getContentPane().add(frequencyField);

		singleRunGenLabel = new JLabel("Number of Generations: ");
		singleRunGenLabel.setSize(180, 30);
		singleRunGenLabel.setLocation(10, 510);
		getContentPane().add(singleRunGenLabel);

		generationField = new MHRangedField(0, 10000, 50);
		generationField.setSize(80, 30);
		generationField.setLocation(210, 510);
		getContentPane().add(generationField);


		runTypeComboBox = new JComboBox(new String[]{singleRunName, multiRunName});
		runTypeComboBox.setSize(200, 50);
		runTypeComboBox.setLocation(50, 540);
		runTypeComboBox.setFocusable(false);
		runTypeComboBox.addActionListener((ActionListener) this);
		getContentPane().add(runTypeComboBox);

		currentDataFlavor = 4;

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inRun) {
					return;
				}

				runButton.setEnabled(false);
				inRun = true;

				if (runningMode == RunType.SINGLE_RUN) {
					singleRun();
				} else {
					multiRun();
				}

			}
		});
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inRun) {
					animationTimer.stop();
					runButton.setEnabled(true);
					inRun = false;
				} else {

					if (runningMode == RunType.SINGLE_RUN) {
						singleRunGraph.clearPoints(-1);
						singleRunHetGraph.clearPoints(-1);
						singleRunBubble.setLevel(Double.parseDouble(frequencyField.getText()));
						currentDataFlavor = 4;
					} else {
						multiRunTotalCount = 0;
						multiRunHist.clear();
						multiRunHist.setMaxHits(100);
						multiRunHetHist.clear();
						multiRunHetHist.setMaxHits(100);
						multiRunCtLabel.setText("Number of Runs: 0");
					}
				}
				repaint();
			}
		});


	}

	/**
	 * Handles an actionPerformed event.
	 *
	 * @param e The ActionEvent calling the actionPerformed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == runTypeComboBox) {
			if (inRun) {
				animationTimer.stop();
				runButton.setEnabled(true);
				inRun = false;
			}
			if (runTypeComboBox.getSelectedIndex() == 0) {
				if (runningMode == RunType.SINGLE_RUN) {
					return;
				}
				//remove all multi run components, add all single run ones
				getContentPane().add(singleRunGraph);
				getContentPane().add(singleRunHetGraph);
				getContentPane().add(singleRunBubble);

				getContentPane().remove(multiRunHist);
				getContentPane().remove(multiRunHetHist);
				getContentPane().remove(multiRunCtLabel);

				repaint();
				runningMode = RunType.SINGLE_RUN;
			} else {
				if (runningMode == RunType.MULTI_RUN) {
					return;
				}
				//remove all single run components, add all multi run ones
				getContentPane().remove(singleRunGraph);
				getContentPane().remove(singleRunHetGraph);
				getContentPane().remove(singleRunBubble);

				getContentPane().add(multiRunHist);
				getContentPane().add(multiRunHetHist);
				getContentPane().add(multiRunCtLabel);

				repaint();
				runningMode = RunType.MULTI_RUN;
			}
			return;
		}

		if (runningMode == RunType.SINGLE_RUN) {
			singleStep();
		} else {
			multiStep();
		}
	}

	/**
	 * Starts the single-run simulation if possible.
	 */
	private void singleRun() {
		double singleP = 0;
		int singlePop = 0;
		int singleGen = 0;

		boolean canContinue = true;

		if (frequencyField.inRange()) {
			singleP = frequencyField.getValue();
		} else {
			canContinue = false;
		}

		if (populationField.inRange()) {
			singlePop = (int) populationField.getValue();
		} else {
			canContinue = false;
		}

		if (generationField.inRange()) {
			singleGen = (int) generationField.getValue();
		} else {
			canContinue = false;
		}

		if (canContinue) {
			currentDataFlavor = (char) ((currentDataFlavor + 1) % 5);

			singleRunPopulation = new Population(singleP, singlePop);

			singleRunGenerationCount = singleGen;


			singleRunGraph.setXmax(singleRunGenerationCount);
			singleRunHetGraph.setXmax(singleRunGenerationCount);

			singleRunGraph.clearPoints(currentDataFlavor);
			singleRunHetGraph.clearPoints(currentDataFlavor);

			DataPoint graph1dp = new DataPoint(singleRunPopulation.getGeneration(), singleRunPopulation.getFrequency(), currentDataFlavor);
			DataPoint graph2dp = new DataPoint(singleRunPopulation.getGeneration(), singleRunPopulation.getHeterozygosity(), currentDataFlavor);
			singleRunGraph.addDataPoint(graph1dp);
			singleRunHetGraph.addDataPoint(graph2dp);
			singleRunBubble.setLevel(singleRunPopulation.getFrequency());

			animationTimer = new Timer(singleRunDelay, this);
			animationTimer.start();
		} else {
			runButton.setEnabled(true);
			inRun = false;
		}
	}

	/**
	 * Starts the multi-run simulation if possible.
	 */
	private void multiRun() {

		boolean canContinue = true;

		if (frequencyField.inRange()) {
			multiRunFrequency = frequencyField.getValue();
		} else {
			canContinue = false;
		}

		if (populationField.inRange()) {
			multiRunPopulation = (int) populationField.getValue();
		} else {
			canContinue = false;
		}

		if (generationField.inRange()) {
			multiRunGenerations = (int) generationField.getValue();
		} else {
			canContinue = false;
		}



		if (canContinue) {
			multiRunGenCount = 0;
			animationTimer = new Timer(multiRunDelay, this);
			animationTimer.start();
		} else {
			runButton.setEnabled(true);
			inRun = false;
		}
	}

	/**
	 * Represents a step in multi-run mode.
	 */
	private void multiStep() {
		multiRunGenCount++;
		if (multiRunGenCount >= multiRunCount) {
			runButton.setEnabled(true);
			animationTimer.stop();
			inRun = false;
		}
		multiRunTotalCount++;
		Population test = new Population(multiRunFrequency, multiRunPopulation);
		test.advance(multiRunGenerations);

		multiRunHist.addData(test.getFrequency());
		multiRunHetHist.addData(test.getHeterozygosity());

		multiRunHist.repaint();
		multiRunHetHist.repaint();

		multiRunCtLabel.setText("Number of Runs: " + multiRunTotalCount);
	}

	/**
	 * Represents a single step in single-run mode.
	 */
	private void singleStep() {
		if (singleRunPopulation.getGeneration() == singleRunGenerationCount || singleRunPopulation.getFrequency() == 1.0 || singleRunPopulation.getFrequency() == 0.0) {
			runButton.setEnabled(true);
			animationTimer.stop();
			inRun = false;
			return;
		}

		singleRunPopulation.advance(1);

		DataPoint graph1dp = new DataPoint(singleRunPopulation.getGeneration(), singleRunPopulation.getFrequency(), currentDataFlavor);
		DataPoint graph2dp = new DataPoint(singleRunPopulation.getGeneration(), singleRunPopulation.getHeterozygosity(), currentDataFlavor);

		singleRunGraph.addDataPoint(graph1dp);
		singleRunHetGraph.addDataPoint(graph2dp);
		singleRunBubble.setLevel(singleRunPopulation.getFrequency());

		singleRunGraph.repaint();
		singleRunHetGraph.repaint();
		singleRunBubble.repaint();
	}
}
