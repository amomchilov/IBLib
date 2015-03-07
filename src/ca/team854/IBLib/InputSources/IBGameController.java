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
public abstract class IBGameController extends IBJoystick {

	public static int LeftThumbstickXAxis;
	public static int LeftThumbstickYAxis;
	public static int RightThumbstickXAxis;
	public static int RightThumbstickYAxis;
	
	public static int LeftShoulderButton;
	public static int RightShoulderButton;
	public static int LeftTriggerButton;
	public static int RightTriggerButton;
	public static int SelectButton;
	public static int StartButton;
	
	
	public IBGameController() {
		this(1);
	}
	
	public IBGameController(int port) {
		super(port);
	}
	
	public double getLeftStickX() {
		return getAxis(LeftThumbstickXAxis);
	}
	
	public double getLeftStickY() {
		return -getAxis(LeftThumbstickYAxis);
	}
	
	public double getRightStickX() {
		return getAxis(RightThumbstickXAxis);
	}
	
	public double getRightStickY() {
		return -getAxis(RightThumbstickYAxis);
	}
	
	public boolean getDPad(IBDirection direction) {
		return super.getDPad(0, direction);
	}
	
	public boolean getDPad_quadrant(IBDirection direction) {
		return super.getDPad_quadrant(0, direction);
	}
	
	
	public boolean getTriggerLeft() { return getRawButton(LeftTriggerButton); }
	public boolean getTriggerRight() { return getRawButton(RightTriggerButton); }
	public boolean getShoulderLeft() { return getRawButton(LeftShoulderButton); }
	public boolean getShoulderRight() { return getRawButton(RightShoulderButton); }
	
	public boolean getSelectButton() { return getRawButton(SelectButton); }
	public boolean getStartButton() { return getRawButton(StartButton); }
}
