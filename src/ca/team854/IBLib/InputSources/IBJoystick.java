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
public class IBJoystick extends IBHID {
	public IBJoystick() {
		this(1);
	}
	
	public IBJoystick(int port) {
		super(port);
	}
}
