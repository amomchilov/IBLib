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
public class IBPS3Controller extends IBGameController {

	public IBPS3Controller() {
		 this(1);
	}

	public IBPS3Controller(int port) {
		super(port);
		LeftThumbstickXAxis = 1;
		LeftThumbstickYAxis = 2;
		RightThumbstickXAxis = 3;
		RightThumbstickYAxis = 4;
		DPadXAxis = 5;
		DPadYAxis = 6;
		
		LeftShoulderButton = 5;
		RightShoulderButton = 6;
		LeftTriggerButton = 7;
		RightTriggerButton = 8;
		SelectButton = 9;
		StartButton = 10;
	}
	
	public boolean getTriangleButton() { return getRawButton(4); }
	public boolean getCircleButton() { return getRawButton(3); }
	public boolean getSquareButton() { return getRawButton(1); }
	public boolean getXButton() { return getRawButton(2); }
}
