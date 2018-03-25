package cellularAutomaton.gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import cellularAutomaton.core.Modele;
import cellularAutomaton.core.binaryCA.GameOfLife;
import cellularAutomaton.core.binaryCA.RuleOfMajority;
import cellularAutomaton.core.binaryCA.RuleOfMinority;
import cellularAutomaton.core.binaryCA.RuleOfParity;
import cellularAutomaton.core.enums.ActiveCA;
import cellularAutomaton.core.latticeGas.Swarming;
import cellularAutomaton.gui.view.Layout;
import cellularAutomaton.gui.view.Panel;
import cellularAutomaton.gui.view.View;
import cellularAutomaton.gui.view.grid.ViewGameOfLife;
import cellularAutomaton.gui.view.grid.ViewRuleOfMajority;
import cellularAutomaton.gui.view.grid.ViewRuleOfMinority;
import cellularAutomaton.gui.view.grid.ViewRuleOfParity;
import cellularAutomaton.gui.view.grid.ViewSwarming;

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
public class Gui extends JFrame implements Observer {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Cellular Automata";
	
	// Fields :
	
	private Modele modele = new Modele();
	
	// Constructors :
	
	public Gui() {
		super(TITLE);
		this.getContentPane().setLayout(new GridBagLayout());
		this.modele.addObserver(this);
		
		// Views :
		
		Panel panelCAs = new Panel();
		Layout.add(this, panelCAs, 0, 0, 1, 1, Layout.CENTER, Layout.BOTH, 2);
		
		LinkedList<View> viewList = new LinkedList<View>();
		
		viewList.add(new ViewGameOfLife(this, new GameOfLife()));
		viewList.add(new ViewRuleOfMajority(this, new RuleOfMajority()));
		viewList.add(new ViewRuleOfMinority(this, new RuleOfMinority()));
		viewList.add(new ViewRuleOfParity(this, new RuleOfParity()));
		viewList.add(new ViewSwarming(this, new Swarming()));
		
		int i = 0;
		for(View view : viewList) {
			Layout.add(panelCAs, view, 0, i, 1, 1, Layout.CENTER, Layout.BOTH, 2);
			i++;
		}
		
		// Menu bar :
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		// File menu :
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		JMenuItem exitMenuItem = new JMenuItem(new AbstractAction() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Gui.this.exit();
				
			}
			
		});
		
		exitMenuItem.setText("Exit");
		fileMenu.add(exitMenuItem);
		
		// CA menu :
		
		JMenu caMenu = new JMenu("Cellular Automata");
		caMenu.setMnemonic(KeyEvent.VK_C);
		menuBar.add(caMenu);
		
		for(ActiveCA ca : ActiveCA.values()) {
			caMenu.add(this.createMenuItem(ca));
		}
		
		// First update :
		
		this.getModele().notifyObservers();
		
		// Confirmation when the user close the application :
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent pwet) {
				Gui.this.exit();
			}
			
		});
		
		// Frame settings :
		
		//this.setIconImage(Toolkit.getDefaultToolkit().getImage(FICHIER_ICON_PROGRAMME));
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setMinimumSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.65)));
		this.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.65)));
		this.pack();
		this.setLocation((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2), (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2));
		this.setVisible(true);
	}
	
	// Getters :
	
	public Modele getModele() {
		return this.modele;
	}
	
	// Methods :
	
	@Override
	public void update(Observable obs, Object obj) {
		this.setTitle(TITLE + " - " + this.getModele().getActiveCA().toString());
	}
	
	public void exit() {
		int answer = this.options("Exit ?", "Confirm exit", 0, "Exit", "Cancel");
		if(answer == 0) {
			System.exit(0);
		}
	}
	
	public void error(String message, String title) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public void warning(String message, String title) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public void info(String message, String title) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public int options(String question, String title, int defaultChoise, String... options) {
		return JOptionPane.showOptionDialog(this, question, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[defaultChoise]);
	}
	
	protected JMenuItem createMenuItem(final ActiveCA ca) {
		JMenuItem menuItem = new JMenuItem(new AbstractAction() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Gui.this.getModele().setActiveCA(ca);
				Gui.this.getModele().notifyObservers();
			}
			
		});
		
		menuItem.setText(ca.toString());
		return menuItem;
	}
	
	// Main :
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception argh) {
			argh.printStackTrace();
		}
		new Gui();
	}
	
}
