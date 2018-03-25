package cellularAutomaton.gui.view.grid;

import static cellularAutomaton.core.CellularAutomaton.COMPUTE_STATISTICS;
import static cellularAutomaton.core.CellularAutomaton.FIRST_ITERATION;
import static cellularAutomaton.core.CellularAutomaton.GRID_HEIGHT;
import static cellularAutomaton.core.CellularAutomaton.GRID_WIDTH;
import static cellularAutomaton.core.CellularAutomaton.NB_ITERATION;
import static cellularAutomaton.core.CellularAutomaton.NB_LAUNCH;
import static cellularAutomaton.core.CellularAutomaton.NB_VARIATION;
import static cellularAutomaton.core.CellularAutomaton.PROBA_INIT;
import static cellularAutomaton.core.CellularAutomaton.UPDATE_PERIOD;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import cellularAutomaton.core.CellularAutomaton;
import cellularAutomaton.gui.Gui;
import cellularAutomaton.gui.view.Layout;
import cellularAutomaton.gui.view.Panel;
import cellularAutomaton.gui.view.ProgressBar;
import cellularAutomaton.gui.view.View;

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
public abstract class ViewCA<T extends CellularAutomaton<?>> extends View {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	
	public static final int MIN_CELL_SIZE = 1;
	public static final int MAX_CELL_SIZE = 20;
	public static final int MIN_BORDER_SIZE = 0;
	public static final int MAX_BORDER_SIZE = 3;
	
	public static final String CELL_SIZE = "Cell size";
	public static final String BORDER_SIZE = "Border size";
	
	public static final String SLASH = System.getProperty("file.separator");
	public static final String SAVE_FOLDER = "." + SLASH + "data" + SLASH;
	
	// Fields :
	
	protected final T ca;
	
	private int cellSize = 10;
	private int borderSize = 1;
	
	protected int height = 0;
	protected int width = 0;
	protected BufferedImage image;
	
	protected final JLabel labelImage = new JLabel();
	protected final JLabel labelNbIterations = new JLabel();
	protected final JLabel labelNbVariation = new JLabel(NB_VARIATION + " :");
	
	protected final Panel panelOptions = new Panel();
	protected final Panel panelRun = new Panel();
	protected final Panel panelStaticSettings = new Panel();
	protected final Panel panelDynamicSettings = new Panel();
	protected final Panel panelGraphic = new Panel();
	protected final JPanel panelGrid = new Panel();
	protected final JPanel panelStatistics = new Panel();
	protected final JPanel panelProgressBar = new Panel();
	protected final Panel panelIteration = new Panel();
	protected final Panel panelDynamicInStatic = new Panel();
	protected final Panel dynamicSettingsContainer = new Panel();
	
	protected final JButton buttonRun = new JButton("Run");
	protected final JButton buttonNext = new JButton("Next");
	protected final JButton buttonStop = new JButton("Stop");
	protected final JButton buttonSaveCharts = new JButton("Save charts");
	protected final JButton buttonUpdatePeriod = new JButton("Apply");
	protected final JButton buttonInitSettings = new JButton("Init grid");
	protected final JButton buttonCellSize = new JButton("Apply");
	protected final JButton buttonBorderSize = new JButton("Apply");
	
	protected final JTextField fieldUpdatePeriod = new JTextField(5);
	protected final JTextField fieldGridHeight = new JTextField(5);
	protected final JTextField fieldGridWidth = new JTextField(5);
	protected final JTextField fieldProbaInit = new JTextField(5);
	protected final JTextField fieldFirstIteration = new JTextField(5);
	protected final JTextField fieldNbIteration = new JTextField(5);
	protected final JTextField fieldNbLaunch = new JTextField(5);
	protected final JTextField fieldNbVariation = new JTextField(5);
	protected final JTextField fieldCellSize = new JTextField(5);
	protected final JTextField fieldBorderSize = new JTextField(5);
	
	protected final JCheckBox checkBoxComputeStats = new JCheckBox(COMPUTE_STATISTICS);
	protected final JCheckBox checkBoxDisplayGrid = new JCheckBox("Display grid");
	protected final JCheckBox checkBoxDisplayStats = new JCheckBox("Display statistics");
	
	protected final ProgressBar transitionBar = new ProgressBar("Transition");
	protected final ProgressBar statsBar = new ProgressBar("Statistics");
	protected final ProgressBar launchBar = new ProgressBar("Launches");
	protected final ProgressBar variationBar = new ProgressBar("Variations");
	
	private boolean inStaticMod = false;
	
	protected final List<Component> settingsComponents = new LinkedList<Component>();
	protected final List<JCheckBox> variationCheckBoxes = new LinkedList<JCheckBox>();
	
	// Constructors :
	
	protected ViewCA(Gui gui, T ca) {
		super(gui);
		this.ca = ca;
		this.ca.addObserver(this);
		
		Layout.add(this, this.panelOptions, 0, 0, 1, 2, 0, 1, Layout.CENTER, Layout.BOTH, 0);
		
		Layout.add(this.panelOptions, this.panelRun, 0, 0, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		this.panelRun.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), "Run Settings"));
		
		Layout.add(this.panelOptions, this.panelStaticSettings, 0, 1, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		this.panelStaticSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), "Static Settings"));
		
		Layout.add(this.panelOptions, this.panelDynamicSettings, 0, 2, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		this.panelDynamicSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), "Dynamic Settings"));
		
		Layout.add(this.panelOptions, this.panelGraphic, 0, 3, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		this.panelGraphic.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), "Display Settings"));
		
		this.initOptionPanel();
		this.updateToolTips();
		this.updateDynamicComponents();
		
		Panel panel = new Panel();
		JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Layout.add(this, scrollPane, 1, 0, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), "Cellular Automaton"));
		
		Layout.add(panel, this.panelGrid, 0, 0, 0, 0, Layout.CENTER, Layout.NONE, 0);
		Layout.add(this.panelGrid, this.labelImage, 0, 0, 1, 1, Layout.CENTER, Layout.BOTH, 2);
		
		Layout.add(panel, this.panelStatistics, 1, 0, 0, 0, Layout.CENTER, Layout.NONE, 0);
		
		Layout.add(this, this.panelProgressBar, 1, 1, 1, 0, Layout.CENTER, Layout.BOTH, 0);
		this.panelProgressBar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), "Progress"));
		
		Layout.add(this.panelProgressBar, this.transitionBar, 0, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(this.panelProgressBar, this.statsBar, 1, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(this.panelProgressBar, this.launchBar, 0, 1, 2, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(this.panelProgressBar, this.variationBar, 0, 2, 2, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		this.buildGrid();
	}
	
	// Getters :
	
	public int getCellSize() {
		return this.cellSize;
	}
	
	public int getBorderSize() {
		return this.borderSize;
	}
	
	public boolean isInStaticMod() {
		return this.inStaticMod;
	}
	
	// Setters :
	
	public void setCellSize(int cellSize) {
		checkCellSize(cellSize);
		
		this.cellSize = cellSize;
		this.buildGrid();
		this.updateGrid();
	}
	
	public void setBorderSize(int borderSize) {
		checkBorderSize(borderSize);
		
		this.borderSize = borderSize;
		this.buildGrid();
		this.updateGrid();
	}
	
	public void setInStaticMod(boolean inStaticMod) {
		this.inStaticMod = inStaticMod;
		this.updateDynamicComponents();
	}
	
	// Methods :
	
	@Override
	public final void updateView() {
		boolean running = this.ca.isRunning();
		boolean computeStats = this.ca.isComputeStats();
		boolean displayGrid = this.ca.isWaitBetweenIteration();
		boolean notBegin = this.ca.getNbVariation() <= 0 && this.ca.getNbLaunch() <= 0 && this.ca.getNbIteration() <= 0;
		boolean workDone = this.ca.getNbVariation() >= this.ca.getMaxNbVariation() && this.ca.getNbLaunch() >= this.ca.getMaxNbLaunch() && this.ca.getNbIteration() >= this.ca.getMaxNbIteration();
		boolean enableChanges = !this.ca.isComputeStats() || notBegin || workDone;
		
		this.labelNbIterations.setText(String.valueOf(this.ca.getNbIteration()));
		
		this.buttonRun.setText(running ? "Pause" : "Run");
		this.buttonRun.setEnabled(!computeStats || !workDone);
		this.buttonNext.setEnabled(!running && (!computeStats || !workDone));
		this.buttonStop.setEnabled(!enableChanges);
		this.buttonSaveCharts.setEnabled(computeStats && workDone);
		
		this.panelProgressBar.setVisible(computeStats);
		
		this.panelGrid.setVisible(displayGrid);
		this.panelStatistics.setVisible(!displayGrid);
		this.checkBoxDisplayGrid.setSelected(displayGrid);
		this.checkBoxDisplayStats.setSelected(!displayGrid);
		
		for(Component component : this.settingsComponents) {
			component.setEnabled(enableChanges);
		}
		
		if(this.ca.getHeight() != this.height || this.ca.getWidth() != this.width) {
			this.buildGrid();
		}
		
		if(this.panelGrid.isVisible()) {
			this.updateGrid();
		}
		
		if(this.panelProgressBar.isVisible()) {
			int firstIteration = this.ca.getFirstIteration();
			int maxNbIteration = this.ca.getMaxNbIteration();
			int nbIteration = this.ca.getNbIteration();
			int transition = (nbIteration > firstIteration) ? firstIteration : nbIteration;
			int statistics = (nbIteration > firstIteration) ? (nbIteration - firstIteration) : 0;
			
			this.transitionBar.setState(transition, firstIteration);
			this.statsBar.setState(statistics, maxNbIteration);
			this.launchBar.setState(this.ca.getNbLaunch(), this.ca.getMaxNbLaunch());
			this.variationBar.setState(this.ca.getNbVariation(), this.ca.getMaxNbVariation());
		}
	}
	
	@Override
	protected void exitView() {
		this.ca.pause();
	}
	
	protected abstract void updateGrid();
	
	protected void buildGrid() {
		this.height = this.ca.getHeight();
		this.width = this.ca.getWidth();
		
		int heightInPx = this.height * (this.getCellSize() + this.getBorderSize()) + this.getBorderSize();
		int widthInPx = this.width * (this.getCellSize() + this.getBorderSize()) + this.getBorderSize();
		
		this.image = new BufferedImage(widthInPx, heightInPx, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D graphics = this.image.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fill(new Rectangle(0, 0, widthInPx, heightInPx));
		graphics.setColor(Color.DARK_GRAY);
		
		for(int i = 0; i <= this.height; i++) {
			graphics.fill(new Rectangle(0, i * (this.getCellSize() + this.getBorderSize()), widthInPx, this.getBorderSize()));
		}
		
		for(int j = 0; j <= this.width; j++) {
			graphics.fill(new Rectangle(j * (this.getCellSize() + this.getBorderSize()), 0, this.getBorderSize(), heightInPx));
		}
		
		graphics.dispose();
		
		this.labelImage.setIcon(new ImageIcon(this.image));
	}
	
	protected void initOptionPanel() {
		// Components initialization :
		
		this.fieldUpdatePeriod.setText(String.valueOf(this.ca.getUpdatePeriod()));
		this.fieldGridHeight.setText(String.valueOf(this.ca.getHeight()));
		this.fieldGridWidth.setText(String.valueOf(this.ca.getWidth()));
		this.fieldProbaInit.setText(String.valueOf(this.ca.getProbaInit()));
		this.fieldFirstIteration.setText(String.valueOf(this.ca.getFirstIteration()));
		this.fieldNbIteration.setText(String.valueOf(this.ca.getMaxNbIteration()));
		this.fieldNbLaunch.setText(String.valueOf(this.ca.getMaxNbLaunch()));
		this.fieldNbVariation.setText(String.valueOf(this.ca.getMaxNbVariation()));
		this.fieldCellSize.setText(String.valueOf(this.getCellSize()));
		this.fieldBorderSize.setText(String.valueOf(this.getBorderSize()));
		
		this.fieldUpdatePeriod.setMinimumSize(this.fieldUpdatePeriod.getPreferredSize());
		this.fieldGridHeight.setMinimumSize(this.fieldGridHeight.getPreferredSize());
		this.fieldGridWidth.setMinimumSize(this.fieldGridWidth.getPreferredSize());
		this.fieldProbaInit.setMinimumSize(this.fieldProbaInit.getPreferredSize());
		this.fieldFirstIteration.setMinimumSize(this.fieldFirstIteration.getPreferredSize());
		this.fieldNbIteration.setMinimumSize(this.fieldNbIteration.getPreferredSize());
		this.fieldNbLaunch.setMinimumSize(this.fieldNbLaunch.getPreferredSize());
		this.fieldNbVariation.setMinimumSize(this.fieldNbVariation.getPreferredSize());
		this.fieldCellSize.setMinimumSize(this.fieldCellSize.getPreferredSize());
		this.fieldBorderSize.setMinimumSize(this.fieldBorderSize.getPreferredSize());
		
		this.checkBoxComputeStats.setSelected(this.ca.isComputeStats());
		this.checkBoxDisplayGrid.setSelected(this.ca.isWaitBetweenIteration());
		this.checkBoxDisplayStats.setSelected(!this.ca.isWaitBetweenIteration());
		
		this.panelIteration.setVisible(false);
		
		// Components registration :
		
		this.settingsComponents.add(this.buttonInitSettings);
		this.settingsComponents.add(this.fieldGridHeight);
		this.settingsComponents.add(this.fieldGridWidth);
		this.settingsComponents.add(this.fieldProbaInit);
		this.settingsComponents.add(this.fieldFirstIteration);
		this.settingsComponents.add(this.fieldNbIteration);
		this.settingsComponents.add(this.fieldNbLaunch);
		this.settingsComponents.add(this.fieldNbVariation);
		this.settingsComponents.add(this.checkBoxComputeStats);
		
		// Run settings :
		
		Panel panelNbIterations = new Panel();
		Layout.add(this.panelRun, panelNbIterations, 0, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(panelNbIterations, new JLabel("Iterations : "), 0, 0, 0, 0, Layout.CENTER, Layout.NONE, 2);
		Layout.add(panelNbIterations, this.labelNbIterations, 1, 0, 0, 0, Layout.CENTER, Layout.NONE, 2);
		
		Layout.add(this.panelRun, this.buttonRun, 0, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(this.panelRun, this.buttonNext, 0, 2, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(this.panelRun, this.buttonStop, 0, 3, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Panel panelUpdatePeriod = new Panel();
		Layout.add(this.panelRun, panelUpdatePeriod, 0, 4, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(panelUpdatePeriod, new JLabel(UPDATE_PERIOD + " (ms) : "), 0, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelUpdatePeriod, this.fieldUpdatePeriod, 1, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelUpdatePeriod, this.buttonUpdatePeriod, 2, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Layout.add(this.panelRun, this.buttonSaveCharts, 0, 5, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		// Static settings :
		
		Layout.add(this.panelStaticSettings, this.checkBoxComputeStats, 0, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Panel panelInitGrid = new Panel();
		Layout.add(this.panelStaticSettings, panelInitGrid, 0, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(panelInitGrid, new JLabel(GRID_HEIGHT + " :"), 0, 0, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(panelInitGrid, this.fieldGridHeight, 1, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelInitGrid, new JLabel(GRID_WIDTH + " :"), 0, 1, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(panelInitGrid, this.fieldGridWidth, 1, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelInitGrid, new JLabel(PROBA_INIT + " :"), 0, 2, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(panelInitGrid, this.fieldProbaInit, 1, 2, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Layout.add(this.panelStaticSettings, this.panelDynamicInStatic, 0, 2, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(this.panelStaticSettings, this.panelIteration, 0, 3, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(this.panelIteration, new JLabel(FIRST_ITERATION + " :"), 0, 0, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(this.panelIteration, this.fieldFirstIteration, 1, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(this.panelIteration, new JLabel(NB_ITERATION + " :"), 0, 1, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(this.panelIteration, this.fieldNbIteration, 1, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(this.panelIteration, new JLabel(NB_LAUNCH + " :"), 0, 2, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(this.panelIteration, this.fieldNbLaunch, 1, 2, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(this.panelIteration, this.labelNbVariation, 0, 3, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(this.panelIteration, this.fieldNbVariation, 1, 3, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Layout.add(this.panelStaticSettings, this.buttonInitSettings, 0, 4, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		// Dinamic settings :
		
		Layout.add(this.panelDynamicSettings, this.dynamicSettingsContainer, 0, 0, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		
		// Display settings :
		
		Panel panelDisplay = new Panel();
		Layout.add(this.panelGraphic, panelDisplay, 0, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(panelDisplay, this.checkBoxDisplayGrid, 0, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelDisplay, this.checkBoxDisplayStats, 1, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Panel panelCells = new Panel();
		Layout.add(this.panelGraphic, panelCells, 0, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(panelCells, new JLabel(CELL_SIZE + " :"), 0, 0, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(panelCells, this.fieldCellSize, 1, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelCells, this.buttonCellSize, 2, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelCells, new JLabel(BORDER_SIZE + " :"), 0, 1, 0, 0, Layout.WEST, Layout.HORIZONTAL, 2);
		Layout.add(panelCells, this.fieldBorderSize, 1, 1, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelCells, this.buttonBorderSize, 2, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		// Action listeners :
		
		this.buttonRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewCA.this.runOrPause();
			}
			
		});
		
		this.buttonNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewCA.this.ca.next();
			}
			
		});
		
		this.buttonStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewCA.this.ca.stop();
			}
			
		});
		
		this.buttonSaveCharts.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewCA.this.saveCharts();
			}
			
		});
		
		this.buttonUpdatePeriod.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewCA.this.sendUpdatePeriod();
			}
			
		});
		
		this.buttonInitSettings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewCA.this.sendInitSettings();
			}
			
		});
		
		this.buttonCellSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewCA.this.sendCellSize();
			}
			
		});
		
		this.buttonBorderSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewCA.this.sendBorderSize();
			}
			
		});
		
		this.checkBoxComputeStats.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewCA.this.updateStaticMod();
			}
			
		});
		
		this.checkBoxDisplayGrid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewCA.this.ca.changeWaitBetweenIteration(true);
			}
			
		});
		
		this.checkBoxDisplayStats.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewCA.this.ca.changeWaitBetweenIteration(false);
			}
			
		});
		
		KeyListener keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// Nothing here.
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// Nothing here.
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(e.getSource() == ViewCA.this.fieldUpdatePeriod) {
						ViewCA.this.sendUpdatePeriod();
					}
					else if(e.getSource() == ViewCA.this.fieldGridHeight || e.getSource() == ViewCA.this.fieldGridWidth || e.getSource() == ViewCA.this.fieldProbaInit || e.getSource() == ViewCA.this.fieldFirstIteration || e.getSource() == ViewCA.this.fieldNbIteration || e.getSource() == ViewCA.this.fieldNbLaunch || e.getSource() == ViewCA.this.fieldNbVariation) {
						ViewCA.this.sendInitSettings();
					}
					else if(e.getSource() == ViewCA.this.fieldCellSize) {
						ViewCA.this.sendCellSize();
					}
					else if(e.getSource() == ViewCA.this.fieldBorderSize) {
						ViewCA.this.sendBorderSize();
					}
				}
			}
			
		};
		
		this.fieldUpdatePeriod.addKeyListener(keyListener);
		this.fieldGridHeight.addKeyListener(keyListener);
		this.fieldGridWidth.addKeyListener(keyListener);
		this.fieldProbaInit.addKeyListener(keyListener);
		this.fieldFirstIteration.addKeyListener(keyListener);
		this.fieldNbIteration.addKeyListener(keyListener);
		this.fieldNbLaunch.addKeyListener(keyListener);
		this.fieldNbVariation.addKeyListener(keyListener);
		this.fieldCellSize.addKeyListener(keyListener);
		this.fieldBorderSize.addKeyListener(keyListener);
		
		this.updateFieldVariation();
	}
	
	protected void updateToolTips() {
		this.fieldUpdatePeriod.setToolTipText("Actual value : " + this.ca.getUpdatePeriod());
		this.fieldGridHeight.setToolTipText("Actual value : " + this.ca.getHeight());
		this.fieldGridWidth.setToolTipText("Actual value : " + this.ca.getWidth());
		this.fieldProbaInit.setToolTipText("Actual value : " + this.ca.getProbaInit());
		this.fieldFirstIteration.setToolTipText("Actual value : " + this.ca.getFirstIteration());
		this.fieldNbIteration.setToolTipText("Actual value : " + this.ca.getMaxNbIteration());
		this.fieldNbLaunch.setToolTipText("Actual value : " + this.ca.getMaxNbLaunch());
		this.fieldNbVariation.setToolTipText("Actual value : " + this.ca.getMaxNbVariation());
		this.fieldCellSize.setToolTipText("Actual value : " + this.getCellSize());
		this.fieldBorderSize.setToolTipText("Actual value : " + this.getBorderSize());
	}
	
	protected void updateCharts() {
		// Empty by defaut.
	}
	
	protected void updateStaticMod() {
		this.setInStaticMod(this.checkBoxComputeStats.isSelected());
	}
	
	protected void updateDynamicComponents() {
		this.panelIteration.setVisible(this.isInStaticMod());
		this.panelDynamicSettings.setVisible(!this.isInStaticMod());
		Layout.add((this.isInStaticMod() ? this.panelDynamicInStatic : this.panelDynamicSettings), this.dynamicSettingsContainer, 0, 0, 1, 1, Layout.CENTER, Layout.BOTH, 0);
		this.update2ndFields();
	}
	
	protected void updateVariationComponents(JCheckBox choise) {
		boolean selected = choise.isSelected();
		for(JCheckBox checkBox : this.variationCheckBoxes) {
			checkBox.setSelected((checkBox == choise) ? selected : false);
		}
		
		this.update2ndFields();
		this.updateFieldVariation();
	}
	
	protected void updateFieldVariation() {
		boolean isThereVariations = this.isThereVariations();
		this.fieldNbVariation.setVisible(isThereVariations);
		this.labelNbVariation.setVisible(isThereVariations);
	}
	
	protected void update2ndFields() {
		this.dynamicSettingsContainer.revalidate();
		this.dynamicSettingsContainer.repaint();
	}
	
	protected boolean isThereVariations() {
		for(JCheckBox checkBox : this.variationCheckBoxes) {
			if(checkBox.isSelected()) {
				return true;
			}
		}
		return false;
	}
	
	protected static void checkCellSize(int cellSize) {
		if(cellSize < MIN_CELL_SIZE || cellSize > MAX_CELL_SIZE) {
			throw new IllegalArgumentException(CELL_SIZE + " must be between " + MIN_CELL_SIZE + " and " + MAX_CELL_SIZE + ".");
		}
	}
	
	protected static void checkBorderSize(int borderSize) {
		if(borderSize < MIN_BORDER_SIZE || borderSize > MAX_BORDER_SIZE) {
			throw new IllegalArgumentException(BORDER_SIZE + " must be between " + MIN_BORDER_SIZE + " and " + MAX_BORDER_SIZE + ".");
		}
	}
	
	protected int getFieldUpdatePeriod() {
		try {
			return Integer.parseInt(this.fieldUpdatePeriod.getText());
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(UPDATE_PERIOD + " must be an integer.");
		}
	}
	
	protected int getFieldGridHeight() {
		try {
			return Integer.parseInt(this.fieldGridHeight.getText());
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(GRID_HEIGHT + " must be an integer.");
		}
	}
	
	protected int getFieldGridWidth() {
		try {
			return Integer.parseInt(this.fieldGridWidth.getText());
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(GRID_WIDTH + " must be an integer.");
		}
	}
	
	protected double getFieldProbaInit() {
		try {
			return Double.parseDouble(this.fieldProbaInit.getText());
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_INIT + " must be a real number.");
		}
	}
	
	protected int getFieldFirstIteration() {
		try {
			if(this.isInStaticMod()) {
				return Integer.parseInt(this.fieldFirstIteration.getText());
			}
			else {
				return this.ca.getFirstIteration();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(FIRST_ITERATION + " must be an integer.");
		}
	}
	
	protected int getFieldNbIteration() {
		try {
			if(this.isInStaticMod()) {
				return Integer.parseInt(this.fieldNbIteration.getText());
			}
			else {
				return this.ca.getMaxNbIteration();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(NB_ITERATION + " must be an integer.");
		}
	}
	
	protected int getFieldNbLaunch() {
		try {
			if(this.isInStaticMod()) {
				return Integer.parseInt(this.fieldNbLaunch.getText());
			}
			else {
				return this.ca.getMaxNbLaunch();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(NB_LAUNCH + " must be an integer.");
		}
	}
	
	protected int getFieldNbVariation() {
		try {
			if(this.isInStaticMod()) {
				if(this.isThereVariations()) {
					return Integer.parseInt(this.fieldNbVariation.getText());
				}
				else {
					return 1;
				}
			}
			else {
				return this.ca.getMaxNbVariation();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(NB_VARIATION + " must be an integer.");
		}
	}
	
	protected int getFieldCellSize() {
		try {
			return Integer.parseInt(this.fieldCellSize.getText());
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(CELL_SIZE + " must be an integer.");
		}
	}
	
	protected int getFieldBorderSize() {
		try {
			return Integer.parseInt(this.fieldBorderSize.getText());
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(BORDER_SIZE + " must be an integer.");
		}
	}
	
	protected void sendUpdatePeriod() {
		try {
			int updatePeriod = this.getFieldUpdatePeriod();
			this.ca.changeUpdatePeriod(updatePeriod);
			this.updateToolTips();
		}
		catch(IllegalArgumentException argh) {
			this.gui.warning(argh.getMessage(), "Invalid value");
		}
	}
	
	protected boolean sendDynamicSettings() {
		this.updateToolTips();
		this.updateCharts();
		return true;
	}
	
	protected void sendInitSettings() {
		if(this.sendDynamicSettings()) {
			try {
				int height = this.getFieldGridHeight();
				int width = this.getFieldGridWidth();
				double probaInit = this.getFieldProbaInit();
				boolean computeStats = this.isInStaticMod();
				int firstIteration = this.getFieldFirstIteration();
				int maxNbIteration = this.getFieldNbIteration();
				int maxNbLaunch = this.getFieldNbLaunch();
				int maxNbVariation = this.getFieldNbVariation();
				this.ca.changeInitSettings(height, width, probaInit, computeStats, firstIteration, maxNbIteration, maxNbLaunch, maxNbVariation);
				this.updateToolTips();
			}
			catch(IllegalArgumentException argh) {
				this.gui.warning(argh.getMessage(), "Invalid value");
			}
		}
	}
	
	protected void sendCellSize() {
		try {
			int cellSize = this.getFieldCellSize();
			this.setCellSize(cellSize);
			this.updateToolTips();
		}
		catch(IllegalArgumentException argh) {
			this.gui.warning(argh.getMessage(), "Invalid value");
		}
	}
	
	protected void sendBorderSize() {
		try {
			int borderSize = this.getFieldBorderSize();
			this.setBorderSize(borderSize);
			this.updateToolTips();
		}
		catch(IllegalArgumentException argh) {
			this.gui.warning(argh.getMessage(), "Invalid value");
		}
	}
	
	protected void runOrPause() {
		if(this.ca.isRunning()) {
			this.ca.pause();
		}
		else {
			LinkedList<String> notValidatedFields = this.notValidatedFields();
			
			if(notValidatedFields.size() > 0) {
				StringBuilder question = new StringBuilder("The folowing fields have been changed but have not been validated :");
				
				for(String field : notValidatedFields) {
					question.append("\n    - " + field);
				}
				
				int answer = this.gui.options(question.toString(), "Confirm launch", 0, "Validate and launch", "Launch without validating", "Cancel");
				
				if(answer == 0) {
					this.sendInitSettings();
					
					try {
						Thread.sleep(100);
					}
					catch(InterruptedException argh) {
						System.err.println("Sleep interupted.");
					}
					
					this.ca.run();
				}
				else if(answer == 1) {
					this.ca.run();
				}
			}
			else {
				this.ca.run();
			}
		}
	}
	
	protected LinkedList<String> notValidatedFields() {
		LinkedList<String> list = new LinkedList<>();
		
		if(this.inStaticMod != this.ca.isComputeStats()) {
			list.add(COMPUTE_STATISTICS);
		}
		
		if(this.getFieldGridHeight() != this.ca.getHeight()) {
			list.add(GRID_HEIGHT);
		}
		
		if(this.getFieldGridWidth() != this.ca.getWidth()) {
			list.add(GRID_WIDTH);
		}
		
		if(this.getFieldProbaInit() != this.ca.getProbaInit()) {
			list.add(PROBA_INIT);
		}
		
		if(this.isInStaticMod()) {
			if(this.getFieldFirstIteration() != this.ca.getFirstIteration()) {
				list.add(FIRST_ITERATION);
			}
			
			if(this.getFieldNbIteration() != this.ca.getMaxNbIteration()) {
				list.add(NB_ITERATION);
			}
			
			if(this.getFieldNbLaunch() != this.ca.getMaxNbLaunch()) {
				list.add(NB_LAUNCH);
			}
			
			if(this.getFieldNbVariation() != this.ca.getMaxNbVariation()) {
				list.add(NB_VARIATION);
			}
		}
		
		return list;
	}
	
	protected void saveCharts() {
		// Empty by default.
	}
	
}
