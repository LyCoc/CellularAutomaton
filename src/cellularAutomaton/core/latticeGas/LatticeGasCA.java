package cellularAutomaton.core.latticeGas;

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
public abstract class LatticeGasCA extends CellularAutomaton<Particles> {
	
	// Constants :
	
	public static final double MIN_INTERACTION_PROBA = 0.0;
	public static final double MAX_INTERACTION_PROBA = 10.0;
	public static final double MIN_PROPAGATION_PROBA = 0.0;
	public static final double MAX_PROPAGATION_PROBA = 1.0;
	
	public static final String PROBA_INTERACTION = "Interaction";
	public static final String PROBA_PROPAGATION = "Propagation proba";
	public static final String PRESERVE_PARTICULES = "Preserve particules";
	
	// Fields :
	
	private double probaInteraction;
	private double probaPropagation;
	private boolean particlesPreserving;
	
	private boolean varyProbaInteraction = false;
	private boolean varyProbaPropagation = false;
	private double probaInteraction1 = 1.0;
	private double probaInteraction2 = 4.0;
	private double probaPropagation1 = 0.8;
	private double probaPropagation2 = 1.0;
	
	// Constructors :
	
	protected LatticeGasCA() {
		super();
	}
	
	// Getters :
	
	/**
	 * Return the probability for a cell to be updated during the interaction part of the grid update.
	 * @return the interaction probability.
	 */
	public double getProbaInteraction() {
		return this.probaInteraction;
	}
	
	/**
	 * Return the probability for a cell to be updated during the propagation part of the grid update.
	 * @return the propagation probability.
	 */
	public double getProbaPropagation() {
		return this.probaPropagation;
	}
	
	/**
	 * Return a boolean indicating if the particles must be preserved during the propagation step.
	 * @return true if particles must be preserved, false if they can be created or destroyed during the propagation step.
	 */
	public boolean isParticlesPreserving() {
		return this.particlesPreserving;
	}
	
	/**
	 * Return a boolean indicating if interaction probability vary.
	 * @return true if interaction probability vary, else false.
	 */
	public boolean isVaryProbaInteraction() {
		return this.varyProbaInteraction;
	}
	
	/**
	 * Return a boolean indicating if propagation probability vary.
	 * @return true if propagation probability vary, else false.
	 */
	public boolean isVaryProbaPropagation() {
		return this.varyProbaPropagation;
	}
	
	/**
	 * Return the 1st value for interaction probability. This is used when interaction probability vary between two values.
	 * @return the 1st value for interaction probability.
	 */
	public double getProbaInteraction1() {
		return this.probaInteraction1;
	}
	
	/**
	 * Return the 2nd value for interaction probability. This is used when interaction probability vary between two values.
	 * @return the 2nd value for interaction probability.
	 */
	public double getProbaInteraction2() {
		return this.probaInteraction2;
	}
	
	/**
	 * Return the 1st value for propagation probability. This is used when propagation probability vary between two values.
	 * @return the 1st value for propagation probability.
	 */
	public double getProbaPropagation1() {
		return this.probaPropagation1;
	}
	
	/**
	 * Return the 2nd value for propagation probability. This is used when propagation probability vary between two values.
	 * @return the 2nd value for propagation probability.
	 */
	public double getProbaPropagation2() {
		return this.probaPropagation2;
	}
	
	// Setters :
	
	/**
	 * Change the probability for a cell to be updated during the interaction part of the grid update.
	 * @param probaInteraction - the new interaction probability.
	 */
	protected void setProbaInteraction(double probaInteraction) {
		this.probaInteraction = probaInteraction;
		this.setChanged();
	}
	
	/**
	 * Change the probability for a cell to be updated during the propagation part of the grid update.
	 * @param probaPropagation - the new propagation probability.
	 */
	protected void setProbaPropagation(double probaPropagation) {
		this.probaPropagation = probaPropagation;
		this.setChanged();
	}
	
	/**
	 * Change the fact that particles must be preserved during the propagation step.
	 * @param particlesPreserving - true if particles must be preserved, false if they can be created or destroyed during the propagation step.
	 */
	protected void setParticlesPreserving(boolean particlesPreserving) {
		this.particlesPreserving = particlesPreserving;
		this.setChanged();
	}
	
	/**
	 * Change the fact that interaction probability vary.
	 * @param varyProbaInteraction - true if interaction probability must vary, else false.
	 */
	protected void setVaryProbaInteraction(boolean varyProbaInteraction) {
		this.varyProbaInteraction = varyProbaInteraction;
		this.setChanged();
	}
	
	/**
	 * Change the fact that propagation probability vary.
	 * @param varyProbaPropagation - true if propagation probability must vary, else false.
	 */
	protected void setVaryProbaPropagation(boolean varyProbaPropagation) {
		this.varyProbaPropagation = varyProbaPropagation;
		this.setChanged();
	}
	
	/**
	 * Change the 1st value for interaction probability. This is used when interaction probability vary between two values.
	 * @param probaInteraction1 - the 1st value for interaction probability.
	 */
	public void setProbaInteraction1(double probaInteraction1) {
		this.probaInteraction1 = probaInteraction1;
		this.setChanged();
	}
	
	/**
	 * Change the 2nd value for interaction probability. This is used when interaction probability vary between two values.
	 * @param probaInteraction2 - the 2nd value for interaction probability.
	 */
	protected void setProbaInteraction2(double probaInteraction2) {
		this.probaInteraction2 = probaInteraction2;
		this.setChanged();
	}
	
	/**
	 * Change the 1st value for propagation probability. This is used when propagation probability vary between two values.
	 * @param probaPropagation1 - the 1st value for propagation probability.
	 */
	public void setProbaPropagation1(double probaPropagation1) {
		this.probaPropagation1 = probaPropagation1;
		this.setChanged();
	}
	
	/**
	 * Change the 2nd value for propagation probability. This is used when propagation probability vary between two values.
	 * @param probaPropagation2 - the 2nd value for propagation probability.
	 */
	protected void setProbaPropagation2(double probaPropagation2) {
		this.probaPropagation2 = probaPropagation2;
		this.setChanged();
	}
	
	// Methods :
	
	@Override
	protected boolean[][] getDefaultPattern() {
		return new boolean[][] { {false, true, false}, {true, false, true}, {false, true, false}};
	}
	
	/**
	 * Return the default interaction probability.
	 * @return the default interaction probability.
	 */
	protected double getDefaultProbaInteraction() {
		return 1.0;
	}
	
	/**
	 * Return the default propagation probability.
	 * @return the default propagation probability.
	 */
	protected double getDefaultProbaPropagation() {
		return 1.0;
	}
	
	@Override
	protected void initSettings() {
		super.initSettings();
		this.setProbaInteraction(this.getDefaultProbaInteraction());
		this.setProbaPropagation(this.getDefaultProbaPropagation());
		this.setParticlesPreserving(true);
	}
	
	@Override
	protected void buildGrid() {
		super.buildGrid();
		
		this.setTab(new Particles[this.getHeight()][this.getWidth()]);
		this.setOldTab(new Particles[this.getHeight()][this.getWidth()]);
		
		for(int i = 0; i < this.getHeight(); i++) {
			for(int j = 0; j < this.getWidth(); j++) {
				boolean top = this.rand.nextDouble() <= (this.getProbaInit());
				boolean bottom = this.rand.nextDouble() <= (this.getProbaInit());
				boolean right = this.rand.nextDouble() <= (this.getProbaInit());
				boolean left = this.rand.nextDouble() <= (this.getProbaInit());
				this.getTab()[i][j] = new Particles(top, bottom, right, left);
			}
		}
	}
	
	@Override
	protected Particles cloneCell(Particles cell) {
		return cell.clone();
	}
	
	@Override
	protected void doCellsTransitions() {
		for(int i = 0; i < this.getHeight(); i++) {
			for(int j = 0; j < this.getWidth(); j++) {
				this.interaction(i, j);
				this.getTab()[i][j].setMove(this.rand.nextDouble() <= this.getProbaPropagation());
			}
		}
		
		if(this.isParticlesPreserving()) {
			for(int i = 0; i < this.getHeight(); i++) {
				for(int j = 0; j < this.getWidth(); j++) {
					this.propagateDontMove(i, j);
				}
			}
		}
		
		this.saveOldTab();
		
		if(this.isParticlesPreserving()) {
			for(int i = 0; i < this.getHeight(); i++) {
				for(int j = 0; j < this.getWidth(); j++) {
					this.propagation(i, j);
				}
			}
		}
		else {
			for(int i = 0; i < this.getHeight(); i++) {
				for(int j = 0; j < this.getWidth(); j++) {
					this.propagationWithoutParticlesConservation(i, j);
				}
			}
		}
	}
	
	/**
	 * Do the interaction part of the update of a cell.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 */
	protected abstract void interaction(int y, int x);
	
	/**
	 * Get the director field of neighboring cells, in the pattern, around the cell at the given coordinates, in the old or the new table.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 * @param old - true if the director field has to be calculated from the old tab, false if it has to be calculated from the new tab.
	 * @return the director field of neighboring cells.
	 */
	private Flux getDirectorField(int y, int x, boolean old) {
		Flux directorField = new Flux();
		
		for(int i = 0; i < this.getPattern().length; i++) {
			for(int j = 0; j < this.getPattern()[0].length; j++) {
				if(this.getPattern()[i][j]) {
					int y2 = y + i - this.getPatternYPos();
					int x2 = x + j - this.getPatternXPos();
					
					if(y2 < 0 || y2 >= this.getHeight() || x2 < 0 || x2 >= this.getWidth()) {
						y2 = (y2 + this.getHeight()) % this.getHeight();
						x2 = (x2 + this.getWidth()) % this.getWidth();
					}
					
					Particles[][] tab = old ? this.getOldTab() : this.getTab();
					Flux flux = tab[y2][x2].getFlux();
					directorField.y += flux.y;
					directorField.x += flux.x;
				}
			}
		}
		
		return directorField;
	}
	
	/**
	 * Get the director field of neighboring cells, in the pattern, around the cell at the given coordinates, in the old table.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 * @return the director field of neighboring cells.
	 */
	protected Flux getOldDirectorField(int y, int x) {
		return this.getDirectorField(y, x, true);
	}
	
	/**
	 * Get the director field of neighboring cells, in the pattern, around the cell at the given coordinates, in the new table.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 * @return the director field of neighboring cells.
	 */
	protected Flux getNewDirectorField(int y, int x) {
		return this.getDirectorField(y, x, false);
	}
	
	/**
	 * If the cell don't has to do the propagation step, it has to tell it to neighboring cells.<br />
	 * This ensures that particles will not be created or destroyed during the propagation step.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 */
	protected void propagateDontMove(int y, int x) {
		Particles cell = this.getTab()[y][x];
		
		if(!cell.isMove()) {
			Particles nextCell;
			boolean propagate;
			int i, j;
			
			if(cell.isTop()) {
				propagate = true;
				i = y;
				j = x;
				while(propagate) {
					i = (i + 1) % this.getHeight();
					nextCell = this.getTab()[i][j];
					if(nextCell.isTop() && nextCell.isMoveTop()) {
						nextCell.setMoveTop(false);
					}
					else {
						propagate = false;
					}
				}
			}
			
			if(cell.isBottom()) {
				propagate = true;
				i = y;
				j = x;
				while(propagate) {
					i = (i - 1 + this.getHeight()) % this.getHeight();
					nextCell = this.getTab()[i][j];
					if(nextCell.isBottom() && nextCell.isMoveBottom()) {
						nextCell.setMoveBottom(false);
					}
					else {
						propagate = false;
					}
				}
			}
			
			if(cell.isRight()) {
				propagate = true;
				i = y;
				j = x;
				while(propagate) {
					j = (j - 1 + this.getWidth()) % this.getWidth();
					nextCell = this.getTab()[i][j];
					if(nextCell.isRight() && nextCell.isMoveRight()) {
						nextCell.setMoveRight(false);
					}
					else {
						propagate = false;
					}
				}
			}
			
			if(cell.isLeft()) {
				propagate = true;
				i = y;
				j = x;
				while(propagate) {
					j = (j + 1) % this.getWidth();
					nextCell = this.getTab()[i][j];
					if(nextCell.isLeft() && nextCell.isMoveLeft()) {
						nextCell.setMoveLeft(false);
					}
					else {
						propagate = false;
					}
				}
			}
		}
	}
	
	/**
	 * Do the propagation part of the update of a cell.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 */
	protected void propagation(int y, int x) {
		Particles cell = this.getOldTab()[y][x];
		Particles otherCell;
		
		otherCell = this.getOldTab()[(y + 1) % this.getHeight()][x];
		boolean top = (!cell.isMoveTop() && cell.isTop()) || (otherCell.isMoveTop() && otherCell.isTop());
		
		otherCell = this.getOldTab()[(y - 1 + this.getHeight()) % this.getHeight()][x];
		boolean bottom = (!cell.isMoveBottom() && cell.isBottom()) || (otherCell.isMoveBottom() && otherCell.isBottom());
		
		otherCell = this.getOldTab()[y][(x - 1 + this.getWidth()) % this.getWidth()];
		boolean right = (!cell.isMoveRight() && cell.isRight()) || (otherCell.isMoveRight() && otherCell.isRight());
		
		otherCell = this.getOldTab()[y][(x + 1) % this.getWidth()];
		boolean left = (!cell.isMoveLeft() && cell.isLeft()) || (otherCell.isMoveLeft() && otherCell.isLeft());
		
		cell = this.getTab()[y][x];
		cell.setTop(top);
		cell.setBottom(bottom);
		cell.setRight(right);
		cell.setLeft(left);
	}
	
	/**
	 * Do the propagation part of the update of a cell, without conserving the particles.
	 * @param y - the Y coordinate of the cell.
	 * @param x - the X coordinate of the cell.
	 */
	protected void propagationWithoutParticlesConservation(int y, int x) {
		if(this.getOldTab()[y][x].isMove()) {
			Particles cell = this.getTab()[y][x];
			cell.setTop(this.getOldTab()[(y + 1) % this.getHeight()][x].isTop());
			cell.setBottom(this.getOldTab()[(y - 1 + this.getHeight()) % this.getHeight()][x].isBottom());
			cell.setRight(this.getOldTab()[y][(x - 1 + this.getWidth()) % this.getWidth()].isRight());
			cell.setLeft(this.getOldTab()[y][(x + 1) % this.getWidth()].isLeft());
		}
	}
	
	/**
	 * Return the number of particles on the grid.
	 * @return the number of particles on the grid.
	 */
	protected int calculateNbParticles() {
		int nbParticles = 0;
		
		for(int i = 0; i < this.getHeight(); i++) {
			for(int j = 0; j < this.getWidth(); j++) {
				nbParticles += this.getTab()[i][j].getNbParticles();
			}
		}
		
		return nbParticles;
	}
	
	/**
	 * Calculate the mean velocity for the actual state of the grid.
	 * @return the mean velocity for the actual state of the grid.
	 */
	protected double meanVelocity() {
		int xVelocitySum = 0;
		int yVelocitySum = 0;
		
		for(int i = 0; i < this.getHeight(); i++) {
			for(int j = 0; j < this.getWidth(); j++) {
				Flux localFlux = this.getTab()[i][j].getFlux();
				xVelocitySum += localFlux.x;
				yVelocitySum += localFlux.y;
			}
		}
		
		return (float) (Math.abs(xVelocitySum) + Math.abs(yVelocitySum)) / (this.getHeight() * this.getWidth());
	}
	
	/**
	 * Calculate the mean alignment for the actual state of the grid.
	 * @return the mean alignment for the actual state of the grid.
	 */
	protected double meanAlignment() {
		int alignmentSum = 0;
		int nbParticles = 0;
		
		for(int i = 0; i < this.getHeight(); i++) {
			for(int j = 0; j < this.getWidth(); j++) {
				Flux localFlux = this.getTab()[i][j].getFlux();
				Flux directorField = this.getNewDirectorField(i, j);
				alignmentSum += ((localFlux.x * directorField.x) + (localFlux.y * directorField.y));
				nbParticles += this.getTab()[i][j].getNbParticles();
			}
		}
		
		return (float) alignmentSum / (nbParticles * this.getNbOfOneInPattern());
	}
	
	@Override
	protected void varyParameters() {
		super.varyParameters();
		
		if(this.isVaryProbaInteraction()) {
			if(this.getNbVariation() == 0) {
				this.setProbaInteraction(this.getProbaInteraction1());
			}
			else if(this.getNbVariation() == this.getMaxNbVariation() - 1) {
				this.setProbaInteraction(this.getProbaInteraction2());
			}
			else {
				this.setProbaInteraction(this.getProbaInteraction1() + ((this.getProbaInteraction2() - this.getProbaInteraction1()) * this.getNbVariation() / (this.getMaxNbVariation() - 1)));
			}
		}
		
		if(this.isVaryProbaPropagation()) {
			if(this.getNbVariation() == 0) {
				this.setProbaPropagation(this.getProbaPropagation1());
			}
			else if(this.getNbVariation() == this.getMaxNbVariation() - 1) {
				this.setProbaPropagation(this.getProbaPropagation2());
			}
			else {
				this.setProbaPropagation(this.getProbaPropagation1() + ((this.getProbaPropagation2() - this.getProbaPropagation1()) * this.getNbVariation() / (this.getMaxNbVariation() - 1)));
			}
		}
	}
	
	/**
	 * Check that an interaction probability is valid.
	 * @param probaInteraction - the interaction probability to check.
	 */
	protected static void checkProbaInteraction(double probaInteraction) {
		if(probaInteraction < MIN_INTERACTION_PROBA || probaInteraction > MAX_INTERACTION_PROBA) {
			throw new IllegalArgumentException(PROBA_INTERACTION + " must be between " + MIN_INTERACTION_PROBA + " and " + MAX_INTERACTION_PROBA + ".");
		}
	}
	
	/**
	 * Check that a propagation probability is valid.
	 * @param probaUpdate - the propagation probability to check.
	 */
	protected static void checkProbaPropagation(double probaPropagation) {
		if(probaPropagation < MIN_PROPAGATION_PROBA || probaPropagation > MAX_PROPAGATION_PROBA) {
			throw new IllegalArgumentException(PROBA_PROPAGATION + " must be between " + MIN_PROPAGATION_PROBA + " and " + MAX_PROPAGATION_PROBA + ".");
		}
	}
	
	/**
	 * Change the the probability for a cell to be updated during the interaction part of the grid update, then notify observers.
	 * @param probaInteraction - the probability for a cell to be updated during the interaction part of the grid update.
	 */
	public void changeProbaInteraction(double probaInteraction) {
		checkProbaInteraction(probaInteraction);
		
		this.setProbaInteraction(probaInteraction);
		this.notifyObservers();
	}
	
	/**
	 * Change the the probability for a cell to be updated during the propagation part of the grid update, then notify observers.
	 * @param probaPropagation - the probability for a cell to be updated during the propagation part of the grid update.
	 */
	public void changeProbaPropagation(double probaPropagation) {
		checkProbaPropagation(probaPropagation);
		
		this.setProbaPropagation(probaPropagation);
		this.notifyObservers();
	}
	
	/**
	 * Change the fact that particles ust be preserved during the propagation step, then notify observers.
	 * @param particlesPreserving - true if particles must be preserved, false if they can be created or destroyed during the propagation step.
	 */
	public void changeParticlesPreserving(boolean particlesPreserving) {
		this.setParticlesPreserving(particlesPreserving);
		this.notifyObservers();
	}
	
	/**
	 * Change all dynamic Settings.
	 * @param probaInteraction - the probability for a cell to be updated during the interaction part of the grid update.
	 * @param probaPropagation - the probability for a cell to be updated during the propagation part of the grid update.
	 * @param particlesPreserving - true if particles must be preserved, false if they can be created or destroyed during the propagation step.
	 * @param varyProbaInteraction - true if interaction probability must vary, else false.
	 * @param varyProbaPropagation - true if propagation probability must vary, else false.
	 * @param probaInteraction1 - the 1st value for interaction probability.
	 * @param probaInteraction2 - the 2nd value for interaction probability.
	 * @param probaPropagation1 - the 1st value for propagation probability.
	 * @param probaPropagation2 - the 2nd value for propagation probability.
	 */
	public void changeDynamicSettings(double probaInteraction, double probaPropagation, boolean particlesPreserving, boolean varyProbaInteraction, boolean varyProbaPropagation, double probaInteraction1, double probaInteraction2, double probaPropagation1, double probaPropagation2) {
		if(varyProbaInteraction) {
			checkProbaInteraction(probaInteraction1);
			checkProbaInteraction(probaInteraction2);
		}
		else {
			checkProbaInteraction(probaInteraction);
		}
		
		if(varyProbaPropagation) {
			checkProbaPropagation(probaPropagation1);
			checkProbaPropagation(probaPropagation2);
		}
		else {
			checkProbaPropagation(probaPropagation);
		}
		
		this.setParticlesPreserving(particlesPreserving);
		this.setVaryProbaInteraction(varyProbaInteraction);
		this.setVaryProbaPropagation(varyProbaPropagation);
		
		if(varyProbaInteraction) {
			this.setProbaInteraction1(probaInteraction1);
			this.setProbaInteraction2(probaInteraction2);
		}
		else {
			this.setProbaInteraction(probaInteraction);
		}
		
		if(varyProbaPropagation) {
			this.setProbaPropagation1(probaPropagation1);
			this.setProbaPropagation2(probaPropagation2);
		}
		else {
			this.setProbaPropagation(probaPropagation);
		}
	}
	
}
