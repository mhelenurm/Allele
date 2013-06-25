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
 * This class is the logic handler for a population.
 *
 * @author Mark Helenurm <mhelenurm@gmail.com>
 * @version 1.0
 * @since Jun 24, 2013
 */
public class Population {

	private static final Logger LOG = Logger.getLogger(Population.class.getName());
	private double initialFrequency; //allele frequency
	private int generationNumber; //current population generation
	private double frequency; //allele frequency
	private int population;
	private boolean alleles[];

	private Population() {
	}

	/**
	 * Initializes a Population.
	 *
	 * @param frequency The initial frequency of the allele.
	 * @param population The initial population count.
	 */
	public Population(double frequency, int population) {
		initialFrequency = (double) ((int) (frequency * population)) / (double) population;
		this.frequency = initialFrequency;
		this.population = population;

		alleles = new boolean[2 * population];
		for (int i = 0; i < 2 * population; i++) {
			alleles[i] = Math.random() < initialFrequency;
		}
	}

	/**
	 * Advances the model by a certain amount of time.
	 *
	 * @param t The amount of timesteps to advance the model.
	 */
	public void advance(int t) {
		for (int i = 0; i < t; i++) {
			generationNumber++;
			int positives = 0;
			for (int j = 0; j < population * 2; j++) {
				if (Math.random() < frequency) {
					positives++;
				}
			}
			frequency = (double) positives / (double) (population * 2);
		}
	}

	/**
	 * Gets the initial frequency of the allele.
	 *
	 * @return The initial frequency of the allele.
	 */
	public double getInitialFrequency() {
		return initialFrequency;
	}

	/**
	 * Gets the current generation number of the model.
	 *
	 * @return The current generation number of the model.
	 */
	public int getGeneration() {
		return generationNumber;
	}

	/**
	 * Gets the current size of the population.
	 *
	 * @return The current size of the population.
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 * Gets the current allele frequency.
	 *
	 * @return The current allele freguency.
	 */
	public double getFrequency() {
		return frequency;
	}

	/**
	 * Gets the heterozygosity of the population.
	 *
	 * @return The heterozygosity of the population.
	 */
	public double getHeterozygosity() {
		return frequency * 2.0 * (1.0 - frequency);
	}
}