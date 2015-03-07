package ca.team854.IBLib.FRC2015.DriveTrain;

import ca.team854.IBLib.FRC2015.IBInput2015;
import ca.team854.IBLib.FRC2015.IBIterativeRobot2015;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class IBDriveTrainManualControl extends Command {

	IBDriveTrainSubsystem driveTrain = IBIterativeRobot2015.driveTrain;
	IBInput2015 input = IBIterativeRobot2015.input;
	
    public IBDriveTrainManualControl() {
    	requires(driveTrain);
    	DriverStation.reportError("Constructed IBDriveTrainManualControl\n", false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DriverStation.reportError("Initialized IBDriveTrainManualControl\n", false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveTrain.getIBRobotdrive().arcadeDrive(input.getDriveY(), -input.getDriveX(), true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
