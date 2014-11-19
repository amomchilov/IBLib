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
public class IBLogitechExtreme3DPro extends IBHID {
	
	public IBLogitechExtreme3DPro() {
		this(1);
	}
	
	public IBLogitechExtreme3DPro(int port) {
		super(port);
	}
	
	public boolean getHatSwitch (IBDirection direction) {
		return super.getDPad(5, 6, direction);
	}
	
	public boolean getThumbButton() { return getRawButton(2); }
	public double getXAxis() { return super.getAxis(1); }	
	public double getYAxis() { return -super.getAxis(2); }	
	public double getZAxis() { return super.getAxis(3); }	
	public double getThrottle() { return -super.getAxis(4); }
}
