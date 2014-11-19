package ca.team854.IBLib.InputSources;

import ca.team854.IBLib.Enums.IBDirection;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Alexander
 */
public class IBHID extends Joystick {

	double[] axisMultipliers; //TODO
	double[] axisDeadzones;
	
	public IBHID() {
		this(1);
	}
	
	public IBHID(int port) {
		super(port);
		axisMultipliers = new double[]{1, 1, 1, 1, 1, 1};
		axisDeadzones = new double[]{0.2, 0.2, 0.2, 0.2, 0.2, 0.2};
	}

	public void setAxisMultipliers(double[] axisModifiers) {
		this.axisMultipliers = axisModifiers;
	}
	
	public void setAxisDeadzones(double[] axisDeadzones) {
		this.axisDeadzones = axisDeadzones;
	}
	
	public double getAxis(int axis) {
		double returnValue = super.getRawAxis(axis) * axisMultipliers[axis];
//		if (returnValue < -axisDeadzones[axis] || axisDeadzones[axis] < returnValue)
		if (-axisDeadzones[axis] < returnValue && returnValue < axisDeadzones[axis])
			return 0;
		return returnValue;
	}
	
	public boolean getDPad (int axisX, int axisY, IBDirection direction) {
		switch (direction.ordinal) {
			case IBDirection.NW_ordinal: return getRawAxis(axisX) == -1 && getRawAxis(axisY) == -1;
			case IBDirection.N_ordinal:  return getRawAxis(axisX) == 0  && getRawAxis(axisY) == -1;
			case IBDirection.NE_ordinal: return getRawAxis(axisX) == 1  && getRawAxis(axisY) == -1;
			case IBDirection.E_ordinal:  return getRawAxis(axisX) == 1  && getRawAxis(axisY) == 0;
			case IBDirection.SE_ordinal: return getRawAxis(axisX) == 1  && getRawAxis(axisY) == 1;
			case IBDirection.S_ordinal:  return getRawAxis(axisX) == 0  && getRawAxis(axisY) == 1;
			case IBDirection.SW_ordinal: return getRawAxis(axisX) == -1 && getRawAxis(axisY) == 1;
			case IBDirection.W_ordinal:  return getRawAxis(axisX) == -1 && getRawAxis(axisY) == 0;
			default: return false;
		}
	}
}
