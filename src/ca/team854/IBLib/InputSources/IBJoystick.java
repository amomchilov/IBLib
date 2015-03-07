/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.team854.IBLib.InputSources;

/**
 *
 * @author Alexander
 */
public abstract class IBJoystick extends IBHID {

	public static int AXIS_X;
	public static int AXIS_Y;
	public static int AXIS_Z;
	
	public IBJoystick() {
		this(0);
	}
	
	public IBJoystick(int port) {
		super(port);
	}

	public double getXAxis() { return super.getAxis(AXIS_X); }	
	public double getYAxis() { return -super.getAxis(AXIS_Y); }	
	public double getZAxis() { return super.getAxis(AXIS_Z); }	
}
