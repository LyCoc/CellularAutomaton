package cellularAutomaton.gui.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
public class Layout {
	
	// Constants :
	
	public static final int CENTER = GridBagConstraints.CENTER;
	public static final int NORTH = GridBagConstraints.NORTH;
	public static final int SOUTH = GridBagConstraints.SOUTH;
	public static final int EAST = GridBagConstraints.EAST;
	public static final int WEST = GridBagConstraints.WEST;
	public static final int NORTHEAST = GridBagConstraints.NORTHEAST;
	public static final int NORTHWEST = GridBagConstraints.NORTHWEST;
	public static final int SOUTHEAST = GridBagConstraints.SOUTHEAST;
	public static final int SOUTHWEST = GridBagConstraints.SOUTHWEST;
	
	public static final int NONE = GridBagConstraints.NONE;
	public static final int HORIZONTAL = GridBagConstraints.HORIZONTAL;
	public static final int VERTICAL = GridBagConstraints.VERTICAL;
	public static final int BOTH = GridBagConstraints.BOTH;
	
	// Metods :
	
	public static void add(Container container, Component component, int xPos, int yPos, double xWeight, double yWeight, int Position, int Extension, int marges) {
		container.add(component, new GridBagConstraints(xPos, yPos, 1, 1, xWeight, yWeight, Position, Extension, new Insets(marges, marges, marges, marges), 0, 0));
	}
	
	public static void add(Container container, Component component, int xPos, int yPos, int xGrid, int yGrid, double xWeight, double yWeight, int Position, int Extension, int marges) {
		container.add(component, new GridBagConstraints(xPos, yPos, xGrid, yGrid, xWeight, yWeight, Position, Extension, new Insets(marges, marges, marges, marges), 0, 0));
	}
	
	public static void add(Container container, Component component, int xPos, int yPos, double xWeight, double yWeight, int Position, int Extension, int top, int left, int bottom, int right) {
		container.add(component, new GridBagConstraints(xPos, yPos, 1, 1, xWeight, yWeight, Position, Extension, new Insets(top, left, bottom, right), 0, 0));
	}
	
	public static void add(Container container, Component component, int xPos, int yPos, int xGrid, int yGrid, double xWeight, double yWeight, int Position, int Extension, int top, int left, int bottom, int right) {
		container.add(component, new GridBagConstraints(xPos, yPos, xGrid, yGrid, xWeight, yWeight, Position, Extension, new Insets(top, left, bottom, right), 0, 0));
	}
	
}
