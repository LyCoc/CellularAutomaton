package cellularAutomaton.core;

import java.util.Observable;

import ec.util.MersenneTwisterFast;

/**
 * Copyright (c) 2013, William Philbert (alias Seigneur Necron), Kevin Grandemange<br />
 * All rights reserved.<br />
 * <br />
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:<br />
 * <br />
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.<br />
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.<br />
 * - The name/pseudo of the authors may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.<br />
 * - You must give the authors credit for their work. Don't claim their work
 *   (edited or not) as completely your own work, or allow others to carry on
 *   believing the work is yours without correcting them.<br />
 * - Don't tell people they're free to use your work if it contains the work of
 *   others. (You don't have the right to grant permission to others unless it's
 *   all your own work.)<br />
 * - Don't make money on the work of the authors.<br />
 * <br />
 * This software is provided "as is" and any express or implied warranties are
 * disclaimed. In no event shall the authors be liable for any direct or indirect
 * damages caused from the use of this software.<br />
 * All damages caused from the use or misuse of this software fall on the user.
 * 
 * @author William Philbert, Kevin Grandemange.
 */
public abstract class CellularAutomaton<T extends Object> extends Observable {
	
	// Constants :
	
	private static final String NL = System.lineSeparator();
	
	private static final int MIN_UPDATE_PERIOD = 1;
	private static final int MAX_UPDATE_PERIOD = 1000;
	private static final int MIN_GRID_SIZE = 1;
	private static final int MAX_GRID_SIZE = 500;
	private static final double MIN_INIT_PROBA = 0.0;
	private static final double MAX_INIT_PROBA = 1.0;
	private static final int MIN_FIRST_ITERATION = 0;
	private static final int MIN_NB_ITERATION = 1;
	private static final int MIN_NB_LAUNCH = 1;
	private static final int MIN_NB_VARIATION = 1;
	
	public static final String UPDATE_PERIOD = "Update period";
	public static final String GRID_HEIGHT = "Grid height";
	public static final String GRID_WIDTH = "Grid width";
	public static final String PROBA_INIT = "Init proba";
	public static final String COMPUTE_STATISTICS = "Compute statistics";
	public static final String FIRST_ITERATION = "First iteration";
	public static final String NB_ITERATION = "Nb iterations";
	public static final String NB_LAUNCH = "Nb launches";
	public static final String NB_VARIATION = "Nb variations";
	
	// Fields :
	
	private int height;
	private int width;
	private double probaInit;
	
	private T[][] tab;
	private T[][] oldTab;
	
	private boolean[][] pattern;
	private int patternXPos;
	private int patternYPos;
	private int nbOfOneInPattern;
	
	private boolean running;
	private boolean computeStats;
	private boolean waitBetweenIteration;
	private int updatePeriod;
	
	private Thread updateThread;
	protected final Object lock = new Object();
	
	private int firstIteration;
	private int maxNbIteration;
	private int maxNbLaunch;
	private int maxNbVariation;
	
	private int nbIteration;
	private int nbLaunch;
	private int nbVariation;
	
	protected MersenneTwisterFast rand = new MersenneTwisterFast();
	
	// Constructors :
	
	/**
	 * Build a cellular automaton with default settings and pattern.
	 */
	protected CellularAutomaton() {
		this.initSettings();
		this.initConfig();
		this.initPattern();
	}
	
	// Getters :
	
	/**
	 * Return the row count.
	 * @return the height of the grid.
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Return the column count.
	 * @return the width of the grid.
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Return the probability for a cell to be filled during initialisation.
	 * @return the initialisation probability.
	 */
	public double getProbaInit() {
		return this.probaInit;
	}
	
	/**
	 * Return the grid containing the state of each cell.
	 * @return the grid.
	 */
	public T[][] getTab() {
		return this.tab;
	}
	
	/**
	 * Return the previous state of the grid.
	 * @return the old grid.
	 */
	public T[][] getOldTab() {
		return this.oldTab;
	}
	
	/**
	 * Return the pattern indicating which surounding cells are checked during transitions.
	 * @return the pattern.
	 */
	public boolean[][] getPattern() {
		return this.pattern;
	}
	
	/**
	 * Return the X position of the cell in the pattern.
	 * @return the X position of the cell in the pattern.
	 */
	public int getPatternXPos() {
		return this.patternXPos;
	}
	
	/**
	 * Return the Y position of the cell in the pattern.
	 * @return the Y position of the cell in the pattern.
	 */
	public int getPatternYPos() {
		return this.patternYPos;
	}
	
	/**
	 * Return the number of cells in the pattern which are checked during transitions.
	 * @return the number of cells in the pattern which are checked during transitions.
	 */
	public int getNbOfOneInPattern() {
		return this.nbOfOneInPattern;
	}
	
	/**
	 * Indicate if the CA is running.
	 * @return true if the CA is running, else false.
	 */
	public boolean isRunning() {
		return this.running;
	}
	
	/**
	 * Indicate if the CA computes statistics between iterations.
	 * @return true if the CA computes statistics between iterations, else false.
	 */
	public boolean isComputeStats() {
		return this.computeStats;
	}
	
	/**
	 * Indicate if the CA waits between iterations (for the grid to be drawn in the gui).
	 * @return true if the CA waits between iterations, else false.
	 */
	public boolean isWaitBetweenIteration() {
		return this.waitBetweenIteration;
	}
	
	/**
	 * Return the update period (in mili-second) of the CA.
	 * @return the update period of the CA.
	 */
	public int getUpdatePeriod() {
		return this.updatePeriod;
	}
	
	/**
	 * Return the number of times that this CA has been updated for the actual launch.
	 * @return the number of times that this CA has been updated for the actual launch.
	 */
	public int getNbIteration() {
		return this.nbIteration;
	}
	
	/**
	 * Return the number of times that the CA has been initialized for the actual variation.
	 * @return the number of times that the CA has been initialized for the actual variation.
	 */
	public int getNbLaunch() {
		return this.nbLaunch;
	}
	
	/**
	 * Return the number of variation the CA has done.
	 * @return the number of variation the CA has done.
	 */
	public int getNbVariation() {
		return this.nbVariation;
	}
	
	/**
	 * Return the number of the first iteration where stats have to be collected.
	 * @return the number of the first iteration where stats have to be collected.
	 */
	public int getFirstIteration() {
		return this.firstIteration;
	}
	
	/**
	 * Return the number of iterations where stats have to be collected.
	 * @return the number of iterations where stats have to be collected.
	 */
	public int getMaxNbIteration() {
		return this.maxNbIteration;
	}
	
	/**
	 * Return the number of times that the CA has to be launched.
	 * @return the number of times that the CA has to be launched.
	 */
	public int getMaxNbLaunch() {
		return this.maxNbLaunch;
	}
	
	/**
	 * Return the number of variations that the CA has to done.
	 * @return the number of variations that the CA has to done.
	 */
	public int getMaxNbVariation() {
		return this.maxNbVariation;
	}
	
	// Setters :
	
	/**
	 * Change the height of the grid.
	 * @param height - the new height of the grid.
	 */
	protected void setHeight(int height) {
		this.height = height;
		this.setChanged();
	}
	
	/**
	 * Change the width of the grid.
	 * @param width - the new width of the grid.
	 */
	protected void setWidth(int width) {
		this.width = width;
		this.setChanged();
	}
	
	/**
	 * Change the probability for a cell to be filled during initialisation.
	 * @param probaInit - the new initialisation probability.
	 */
	protected void setProbaInit(double probaInit) {
		this.probaInit = probaInit;
		this.setChanged();
	}
	
	/**
	 * Change the grid.
	 * @param tab - the new grid.
	 */
	protected void setTab(T[][] tab) {
		this.tab = tab;
		this.setChanged();
	}
	
	/**
	 * Change the old grid.
	 * @param oldTab - the new old grid. XD
	 */
	protected void setOldTab(T[][] oldTab) {
		this.oldTab = oldTab;
		this.setChanged();
	}
	
	/**
	 * Change the pattern indicating which surounding cells are checked during transitions.
	 * @param pattern - the new pattern.
	 */
	protected void setPattern(boolean[][] pattern) {
		this.pattern = pattern;
		this.setChanged();
	}
	
	/**
	 * Change the X position of the cell in the pattern.
	 * @param patternXPos - the new X position of the cell in the pattern.
	 */
	protected void setPatternXPos(int patternXPos) {
		if(patternXPos < 0 || patternXPos >= this.getPattern()[0].length) {
			throw new IllegalArgumentException("The cell position must be inside of the pattern.");
		}
		this.patternXPos = patternXPos;
		this.setChanged();
	}
	
	/**
	 * Change the Y position of the cell in the pattern.
	 * @param patternYPos - the new Y position of the cell in the pattern.
	 */
	protected void setPatternYPos(int patternYPos) {
		if(patternYPos < 0 || patternYPos >= this.getPattern().length) {
			throw new IllegalArgumentException("The cell position must be inside of the pattern.");
		}
		this.patternYPos = patternYPos;
		this.setChanged();
	}
	
	/**
	 * Update the number of cells in the pattern which are checked during transitions.
	 * @param nbOfOneInPattern - the number of cells in the pattern which are checked during transitions.
	 */
	protected void setNbOfOneInPattern(int nbOfOneInPattern) {
		this.nbOfOneInPattern = nbOfOneInPattern;
		this.setChanged();
	}
	
	/**
	 * Pause/Resume the CA.
	 * @param running - true to resume, false to pause.
	 */
	protected void setRunning(boolean running) {
		this.running = running;
		this.setChanged();
		if(running) {
			synchronized(this.lock) {
				this.lock.notifyAll();
			}
		}
	}
	
	/**
	 * Choose if the CA must compute statistics between iterations.
	 * @param computeStats - true if the CA compute statistics between iterations, else flase.
	 */
	protected void setComputeStats(boolean computeStats) {
		this.computeStats = computeStats;
		this.setChanged();
	}
	
	/**
	 * Choose if the CA must wait between iterations (for the grid to be drawn on the gui).
	 * @param waitBetweenIteration - true if the CA must wait between interations, else flase.
	 */
	protected void setWaitBetweenIteration(boolean waitBetweenIteration) {
		this.waitBetweenIteration = waitBetweenIteration;
		this.setChanged();
	}
	
	/**
	 * Change the update period of the CA.
	 * @param updatePeriod - the update period (in mili-second) of the CA.
	 */
	protected void setUpdatePeriod(int updatePeriod) {
		this.updatePeriod = updatePeriod;
		this.setChanged();
	}
	
	/**
	 * Sets the number of times that this grid has been updated for the actual launch.
	 * @param nbIteration - the number of times that this grid has been updated for the actual launch.
	 */
	protected void setNbIteration(int nbIteration) {
		this.nbIteration = nbIteration;
		this.setChanged();
	}
	
	/**
	 * Set the number of times that the grid has been initialized for the actual variation.
	 * @param nbLaunch - the number of times that the grid has been initialized for the actual variation.
	 */
	protected void setNbLaunch(int nbLaunch) {
		this.nbLaunch = nbLaunch;
		this.setChanged();
	}
	
	/**
	 * Set the number of variation this CA has done.
	 * @param nbVariation - the number of variation this CA has done.
	 */
	protected void setNbVariation(int nbVariation) {
		this.nbVariation = nbVariation;
		this.setChanged();
	}
	
	/**
	 * Set the number of the first iteration where stats have to be collected.
	 * @param firstIteration - the number of the first iteration where stats have to be collected.
	 */
	protected void setFirstIteration(int firstIteration) {
		this.firstIteration = firstIteration;
		this.setChanged();
	}
	
	/**
	 * Set the number of iterations where stats have to be collected for each launch.
	 * @param maxNbIteration - the number of iterations where stats have to be collected for each launch.
	 */
	protected void setMaxNbIteration(int maxNbIteration) {
		this.maxNbIteration = maxNbIteration;
		this.setChanged();
	}
	
	/**
	 * Set the number of times that the CA has to be launched for each variation.
	 * @param maxNbLaunch - the number of times that the CA has to be launched for each variation.
	 */
	protected void setMaxNbLaunch(int maxNbLaunch) {
		this.maxNbLaunch = maxNbLaunch;
		this.setChanged();
	}
	
	/**
	 * Set the number of variations that the CA has to do.
	 * @param maxNbVariation - the number of variations that the CA has to do.
	 */
	protected void setMaxNbVariation(int maxNbVariation) {
		this.maxNbVariation = maxNbVariation;
		this.setChanged();
	}
	
	// Methods :
	
	/**
	 * Return the default height of the grid.
	 * @return the default height of the grid.
	 */
	protected int getDefaultHeight() {
		return 50;
	}
	
	/**
	 * Return the default width of the grid.
	 * @return the default width of the grid.
	 */
	protected int getDefaultWidth() {
		return 50;
	}
	
	/**
	 * Return the default initialisation probability.
	 * @return the default initialisation probability.
	 */
	protected double getDefaultProbaInit() {
		return 0.4;
	}
	
	/**
	 * Return the default update period.
	 * @return the default update period.
	 */
	protected int getDefaultUpdatePeriod() {
		return 100;
	}
	
	/**
	 * Return the default pattern.
	 * @return the default pattern.
	 */
	protected boolean[][] getDefaultPattern() {
		return new boolean[][] { {true, true, true}, {true, false, true}, {true, true, true}};
	}
	
	/**
	 * Return the default X position of the cell in the pattern.
	 * @return the default X position of the cell in the pattern.
	 */
	protected int getDefaultPatternXPos() {
		return 1;
	}
	
	/**
	 * Return the default Y position of the cell in the pattern.
	 * @return the default Y position of the cell in the pattern.
	 */
	protected int getDefaultPatternYPos() {
		return 1;
	}
	
	/**
	 * Return the default number of the first iteration where stats have to be collected.
	 * @return the default number of the first iteration where stats have to be collected.
	 */
	protected int getDefaultFirstIteration() {
		return 1000;
	}
	
	/**
	 * Return the default number of iterations where stats have to be collected for each launch.
	 * @return the default number of iterations where stats have to be collected for each launch.
	 */
	protected int getDefaultMaxNbIteration() {
		return 100;
	}
	
	/**
	 * Return the default number of times that the CA has to be launched for each variation.
	 * @return the default number of times that the CA has to be launched for each variation.
	 */
	protected int getDefaultMaxNbLaunch() {
		return 10;
	}
	
	/**
	 * Return the default number of variations that the CA has to do.
	 * @return the default number of variations that the CA has to do.
	 */
	protected int getDefaultMaxNbVariation() {
		return 2;
	}
	
	/**
	 * Initialize the basic configuration to default values.
	 */
	protected void initConfig() {
		this.setConfig(this.getDefaultHeight(), this.getDefaultWidth(), this.getDefaultProbaInit(), false, this.getDefaultFirstIteration(), this.getDefaultMaxNbIteration(), this.getDefaultMaxNbLaunch(), this.getDefaultMaxNbVariation());
	}
	
	/**
	 * Set the basic configuration to specific values.
	 * @param height - the row count.
	 * @param width - the column count.
	 * @param probaInit - the probability for a cell to be filled during initialisation.
	 * @param computeStats - whether or not the CA compute statistics between iterations.
	 * @param firstIteration - the number of the first iteration where stats have to be collected.
	 * @param maxNbIteration - the number of iterations where stats have to be collected for each launch.
	 * @param maxNbLaunch - the number of times that the CA has to be launched for each variation.
	 * @param maxNbVariation - the number of variation that the CA has to do.
	 */
	protected void setConfig(int height, int width, double probaInit, boolean computeStats, int firstIteration, int maxNbIteration, int maxNbLaunch, int maxNbVariation) {
		this.setHeight(height);
		this.setWidth(width);
		this.setProbaInit(probaInit);
		this.setComputeStats(computeStats);
		this.setFirstIteration(firstIteration);
		this.setMaxNbIteration(maxNbIteration);
		this.setMaxNbLaunch(maxNbLaunch);
		this.setMaxNbVariation(maxNbVariation);
		this.initThread();
	}
	
	/**
	 * Initialize the settings to default values.
	 */
	protected void initSettings() {
		this.setWaitBetweenIteration(true);
		this.setUpdatePeriod(this.getDefaultUpdatePeriod());
	}
	
	/**
	 * Initialize the pattern to default value.
	 */
	protected void initPattern() {
		this.setPattern(this.getDefaultPattern(), this.getDefaultPatternXPos(), this.getDefaultPatternYPos());
	}
	
	/**
	 * Set the pattern to a specific value.
	 * @param pattern - the boolean table which represent the pattern.
	 * @param patternXPos - the X position of the cell in the pattern.
	 * @param patternYPos - the Y position of the cell in the pattern.
	 */
	protected void setPattern(boolean[][] pattern, int patternXPos, int patternYPos) {
		this.setPattern(pattern);
		this.setPatternXPos(patternXPos);
		this.setPatternYPos(patternYPos);
		this.updateNbOfOneInPattern();
	}
	
	/**
	 * Initialize and start the update thread.
	 */
	protected void initThread() {
		this.setRunning(false);
		this.setNbVariation(0);
		this.setNbLaunch(0);
		this.setNbIteration(0);
		
		if(this.updateThread != null) {
			this.updateThread.interrupt();
			try {
				this.updateThread.join();
			}
			catch(InterruptedException argh) {
				argh.printStackTrace();
			}
		}
		
		if(this.computeStats) {
			this.updateThread = new Thread() {
				
				@Override
				public void run() {
					CellularAutomaton.this.statisticsLoop();
				}
				
			};
		}
		else {
			this.buildGrid();
			
			this.updateThread = new Thread() {
				
				@Override
				public void run() {
					CellularAutomaton.this.updateLoop();
				}
				
			};
		}
		
		this.updateThread.start();
	}
	
	/**
	 * Generate the grid.
	 */
	protected void buildGrid() {
		this.setNbIteration(0);
	}
	
	/**
	 * Update the count of one in the pattern.
	 */
	protected void updateNbOfOneInPattern() {
		int nbOfOneInPattern = 0;
		
		for(int i = 0; i < this.getPattern().length; i++) {
			for(int j = 0; j < this.getPattern()[0].length; j++) {
				if(this.getPattern()[i][j]) {
					nbOfOneInPattern++;
				}
			}
		}
		
		this.setNbOfOneInPattern(nbOfOneInPattern);
	}
	
	/**
	 * Copy the content of tab in oldTab.
	 */
	protected void saveOldTab() {
		for(int i = 0; i < this.getHeight(); i++) {
			for(int j = 0; j < this.getWidth(); j++) {
				this.getOldTab()[i][j] = this.cloneCell(this.getTab()[i][j]);
			}
		}
	}
	
	protected abstract T cloneCell(T cell);
	
	/**
	 * Update the grid.
	 */
	protected void updateGrid() {
		this.setNbIteration(this.getNbIteration() + 1);
		this.saveOldTab();
		this.doCellsTransitions();
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Upadate all the cells.
	 */
	protected abstract void doCellsTransitions();
	
	/**
	 * The loop which is executed by the update thread if this CA don't has to compute stats.
	 */
	protected void updateLoop() {
		while(true) {
			if(!this.doLoop()) {
				return;
			}
		}
	}
	
	/**
	 * The loop which is executed by the update thread if this CA must compute stats.
	 */
	protected void statisticsLoop() {
		while(this.getNbVariation() < this.maxNbVariation) {
			this.varyParameters();
			
			while(this.getNbLaunch() < this.maxNbLaunch) {
				this.buildGrid();
				
				while(this.getNbIteration() < this.firstIteration) {
					if(!this.doLoop()) {
						return;
					}
				}
				
				while(this.getNbIteration() < this.firstIteration + this.maxNbIteration) {
					if(!this.doLoop()) {
						return;
					}
					this.computeStats();
				}
				
				this.registerStatsForLaunch();
			}
			
			this.registerStatsForVariation();
		}
		
		this.notifyObservers();
	}
	
	/**
	 * This method represent one loop of the update thread.
	 * @return true if the the loop terminated properly, false if it was interupted.
	 */
	protected boolean doLoop() {
		while(!this.isRunning()) {
			try {
				synchronized(this.lock) {
					this.lock.wait();
				}
			}
			catch(InterruptedException argh) {
				return false;
			}
		}
		
		this.updateGrid();
		
		try {
			if(this.isWaitBetweenIteration()) {
				Thread.sleep(this.getUpdatePeriod());
			}
		}
		catch(InterruptedException argh) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Collect stats from the grid.
	 */
	protected abstract void computeStats();
	
	/**
	 * Increment the parameters which have to vary durring the update loop.
	 */
	protected void varyParameters() {
		this.setNbLaunch(0);
	}
	
	/**
	 * Register the stats collected durring this launch.
	 */
	protected void registerStatsForLaunch() {
		this.setNbLaunch(this.getNbLaunch() + 1);
	}
	
	/**
	 * Register the stats collected durring this variation.
	 */
	protected void registerStatsForVariation() {
		this.setNbVariation(this.getNbVariation() + 1);
	}
	
	/**
	 * Run this CA.
	 */
	public void run() {
		this.setRunning(true);
		this.notifyObservers();
	}
	
	/**
	 * Pause this CA.
	 */
	public void pause() {
		this.setRunning(false);
		this.notifyObservers();
	}
	
	/**
	 * Go to the next step, if the CA is not already running.
	 */
	public void next() {
		if(!this.isRunning()) {
			this.updateGrid();
		}
		this.notifyObservers();
	}
	
	/**
	 * Stop the CA and reset the update thread.
	 */
	public void stop() {
		this.initThread();
		this.notifyObservers();
	}
	
	/**
	 * Check that a height is valid.
	 * @param height - the height to check.
	 */
	protected static void checkHeight(int height) {
		if(height < MIN_GRID_SIZE || height > MAX_GRID_SIZE) {
			throw new IllegalArgumentException(GRID_HEIGHT + " must be between " + MIN_GRID_SIZE + " and " + MAX_GRID_SIZE + ".");
		}
	}
	
	/**
	 * Check that a width is valid.
	 * @param width - the width to check.
	 */
	protected static void checkWidth(int width) {
		if(width < MIN_GRID_SIZE || width > MAX_GRID_SIZE) {
			throw new IllegalArgumentException(GRID_WIDTH + " must be between " + MIN_GRID_SIZE + " and " + MAX_GRID_SIZE + ".");
		}
	}
	
	/**
	 * Check that an initialisation probability is valid.
	 * @param probaInit - the initialisation probability to check.
	 */
	protected static void checkProbaInit(double probaInit) {
		if(probaInit < MIN_INIT_PROBA || probaInit > MAX_INIT_PROBA) {
			throw new IllegalArgumentException(PROBA_INIT + " must be between " + MIN_INIT_PROBA + " and " + MAX_INIT_PROBA + ".");
		}
	}
	
	/**
	 * Check that an update period is valid.
	 * @param updatePeriod - the update period to check.
	 */
	protected static void checkUpdatePeriod(int updatePeriod) {
		if(updatePeriod < MIN_UPDATE_PERIOD || updatePeriod > MAX_UPDATE_PERIOD) {
			throw new IllegalArgumentException("Update period must be between " + MIN_UPDATE_PERIOD + " and " + MAX_UPDATE_PERIOD + ".");
		}
	}
	
	/**
	 * Check that the number of the first iteration is valid.
	 * @param firstIteration - the number of the first iteration.
	 */
	protected static void checkFirstIteration(int firstIteration) {
		if(firstIteration < MIN_FIRST_ITERATION) {
			throw new IllegalArgumentException(FIRST_ITERATION + " (the number of the first iteration where stats have to be collected) must be greater or equals to " + MIN_FIRST_ITERATION + ".");
		}
	}
	
	/**
	 * Check that the number of iterations is valid.
	 * @param nbIteration - the number of iterations.
	 */
	protected static void checkNbIteration(int nbIteration) {
		if(nbIteration < MIN_NB_ITERATION) {
			throw new IllegalArgumentException(NB_ITERATION + " (the number of iterations where stats have to be collected) must be greater or equals to " + MIN_NB_ITERATION + ".");
		}
	}
	
	/**
	 * Check that the number of launches is valid.
	 * @param nbLaunch - the number of launches.
	 */
	protected static void checkNbLaunch(int nbLaunch) {
		if(nbLaunch < MIN_NB_LAUNCH) {
			throw new IllegalArgumentException(NB_VARIATION + " (the number of launches per varition) must be greater or equals to " + MIN_NB_LAUNCH + ".");
		}
	}
	
	/**
	 * Check that the number of variation is valid.
	 * @param nbVariation - the number of variations.
	 */
	protected static void checkNbVariation(int nbVariation) {
		if(nbVariation < MIN_NB_VARIATION) {
			throw new IllegalArgumentException(NB_VARIATION + " (the number of different values that a variable parameter must take) must be greater or equals to " + MIN_NB_LAUNCH + ".");
		}
	}
	
	/**
	 * Change the update period of the CA, then notify observers.
	 * @param updatePeriod - the new update period.
	 */
	public void changeUpdatePeriod(int updatePeriod) {
		checkUpdatePeriod(updatePeriod);
		
		this.setUpdatePeriod(updatePeriod);
		this.notifyObservers();
	}
	
	/**
	 * Rebuild the grid with new initialisation settings, then notify observers.
	 * @param height - the new height of the grid.
	 * @param width - the new width of the grid.
	 * @param probaInit - the probability for a cell to be filled during initialisation.
	 * @param computeStats - whether or not the CA compute statistics between iterations.
	 * @param firstIteration - the number of the first iteration where stats have to be collected.
	 * @param maxNbIteration - the number of iterations where stats have to be collected for each launch.
	 * @param maxNbLaunch - the number of times that the CA has to be launched for each variation.
	 * @param maxNbVariation - the number of variations that the CA has to do.
	 */
	public void changeInitSettings(int height, int width, double probaInit, boolean computeStats, int firstIteration, int maxNbIteration, int maxNbLaunch, int maxNbVariation) {
		checkHeight(height);
		checkWidth(width);
		checkProbaInit(probaInit);
		checkFirstIteration(firstIteration);
		checkNbIteration(maxNbIteration);
		checkNbLaunch(maxNbLaunch);
		checkNbVariation(maxNbVariation);
		
		this.setConfig(height, width, probaInit, computeStats, firstIteration, maxNbIteration, maxNbLaunch, maxNbVariation);
		this.notifyObservers();
	}
	
	/**
	 * Choose if the CA must wait between iterations, then notify observers.
	 * @param waitBetweenIteration - 
	 */
	public void changeWaitBetweenIteration(boolean waitBetweenIteration) {
		this.setWaitBetweenIteration(waitBetweenIteration);
		this.notifyObservers();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("Tab :" + NL);
		
		for(int i = 0; i < this.getWidth(); i++) {
			for(int j = 0; j < this.getHeight(); j++) {
				result.append(this.getTab()[i][j].toString() + " ");
			}
			result.append(NL);
		}
		
		return result.toString();
	}
	
}
