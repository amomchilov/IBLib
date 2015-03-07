package ca.team854.IBLib.FRC2015.DriveTrain;

import ca.team854.IBLib.FRC2015.IBInput2015;
import ca.team854.IBLib.FRC2015.IBIterativeRobot2015;
import edu.wpi.first.wpilibj.command.Command;

public class IBDriveTrainDrive extends Command {

	IBDriveTrainSubsystem driveTrain = IBIterativeRobot2015.driveTrain;
	IBInput2015 input = IBIterativeRobot2015.input;
	
	double speedX, speedY, time;
	
    public IBDriveTrainDrive(double speedX, double speedY, double time) {
    	requires(driveTrain);
    	
    	this.speedX = speedX;
    	this.speedY = speedY;
    	
    	setTimeout(time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveTrain.getIBRobotdrive().arcadeDrive(speedY, speedX, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut();
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
