/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.team854.IBLib.InputSources;

import ca.team854.IBLib.Enums.IBDirection;

/**
 *
 * @author Alexander
 */
public class IBLogitechExtreme3DPro extends IBJoystick {
	
	public IBLogitechExtreme3DPro() {
		this(0);
	}
	
	public IBLogitechExtreme3DPro(int port) {
		super(port);
		AXIS_X = 1;
		AXIS_Y = 2;
		AXIS_Z = 3;
	}
	
	public boolean getHatSwitch (IBDirection direction) {
		return super.getDPad(0, direction);
	}
	
	public boolean getHatSwitch_quadrant(IBDirection direction) {
		return super.getDPad_quadrant(0, direction);
	}
	

	public boolean getThumbButton() { return getRawButton(2); }
	public double getThrottle() { return -super.getAxis(4); }
}
