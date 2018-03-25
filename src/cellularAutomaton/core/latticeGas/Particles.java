package cellularAutomaton.core.latticeGas;

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
public class Particles {
	
	// Fields :
	
	private boolean top;
	private boolean bottom;
	private boolean right;
	private boolean left;
	
	private boolean move;
	private boolean moveTop;
	private boolean moveBottom;
	private boolean moveRight;
	private boolean moveLeft;
	
	// Constructors :
	
	public Particles(boolean top, boolean bottom, boolean right, boolean left) {
		this(top, bottom, right, left, true, true, true, true, true);
	}
	
	private Particles(boolean top, boolean bottom, boolean right, boolean left, boolean move, boolean moveTop, boolean moveBottom, boolean moveRight, boolean moveLeft) {
		this.setTop(top);
		this.setBottom(bottom);
		this.setRight(right);
		this.setLeft(left);
		this.setMove(move);
		this.setMoveTop(moveTop);
		this.setMoveBottom(moveBottom);
		this.setMoveRight(moveRight);
		this.setMoveLeft(moveLeft);
	}
	
	@Override
	public Particles clone() {
		return new Particles(this.top, this.bottom, this.right, this.left, this.move, this.moveTop, this.moveBottom, this.moveRight, this.moveLeft);
	}
	
	// Getters :
	
	public boolean isTop() {
		return this.top;
	}
	
	public boolean isBottom() {
		return this.bottom;
	}
	
	public boolean isRight() {
		return this.right;
	}
	
	public boolean isLeft() {
		return this.left;
	}
	
	public boolean isMove() {
		return this.move;
	}
	
	public boolean isMoveTop() {
		return this.moveTop;
	}
	
	public boolean isMoveBottom() {
		return this.moveBottom;
	}
	
	public boolean isMoveRight() {
		return this.moveRight;
	}
	
	public boolean isMoveLeft() {
		return this.moveLeft;
	}
	
	// Setters :
	
	protected void setTop(boolean top) {
		this.top = top;
	}
	
	protected void setBottom(boolean bottom) {
		this.bottom = bottom;
	}
	
	protected void setRight(boolean right) {
		this.right = right;
	}
	
	protected void setLeft(boolean left) {
		this.left = left;
	}
	
	protected void setMove(boolean move) {
		this.move = move;
		this.setMoveTop(move);
		this.setMoveBottom(move);
		this.setMoveRight(move);
		this.setMoveLeft(move);
	}
	
	protected void setMoveTop(boolean moveTop) {
		this.moveTop = moveTop;
	}
	
	protected void setMoveBottom(boolean moveBottom) {
		this.moveBottom = moveBottom;
	}
	
	protected void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}
	
	protected void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}
	
	// Methods :
	
	public int getNbParticles() {
		int nbParticles = 0;
		
		if(this.isTop()) {
			nbParticles++;
		}
		
		if(this.isBottom()) {
			nbParticles++;
		}
		
		if(this.isRight()) {
			nbParticles++;
		}
		
		if(this.isLeft()) {
			nbParticles++;
		}
		
		return nbParticles;
	}
	
	public Flux getFlux() {
		Flux flux = new Flux();
		
		if(this.isTop()) {
			flux.y++;
		}
		
		if(this.isBottom()) {
			flux.y--;
		}
		
		if(this.isRight()) {
			flux.x++;
		}
		
		if(this.isLeft()) {
			flux.x--;
		}
		
		return flux;
	}
	
}
