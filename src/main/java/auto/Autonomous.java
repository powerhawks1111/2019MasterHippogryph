package auto;

import java.util.ArrayList; 

import org.usfirst.frc.team1111.robot.Robot;

import auto.cmds.CylinderCommand;
import auto.cmds.ReleaseCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import power.hawks.frc.lib.auto.Scheduler;
import power.hawks.frc.lib.auto.cmds.Command;
import power.hawks.frc.lib.auto.cmds.MoveDistCommand;
import power.hawks.frc.lib.auto.cmds.MoveTimeCommand;
import power.hawks.frc.lib.auto.cmds.TurnCommand;
import power.hawks.frc.lib.subsys.DriveTrain;
import subsys.Release;
import vars.Dimensions;


public class Autonomous {

	/* Instance Variables */
	
	public ArrayList<Command> commands = new ArrayList<Command>();
	public Scheduler scheduler = new Scheduler();
	boolean panic = false;
	boolean done = false;
	boolean running = false;
	int i = 0;
	
	/* Objects */
	
	DriveTrain driveTrain;
	Release release;
	
	/* Constructor */
	
	public Autonomous(DriveTrain dt) {
		driveTrain = dt;
//		release = r;
	}
	
	/* Methods */ 
	
	public void testAuto() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.TEST, 0));
	}
	
	/* Command (Robot) at Position A1 to move to Baseline */
	
	public void posA1Baseline() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_A1_BL, 0));
	}
	
	/* Command (Robot) at Position A2 to move to Baseline */
	
	public void posA2Baseline() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_A2_BL, 0));
	}
	
	/* Command (Robot) at Position B to move to Baseline */
	
	public void posBBaseline() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_B_BL, 0));
	}
	
	/* Command (Robot) at Position C1 to move to Baseline */
	
	public void posC1Baseline() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_C1_BL, 0));
	}
	
	/* Command (Robot) at Position C2 to move to Baseline */
	
	public void posC2Baseline() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_C2_BL, 0)); 
	}
	
	/* Command (Robot) at Position A1 to move to Cargo Ship A1 */
	
	public void posA1CargoShipA1() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_A1_CS_A_1, 0));
	}
	
	/* Command (Robot) at Position A2 to move to Cargo Ship A1 */
	
	public void posA2CargoShipA1() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_A2_CS_A_1, 0));
	}
	
	/* Command (Robot) at Position B to move to Cargo Ship A1 */
	
	public void posBCargoShipA1() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_B_CS_A_1, 0));
	}
	
	/* Command (Robot) at Position B to move to Cargo Ship C1 */
	
	public void posBCargoShipC1() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_B_CS_C_1, 0));
	}
	
	/* Command (Robot) at Position C1 to move to Cargo Ship C1 */
	
	public void posC1CargoShipC1() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_C1_CS_C_1, 0));
	}
	
	/* Command (Robot) at Position C2 to move to Cargo Ship C1 */
	
	public void posC2CargoShipC1() {
		commands.add(new MoveDistCommand(driveTrain, Dimensions.HAB_C2_CS_C_1, 0));
	}
	
	/* Command (Robot) at Cargo Ship A1 to move to Loading Zone A */
	
	public void CargoShipA1LoadingZoneA() {
		commands.add(new TurnCommand(driveTrain, Dimensions.ANG_CS_A_1_LZ_A));
		commands.add(new MoveDistCommand(driveTrain, Dimensions.CS_A_1_LZ_A, 0));
	}
	
	/* Command (Robot) at Cargo Ship C1 to move to Loading Zone C */
	
	public void CargoShipC1LoadingZoneC() {
		commands.add(new TurnCommand(driveTrain, Dimensions.ANG_CS_C_1_LZ_C));
		commands.add(new MoveDistCommand(driveTrain, Dimensions.CS_C_1_LZ_C, 0));
	}
	
	/* Command (Robot) to move to 90 Degrees  */
	
	public void Panic() {
		commands.add(new TurnCommand(driveTrain, 90)); 
	}
	
	/* Generates Pathway with data Returned from Smart Dash Board */
	
	public void genBaseline(String sp) {
		if(sp.equals(Robot.POSITION_A1)) {
			posA1Baseline();
		}
		else if(sp.equals(Robot.POSITION_A2)) {
			posA2Baseline();
		}
		else if(sp.equals(Robot.POSITION_B)) {
			posBBaseline();
		}
		else if(sp.equals(Robot.POSITION_C1)) {
			posC1Baseline();
		}
		else if(sp.equals(Robot.POSITION_C2)) {
			posC2Baseline();
		}
		
		panic = true;
//		scheduler.addCommands(commands);
	}
	
	/*
	 * sp = starting position
	 * d = destination of the robot
	 * This method would generate the autonomous pathway of the robot during sandstorm if we were to use autonomous
	 */
	public void genPathway(String sp, String d) {
		if(d.equals(Robot.PANIC)) {
			Panic();
			panic = true;
		}
		
		if(sp.equals(Robot.POSITION_A1)) {
			if(d.equals(Robot.POS_A1_CARGOSHIP_A1)) {
				posA1CargoShipA1();
			}
			else {
				posA1Baseline();
				panic = true;
			}
		}
		if(sp.equals(Robot.POSITION_A2)) {
			if(d.equals(Robot.POS_A2_CARGOSHIP_A1)) {
				posA2CargoShipA1();
			}
			else {
				posA2Baseline();
				panic = true;
			}
		}
		if(sp.equals(Robot.POSITION_B)) {
			if(d.equals(Robot.POS_B_CARGOSHIP_A1)) {
				posBCargoShipA1();
			}
			else if(d.equals(Robot.POS_B_CARGOSHIP_C1)) {
				posBCargoShipC1();
			}
			else {
				posBBaseline();
				panic = true;
			}
		}
		if(sp.equals(Robot.POSITION_C1)) {
			if(d.equals(Robot.POS_C1_CARGOSHIP_C1)) {
				posC1CargoShipC1();
			}
			else {
				posC1Baseline();
				panic = true;
			}
		}
		if(sp.equals(Robot.POSITION_C2)) {
			if(d.equals(Robot.POS_C2_CARGOSHIP_C1)) {
				posC2CargoShipC1();
			}
			else {
				posC2Baseline();
				panic = true;
			}
		}
		if(sp.equals("Test")) {
			if(d.equals("Test")) {
				testAuto();
			}
			else {
				panic = true;
			}
		}
		scheduler.addCommands(commands);
		
	}
	
	/* Reset (Autonomous) */
	
	public void reset() {
		panic = false;
		commands.clear();
		running = false;
		done = false;
		i = 0;
	}

	/* runs all autos in the command list */
	public void runAuto() {
		if (!done) {
			commands.get(i).execute();
		}
		
		if (!commands.get(i).isComplete()) {
			running = true;
		} else {
			if (i < commands.size() - 1) {
				i++;
			} else {
				driveTrain.stop();
				//release.stop();
				done = true;
			}
			running = false;
		}
		SmartDashboard.putBoolean("Running: ", running);
	}
	

}
