/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package ca.team854.IBLib.Test;

import ca.team854.IBLib.Drive.IBDriveBundle;
import ca.team854.IBLib.Drive.IBDriveBundle.IBDriveBundleFactory;
import ca.team854.IBLib.Drive.IBRobotDrive;
import ca.team854.IBLib.IBDriverStationLCD;
import ca.team854.IBLib.InputSources.IBPS3Controller;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class IBIterativeRobot extends IterativeRobot {

	IBPS3Controller input;
	IBDriverStationLCD lcd;
	IBRobotDrive drive;
	Talon rightTalons;
	Encoder leftEncoder, rightEncoder;
	PIDController rightPID;
	//public void startCompetition() {}
	//private boolean nextPeriodReady() {}

	public void robotInit() {
		System.out.println("robotInit()");
		input = new IBPS3Controller();
		lcd  = new IBDriverStationLCD(13, 14);
//		drive = IBRobotDrive.newBasicInstance(Victor.class, PORTS.LEFT_MOTORS, PORTS.RIGHT_MOTORS);
		
		IBDriveBundle leftBundle = new IBDriveBundleFactory()
				.addMotor(PORTS.LEFT_MOTORS, Victor.class)
				.addEncoder(PORTS.LEFT_ENCODER_A, PORTS.LEFT_ENCODER_B)
				.addPIDController(0, 0, 0, 1)
				.commit();
		
		IBDriveBundle rightBundle = new IBDriveBundleFactory()
				.addMotor(PORTS.RIGHT_MOTORS, Victor.class)
				.addEncoder(PORTS.RIGHT_ENCODER_A, PORTS.RIGHT_ENCODER_B)
				.addPIDController(0, 0, 0, 1)
				.commit();
		
		drive = new IBRobotDrive(leftBundle, rightBundle);
		
		System.out.println(drive);
		
//		leftEncoder = new Encoder(
//				PORTS.LEFT_ENCODER_A,
//				PORTS.LEFT_ENCODER_B,
//				false, CounterBase.EncodingType.k1X);
//		leftEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate);
////		leftEncoder.setDistancePerPulse(0.1);
//		leftEncoder.start();
		
//		rightEncoder = new Encoder(
//				PORTS.RIGHT_ENCODER_A,
//				PORTS.RIGHT_ENCODER_B,
//				true, CounterBase.EncodingType.k1X);
//		rightEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate);
////		rightEncoder.setDistancePerPulse(0.1);
//		rightEncoder.start();
		
//		rightPID = new PIDController(0.1, 0.0, 0, 1, rightEncoder, rightTalons);
//		rightPID.setInputRange(-3750, 3750);
//		rightPID.setOutputRange(-1, 1);
//		rightPID.enable();
//		
	}

	public void disabledInit() {
		System.out.println("disabledInit()");
		lcd.clear();
	}
	
	public void disabledPeriodic() {}

	public void autonomousInit() {}
	public void autonomousPeriodic() {}

	public void teleopInit() {
		System.out.println("teleopInit()");
	}

	byte counter = 0;
	public void teleopPeriodic() {
		//System.out.println("teleopPeriodic()");
		double inputX = input.getRightStickX();
		double inputY = input.getLeftStickY();
		double leftMotorSpeed = drive.getRearLeftMotorSpeed();
		double rightMotorSpeed = drive.getRearRightMotorSpeed();
		double leftEncoderFeedback = drive.getRearLeftBundle().getEncoder().getRate();
		double rightEncoderFeedback = drive.getRearRightBundle().getEncoder().getRate();
		
		drive.arcadeDrive(inputY, inputX, false);
		
//		rightTalons.set(speed);
//		rightPID.setSetpoint(inputSpeed);
//		rightPID.enable();
		
		if (++counter == 10) {
//			lcd.display("Left  X axis: "+input.getLeftStickX(),
//					"Left  Y axis: "+input.getLeftStickY(),
//					"Right X axis: "+input.getRightStickX(),
//					"Right Y axis: "+input.getRightStickY(),
//					"D-Pad X axis: "+input.getRawAxis(5),
//					"D-Pad Y axis: "+-input.getRawAxis(6));
			
			lcd.display("L  Y axis: " + inputY,
					"R X axis: " + inputX, 
					"L Output: " + leftMotorSpeed,
					"R Output: " + rightMotorSpeed,
					"L Encoder: " + leftEncoderFeedback,
					"R Encoder " + rightEncoderFeedback);
//			lcd.display("Left  Y axis: "+inputSpeed,
//					"R setpoint: "+rightPID.getSetpoint(),
//					"R Output: "+rightPID.get(),
//					"R Encoder "+encoderSpeed,
//					"", "");
			
			SmartDashboard.putNumber("InputSpeed", inputY);
			SmartDashboard.putNumber("InputTurn", inputX);
			SmartDashboard.putNumber("LeftMotorsOutput", leftMotorSpeed);
			SmartDashboard.putNumber("RightMotorsOutput", rightMotorSpeed);
			SmartDashboard.putNumber("LeftEncoderFeedback", leftEncoderFeedback);
			SmartDashboard.putNumber("RightEncoderFeedback", rightEncoderFeedback);
			counter = 0;
		}
	}

	public void testInit() {}
	public void testPeriodic() {}
}
