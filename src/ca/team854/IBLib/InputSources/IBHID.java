package ca.team854.IBLib.InputSources;

import ca.team854.IBLib.Enums.IBDirection;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Alexander
 */
public class IBHID extends Joystick {

	public class IBAxisData {
		public double multiplier;
		public double deadzone;
		public double linearSlope;
		public double linearOffset;
		
		public IBAxisData(double multiplier, double deadzone, double linearSlope, double linearOffset) {
			this.multiplier = multiplier;
			this.deadzone = deadzone;
			this.linearSlope = linearSlope;
			this.linearOffset = linearOffset;
		}
	}
	
	IBAxisData[] axisData;
	
	double[] axisMultipliers;
	double[] axisDeadzones;
	
	public IBHID() {
		this(0);
	}
	
	public IBHID(int port) {
		super(port);
		double multiplier = 1;
		double deadzone = 0.2;
		double linearSlope = 1 / (1 - deadzone);
		double linearOffset = deadzone / (deadzone - 1);
		axisData = new IBAxisData[]{
				new IBAxisData(multiplier, deadzone, linearSlope, linearOffset),
				new IBAxisData(multiplier, deadzone, linearSlope, linearOffset),
				new IBAxisData(multiplier, deadzone, linearSlope, linearOffset),
				new IBAxisData(multiplier, deadzone, linearSlope, linearOffset),
				new IBAxisData(multiplier, deadzone, linearSlope, linearOffset),
				new IBAxisData(multiplier, deadzone, linearSlope, linearOffset)};
	}

	public void setAxisMultipliers(double[] axisMultiplier) {
		for (int i = 0; i < 6; i++) this.setAxisMultiplier(i, axisMultiplier[i]);
	}
	
	public void setAxisDeadzones(double[] axisDeadzones) {
		for (int i = 0; i < 6; i++) this.setAxisDeadzone(i, axisDeadzones[i]);
	}
	
	public void setAxisMultiplier(int axis, double multiplier) {
		axisData[axis].multiplier = multiplier;
	}
	
	public void setAxisDeadzone(int axis, double deadzone) {
		IBAxisData ad = axisData[axis];
		ad.deadzone = deadzone;
		ad.linearSlope = 1 / (1 - deadzone);
		ad.linearOffset = deadzone / (deadzone - 1);
	}
	
	public double getAxis(int axis) {
//		double returnValue = super.getRawAxis(axis) * axisMultipliers[axis];
////		if (returnValue < -axisDeadzones[axis] || axisDeadzones[axis] < returnValue)
//		if (-axisDeadzones[axis] < returnValue && returnValue < axisDeadzones[axis]) return 0;
//		return returnValue;
		IBAxisData ad = axisData[axis];
		double input = super.getRawAxis(axis) * ad.multiplier;
		double deadzone = ad.deadzone;
		double slope = ad.linearSlope;
		double offset = ad.linearOffset;
		if (input <= -deadzone) return input * slope - offset; //interval [-1, DeadBand]
		else if (deadzone <= input) return input * slope + offset; //interval [DeadBand, 1]
		else return 0; //intveral (deadband, deadband)
	}
	

	public boolean getDPad (int pov, IBDirection direction) {
		int povValue = getPOV(0);
		switch (direction) {
			case N : return povValue ==   0;
			case NE: return povValue ==  45;
			case E : return povValue ==  90;
			case SE: return povValue == 135;
			case S : return povValue == 180;
			case SW: return povValue == 225;
			case W : return povValue == 270;
			case NW: return povValue == 315;
			default: return false;  //why the fuck is java asking me for a default value for an enum switch?
		}
	}
	
	public boolean getDPad_quadrant(int pov, IBDirection direction) {
		int povValue = getPOV(0);
		switch (direction) {
			case N : return povValue == 315 || povValue ==   0 || povValue ==  45;
			case NE: return povValue ==   0 || povValue ==  45 || povValue ==  90;
			case E : return povValue ==  45 || povValue ==  90 || povValue == 135;
			case SE: return povValue ==  90 || povValue == 135 || povValue == 180;
			case S : return povValue == 135 || povValue == 180 || povValue == 225;
			case SW: return povValue == 180 || povValue == 225 || povValue == 270;
			case W : return povValue == 225 || povValue == 270 || povValue == 315;
			case NW: return povValue == 315 || povValue == 315 || povValue ==   0;
			default: return false; //why the fuck is java asking me for a default value for an enum switch?
		}
	}
	
	public void setRumble(double value) {
		super.setRumble(RumbleType.kLeftRumble, (float) value);
		super.setRumble(RumbleType.kRightRumble, (float) value);
	}
	
	public boolean getRawButton(int button) {
		if (button <= 0) DriverStation.reportError("Button <= 0\n", true);
		return super.getRawButton(button);
	}
}
