package ca.team854.IBLib.FRC2015.Forklift;

import ca.team854.IBLib.FRC2015.IBIterativeRobot2015;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IBForkliftLower extends Command {
	
	IBForkliftSubsystem forklift = IBIterativeRobot2015.forklift;
	
    public IBForkliftLower(double seconds) {
    	requires(forklift);
    	
    	setTimeout(seconds);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	forklift.lower();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || forklift.reachedBottom();
    }

    // Called once after isFinished returns true
    protected void end() {
    	forklift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
