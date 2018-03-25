package cellularAutomaton.core.latticeGas;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
public class Swarming extends LatticeGasCA {
	
	// Constants :
	
	private static final Particles[] CONFIGURATIONS_1 = {new Particles(true, false, false, false), new Particles(false, true, false, false), new Particles(false, false, true, false), new Particles(false, false, false, true)};
	private static final Particles[] CONFIGURATIONS_2 = {new Particles(false, true, false, true), new Particles(true, false, true, false), new Particles(true, true, false, false), new Particles(false, true, true, false), new Particles(false, false, true, true), new Particles(true, false, false, true)};
	private static final Particles[] CONFIGURATIONS_3 = {new Particles(false, true, true, true), new Particles(true, false, true, true), new Particles(true, true, false, true), new Particles(true, true, true, false)};
	
	// Fields :
	
	private int nbParticles;
	private int initNbParticles;
	
	private double meanVelocitySum;
	private double meanAlignmentSum;
	private double nbParticlesSum;
	
	private double averageMeanVelocitySum;
	private double averageMeanAlignmentSum;
	private double averageNbParticlesSum;
	private double averageNbParticlesVariationSum;
	
	private XYSeries velocityData;
	private XYSeries alignmentData;
	private XYSeries nbParticlesData;
	private XYSeries nbParticlesVariationData;
	
	private XYSeries averageVelocityData;
	private XYSeries averageAlignmentData;
	private XYSeries averageNbParticlesData;
	private XYSeries averageNbParticlesVariationData;
	
	private XYSeriesCollection velocityDataset;
	private XYSeriesCollection alignmentDataset;
	private XYSeriesCollection nbParticlesDataset;
	private XYSeriesCollection nbParticlesVariationDataset;
	
	// Constructors :
	
	public Swarming() {
		super();
	}
	
	// Getters :
	
	/**
	 * Return the registered number of particles.
	 * @return the registered number of particles.
	 */
	public int getNbParticles() {
		return this.nbParticles;
	}
	
	public int getInitNbParticles() {
		return this.initNbParticles;
	}
	
	/**
	 * Return the sum of the mean velocities of the iterations of this launch.
	 * @return the sum of the mean velocities of the iterations of this launch.
	 */
	public double getMeanVelocitySum() {
		return this.meanVelocitySum;
	}
	
	/**
	 * Return the sum of the mean alignments of the iterations of this launch.
	 * @return the sum of the mean alignments of the iterations of this launch.
	 */
	public double getMeanAlignmentSum() {
		return this.meanAlignmentSum;
	}
	
	public double getNbParticlesSum() {
		return this.nbParticlesSum;
	}
	
	public double getAverageMeanVelocitySum() {
		return this.averageMeanVelocitySum;
	}
	
	public double getAverageMeanAlignmentSum() {
		return this.averageMeanAlignmentSum;
	}
	
	public double getAverageNbParticlesSum() {
		return this.averageNbParticlesSum;
	}
	
	public double getAverageNbParticlesVariationSum() {
		return this.averageNbParticlesVariationSum;
	}
	
	public XYSeries getVelocityData() {
		return this.velocityData;
	}
	
	public XYSeries getAlignmentData() {
		return this.alignmentData;
	}
	
	public XYSeries getNbParticlesData() {
		return this.nbParticlesData;
	}
	
	public XYSeries getNbParticlesVariationData() {
		return this.nbParticlesVariationData;
	}
	
	public XYSeries getAverageVelocityData() {
		return this.averageVelocityData;
	}
	
	public XYSeries getAverageAlignmentData() {
		return this.averageAlignmentData;
	}
	
	public XYSeries getAverageNbParticlesData() {
		return this.averageNbParticlesData;
	}
	
	public XYSeries getAverageNbParticlesVariationData() {
		return this.averageNbParticlesVariationData;
	}
	
	public XYSeriesCollection getVelocityDataset() {
		return this.velocityDataset;
	}
	
	public XYSeriesCollection getAlignmentDataset() {
		return this.alignmentDataset;
	}
	
	public XYSeriesCollection getNbParticlesDataset() {
		return this.nbParticlesDataset;
	}
	
	public XYSeriesCollection getNbParticlesVariationDataset() {
		return this.nbParticlesVariationDataset;
	}
	
	// Setters :
	
	/**
	 * Update the registered number of particles.
	 * @param nbParticles - the number of particles.
	 */
	protected void setNbParticles(int nbParticles) {
		this.nbParticles = nbParticles;
	}
	
	protected void setInitNbParticles(int initNbParticles) {
		this.initNbParticles = initNbParticles;
	}
	
	@Override
	protected void setParticlesPreserving(boolean particlesPreserving) {
		this.setNbParticles(-1);
		super.setParticlesPreserving(particlesPreserving);
	}
	
	/**
	 * Set the sum of the mean velocities of the iterations of this launch.
	 * @param meanVelocitySum - the new velocities sum.
	 */
	protected void setMeanVelocitySum(double meanVelocitySum) {
		this.meanVelocitySum = meanVelocitySum;
	}
	
	/**
	 * Set the sum of the mean alignments of the iterations of this launch.
	 * @param meanAlignmentSum - the new alignments sum.
	 */
	protected void setMeanAlignmentSum(double meanAlignmentSum) {
		this.meanAlignmentSum = meanAlignmentSum;
	}
	
	protected void setNbParticlesSum(double nbParticlesSum) {
		this.nbParticlesSum = nbParticlesSum;
	}
	
	protected void setAverageMeanVelocitySum(double averageMeanVelocitySum) {
		this.averageMeanVelocitySum = averageMeanVelocitySum;
	}
	
	protected void setAverageMeanAlignmentSum(double averageMeanAlignmentSum) {
		this.averageMeanAlignmentSum = averageMeanAlignmentSum;
	}
	
	protected void setAverageNbParticlesSum(double averageNbParticlesSum) {
		this.averageNbParticlesSum = averageNbParticlesSum;
	}
	
	protected void setAverageNbParticlesVariationSum(double averageNbParticlesVariationSum) {
		this.averageNbParticlesVariationSum = averageNbParticlesVariationSum;
	}
	
	protected void setVelocityData(XYSeries velocityData) {
		this.velocityData = velocityData;
	}
	
	protected void setAlignmentData(XYSeries alignmentData) {
		this.alignmentData = alignmentData;
	}
	
	protected void setNbParticlesData(XYSeries nbParticlesData) {
		this.nbParticlesData = nbParticlesData;
	}
	
	protected void setNbParticlesVariationData(XYSeries nbParticlesVariationData) {
		this.nbParticlesVariationData = nbParticlesVariationData;
	}
	
	protected void setAverageVelocityData(XYSeries averageVelocityData) {
		this.averageVelocityData = averageVelocityData;
	}
	
	protected void setAverageAlignmentData(XYSeries averageAlignmentData) {
		this.averageAlignmentData = averageAlignmentData;
	}
	
	protected void setAverageNbParticlesData(XYSeries averageNbParticlesData) {
		this.averageNbParticlesData = averageNbParticlesData;
	}
	
	protected void setAverageNbParticlesVariationData(XYSeries averageNbParticlesVariationData) {
		this.averageNbParticlesVariationData = averageNbParticlesVariationData;
	}
	
	protected void setVelocityDataset(XYSeriesCollection velocityDataset) {
		this.velocityDataset = velocityDataset;
	}
	
	protected void setAlignmentDataset(XYSeriesCollection alignmentDataset) {
		this.alignmentDataset = alignmentDataset;
	}
	
	protected void setNbParticlesDataset(XYSeriesCollection nbParticlesDataset) {
		this.nbParticlesDataset = nbParticlesDataset;
	}
	
	protected void setNbParticlesVariationDataset(XYSeriesCollection nbParticlesVariationDataset) {
		this.nbParticlesVariationDataset = nbParticlesVariationDataset;
	}
	
	// Methods :
	
	@Override
	protected double getDefaultProbaInit() {
		return 0.2;
	}
	
	@Override
	protected double getDefaultProbaInteraction() {
		return 1.5;
	}
	
	@Override
	protected void buildGrid() {
		super.buildGrid();
		int nbParticles = this.calculateNbParticles();
		this.setNbParticles(nbParticles);
		this.setInitNbParticles(nbParticles);
	}
	
	@Override
	protected void initConfig() {
		this.setVelocityDataset(new XYSeriesCollection());
		this.setAlignmentDataset(new XYSeriesCollection());
		this.setNbParticlesDataset(new XYSeriesCollection());
		this.setNbParticlesVariationDataset(new XYSeriesCollection());
		
		this.setVelocityData(new XYSeries("mean velocity"));
		this.setAlignmentData(new XYSeries("mean alignment"));
		this.setNbParticlesData(new XYSeries("nb particles"));
		this.setNbParticlesVariationData(new XYSeries("nb particles variation"));
		
		this.setAverageVelocityData(new XYSeries("average mean velocity"));
		this.setAverageAlignmentData(new XYSeries("average mean alignment"));
		this.setAverageNbParticlesData(new XYSeries("average nb particles"));
		this.setAverageNbParticlesVariationData(new XYSeries("average nb particles variation"));
		
		this.getVelocityDataset().addSeries(this.getAverageVelocityData());
		this.getAlignmentDataset().addSeries(this.getAverageAlignmentData());
		this.getNbParticlesDataset().addSeries(this.getAverageNbParticlesData());
		this.getNbParticlesVariationDataset().addSeries(this.getAverageNbParticlesVariationData());
		
		this.getVelocityDataset().addSeries(this.getVelocityData());
		this.getAlignmentDataset().addSeries(this.getAlignmentData());
		this.getNbParticlesDataset().addSeries(this.getNbParticlesData());
		this.getNbParticlesVariationDataset().addSeries(this.getNbParticlesVariationData());
		
		super.initConfig();
	}
	
	@Override
	protected void initThread() {
		super.initThread();
		
		this.setMeanVelocitySum(0);
		this.setMeanAlignmentSum(0);
		this.setNbParticlesSum(0);
		
		this.setAverageMeanVelocitySum(0);
		this.setAverageMeanAlignmentSum(0);
		this.setAverageNbParticlesSum(0);
		this.setAverageNbParticlesVariationSum(0);
		
		this.getVelocityData().clear();
		this.getAlignmentData().clear();
		this.getNbParticlesData().clear();
		this.getNbParticlesVariationData().clear();
		
		this.getAverageVelocityData().clear();
		this.getAverageAlignmentData().clear();
		this.getAverageNbParticlesData().clear();
		this.getAverageNbParticlesVariationData().clear();
	}
	
	@Override
	protected void doCellsTransitions() {
		super.doCellsTransitions();
		this.checkNbParticles();
	}
	
	@Override
	protected void interaction(int y, int x) {
		int nbParticles = this.getOldTab()[y][x].getNbParticles();
		
		if(nbParticles > 0 && nbParticles < 4) {
			Particles[] configurations;
			
			if(nbParticles == 1) {
				configurations = CONFIGURATIONS_1;
			}
			else if(nbParticles == 2) {
				configurations = CONFIGURATIONS_2;
			}
			else {
				configurations = CONFIGURATIONS_3;
			}
			
			double[] probabilities = new double[configurations.length];
			
			Flux directorField = this.getOldDirectorField(y, x);
			double alpha = this.getProbaInteraction();
			double sumProba = 0;
			
			for(int i = 0; i < configurations.length; i++) {
				Flux localFlux = configurations[i].getFlux();
				sumProba += Math.exp(alpha * ((directorField.x * localFlux.x) + (directorField.y * localFlux.y)));
				probabilities[i] = sumProba;
			}
			
			for(int i = 0; i < configurations.length; i++) {
				probabilities[i] /= sumProba;
			}
			
			double rand = this.rand.nextDouble();
			int i = 0;
			
			while(i < (configurations.length - 1) && rand > probabilities[i]) {
				i++;
			}
			
			this.getTab()[y][x] = configurations[i].clone();
		}
	}
	
	/**
	 * Check that the number of particles has not changed.
	 */
	protected void checkNbParticles() {
		int nbParticles = this.calculateNbParticles();
		
		if(this.isParticlesPreserving() && this.getNbParticles() != -1 && this.getNbParticles() != nbParticles) {
			System.out.println("Warning : the number of particles has changed ! (" + this.getNbParticles() + " -> " + nbParticles + ")");
		}
		
		this.setNbParticles(nbParticles);
	}
	
	@Override
	protected void computeStats() {
		this.setMeanVelocitySum(this.getMeanVelocitySum() + this.meanVelocity());
		this.setMeanAlignmentSum(this.getMeanAlignmentSum() + this.meanAlignment());
		this.setNbParticlesSum(this.getNbParticlesSum() + this.getNbParticles());
	}
	
	@Override
	protected void registerStatsForLaunch() {
		double proba = (this.isVaryProbaInteraction() ? this.getProbaInteraction() : this.getProbaPropagation());
		
		double velocity = this.getMeanVelocitySum() / this.getMaxNbIteration();
		double alignment = this.getMeanAlignmentSum() / this.getMaxNbIteration();
		double nbParticles = this.getNbParticlesSum() / this.getMaxNbIteration();
		double nbParticlesVariation = this.getNbParticles() - this.getInitNbParticles();
		
		this.getVelocityData().add(proba, velocity);
		this.getAlignmentData().add(proba, alignment);
		this.getNbParticlesData().add(proba, nbParticles);
		this.getNbParticlesVariationData().add(proba, nbParticlesVariation);
		
		this.setAverageMeanVelocitySum(this.getAverageMeanVelocitySum() + velocity);
		this.setAverageMeanAlignmentSum(this.getAverageMeanAlignmentSum() + alignment);
		this.setAverageNbParticlesSum(this.getAverageNbParticlesSum() + nbParticles);
		this.setAverageNbParticlesVariationSum(this.getAverageNbParticlesVariationSum() + nbParticlesVariation);
		
		this.setMeanVelocitySum(0);
		this.setMeanAlignmentSum(0);
		this.setNbParticlesSum(0);
		
		super.registerStatsForLaunch();
	}
	
	@Override
	protected void registerStatsForVariation() {
		double proba = (this.isVaryProbaInteraction() ? this.getProbaInteraction() : this.getProbaPropagation());
		
		double velocity = this.getAverageMeanVelocitySum() / this.getMaxNbLaunch();
		double alignment = this.getAverageMeanAlignmentSum() / this.getMaxNbLaunch();
		double nbParticles = this.getAverageNbParticlesSum() / this.getMaxNbLaunch();
		double nbParticlesVariation = this.getAverageNbParticlesVariationSum() / this.getMaxNbLaunch();
		
		this.getAverageVelocityData().add(proba, velocity);
		this.getAverageAlignmentData().add(proba, alignment);
		this.getAverageNbParticlesData().add(proba, nbParticles);
		this.getAverageNbParticlesVariationData().add(proba, nbParticlesVariation);
		
		this.setAverageMeanVelocitySum(0);
		this.setAverageMeanAlignmentSum(0);
		this.setAverageNbParticlesSum(0);
		this.setAverageNbParticlesVariationSum(0);
		
		super.registerStatsForVariation();
	}
	
}
