package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import power.hawks.frc.lib.MiniPID;
import power.hawks.frc.lib.Utility;
import power.hawks.frc.lib.subsys.DriveTrain;
import power.hawks.frc.lib.vars.TalonGroup;
import subsys.Release;
import vars.ControllerMap;
import vars.Dimensions;
import vars.Motors;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Operator {

	private Compressor nothing = new Compressor();
	public DoubleSolenoid piston = new DoubleSolenoid(0, 1);
	public DoubleSolenoid pistonLock = new DoubleSolenoid(2, 3);

	Joystick joystick = new Joystick(ControllerMap.O_CONTROLLER_PORT);
	Joystick joystickDriver = new Joystick(ControllerMap.D_CONTROLLER_PORT);
	private static final int LOW_TIER = 2300;
	private boolean isDone = false;
	private int currentStepPlace = 0;
	private int currentStepPickup = 0;
	private int autoTimer = 0;
	private int timer = 15;
	private boolean flap = false;
	private boolean controlMode = HATCHPANNEL;
	private static boolean HATCHPANNEL = true;
	private static boolean CARGO = false;
	private static boolean OVERRIDEANGLE = false;
	public static int angleThing =  Motors.intakeAngle.getSelectedSensorPosition();
	DigitalInput optical = new DigitalInput(0); //SEE IF CORRECT
	DriveTrain driveTrain;
	int TPI = 449;
	//lift pid
	public double pT = .0005, iT = 0.0000045, dT = 0; //196:1 Gear ratio
	// public double pT = .0005, iT = 0.0000045, dT = 0; //old 50:1 gear ratio

	public int iZone = 200;
// public double pI = .0001, iI = 0.000000, dI = .00000;
	public int firstTier = 2000; //CHANGE
	// public double pI = .0009, iI = 0.000005, dI = .00005;
	public double pI = .0009, iI = 0.000005, dI = .00005;

	//	public double pI = .002, iI = 0.000012, dI = .025; close as of 1600 3/2

	//low 9275
	//mid 18200
	//high 27125 

	MiniPID pidDist = new MiniPID(pT, iT, dT);


	MiniPID intakeWrist = new MiniPID(pI, iI, dI);

	TalonGroup liftGroup;
	
	public Operator(DriveTrain dt) {
		pistonLock.set(Value.kReverse);
		driveTrain = dt;
		liftGroup = new TalonGroup(Motors.lift, 1);
	}

	public void resetEncoders() {
		liftGroup.resetEncoder();
		Motors.intakeAngle.setSelectedSensorPosition(0, 0, 30); //changed from 200
	}

	/* returns if intake angle is not above 90 degrees*/
	public boolean isLiftReady() {
		return !optical.get();
	}
	/*
	 * operates the mechanism to intake or release
	 * buttons desired by the drive team still need to be inputed
	 */

	public void pidStuff(){
		
	}
	public void operate() {
		SmartDashboard.putNumber("intakeAngle ticks", Motors.intakeAngle.getSelectedSensorPosition());
		autoHatchPannelPlace(currentStepPlace, LOW_TIER);
		autoHatchPannelPickUp(currentStepPickup);
		intake();
		// setAllAngle();
		intakeAngle();
		upDown();
		// pistonPush();
		flappyBird();
		// liftAuto();
		// changeMode();
		lockPiston();
		intakeReset();
		// overrideOptical();
		// defenceMode();
		// SmartDashboard.putNumber(Motors.liftRight.getpercen)
		if (joystick.getRawButton(ControllerMap.O_X_BUTTON)) {
			// moveToAngleTicks(258);
			// moveToLevel(10593);
			// moveToAngleTicks(841);
			// Motors.intakeAngle.set(ControlMode.PercentOutput, .50);
		}

		if (joystick.getPOV() == ControllerMap.O_DOWN_DPAD) {
			// moveToLevel(2965);

		}
		else{
			// Motors.intakeAngle.set(ControlMode.PercentOutput, 0);
		}
		liftAuto2();
		SmartDashboard.putNumber("intake percent speed", Motors.intakeAngle.getMotorOutputPercent());
	}

	

	public void autoHatchPannelPlace(int currentStep, int levelTicks) {

		RobotState.setState(true);

		if(joystickDriver.getRawButton(ControllerMap.D_B_BUTTON) && currentStepPlace == 0) {
			currentStepPlace = 1;
		}

		if(currentStepPlace == 1 && autoTimer <= 10 ) {
			flap = true;
			autoTimer++;
		} else if (currentStepPlace == 1 && autoTimer > 10) {
			autoTimer = 0;
			currentStepPlace = 2;
		}

		if(currentStepPlace == 2 && isDone) {
			isDone = false;
			currentStepPlace = 3;
		} else if(currentStepPlace == 2) {
			isDone = moveToLevel(levelTicks - 500); //Calibrate AnnoyAri
			System.out.println();
		}

		if(currentStepPlace == 3 && autoTimer <= 10) {
			Motors.driveFL.set(ControlMode.PercentOutput, -.6);
			Motors.driveFR.set(ControlMode.PercentOutput, .6);
			Motors.driveBL.set(ControlMode.PercentOutput, -.6);
			Motors.driveBR.set(ControlMode.PercentOutput, .6);
			autoTimer++;
			System.out.println(autoTimer);
		} else if (currentStepPlace == 3 && autoTimer > 10) {
			piston.set(Value.kReverse);
			Motors.driveFL.set(ControlMode.PercentOutput, 0);
			Motors.driveFR.set(ControlMode.PercentOutput, 0);
			Motors.driveBL.set(ControlMode.PercentOutput, 0);
			Motors.driveBR.set(ControlMode.PercentOutput, 0);
			currentStepPlace = 0;
			autoTimer = 0;
		}
		System.out.println(currentStepPlace);
	}

	public void autoHatchPannelPickUp(int currentStep) {

		if(joystickDriver.getRawButton(ControllerMap.D_X_BUTTON) && currentStep == 0) {
			currentStep = 1;
		}

		if(currentStep == 1 && isDone) {
			isDone = false;
			currentStep = 2;
			piston.set(Value.kReverse);
		} else if(currentStep == 2) {
			isDone = moveToLevel(LOW_TIER + 500); //Calibrate AnnoyAri
		}

		if(currentStep == 2 && isDone) {
			isDone = false;
			currentStep = 3;
		} else if(currentStep == 2) {
			isDone = moveToLevel(LOW_TIER + 3000); //Calibrate AnnoyAri
		}

		if(currentStep == 2 && autoTimer < 10) {
			Motors.driveFL.set(ControlMode.PercentOutput, .4);
			Motors.driveFR.set(ControlMode.PercentOutput,- .4);
			Motors.driveBL.set(ControlMode.PercentOutput, .4);
			Motors.driveBR.set(ControlMode.PercentOutput, -.4);
		} else if (currentStep == 2 && autoTimer > 10) {
			piston.set(Value.kReverse);
			Motors.driveFL.set(ControlMode.PercentOutput, 0);
			Motors.driveFR.set(ControlMode.PercentOutput,- 0);
			Motors.driveBL.set(ControlMode.PercentOutput, 0);
			Motors.driveBR.set(ControlMode.PercentOutput, -0);
			currentStep = 4;
			autoTimer = 0;
		}

		if(currentStep == 4 && isDone) {
			isDone = false;
			currentStep = 0;
		} else if(currentStep == 4) {
			isDone = moveToLevel(0); //Calibrate AnnoyAri
		}
	}


	// public double aboveFirstTier(){
	// 	if (liftGroup.getSensorData() > LOW_TIER + 500) {
	// 		return .6;
	// 	} else {
	// 		return 1;
	// 	}
	// }
	
	public void lockPiston(){
		if (joystick.getRawButton(ControllerMap.O_RIGHT_BUMPER)) {
			pistonLock.set(Value.kReverse);
			SmartDashboard.putBoolean("90 DEGREES", false);
		} else if (joystick.getRawButton(ControllerMap.O_LEFT_BUMPER)) {
			pistonLock.set(Value.kForward);
			SmartDashboard.putBoolean("90 DEGREES", true);
		}
	}
	public void intakeReset() {
		if (joystick.getRawButton(8)) {
			Motors.intakeAngle.setSelectedSensorPosition(0, 0, 30);
			liftGroup.resetEncoder();
		} else if (joystick.getRawButton(7)) {
			Motors.intakeAngle.setSelectedSensorPosition(1172,0,30);

		}
	}

	public void defenceMode() {
		if (joystick.getRawButton(ControllerMap.O_X_BUTTON)) {
			moveToLevel(0);
			moveToAngleTicks(0); //might need to change
		}
	}

	public void intake() {
		if (joystick.getRawButton(ControllerMap.O_A_BUTTON) || Robot.annoyLiam < 5) {
			Motors.intake.set(ControlMode.PercentOutput, .5); // used to be 1
		} else if (joystick.getRawButton(ControllerMap.O_B_BUTTON)) {
			Motors.intake.set(ControlMode.PercentOutput, -.5); //used to be 1
		} else {
			Motors.intake.set(ControlMode.PercentOutput, .1);
		}
		Robot.annoyLiam++;
		System.out.println(Robot.annoyLiam);
	}

	public void ninety(){
		if (joystick.getRawButton(ControllerMap.O_X_BUTTON) && timer == 30) {
			controlMode = !controlMode;
			timer = 0;
			if (controlMode) {
				moveToAngleTicks(348);
			} else {

			}
		} else if (timer != 30) {
			timer++;
		}
	}

	public void upDown() {
		double limitUp = 1; //used to be .8
		double limitDown = 1;
		int curTicks = liftGroup.getSensorData();
		SmartDashboard.putNumber("curTicks ", curTicks);
		// double minSpeedCheck = joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) * limit;
		double minBreak = 0.00;//USED TO BE .2
		if (joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) > minBreak ) {//&& isLiftReady()
			System.out.println("DOWN");
			// if(minSpeedCheck > break){
			Motors.liftLeft.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) * limitDown);
			Motors.liftRight.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) * -limitDown);
		} else if (joystick.getRawAxis(ControllerMap.O_RIGHT_TRIGGER) > minBreak ) {//&& isLiftReady()
			System.out.println("UP");
			Motors.liftLeft.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_RIGHT_TRIGGER) * -limitUp);	
			Motors.liftRight.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_RIGHT_TRIGGER) * limitUp);
		} else if (joystick.getRawAxis(ControllerMap.O_RIGHT_TRIGGER) == 0 && joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) == 0) {
			if(curTicks < 500) {
				// Motors.liftLeft.set(ControlMode.PercentOutput, 0);//wont drift down
				// Motors.liftRight.set(ControlMode.PercentOutput, 0);//wont drift down
				Motors.liftLeft.set(ControlMode.PercentOutput, -minBreak);//wont drift down
				Motors.liftRight.set(ControlMode.PercentOutput, minBreak);//wont drift down
			} else {
				Motors.liftLeft.set(ControlMode.PercentOutput, -minBreak);//wont drift down
				Motors.liftRight.set(ControlMode.PercentOutput, minBreak);//wont drift down
			}
		} else {
			Motors.liftLeft.set(ControlMode.PercentOutput, -minBreak);
			Motors.liftRight.set(ControlMode.PercentOutput, minBreak);
		}
	}

	public double ariBreakSpeed(){
		int ticks =  Motors.intakeAngle.getSelectedSensorPosition();
		double result;
		int toBoard = 1172; //may need to change
		ticks = toBoard - ticks;
		int someDistance = 1020; //90 degrees

		if (ticks < someDistance) {
			// result = (24939.05923 * (ticks * ticks)) + (-34765.09592 * ticks) + 12379.49228; //oldest
			// result = (-18827.48397 * (ticks * ticks)) + (-15078.97768 * ticks) + -24230.982905; //older
			result = -0.66712499 + (0.000535068838 *(ticks)); //linreg r = 0.969
		} else {	
			result = .3;
		}
		System.out.println("result break " + result);
		return result;
	}

	public double ariIntakeSpeed() {
		int ticks =  Motors.intakeAngle.getSelectedSensorPosition();
		int toBoard = 1172; //may need to change
		ticks = toBoard - ticks;
		SmartDashboard.putNumber("THE TICKS ", ticks);
		double result;
		int someDistance = 1020;

		if (ticks < someDistance) {
			result = (-.00068627450980392 * ticks) + 1;
			if (result > 1) {
				result = 1;
			}
		} else {
			result = .3;
		}
		System.out.println("RESULT " + result);
		return result;
	}

	// public void moveToHatch(){
	// 	if(joystick.getRawButton(ControllerMap.O_RIGHT_BUMPER)){
	// 		H
	// 		moveToAngleTicks(258);
	// 	}
	// }
	public void intakeAngle() {
		// SmartDashboard.putNumber("ANGLE TICKS", "hi");

		// if(isLiftReady() || OVERRIDEANGLE){```````````````````````````
			// if(HATCHPANNEL){
			// 	moveToAngleTicks(258);
			// }else if(CARGO){
			// 	moveToAngleTicks(1300);
			// }else{
			if (!joystick.getRawButton(ControllerMap.O_A_BUTTON)) {
					if (joystick.getRawAxis(ControllerMap.O_LEFT_STICK) > 0) {
						Motors.intakeAngle.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_LEFT_STICK) * ariIntakeSpeed()*.9);
					} else if (joystick.getRawAxis(ControllerMap.O_LEFT_STICK) < 0) {
						Motors.intakeAngle.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_LEFT_STICK) * ariIntakeSpeed() * .75);
					} else {
						Motors.intakeAngle.set(ControlMode.PercentOutput, 0);
						// ariBreakSpeed();
					}
			}
			// }
		// 	//working jank intake code that has no control from here
		// 	SmartDashboard.putNumber("intakeAngle ticks", Motors.intakeAngle.getSelectedSensorPosition());
		// 	System.out.println("TICKS " +  Motors.intakeAngle.getSelectedSensorPosition());
		// 	if(!joystick.getRawButton(ControllerMap.O_A_BUTTON)){
		// 	if(joystick.getRawAxis(ControllerMap.O_LEFT_STICK) > 0){
		// 		Motors.intakeAngle.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_LEFT_STICK) *.7);
		// 	}else if(joystick.getRawAxis(ControllerMap.O_LEFT_STICK) < 0){
		// 		Motors.intakeAngle.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_LEFT_STICK) * .7);
		// 	}else{
		// 		Motors.intakeAngle.set(ControlMode.PercentOutput, 0);
		// 	}
		// }
		// //to here

			// if(joystick.getRawButton(ControllerMap.O_X_BUTTON)){
			// 	moveToAngleTicks( Motors.intakeAngle.getSelectedSensorPosition());
			// }
			
		// }else if(!isLiftReady() && !OVERRIDEANGLE){
		// 	Motors.intakeAngle.set(ControlMode.PercentOutput, .7); //CHANGE
		// 	SmartDashboard.putString("intakeAngle", "AUTO 90 DEGREE LINE UP");
		// }else{
		// 	Motors.intakeAngle.set(ControlMode.PercentOutput, 0);
		// }

		// Motors.intakeAngle.set(ControlMode.PercentOutput, .4);
		// if (controlMode == CARGO) {
		// 	Motors.intakeAngle.set(ControlMode.PercentOutput, 0.1);
		// } else if (controlMode == HATCHPANNEL) {
		// 	Motors.intakeAngle.set(ControlMode.PercentOutput, 0.65);
		// }
	}

	public void pistonPush() {
		if (joystick.getRawButton(ControllerMap.O_Y_BUTTON)) {
			piston.set(Value.kForward);
			System.out.println("PISTON HAS BEEN PUSHED");
		} else {
			piston.set(Value.kReverse);
		}
	}

	public void flappyBird() {
		// int ballTicks = 950; // 70 degree ticks
		// if (Motors.intakeAngle.getSelectedSensorPosition() < ballTicks) {
		// 	flap = false;
		// }
		if (joystick.getRawButton(ControllerMap.O_Y_BUTTON) && timer == 15) {
			controlMode = !controlMode;
			timer = 0;
			if (controlMode) { // ||  Motors.intakeAngle.getSelectedSensorPosition() < ballTicks
				flap = true;
			} else {
				flap = false;
			}
		} else if (timer != 15) {
			timer++;
		}

		if (joystick.getRawButton(ControllerMap.O_RIGHT_BUMPER)) {
			flap = false;
		}

		if (flap) {
			piston.set(Value.kForward);
			SmartDashboard.putBoolean("FLAP ", true);
		} else {
			piston.set(Value.kReverse);
			SmartDashboard.putBoolean("FLAP ", false);
		}
	}
	public void liftAuto() {
		// double level =  Dimensions.RS_A_1_LVL_1;
		// if (joystick.getPOV() == ControllerMap.O_UP_DPAD	&& level != 3){
		// 	moveToLevel(level, level + Dimensions.RS_LEVEL_DISTANCE);
		// 	level += Dimensions.RS_LEVEL_DISTANCE;
		// } else if (joystick.getPOV() == ControllerMap.O_DOWN_DPAD) {
		// 	moveToLevel(level, level - Dimensions.RS_LEVEL_DISTANCE);
		// 	level -= Dimensions.RS_LEVEL_DISTANCE;
		// } else if (joystick.getPOV() == ControllerMap.O_RIGHT_DPAD) {
		// moveToLevel(level, Dimensions.RS_LEVEL_DISTANCE);
		// level = Dimensions.RS_LEVEL_DISTANCE; 
		// }
		// if (joystick.getPOV() == ControllerMap.O_UP_DPAD){
		// 	moveToLevel(5000);
		// } else {
		// 	moveToLevel(2500);
		// }
		//  moveToLevel(12);
		if (joystick.getPOV() == ControllerMap.O_UP_DPAD) {
			moveToLevel(12);
		}
	}

	public void liftAuto2() {
		// if (isLiftReady()) {
			if (Motors.intakeAngle.getSelectedSensorPosition() >= 900) {
				SmartDashboard.putString("PID lift", "cargo");
				if (joystick.getPOV() == ControllerMap.O_UP_DPAD) {
					moveToLevel(Dimensions.CARGO_LVL3); //change
				} else if (joystick.getPOV() == ControllerMap.O_LEFT_DPAD ||
				 joystick.getPOV() == ControllerMap.O_RIGHT_DPAD){
					moveToLevel(Dimensions.CARGO_LVL2); //change
				}else if(joystick.getPOV() == ControllerMap.O_DOWN_DPAD){
					moveToLevel(Dimensions.CARGO_LVL1); //change
				}
			}else if(Motors.intakeAngle.getSelectedSensorPosition() < 900){
				SmartDashboard.putString("PID lift", "hatch panel");
				if(joystick.getPOV() == ControllerMap.O_UP_DPAD){
					moveToLevel(Dimensions.PANNEL_LVL3); //change
				}else if(joystick.getPOV() == ControllerMap.O_LEFT_DPAD ||
				 joystick.getPOV() == ControllerMap.O_RIGHT_DPAD){
					moveToLevel(Dimensions.PANNEL_LVL2); //change
				}else if(joystick.getPOV() == ControllerMap.O_DOWN_DPAD){
					moveToLevel(Dimensions.PANNEL_LVL1); //change
				}
			}
		// }
	}

	public void setAllAngle(){
		if (joystick.getRawButton(ControllerMap.O_RIGHT_BUMPER)) {
			angleThing = 0;
			moveToAngleTicks(angleThing);
		} else if (joystick.getRawButton(ControllerMap.O_LEFT_BUMPER)) {
			angleThing = 900; //CHANGE FOR 90 DEGREES
			moveToAngleTicks(angleThing);
		} else if (joystick.getRawAxis(ControllerMap.O_LEFT_STICK) > .1) {
			angleThing++;
			moveToAngleTicks(angleThing);
		} else if (joystick.getRawAxis(ControllerMap.O_LEFT_STICK) < .1) {
			angleThing--;
			moveToAngleTicks(angleThing);
		}
		
	}

	public void moveToAngleTicks(int desTicks) {
		int ticks = Motors.intakeAngle.getSelectedSensorPosition();
		int deadZone = 0;
		// desTicks = 900;
		intakeWrist.setSetpoint(desTicks);
		SmartDashboard.putNumber("ANGLE TICKS", ticks);
		if (Utility.inRange(ticks, desTicks, deadZone)) {
			// intakeWrist.setSetpoint(0);
		 } else {
			Motors.intakeAngle.set(ControlMode.PercentOutput, intakeWrist.getOutput(ticks));
			// System.out.println("left" + Motors.liftLeft.getMotorOutputPercent());
			// System.out.println("right" + Motors.liftRight.getMotorOutputPercent());
			System.out.println(-intakeWrist.getOutput(ticks));
		}
	}

	public boolean moveToLevel(double desLevel) {
		pidDist.setMaxIOutput(iZone);
		SmartDashboard.putNumber("desLevel ", desLevel);
		SmartDashboard.putNumber("curTicks ", liftGroup.getSensorData());
		int desTicks = (int) ((desLevel));
		System.out.println("desTicks " + desTicks);
		int deadZone = 19;
		pidDist.setSetpoint(desTicks);
		int curTicks = liftGroup.getSensorData();
		if (Utility.inRange(curTicks, desTicks, deadZone)) {
			liftGroup.set(0.00);
			isDone = true;

		 } else {
			Motors.liftLeft.set(ControlMode.PercentOutput, pidDist.getOutput(curTicks) * -1);
			Motors.liftRight.set(ControlMode.PercentOutput, pidDist.getOutput(curTicks));

			// liftGroup.set(pidDist.getOutput(curTicks) * .7);
			System.out.println("left" + Motors.liftLeft.getMotorOutputPercent());
			System.out.println("right" + -Motors.liftRight.getMotorOutputPercent());

		}
		SmartDashboard.updateValues();
		return isDone;
	}

	public DoubleSolenoid getPiston() {
		return piston;
	}

	// public void overrideOptical(){
	// 	if (joystick.getRawButton(ControllerMap.O_X_BUTTON) && timer == 30) {
	// 		controlMode = !controlMode;
	// 		timer = 0;
	// 		if (controlMode) {
	// 			OVERRIDEANGLE = true;
	// 		} else {
	// 			OVERRIDEANGLE = false;
	// 		}
	// 	} else if (timer != 30) {
	// 		timer++;
	// 	}
	// }
	
	//this actually works woot woot
	public void changeMode() {
		// if (joystick.getRawButton(ControllerMap.O_X_BUTTON) && timer == 30) {
		// 	controlMode = !controlMode;
		// 	timer = 0;
		// 	if (controlMode) {
		// 		SmartDashboard.setDefaultString("Current Control Mode", "CARGO");
		// 		//LIAM CHECK IF CORRECT
		// 		CARGO = true;
		// 		HATCHPANNEL = false;
		// 	} else {
		// 		SmartDashboard.setDefaultString("Current Control Mode", "HATCHPANNEL");
		// 		CARGO = false;
		// 		HATCHPANNEL = true;
		// 	}
		// } else if (timer != 30) {
		// 	timer++;
		// }
		if(joystick.getRawButton(ControllerMap.O_RIGHT_BUMPER)){
			CARGO = true;
			HATCHPANNEL = false;
		}else if(joystick.getRawButton(ControllerMap.O_LEFT_BUMPER)){
			CARGO = false;
			HATCHPANNEL = true;
		}else if(joystick.getRawAxis(ControllerMap.O_LEFT_STICK) < -0.1 || joystick.getRawAxis(ControllerMap.O_LEFT_STICK) > 0.1){
			CARGO = false;
			HATCHPANNEL = false;
		}
		SmartDashboard.putBoolean("CARGO", CARGO);
		SmartDashboard.putBoolean("HATCHPANEL", HATCHPANNEL);
	}
}
