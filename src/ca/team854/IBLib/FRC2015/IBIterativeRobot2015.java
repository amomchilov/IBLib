package ca.team854.IBLib.FRC2015;

import ca.team854.IBLib.Drive.IBRobotDrive;
import ca.team854.IBLib.FRC2015.DriveTrain.IBDriveTrainSubsystem;
import ca.team854.IBLib.FRC2015.Forklift.IBForkliftSubsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class IBIterativeRobot2015 extends IterativeRobot {

	public static PowerDistributionPanel pd;	
	public static IBUSBCamera camera;// = new CameraFeed();
	public static IBInput2015 input;
	public static IBDriveTrainSubsystem driveTrain;
	public static IBForkliftSubsystem forklift;

	CommandGroup auto;
	
	public void robotInit() { 
		DriverStation.reportError("===== disabledInit() =====", false);
		
		pd = new PowerDistributionPanel();
		driveTrain = new IBDriveTrainSubsystem();
		forklift = new IBForkliftSubsystem();
		input = new IBInput2015();
		
		SmartDashboard.putBoolean("ControllerOrJoystick", false);
	}

	public void disabledInit() {
		System.out.println("disabledInit()");
	}
	
	public void disabledPeriodic() {}

	public void autonomousInit() {
		System.out.println("autonomousInit()");
		auto = new IBAutonomous2015();
		auto.start();
	}
	public void autonomousPeriodic() {
        Scheduler.getInstance().run();
	}

	public void teleopInit() {
		System.out.println("teleopInit()");
		if (auto != null && auto.isRunning()) auto.cancel(); //cancel autonomous command, in case it hasn't stopped already
	}

	byte counter = 0;
	
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		//boolean controllerOrJoystick = SmartDashboard.getBoolean("ControllerOrJoystick", false);
		
		input.setControllerActive();

		if (++counter == 10) {
			IBRobotDrive drive = driveTrain.getIBRobotdrive();
			double inputX = input.getDriveX();
			double inputY = input.getDriveY();
			double leftMotorSpeed = drive.getRearLeftMotorSpeed();
			double rightMotorSpeed = drive.getRearRightMotorSpeed();
			double leftEncoderFeedback = drive.getRearLeftBundle().getEncoder().getRate();
			double rightEncoderFeedback = drive.getRearRightBundle().getEncoder().getRate();
			
			SmartDashboard.putNumber("InputSpeed", inputY);
			SmartDashboard.putNumber("InputTurn", inputX);
			SmartDashboard.putNumber("LeftMotorsOutput", leftMotorSpeed);
			SmartDashboard.putNumber("RightMotorsOutput", rightMotorSpeed);
			SmartDashboard.putNumber("LeftEncoderFeedback", leftEncoderFeedback);
			SmartDashboard.putNumber("RightEncoderFeedback", rightEncoderFeedback);
	
			System.out.println("pd.getTemperature(): " + pd.getTemperature());
			System.out.println("pd.getVoltage(): " + pd.getVoltage());
			System.out.println("pd.getTotalCurrent(): " + pd.getTotalCurrent());
			System.out.println("pd.getTotalEnergy(): " + pd.getTotalEnergy());
			System.out.println("pd.getTotalPower(): " + pd.getTotalPower());
			
			counter = 0;
		}
	}

	public void testInit() {}

	public void testPeriodic() {
		teleopPeriodic();
	}
}
