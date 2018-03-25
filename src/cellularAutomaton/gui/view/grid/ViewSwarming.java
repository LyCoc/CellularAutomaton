package cellularAutomaton.gui.view.grid;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import cellularAutomaton.core.enums.ActiveCA;
import cellularAutomaton.core.latticeGas.LatticeGasCA;
import cellularAutomaton.core.latticeGas.Swarming;
import cellularAutomaton.gui.Gui;
import cellularAutomaton.gui.view.Layout;
import cellularAutomaton.gui.view.XYChartFactory;

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
public class ViewSwarming extends ViewLatticeGasCA<Swarming> {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_TITLE = "Parameter";
	private static final String MEAN_VELOCITY = "Mean velocity";
	private static final String MEAN_ALIGNMENT = "Mean alignment";
	private static final String NB_PARTICLES = "Nb particles";
	private static final String NB_PARTICLES_VARIATION = "Nb particles variation";
	
	// Fields :
	
	protected final JFreeChart velocityChart;
	protected final JFreeChart alignmentChart;
	protected final JFreeChart nbParticlesChart;
	protected final JFreeChart nbParticlesVariationChart;
	
	// Constructors :
	
	public ViewSwarming(Gui gui, Swarming ca) {
		super(gui, ca);
		
		this.velocityChart = XYChartFactory.createXYChart(DEFAULT_TITLE, MEAN_VELOCITY, this.ca.getVelocityDataset());
		this.alignmentChart = XYChartFactory.createXYChart(DEFAULT_TITLE, MEAN_ALIGNMENT, this.ca.getAlignmentDataset());
		this.nbParticlesChart = XYChartFactory.createXYChart(DEFAULT_TITLE, NB_PARTICLES, this.ca.getNbParticlesDataset());
		this.nbParticlesVariationChart = XYChartFactory.createXYChart(DEFAULT_TITLE, NB_PARTICLES_VARIATION, this.ca.getNbParticlesVariationDataset());
		
		ChartPanel panelVelocityChart = new ChartPanel(this.velocityChart);
		ChartPanel panelAlignmentChart = new ChartPanel(this.alignmentChart);
		ChartPanel panelNbParticlesChart = new ChartPanel(this.nbParticlesChart);
		ChartPanel panelNbParticlesVariationChart = new ChartPanel(this.nbParticlesVariationChart);
		
		Layout.add(this.panelStatistics, panelVelocityChart, 0, 0, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		Layout.add(this.panelStatistics, panelAlignmentChart, 0, 1, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		Layout.add(this.panelStatistics, panelNbParticlesChart, 0, 2, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		Layout.add(this.panelStatistics, panelNbParticlesVariationChart, 0, 3, 1, 1, Layout.CENTER, Layout.BOTH, 0);
	}
	
	// Methods :
	
	@Override
	protected boolean isDisplayed() {
		return this.gui.getModele().getActiveCA() == ActiveCA.SWARMING;
	}
	
	@Override
	protected void updateCharts() {
		String title = this.getChartsTitle();
		XYChartFactory.changeXAxisTitle(this.velocityChart, title);
		XYChartFactory.changeXAxisTitle(this.alignmentChart, title);
		XYChartFactory.changeXAxisTitle(this.nbParticlesChart, title);
		XYChartFactory.changeXAxisTitle(this.nbParticlesVariationChart, title);
	}
	
	protected String getChartsTitle() {
		if(this.getFieldVaryInteractionProba()) {
			return LatticeGasCA.PROBA_INTERACTION;
		}
		else if(this.getFieldVaryPropagationProba()) {
			return LatticeGasCA.PROBA_PROPAGATION;
		}
		else {
			return DEFAULT_TITLE;
		}
	}
	
	@Override
	protected void saveCharts() {
		StringBuilder builder = new StringBuilder();
		builder.append("_Init_");
		builder.append(this.ca.getProbaInit());
		builder.append("_Inter_");
		
		if(this.ca.isVaryProbaInteraction()) {
			builder.append(this.ca.getProbaInteraction1());
			builder.append("_");
			builder.append(this.ca.getProbaInteraction2());
		}
		else {
			builder.append(this.ca.getProbaInteraction());
		}
		
		builder.append("_Propa_");
		
		if(this.ca.isVaryProbaPropagation()) {
			builder.append(this.ca.getProbaPropagation1());
			builder.append("_");
			builder.append(this.ca.getProbaPropagation2());
		}
		else {
			builder.append(this.ca.getProbaPropagation());
		}
		
		builder.append("_");
		builder.append(this.ca.isParticlesPreserving() ? "PY" : "PN");
		builder.append("_Var_");
		builder.append(this.ca.getFirstIteration());
		builder.append("_");
		builder.append(this.ca.getMaxNbIteration());
		builder.append("_");
		builder.append(this.ca.getMaxNbLaunch());
		builder.append("_");
		builder.append(this.ca.getMaxNbVariation());
		builder.append(".png");
		
		String fileNameEnd = builder.toString();
		
		File fileVelocityChart = new File(SAVE_FOLDER + "Velocity" + fileNameEnd);
		File fileAlignmentChart = new File(SAVE_FOLDER + "Alignment" + fileNameEnd);
		File fileNbParticlesChart = new File(SAVE_FOLDER + "NbParticles" + fileNameEnd);
		File fileNbParticlesVariationChart = new File(SAVE_FOLDER + "NbParticlesVariation" + fileNameEnd);
		
		if(fileVelocityChart.exists() || fileAlignmentChart.exists() || fileNbParticlesChart.exists() || fileNbParticlesVariationChart.exists()) {
			int answer = this.gui.options("The files for this simulation already exist. Do you want to overwrite them ?", "Confirm overwrite", 0, "Overwrite", "Cancel");
			if(answer == 1) {
				return;
			}
		}
		
		int width = 960;
		int height = 540;
		
		try {
			ChartUtilities.saveChartAsPNG(fileVelocityChart, this.velocityChart, width, height);
			ChartUtilities.saveChartAsPNG(fileAlignmentChart, this.alignmentChart, width, height);
			ChartUtilities.saveChartAsPNG(fileNbParticlesChart, this.nbParticlesChart, width, height);
			ChartUtilities.saveChartAsPNG(fileNbParticlesVariationChart, this.nbParticlesVariationChart, width, height);
			this.gui.info("Save completed.", "Info");
		}
		catch(IOException argh) {
			argh.printStackTrace();
			this.gui.error("An error occured while trying to save the charts.", "Save error");
		}
	}
	
}
