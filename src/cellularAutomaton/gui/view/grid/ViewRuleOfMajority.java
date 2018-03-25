package cellularAutomaton.gui.view.grid;

import cellularAutomaton.core.binaryCA.RuleOfMajority;
import cellularAutomaton.core.enums.ActiveCA;
import cellularAutomaton.gui.Gui;

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
public class ViewRuleOfMajority extends ViewBinaryCA<RuleOfMajority> {
	
	// Constants :
	
	private static final long serialVersionUID = 1L;
	
	// Constructors :
	
	public ViewRuleOfMajority(Gui gui, RuleOfMajority ca) {
		super(gui, ca);
	}
	
	// Methods :
	
	@Override
	protected boolean isDisplayed() {
		return this.gui.getModele().getActiveCA() == ActiveCA.RULE_OF_MAJORITY;
	}
	
}
