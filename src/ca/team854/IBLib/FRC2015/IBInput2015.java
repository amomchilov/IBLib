package ca.team854.IBLib.FRC2015;

import ca.team854.IBLib.FRC2015.Forklift.IBForkliftLowerToBottom;
import ca.team854.IBLib.FRC2015.Forklift.IBForkliftRaiseToTop;
import ca.team854.IBLib.FRC2015.Forklift.IBForkliftStop;
import ca.team854.IBLib.InputSources.IBHID;
import ca.team854.IBLib.InputSources.IBLogitechExtreme3DPro;
import ca.team854.IBLib.InputSources.IBPS3Controller;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class IBInput2015 {
	
	IBHID input = new IBHID();
	
	JoystickButton squareButton = new JoystickButton(input, IBPS3Controller.SquareButton);
	JoystickButton triangleButton = new JoystickButton(input, IBPS3Controller.TriangleButton);
	JoystickButton circleButton = new JoystickButton(input, IBPS3Controller.CircleButton);
	JoystickButton XButton = new JoystickButton(input, IBPS3Controller.XButton);
	
	int driveAxisX;
	int driveAxisY;
	int raiseForkliftButton;
	int lowerForkliftButton;
	
	IBInput2015() {
		driveAxisX = IBPS3Controller.RightThumbstickXAxis;
		driveAxisY = IBPS3Controller.LeftThumbstickYAxis;
		raiseForkliftButton = IBPS3Controller.RightTriggerButton;
		lowerForkliftButton = IBPS3Controller.LeftTriggerButton;
		
		input.setAxisMultiplier(IBPS3Controller.RightThumbstickXAxis, 0.7);
		
		squareButton.whenPressed(new IBForkliftLowerToBottom());
		triangleButton.whenPressed(new IBForkliftRaiseToTop());
		XButton.whenPressed(new IBForkliftStop());
//		circleButton.whenPressed(new UpdateFrontCamera());
	}
	
	public IBHID getInputDevice() {
		return input;
	}
	
	public void setJoystickActive() {
		driveAxisX = IBLogitechExtreme3DPro.AXIS_X;
		driveAxisY = IBLogitechExtreme3DPro.AXIS_Y;
		raiseForkliftButton = 5;
		lowerForkliftButton = 3;
		
		input.setAxisMultiplier(IBPS3Controller.RightThumbstickXAxis, 1.0);
	}
	
	public void setControllerActive() {
		
	}

	public double getDriveX() {
		return input.getAxis(driveAxisX);
	}
	
	public double getDriveY() {
		return input.getAxis(driveAxisY);
	}
	
	public boolean getShouldRaiseForklift() {
		return input.getRawButton(raiseForkliftButton);
	}
	
	public boolean getShouldLowerForklift() {
		return input.getRawButton(lowerForkliftButton);
	}
}
