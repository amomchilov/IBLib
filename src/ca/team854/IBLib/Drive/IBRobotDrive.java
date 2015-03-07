package ca.team854.IBLib.Drive;

import ca.team854.IBLib.Utils;
import ca.team854.IBLib.Drive.IBDriveBundle.IBDriveBundleFactory;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Alexander
 */
public class IBRobotDrive extends RobotDrive {

	private boolean twoMotorMode;
	private boolean controlledAccelerationMode;
	
	private double previousLeftSpeed  = 0;
	private double previousRightSpeed = 0;
	private double maxAccelConstant = 0.05;
	
	private final IBDriveBundle frontLeftBundle;
	private final IBDriveBundle rearLeftBundle;
	private final IBDriveBundle frontRightBundle;
	private final IBDriveBundle rearRightBundle;
	
	public static IBRobotDrive newBasicInstance(Class<? extends SpeedController> c,
			int leftMotorChannel,
			int rightMotorChannel) {
		return new IBRobotDrive(
				IBDriveBundleFactory.newBasicBundle(leftMotorChannel, c),
				IBDriveBundleFactory.newBasicBundle(rightMotorChannel, c));
	}
	
	public static IBRobotDrive newBasicInstance(Class<? extends SpeedController> c,
				int frontLeftMotorChannel, 
				int rearLeftMotorChannel,  
				int frontRightMotorChannel,
				int rearRightMotorChannel) {
		return new IBRobotDrive(
				IBDriveBundleFactory.newBasicBundle(frontLeftMotorChannel,  c),
				IBDriveBundleFactory.newBasicBundle(rearLeftMotorChannel,   c),
				IBDriveBundleFactory.newBasicBundle(frontRightMotorChannel, c),
				IBDriveBundleFactory.newBasicBundle(rearRightMotorChannel,  c));
	}
	
	public IBRobotDrive(IBDriveBundle leftBundle,
						IBDriveBundle rightBundle) {
		super(leftBundle.getMotor(),
			  rightBundle.getMotor());
		this.frontLeftBundle  = null;
		this.rearLeftBundle   = leftBundle;
		this.frontRightBundle = null;
		this.rearRightBundle  = rightBundle;
		
		twoMotorMode = true;
	}
	
	public IBRobotDrive(IBDriveBundle frontLeftBundle,
						IBDriveBundle rearLeftBundle,
						IBDriveBundle frontRightBundle,
						IBDriveBundle rearRightBundle) {
		super(frontLeftBundle.getMotor(),
			  frontLeftBundle.getMotor(),
			  frontRightBundle.getMotor(),
			  rearRightBundle.getMotor());
		this.frontLeftBundle  = frontLeftBundle;
		this.rearLeftBundle   = rearLeftBundle;
		this.frontRightBundle = frontRightBundle;
		this.rearRightBundle  = rearRightBundle;
	}
	
	public String toString() {
		return toString(0);
	}
	
	public String toString(int indentLevel) {
		String s = "[IBRobotDrive:";
		String indent1 = Utils.indent(indentLevel + 1);
		if ( getFrontLeftBundle() != null) s += "\n" + indent1 + "Front-left:\n"  +  getFrontLeftBundle().toString(indentLevel + 1);
		if (  getRearLeftBundle() != null) s += "\n" + indent1 + "Rear-left:\n"   +   getRearLeftBundle().toString(indentLevel + 1);
		if (getFrontRightBundle() != null) s += "\n" + indent1 + "Front-Right:\n" + getFrontRightBundle().toString(indentLevel + 1);
		if ( getRearRightBundle() != null) s += "\n" + indent1 + "Rear-right:\n"  +  getRearRightBundle().toString(indentLevel + 1);
		if (controlledAccelerationMode) s += "\n" + indent1 + "Acceleration Constant: " + maxAccelConstant;
		return s + "\n]";
	}
	
	public String getDescription() {
		return "IBRobotDrive";
	}

	public boolean isTwoMotorMode() {
		return twoMotorMode;
	}

	public void setControlledAccelerationMode(boolean state) {
		controlledAccelerationMode = state;
	}
	
	public boolean getControlledAccelerationMode() {
		return controlledAccelerationMode;
	}
	
	public void setAccelerationConstant(double maxAccelConstant) {
		this.maxAccelConstant = maxAccelConstant;
	}
	
	public double getAcceleration() {
		return maxAccelConstant;
	}
	
	public IBDriveBundle getFrontLeftBundle() {
		return frontLeftBundle;
	}

	public IBDriveBundle getRearLeftBundle() {
		return rearLeftBundle;
	}

	public IBDriveBundle getFrontRightBundle() {
		return frontRightBundle;
	}

	public IBDriveBundle getRearRightBundle() {
		return rearRightBundle;
	}
	
	//TODO: get encoder feedback, if available.
	public double getFrontLeftMotorSpeed() {
		if (m_frontLeftMotor != null) return m_frontLeftMotor.get();
		return 0;
	}
	
	public double getFrontRightMotorSpeed() {
		if (m_frontRightMotor != null) return m_frontRightMotor.get();
		return 0;
	}
	
	public double getRearLeftMotorSpeed() {
		if (m_rearLeftMotor != null) return m_rearLeftMotor.get();
		return 0;
	}
	
	public double getRearRightMotorSpeed() {
		if (m_rearRightMotor != null) return m_rearRightMotor.get();
		return 0;
	}
	
	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
		super.arcadeDrive(-moveValue, rotateValue, squaredInputs);
	}
	
	public void tankDrive(double leftValue, double rightValue, boolean squaredInputs) {
		super.tankDrive(-leftValue, -rightValue, squaredInputs);
	}
	
	public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
		if (m_rearLeftMotor == null || m_rearRightMotor == null) {
			throw new NullPointerException("Null motor provided");
		}
		
		if (controlledAccelerationMode) {
			leftOutput  = previousLeftSpeed  + (leftOutput  -  previousLeftSpeed) * maxAccelConstant;
			rightOutput = previousRightSpeed + (rightOutput - previousRightSpeed) * maxAccelConstant;
		}
		
		double leftSpeed  = m_maxOutput * limit(leftOutput );
		double rightSpeed = m_maxOutput * limit(rightOutput);
		
		previousLeftSpeed  =  leftOutput;
		previousRightSpeed = rightOutput;

		if (frontLeftBundle != null)  frontLeftBundle.set(m_invertedMotors[MotorType.kFrontLeft.value] * leftSpeed);
		if (rearLeftBundle  != null)   rearLeftBundle.set(m_invertedMotors[MotorType.kRearLeft.value ] * leftSpeed);
		else m_rearLeftMotor.set(m_invertedMotors[MotorType.kRearLeft.value] * leftSpeed);
		
		if (frontRightBundle != null) frontRightBundle.set(-m_invertedMotors[MotorType.kFrontRight.value] * rightSpeed);
		if (rearRightBundle  != null)  rearRightBundle.set(-m_invertedMotors[MotorType.kRearRight.value ] * rightSpeed);
		else m_rearRightMotor.set(-m_invertedMotors[MotorType.kRearRight.value] * rightSpeed);

		if (m_safetyHelper != null) m_safetyHelper.feed();
    }
	
	public void mecanumDrive_Cartesian(double x, double y, double rotation, double gyroAngle) {
		throw new RuntimeException("NOT IMPLEMENTED YET"); //TODO: implement
	}

	public void mecanumDrive_Polar(double magnitude, double direction, double rotation) {
		throw new RuntimeException("NOT IMPLEMENTED YET"); //TODO: implement
	}
}