package auto.cmds;

import power.hawks.frc.lib.subsys.DriveTrain;

public class TurnCommand {

	DriveTrain driveTrain;
	double target;
	boolean complete = false;
	
	//constructor
	public TurnCommand(DriveTrain dt, double t) {
		// TODO Auto-generated constructor stub
		
		driveTrain = dt;
		target = t;
	}
	
	//turns the robot to the inputed angle when called
	public void execute() {
		driveTrain.turnTo(target);
		complete = true;
	}
	
	//returns the boolean complete
	public boolean isComplete() {
		return complete;
	}


}
