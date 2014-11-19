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
public class IBXBox360Controller extends IBGameController {

	public IBXBox360Controller() {
		this(1);
	}
	
	public IBXBox360Controller(int port) {
		super(port);
		LeftThumbstickXAxis = 5;
		LeftThumbstickYAxis = 6;
		RightThumbstickXAxis = 3;
		RightThumbstickYAxis = 4;
		DPadXAxis = 1;
		DPadYAxis = 2;
		
		LeftShoulderButton = 5;
		RightShoulderButton = 6;
		LeftTriggerButton = 7;
		RightTriggerButton = 8;
		SelectButton = 9;
		StartButton = 10;
	}
	
	public boolean getAButton() { return getRawButton(2); }
	public boolean getBButton() { return getRawButton(3); }
	public boolean getXButton() { return getRawButton(1); }
	public boolean getYButton() { return getRawButton(4); }
}
