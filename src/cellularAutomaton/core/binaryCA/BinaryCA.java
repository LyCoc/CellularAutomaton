package cellularAutomaton.core.binaryCA;

import cellularAutomaton.core.CellularAutomaton;

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
public abstract class BinaryCA extends CellularAutomaton<Boolean> {
	
	// Constants :
	
	public static final double MIN_UPDATE_PROBA = 0.5;
	public static final double MAX_UPDATE_PROBA = 1.0;
	
	public static final String PROBA_UPDATE = "Update proba";
	public static final String LOOP_ON_EDGES = "Loop on edges";
	
	// Fields :
	
	private double probaUpdate;
	private boolean loop;
	
	private boolean varyProbaUpdate = false;
	private double probaUpdate1 = MIN_UPDATE_PROBA;
	private double probaUpdate2 = MAX_UPDATE_PROBA;
	
	// Constructors :
	
	protected BinaryCA() {
		super();
	}
	
	// Getters :
	
	/**
	 * Return the probability for a cell to be updated during the grid update.
	 * @return the update probability.
	 */
	public double getProbaUpdate() {
		return this.probaUpdate;
	}
	
	/**
	 * Return a boolean indicating if the opposite edges of the grid are connected.
	 * @return true if opposite edges of the grid are connected, else false.
	 */
	public boolean isLoop() {
		return this.loop;
	}
	
	/**
	 * Return a boolean indicating if update probability vary.
	 * @return true if update probability vary, else false.
	 */
	public boolean isVaryProbaUpdate() {
		return this.varyProbaUpdate;
	}
	
	/**
	 * Return the 1st value for update probability. This is used when update probability vary between two values.
	 * @return the 1st value for update probability.
	 */
	public double getProbaUpdate1() {
		return this.probaUpdate1;
	}
	
	/**
	 * Return the 2nd value for update probability. This is used when update probability vary between two values.
	 * @return the 2nd value for update probability.
	 */
	public double getProbaUpdate2() {
		return this.probaUpdate2;
	}
	
	// Setters :
	
	/**
	 * Change the probability for a cell to be updated during the grid update.
	 * @param probaUpdate - the new update probability.
	 */
	protected void setProbaUpdate(double probaUpdate) {
		this.probaUpdate = probaUpdate;
		this.setChanged();
	}
	
	/**
	 * Change the fact that the edges are connected or not.
	 * @param loop - true if opposite edges of the grid have to be connected, else false.
	 */
	protected void setLoop(boolean loop) {
		this.loop = loop;
		this.setChanged();
	}
	
	/**
	 * Change the fact that update probability vary.
	 * @param varyProbaUpdate - true if update probability must vary, else false.
	 */
	protected void setVaryProbaUpdate(boolean varyProbaUpdate) {
		this.varyProbaUpdate = varyProbaUpdate;
		this.setChanged();
	}
	
	/**
	 * Change the 1st value for update probability. This is used when update probability vary between two values.
	 * @param probaUpdate1 - the 1st value for update probability.
	 */
	public void setProbaUpdate1(double probaUpdate1) {
		this.probaUpdate1 = probaUpdate1;
		this.setChanged();
	}
	
	/**
	 * Change the 2nd value for update probability. This is used when update probability vary between two values.
	 * @param probaUpdate2 - the 2nd value for update probability.
	 */
	protected void setProbaUpdate2(double probaUpdate2) {
		this.probaUpdate2 = probaUpdate2;
		this.setChanged();
	}
	
	// Methods :
	
	/**
	 * Return the default update probability.
	 * @return the default update probability.
	 */
	protected double getDefaultProbaUpdate() {
		return 1.0;
	}
	
	/**
	 * Return the default choice for the fact that the opposite edges of the grid are connected.
	 * @return the default choice for the fact that the opposite edges of the grid are connected.
	 */
	protected boolean getDefaultLoop() {
		return true;
	}
	
	@Override
	protected void initSettings() {
		super.initSettings();
		this.setProbaUpdate(this.getDefaultProbaUpdate());
		this.setLoop(this.getDefaultLoop());
	}
	
	@Override
	protected void buildGrid() {
		super.buildGrid();
		
		this.setTab(new Boolean[this.getHeight()][this.getWidth()]);
		this.setOldTab(new Boolean[this.getHeight()][this.getWidth()]);
		
		for(int i = 0; i < this.getHeight(); i++) {
			for(int j = 0; j < this.getWidth(); j++) {
				this.getTab()[i][j] = this.rand.nextDouble() <= this.getProbaInit();
			}
		}
	}
	
	@Override
	protected Boolean cloneCell(Boolean cell) {
		return new Boolean(cell);
	}
	
	@Override
	protected void doCellsTransitions() {
		for(int i = 0; i < this.getHeight(); i++) {
			for(int j = 0; j < this.getWidth(); j++) {
				if(this.rand.nextDouble() <= this.getProbaUpdate()) {
					this.transition(i, j);
				}
			}
		}
	}
	
	/**
	 * Update one cell.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 */
	protected void transition(int y, int x) {
		this.getTab()[y][x] = this.getNewState(this.getOldTab()[y][x], this.getNbLivingCells(y, x));
	}
	
	/**
	 * Calculate the new state of the cell, from it's old state and the count of surounding living cells.
	 * @param oldState - the old state of the cell.
	 * @param nbLivingCells - the count of surounding living cells.
	 * @return the new state of the cell.
	 */
	protected abstract boolean getNewState(boolean oldState, int nbLivingCells);
	
	/**
	 * Get the living cells count, in the pattern, around the cell at the given coordinates.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 * @return the living cells count.
	 */
	protected int getNbLivingCells(int y, int x) {
		int nbLivingCells = 0;
		
		for(int i = 0; i < this.getPattern().length; i++) {
			for(int j = 0; j < this.getPattern()[0].length; j++) {
				if(this.getPattern()[i][j]) {
					int y2 = y + i - this.getPatternYPos();
					int x2 = x + j - this.getPatternXPos();
					
					if(y2 < 0 || y2 >= this.getHeight() || x2 < 0 || x2 >= this.getWidth()) {
						if(!this.isLoop()) {
							continue;
						}
						
						y2 = (y2 + this.getHeight()) % this.getHeight();
						x2 = (x2 + this.getWidth()) % this.getWidth();
					}
					
					if(this.getOldTab()[y2][x2]) {
						nbLivingCells++;
					}
				}
			}
		}
		
		return nbLivingCells;
	}
	
	@Override
	protected void computeStats() {
		// Recolter les stats ici.
	}
	
	@Override
	protected void varyParameters() {
		super.varyParameters();
		
		if(this.isVaryProbaUpdate()) {
			if(this.getNbVariation() == 0) {
				this.setProbaUpdate(this.getProbaUpdate1());
			}
			else if(this.getNbVariation() == this.getMaxNbVariation() - 1) {
				this.setProbaUpdate(this.getProbaUpdate2());
			}
			else {
				this.setProbaUpdate(this.getProbaUpdate1() + ((this.getProbaUpdate2() - this.getProbaUpdate1()) * this.getNbVariation() / (this.getMaxNbVariation() - 1)));
			}
			
			System.out.println("Proba update : " + this.getProbaUpdate());
		}
	}
	
	/**
	 * Check that an update probability is valid.
	 * @param probaUpdate - the update probability to check.
	 */
	protected static void checkProbaUpdate(double probaUpdate) {
		if(probaUpdate < MIN_UPDATE_PROBA || probaUpdate > MAX_UPDATE_PROBA) {
			throw new IllegalArgumentException(PROBA_UPDATE + " must be between " + MIN_UPDATE_PROBA + " and " + MAX_UPDATE_PROBA + ".");
		}
	}
	
	/**
	 * Change the the probability for a cell to be updated during the grid update, then notify observers.
	 * @param probaUpdate - the probability for a cell to be updated during the grid update.
	 */
	public void changeProbaUpdate(double probaUpdate) {
		checkProbaUpdate(probaUpdate);
		
		this.setProbaUpdate(probaUpdate);
		this.notifyObservers();
	}
	
	/**
	 * Change the fact that the edges are connected or not, then notify observers.
	 * @param loop - true if opposite edges of the grid have to be connected, else false.
	 */
	public void changeEdgeLoop(boolean loop) {
		this.setLoop(loop);
		this.notifyObservers();
	}
	
	/**
	 * Change all dynamic Settings.
	 * @param probaUpdate - the probability for a cell to be updated during the grid update.
	 * @param loop - true if opposite edges of the grid have to be connected, else false.
	 * @param varyProbaUpdate - true if update probability must vary, else false.
	 * @param probaUpdate1 - the 1st value for update probability.
	 * @param probaUpdate2 - the 2nd value for update probability.
	 */
	public void changeDynamicSettings(double probaUpdate, boolean loop, boolean varyProbaUpdate, double probaUpdate1, double probaUpdate2) {
		
		if(varyProbaUpdate) {
			checkProbaUpdate(probaUpdate1);
			checkProbaUpdate(probaUpdate2);
		}
		else {
			checkProbaUpdate(probaUpdate);
		}
		
		this.setLoop(loop);
		this.setVaryProbaUpdate(varyProbaUpdate);
		
		if(varyProbaUpdate) {
			this.setProbaUpdate1(probaUpdate1);
			this.setProbaUpdate2(probaUpdate2);
		}
		else {
			this.setProbaUpdate(probaUpdate);
		}
	}
	
}
