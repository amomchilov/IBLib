package ca.team854.IBLib.Drive;

import ca.team854.IBLib.Drive.IBDriveBundle.IBDriveBundleFactory;
import ca.team854.IBLib.Utils;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 * @author Alexander
 */
public class IBRobotDrive extends RobotDrive {

	private boolean twoMotorMode;
	
	private final IBDriveBundle frontLeftBundle;
	private final IBDriveBundle rearLeftBundle;
	private final IBDriveBundle frontRightBundle;
	private final IBDriveBundle rearRightBundle;
	
	public static IBRobotDrive newBasicInstance(Class c,
			int leftMotorChannel,
			int rightMotorChannel) {
		return new IBRobotDrive(
				IBDriveBundleFactory.newBasicBundle(leftMotorChannel, c),
				IBDriveBundleFactory.newBasicBundle(rightMotorChannel, c));
	}
	
	public static IBRobotDrive newBasicInstance(Class c,
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
		if ( getRearRightBundle() != null) s += "\n" + indent1 + "Rear-left:\n"   +  getRearRightBundle().toString(indentLevel + 1);
		return s + "\n]";
	}
	
	public String getDescription() {
		return "IBRobotDrive";
	}

	public boolean isTwoMotorMode() {
		return twoMotorMode;
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
	
	public double getFrontLeftMotorSpeed() {
		if (m_frontLeftMotor != null) return m_frontLeftMotor.get();
		else return 0;
	}
	
	public double getFrontRightMotorSpeed() {
		if (m_frontRightMotor != null) return m_frontRightMotor.get();
		else return 0;
	}
	
	public double getRearLeftMotorSpeed() {
		if (m_rearLeftMotor != null) return m_rearLeftMotor.get();
		else return 0;
	}
	
	public double getRearRightMotorSpeed() {
		if (m_rearRightMotor != null) return m_rearRightMotor.get();
		else return 0;
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
		
		double leftSpeed = m_maxOutput * limit(leftOutput);
		double rightSpeed = m_maxOutput * limit(rightOutput);

		if (frontLeftBundle != null) {
			 frontLeftBundle.set(m_invertedMotors[MotorType.kFrontLeft.value]   * leftSpeed);
		}	  rearLeftBundle.set(m_invertedMotors[MotorType.kRearLeft.value]    * leftSpeed);

		if (frontRightBundle != null) {
			frontRightBundle.set(-m_invertedMotors[MotorType.kFrontRight.value] * rightSpeed);
		}	 rearRightBundle.set(-m_invertedMotors[MotorType.kRearRight.value]  * rightSpeed);

		if (m_safetyHelper != null) m_safetyHelper.feed();
    }
	
//	public void mecanumDrive_Cartesian(double x, double y, double rotation, double gyroAngle) {
/**@TODO*/
//	}

//	public void mecanumDrive_Polar(double magnitude, double direction, double rotation) {
/**@TODO*/
//	}
}