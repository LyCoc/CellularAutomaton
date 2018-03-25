package cellularAutomaton.gui.view.grid;

import static cellularAutomaton.core.latticeGas.LatticeGasCA.PRESERVE_PARTICULES;
import static cellularAutomaton.core.latticeGas.LatticeGasCA.PROBA_INTERACTION;
import static cellularAutomaton.core.latticeGas.LatticeGasCA.PROBA_PROPAGATION;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cellularAutomaton.core.latticeGas.LatticeGasCA;
import cellularAutomaton.core.latticeGas.Particles;
import cellularAutomaton.gui.Gui;
import cellularAutomaton.gui.view.Layout;
import cellularAutomaton.gui.view.Panel;

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
public abstract class ViewLatticeGasCA<T extends LatticeGasCA> extends ViewCA<T> {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	private static final Color[] COLORS = {Color.WHITE, Color.RED, Color.GREEN, Color.LIGHT_GRAY, Color.ORANGE, Color.PINK, Color.YELLOW, Color.ORANGE, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.BLUE, Color.LIGHT_GRAY, Color.RED, Color.GREEN, Color.BLACK};
	
	// Fields :
	
	protected JButton buttonProbaInteraction;
	protected JButton buttonProbaPropagation;
	
	protected JTextField fieldProbaInteraction;
	protected JTextField fieldProbaInteraction1;
	protected JTextField fieldProbaInteraction2;
	protected JTextField fieldProbaPropagation;
	protected JTextField fieldProbaPropagation1;
	protected JTextField fieldProbaPropagation2;
	
	protected JCheckBox checkBoxParticlesPreserving;
	protected JCheckBox checkBoxProbaInteraction;
	protected JCheckBox checkBoxProbaPropagation;
	
	// Constructors :
	
	protected ViewLatticeGasCA(Gui gui, T ca) {
		super(gui, ca);
	}
	
	// Methods :
	
	@Override
	protected void updateGrid() {
		Graphics2D graphics = this.image.createGraphics();
		
		for(int i = 0; i < this.ca.getHeight(); i++) {
			for(int j = 0; j < this.ca.getWidth(); j++) {
				Particles particles = this.ca.getTab()[i][j];
				
				if(particles == null) {
					continue;
				}
				
				int colorIndex = 0;
				if(particles.isTop()) {
					colorIndex += 8;
				}
				if(particles.isBottom()) {
					colorIndex += 4;
				}
				if(particles.isRight()) {
					colorIndex += 2;
				}
				if(particles.isLeft()) {
					colorIndex += 1;
				}
				
				graphics.setColor(COLORS[colorIndex]);
				graphics.fill(new Rectangle(j * (this.getCellSize() + this.getBorderSize()) + this.getBorderSize(), i * (this.getCellSize() + this.getBorderSize()) + this.getBorderSize(), this.getCellSize(), this.getCellSize()));
			}
		}
		
		graphics.dispose();
		this.labelImage.repaint();
	}
	
	@Override
	protected void initOptionPanel() {
		super.initOptionPanel();
		
		// Components initialization :
		
		this.buttonProbaInteraction = new JButton("Apply");
		this.buttonProbaPropagation = new JButton("Apply");
		
		this.fieldProbaInteraction = new JTextField(String.valueOf(this.ca.getProbaInteraction()), 5);
		this.fieldProbaInteraction1 = new JTextField(String.valueOf(this.ca.getProbaInteraction1()), 5);
		this.fieldProbaInteraction2 = new JTextField(String.valueOf(this.ca.getProbaInteraction2()), 5);
		this.fieldProbaPropagation = new JTextField(String.valueOf(this.ca.getProbaPropagation()), 5);
		this.fieldProbaPropagation1 = new JTextField(String.valueOf(this.ca.getProbaPropagation1()), 5);
		this.fieldProbaPropagation2 = new JTextField(String.valueOf(this.ca.getProbaPropagation2()), 5);
		
		this.fieldProbaInteraction.setMinimumSize(this.fieldProbaInteraction.getPreferredSize());
		this.fieldProbaInteraction1.setMinimumSize(this.fieldProbaInteraction1.getPreferredSize());
		this.fieldProbaInteraction2.setMinimumSize(this.fieldProbaInteraction2.getPreferredSize());
		this.fieldProbaPropagation.setMinimumSize(this.fieldProbaPropagation.getPreferredSize());
		this.fieldProbaPropagation1.setMinimumSize(this.fieldProbaPropagation1.getPreferredSize());
		this.fieldProbaPropagation2.setMinimumSize(this.fieldProbaPropagation2.getPreferredSize());
		
		this.checkBoxParticlesPreserving = new JCheckBox(PRESERVE_PARTICULES, this.ca.isParticlesPreserving());
		this.checkBoxProbaInteraction = new JCheckBox("", this.ca.isVaryProbaInteraction());
		this.checkBoxProbaPropagation = new JCheckBox("", this.ca.isVaryProbaPropagation());
		
		this.fieldProbaInteraction1.setVisible(false);
		this.fieldProbaInteraction2.setVisible(false);
		this.fieldProbaPropagation1.setVisible(false);
		this.fieldProbaPropagation2.setVisible(false);
		this.checkBoxProbaInteraction.setVisible(false);
		this.checkBoxProbaPropagation.setVisible(false);
		
		// Components registration :
		
		this.settingsComponents.add(this.buttonProbaInteraction);
		this.settingsComponents.add(this.buttonProbaPropagation);
		this.settingsComponents.add(this.fieldProbaInteraction);
		this.settingsComponents.add(this.fieldProbaInteraction1);
		this.settingsComponents.add(this.fieldProbaInteraction2);
		this.settingsComponents.add(this.fieldProbaPropagation);
		this.settingsComponents.add(this.fieldProbaPropagation1);
		this.settingsComponents.add(this.fieldProbaPropagation2);
		this.settingsComponents.add(this.checkBoxParticlesPreserving);
		this.settingsComponents.add(this.checkBoxProbaInteraction);
		this.settingsComponents.add(this.checkBoxProbaPropagation);
		
		this.variationCheckBoxes.add(this.checkBoxProbaInteraction);
		this.variationCheckBoxes.add(this.checkBoxProbaPropagation);
		
		// Dynamic settings :
		
		Panel panelInteraction = new Panel();
		Layout.add(this.dynamicSettingsContainer, panelInteraction, 0, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(panelInteraction, new JLabel(PROBA_INTERACTION + " :"), 0, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelInteraction, this.fieldProbaInteraction, 1, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelInteraction, this.fieldProbaInteraction1, 2, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelInteraction, this.fieldProbaInteraction2, 3, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelInteraction, this.buttonProbaInteraction, 4, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelInteraction, this.checkBoxProbaInteraction, 5, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Panel panelPropagation = new Panel();
		Layout.add(this.dynamicSettingsContainer, panelPropagation, 0, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(panelPropagation, new JLabel(PROBA_PROPAGATION + " :"), 0, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelPropagation, this.fieldProbaPropagation, 1, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelPropagation, this.fieldProbaPropagation1, 2, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelPropagation, this.fieldProbaPropagation2, 3, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelPropagation, this.buttonProbaPropagation, 4, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelPropagation, this.checkBoxProbaPropagation, 5, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Layout.add(this.dynamicSettingsContainer, this.checkBoxParticlesPreserving, 0, 2, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		// Action listeners :
		
		this.buttonProbaInteraction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewLatticeGasCA.this.sendProbaInteraction();
			}
			
		});
		
		this.buttonProbaPropagation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewLatticeGasCA.this.sendProbaPropagation();
			}
			
		});
		
		this.checkBoxParticlesPreserving.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewLatticeGasCA.this.sendParticlesConserving();
			}
			
		});
		
		this.checkBoxProbaInteraction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewLatticeGasCA.this.updateVariationComponents(ViewLatticeGasCA.this.checkBoxProbaInteraction);
			}
			
		});
		
		this.checkBoxProbaPropagation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewLatticeGasCA.this.updateVariationComponents(ViewLatticeGasCA.this.checkBoxProbaPropagation);
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
					if(ViewLatticeGasCA.this.isInStaticMod()) {
						if(e.getSource() == ViewLatticeGasCA.this.fieldProbaInteraction || e.getSource() == ViewLatticeGasCA.this.fieldProbaInteraction1 || e.getSource() == ViewLatticeGasCA.this.fieldProbaInteraction2 || e.getSource() == ViewLatticeGasCA.this.fieldProbaPropagation || e.getSource() == ViewLatticeGasCA.this.fieldProbaPropagation1 || e.getSource() == ViewLatticeGasCA.this.fieldProbaPropagation2) {
							ViewLatticeGasCA.this.sendInitSettings();
						}
					}
					else {
						if(e.getSource() == ViewLatticeGasCA.this.fieldProbaInteraction) {
							ViewLatticeGasCA.this.sendProbaInteraction();
						}
						else if(e.getSource() == ViewLatticeGasCA.this.fieldProbaPropagation) {
							ViewLatticeGasCA.this.sendProbaPropagation();
						}
					}
				}
			}
			
		};
		
		this.fieldProbaInteraction.addKeyListener(keyListener);
		this.fieldProbaInteraction1.addKeyListener(keyListener);
		this.fieldProbaInteraction2.addKeyListener(keyListener);
		this.fieldProbaPropagation.addKeyListener(keyListener);
		this.fieldProbaPropagation1.addKeyListener(keyListener);
		this.fieldProbaPropagation2.addKeyListener(keyListener);
	}
	
	@Override
	protected void updateToolTips() {
		super.updateToolTips();
		this.fieldProbaInteraction.setToolTipText("Actual value : " + this.ca.getProbaInteraction());
		this.fieldProbaInteraction1.setToolTipText("Actual value : " + this.ca.getProbaInteraction1());
		this.fieldProbaInteraction2.setToolTipText("Actual value : " + this.ca.getProbaInteraction2());
		this.fieldProbaPropagation.setToolTipText("Actual value : " + this.ca.getProbaPropagation());
		this.fieldProbaPropagation1.setToolTipText("Actual value : " + this.ca.getProbaPropagation1());
		this.fieldProbaPropagation2.setToolTipText("Actual value : " + this.ca.getProbaPropagation2());
	}
	
	@Override
	protected void updateDynamicComponents() {
		super.updateDynamicComponents();
		this.buttonProbaInteraction.setVisible(!this.isInStaticMod());
		this.buttonProbaPropagation.setVisible(!this.isInStaticMod());
		this.checkBoxProbaInteraction.setVisible(this.isInStaticMod());
		this.checkBoxProbaPropagation.setVisible(this.isInStaticMod());
	}
	
	@Override
	protected void update2ndFields() {
		boolean varyProbaInteraction = this.isInStaticMod() && this.checkBoxProbaInteraction.isSelected();
		boolean varyProbaPropagation = this.isInStaticMod() && this.checkBoxProbaPropagation.isSelected();
		
		this.fieldProbaInteraction.setVisible(!varyProbaInteraction);
		this.fieldProbaInteraction1.setVisible(varyProbaInteraction);
		this.fieldProbaInteraction2.setVisible(varyProbaInteraction);
		
		this.fieldProbaPropagation.setVisible(!varyProbaPropagation);
		this.fieldProbaPropagation1.setVisible(varyProbaPropagation);
		this.fieldProbaPropagation2.setVisible(varyProbaPropagation);
		
		super.update2ndFields();
	}
	
	protected double getFieldProbaInteraction() {
		try {
			if(!this.isInStaticMod() || !this.getFieldVaryInteractionProba()) {
				return Double.parseDouble(this.fieldProbaInteraction.getText());
			}
			else {
				return this.ca.getProbaInteraction();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_INTERACTION + " must be a real number.");
		}
	}
	
	protected double getFieldProbaInteraction1() {
		try {
			if(this.isInStaticMod() && this.getFieldVaryInteractionProba()) {
				return Double.parseDouble(this.fieldProbaInteraction1.getText());
			}
			else {
				return this.ca.getProbaInteraction1();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_INTERACTION + " must be a real number.");
		}
	}
	
	protected double getFieldProbaInteraction2() {
		try {
			if(this.isInStaticMod() && this.getFieldVaryInteractionProba()) {
				return Double.parseDouble(this.fieldProbaInteraction2.getText());
			}
			else {
				return this.ca.getProbaInteraction2();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_INTERACTION + " must be a real number.");
		}
	}
	
	protected double getFieldProbaPropagation() {
		try {
			if(!this.isInStaticMod() || !this.getFieldVaryPropagationProba()) {
				return Double.parseDouble(this.fieldProbaPropagation.getText());
			}
			else {
				return this.ca.getProbaPropagation();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_PROPAGATION + " must be a real number.");
		}
	}
	
	protected double getFieldProbaPropagation1() {
		try {
			if(this.isInStaticMod() && this.getFieldVaryPropagationProba()) {
				return Double.parseDouble(this.fieldProbaPropagation1.getText());
			}
			else {
				return this.ca.getProbaPropagation1();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_PROPAGATION + " must be a real number.");
		}
	}
	
	protected double getFieldProbaPropagation2() {
		try {
			if(this.isInStaticMod() && this.getFieldVaryPropagationProba()) {
				return Double.parseDouble(this.fieldProbaPropagation2.getText());
			}
			else {
				return this.ca.getProbaPropagation2();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_PROPAGATION + " must be a real number.");
		}
	}
	
	protected boolean getFieldParticlesPreserving() {
		return this.checkBoxParticlesPreserving.isSelected();
	}
	
	protected boolean getFieldVaryInteractionProba() {
		return this.checkBoxProbaInteraction.isSelected();
	}
	
	protected boolean getFieldVaryPropagationProba() {
		return this.checkBoxProbaPropagation.isSelected();
	}
	
	protected void sendProbaInteraction() {
		try {
			double probaInteraction = this.getFieldProbaInteraction();
			this.ca.changeProbaInteraction(probaInteraction);
			this.updateToolTips();
		}
		catch(IllegalArgumentException argh) {
			this.gui.warning(argh.getMessage(), "Invalid value");
		}
	}
	
	protected void sendProbaPropagation() {
		try {
			double probaPropagation = this.getFieldProbaPropagation();
			this.ca.changeProbaPropagation(probaPropagation);
			this.updateToolTips();
		}
		catch(IllegalArgumentException argh) {
			this.gui.warning(argh.getMessage(), "Invalid value");
		}
	}
	
	protected void sendParticlesConserving() {
		boolean particlesPreserving = this.getFieldParticlesPreserving();
		this.ca.changeParticlesPreserving(particlesPreserving);
	}
	
	@Override
	protected boolean sendDynamicSettings() {
		try {
			double probaInteraction = this.getFieldProbaInteraction();
			double probaInteraction1 = this.getFieldProbaInteraction1();
			double probaInteraction2 = this.getFieldProbaInteraction2();
			double probaPropagation = this.getFieldProbaPropagation();
			double probaPropagation1 = this.getFieldProbaPropagation1();
			double probaPropagation2 = this.getFieldProbaPropagation2();
			boolean particlesPreserving = this.getFieldParticlesPreserving();
			boolean varyInteractionProba = this.getFieldVaryInteractionProba();
			boolean varyPropagationProba = this.getFieldVaryPropagationProba();
			this.ca.changeDynamicSettings(probaInteraction, probaPropagation, particlesPreserving, varyInteractionProba, varyPropagationProba, probaInteraction1, probaInteraction2, probaPropagation1, probaPropagation2);
			return super.sendDynamicSettings();
		}
		catch(IllegalArgumentException argh) {
			this.gui.warning(argh.getMessage(), "Invalid value");
		}
		return false;
	}
	
	@Override
	protected LinkedList<String> notValidatedFields() {
		LinkedList<String> list = super.notValidatedFields();
		
		if(this.isInStaticMod()) {
			if(this.getFieldVaryInteractionProba()) {
				if(this.getFieldProbaInteraction1() != this.ca.getProbaInteraction1() || this.getFieldProbaInteraction2() != this.ca.getProbaInteraction2()) {
					list.add(PROBA_INTERACTION);
				}
			}
			else {
				if(this.getFieldProbaInteraction() != this.ca.getProbaInteraction()) {
					list.add(PROBA_INTERACTION);
				}
			}
			
			if(this.getFieldVaryPropagationProba()) {
				if(this.getFieldProbaPropagation1() != this.ca.getProbaPropagation1() || this.getFieldProbaPropagation2() != this.ca.getProbaPropagation2()) {
					list.add(PROBA_PROPAGATION);
				}
			}
			else {
				if(this.getFieldProbaPropagation() != this.ca.getProbaPropagation()) {
					list.add(PROBA_PROPAGATION);
				}
			}
		}
		
		return list;
	}
	
}
