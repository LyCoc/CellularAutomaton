package cellularAutomaton.gui.view.grid;

import static cellularAutomaton.core.binaryCA.BinaryCA.LOOP_ON_EDGES;
import static cellularAutomaton.core.binaryCA.BinaryCA.PROBA_UPDATE;

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

import cellularAutomaton.core.binaryCA.BinaryCA;
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
public abstract class ViewBinaryCA<T extends BinaryCA> extends ViewCA<T> {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	
	// Fields :
	
	protected JButton buttonProbaUpdate;
	
	protected JTextField fieldProbaUpdate;
	protected JTextField fieldProbaUpdate1;
	protected JTextField fieldProbaUpdate2;
	
	protected JCheckBox checkBoxLoop;
	protected JCheckBox checkBoxProbaUpdate;
	
	// Constructors :
	
	protected ViewBinaryCA(Gui gui, T ca) {
		super(gui, ca);
	}
	
	// Methods :
	
	@Override
	protected void updateGrid() {
		Graphics2D graphics = this.image.createGraphics();
		
		for(int i = 0; i < this.ca.getHeight(); i++) {
			for(int j = 0; j < this.ca.getWidth(); j++) {
				Boolean cell = this.ca.getTab()[i][j];
				
				if(cell == null) {
					continue;
				}
				
				graphics.setColor(cell.booleanValue() ? Color.BLUE : Color.WHITE);
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
		
		this.buttonProbaUpdate = new JButton("Apply");
		
		this.fieldProbaUpdate = new JTextField(String.valueOf(this.ca.getProbaUpdate()), 5);
		this.fieldProbaUpdate1 = new JTextField(String.valueOf(this.ca.getProbaUpdate1()), 5);
		this.fieldProbaUpdate2 = new JTextField(String.valueOf(this.ca.getProbaUpdate2()), 5);
		
		this.fieldProbaUpdate.setMinimumSize(this.fieldProbaUpdate.getPreferredSize());
		this.fieldProbaUpdate1.setMinimumSize(this.fieldProbaUpdate1.getPreferredSize());
		this.fieldProbaUpdate2.setMinimumSize(this.fieldProbaUpdate2.getPreferredSize());
		
		this.checkBoxLoop = new JCheckBox(LOOP_ON_EDGES, this.ca.isLoop());
		this.checkBoxProbaUpdate = new JCheckBox("", this.ca.isVaryProbaUpdate());
		
		this.fieldProbaUpdate1.setVisible(false);
		this.fieldProbaUpdate2.setVisible(false);
		this.checkBoxProbaUpdate.setVisible(false);
		
		// Components registration :
		
		this.settingsComponents.add(this.buttonProbaUpdate);
		this.settingsComponents.add(this.fieldProbaUpdate);
		this.settingsComponents.add(this.fieldProbaUpdate1);
		this.settingsComponents.add(this.fieldProbaUpdate2);
		this.settingsComponents.add(this.checkBoxLoop);
		this.settingsComponents.add(this.checkBoxProbaUpdate);
		
		this.variationCheckBoxes.add(this.checkBoxProbaUpdate);
		
		// Dynamic settings :
		
		Panel panelUpdate = new Panel();
		Layout.add(this.dynamicSettingsContainer, panelUpdate, 0, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 0);
		
		Layout.add(panelUpdate, new JLabel(PROBA_UPDATE + " :"), 0, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelUpdate, this.fieldProbaUpdate, 1, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelUpdate, this.fieldProbaUpdate1, 2, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelUpdate, this.fieldProbaUpdate2, 3, 0, 0.1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelUpdate, this.buttonProbaUpdate, 4, 0, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		Layout.add(panelUpdate, this.checkBoxProbaUpdate, 5, 0, 0, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		Layout.add(this.dynamicSettingsContainer, this.checkBoxLoop, 0, 1, 1, 0, Layout.CENTER, Layout.HORIZONTAL, 2);
		
		// Action listeners :
		
		this.buttonProbaUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewBinaryCA.this.sendProbaUpdate();
			}
			
		});
		
		this.checkBoxLoop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewBinaryCA.this.sendLoopOnEdge();
			}
			
		});
		
		this.checkBoxProbaUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewBinaryCA.this.updateVariationComponents(ViewBinaryCA.this.checkBoxProbaUpdate);
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
					if(ViewBinaryCA.this.isInStaticMod()) {
						if(e.getSource() == ViewBinaryCA.this.fieldProbaUpdate || e.getSource() == ViewBinaryCA.this.fieldProbaUpdate1 || e.getSource() == ViewBinaryCA.this.fieldProbaUpdate2) {
							ViewBinaryCA.this.sendInitSettings();
						}
					}
					else {
						if(e.getSource() == ViewBinaryCA.this.fieldProbaUpdate) {
							ViewBinaryCA.this.sendProbaUpdate();
						}
					}
				}
			}
			
		};
		
		this.fieldProbaUpdate.addKeyListener(keyListener);
		this.fieldProbaUpdate1.addKeyListener(keyListener);
		this.fieldProbaUpdate2.addKeyListener(keyListener);
	}
	
	@Override
	protected void updateToolTips() {
		super.updateToolTips();
		this.fieldProbaUpdate.setToolTipText("Actual value : " + this.ca.getProbaUpdate());
		this.fieldProbaUpdate1.setToolTipText("Actual value : " + this.ca.getProbaUpdate1());
		this.fieldProbaUpdate2.setToolTipText("Actual value : " + this.ca.getProbaUpdate2());
	}
	
	@Override
	protected void updateDynamicComponents() {
		super.updateDynamicComponents();
		this.buttonProbaUpdate.setVisible(!this.isInStaticMod());
		this.checkBoxProbaUpdate.setVisible(this.isInStaticMod());
	}
	
	@Override
	protected void update2ndFields() {
		boolean varyProbaUpdate = this.isInStaticMod() && this.checkBoxProbaUpdate.isSelected();
		
		this.fieldProbaUpdate.setVisible(!varyProbaUpdate);
		this.fieldProbaUpdate1.setVisible(varyProbaUpdate);
		this.fieldProbaUpdate2.setVisible(varyProbaUpdate);
		
		super.update2ndFields();
	}
	
	protected double getFieldProbaUpdate() {
		try {
			if(!this.isInStaticMod() || !this.getFieldVaryUpdateProba()) {
				return Double.parseDouble(this.fieldProbaUpdate.getText());
			}
			else {
				return this.ca.getProbaUpdate();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_UPDATE + " must be a real number.");
		}
	}
	
	protected double getFieldProbaUpdate1() {
		try {
			if(this.isInStaticMod() && this.getFieldVaryUpdateProba()) {
				return Double.parseDouble(this.fieldProbaUpdate1.getText());
			}
			else {
				return this.ca.getProbaUpdate1();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_UPDATE + " must be a real number.");
		}
	}
	
	protected double getFieldProbaUpdate2() {
		try {
			if(this.isInStaticMod() && this.getFieldVaryUpdateProba()) {
				return Double.parseDouble(this.fieldProbaUpdate2.getText());
			}
			else {
				return this.ca.getProbaUpdate2();
			}
		}
		catch(NumberFormatException argh) {
			throw new IllegalArgumentException(PROBA_UPDATE + " must be a real number.");
		}
	}
	
	protected boolean getFieldLoop() {
		return this.checkBoxLoop.isSelected();
	}
	
	protected boolean getFieldVaryUpdateProba() {
		return this.checkBoxProbaUpdate.isSelected();
	}
	
	protected void sendProbaUpdate() {
		try {
			double probaUpdate = this.getFieldProbaUpdate();
			this.ca.changeProbaUpdate(probaUpdate);
			this.updateToolTips();
		}
		catch(IllegalArgumentException argh) {
			this.gui.warning(argh.getMessage(), "Invalid value");
		}
	}
	
	protected void sendLoopOnEdge() {
		boolean loop = this.getFieldLoop();
		this.ca.changeEdgeLoop(loop);
	}
	
	@Override
	protected boolean sendDynamicSettings() {
		try {
			double probaUpdate = this.getFieldProbaUpdate();
			double probaUpdate1 = this.getFieldProbaUpdate1();
			double probaUpdate2 = this.getFieldProbaUpdate2();
			boolean loop = this.getFieldLoop();
			boolean varyUpdateProba = this.getFieldVaryUpdateProba();
			this.ca.changeDynamicSettings(probaUpdate, loop, varyUpdateProba, probaUpdate1, probaUpdate2);
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
			if(this.getFieldVaryUpdateProba()) {
				if(this.getFieldProbaUpdate1() != this.ca.getProbaUpdate1() || this.getFieldProbaUpdate2() != this.ca.getProbaUpdate2()) {
					list.add(PROBA_UPDATE);
				}
			}
			else {
				if(this.getFieldProbaUpdate() != this.ca.getProbaUpdate()) {
					list.add(PROBA_UPDATE);
				}
			}
		}
		
		return list;
	}
	
}
