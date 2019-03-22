package org.usfirst.frc.team1111.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import power.hawks.frc.lib.subsys.DriveTrain;
import vars.ControllerMap;
import vars.Motors;
import subsys.sensors.*;

public class Driver {
	
	PixyCam cam = new PixyCam();
	//Operator op = new Operator(driveTrain);

	Joystick joystick = new Joystick(ControllerMap.D_CONTROLLER_PORT);
	DriveTrain driveTrain;
	boolean driving = true;
	static public boolean driveType = true;
	
	private double right = 0;
	private double left = 0;

	private static boolean TANK = false;
	private static boolean ARCADE = true;

	enum DriveMode {
		ARCADE,
		NONE
	}
	DriveMode driveMode = DriveMode.NONE;

	//constructor

	public Driver(DriveTrain dt) {
		driveTrain = dt;
	}
	
	//code made by ari that sets the motors to a speed calculated by an equation ari wrote

	public double ariSpeedLeft(int x) {
		double result = (0.01257845050366*(double)x)-1.0104688571278;
		if (result > 1) {
			result = 1;
		}
		return result;
	}
	
	public double ariSpeedRight(int x) {
		double result = (-0.0125*(double)x)+3;
		if (result < -1) {
			result = -1;
		}
		return result;
	}
	
	public void drive(double left, double right) {
		Motors.driveFL.set(ControlMode.PercentOutput, left);
		Motors.driveFR.set(ControlMode.PercentOutput,- right);
		Motors.driveBL.set(ControlMode.PercentOutput, left);
		Motors.driveBR.set(ControlMode.PercentOutput, -right);
	}
	
	public void drive() {
		//lever();
		//switchDrive();
		arcadeDrive();
		//tankDrive();
		SmartDashboard.putNumber("Left", this.left);
		SmartDashboard.putNumber("Right", this.right);
		drive(this.left, this.right);
		lineUp();
	}

	// public void switchDrive() {
	// 	if (joystick.getRawButton(ControllerMap.D_X_BUTTON)) {
	// 		driveMode = DriveMode.ARCADE;
	// 		driveType = TANK;
	// 	} else if (joystick.getRawButton(ControllerMap.D_B_BUTTON)) {
	// 		driveMode = DriveMode.TANK;
	// 		driveType = ARCADE;
	// 	}
	// 	SmartDashboard.putString("Drive mode", driveMode.toString());
		
	// }

	public void check() {
		
	}

	// public void nolanControls(){
	// 	if (joystick.getRawAxis(ControllerMap.D_LEFT_TRIGGER) > 0.1){
	// 		Operator.piston.set(Value.kForward);
	// 	} else if(joystick.getRawAxis(ControllerMap.D_LEFT_TRIGGER) > 0.1){
	// 		Motors.intake.set(ControlMode.PercentOutput, 1);
	// 	}
	// }
	public boolean driverRightTrigger(){
		if(joystick.getRawAxis(ControllerMap.D_LEFT_TRIGGER) > 0.1){
			return true;
		}else{
			return false;
		}
	}

	public boolean driverLeftTrigger(){
		if(joystick.getRawAxis(ControllerMap.D_RIGHT_TRIGGER) > 0.1){
			return true;
		}else{
			return false;
		}
	}
	
	public void arcadeDrive() {
				this.left = joystick.getRawAxis(ControllerMap.D_RIGHT_X_STICK)-joystick.getRawAxis(ControllerMap.D_LEFT_Y_STICK);
				this.right =  -joystick.getRawAxis(ControllerMap.D_RIGHT_X_STICK)-joystick.getRawAxis(ControllerMap.D_LEFT_Y_STICK);
	}

	// public void tankDrive() {
	// 	if (driveType == TANK) {
	// 		this.left = -joystick.getRawAxis(ControllerMap.D_LEFT_Y_STICK);
	// 		this.right = -joystick.getRawAxis(ControllerMap.D_RIGHT_Y_STICK);
	// 	}
	// }

	public void lineUp() {
		cam.updateData();
		int x = cam.getX();
		SmartDashboard.putNumber("X position ", x);
		double speed = -0.4;
		if(joystick.getRawButton(ControllerMap.D_RIGHT_BUMPER)) {
			if(x > 0) {
				drive(-ariSpeedLeft(x) * speed, -ariSpeedRight(x) * speed);
			}
		}
	}

	
	public void lever() {
		if (joystick.getRawAxis(ControllerMap.D_RIGHT_TRIGGER) > 0) {
			Motors.lever.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.D_RIGHT_TRIGGER));
		} else if (joystick.getRawAxis(ControllerMap.D_LEFT_TRIGGER) > 0) {
			Motors.lever.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.D_LEFT_TRIGGER) * -1);
		} else {
			Motors.lever.set(ControlMode.PercentOutput, 0.0);
		}
	}

	
}
