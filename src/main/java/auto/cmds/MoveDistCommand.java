package auto.cmds;

import power.hawks.frc.lib.subsys.DriveTrain;

public class MoveDistCommand {

	DriveTrain driveTrain;
	double target;
	double angle;
	boolean complete;
	
	//moveDist constructor
	public MoveDistCommand(DriveTrain dt, double t, double a) {
		// TODO Auto-generated constructor stub
		driveTrain = dt;
		target = t;
		angle = a;
	}
	
	//moves the robot when called for the inputted distance
	public void execute() {
		driveTrain.driveDistance(target, angle);
		complete = true;
	}
	
	//returns the boolean complete
	public boolean isComplete() {
		return complete;
	}


}
