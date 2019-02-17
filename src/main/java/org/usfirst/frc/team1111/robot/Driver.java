package org.usfirst.frc.team1111.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import power.hawks.frc.lib.subsys.DriveTrain;
import vars.ControllerMap;
import vars.Motors;
import subsys.sensors.*;

public class Driver {
	
	PixyCam cam = new PixyCam();

	Joystick joystick = new Joystick(ControllerMap.D_CONTROLLER_PORT);
	DriveTrain driveTrain;
	boolean driving = true;
	static public boolean arcadeDrive = true;
	
	public static TalonSRX driveFrontLeft = new TalonSRX(36);
	public static TalonSRX driveFrontRight = new TalonSRX(42);
	public static TalonSRX driveBackLeft = new TalonSRX(11);
	public static TalonSRX driveBackRight = new TalonSRX(7);
	//constructor
	public Driver(DriveTrain dt) {
		driveTrain = dt;
	}
	
	//moves the robot with the joysticks in tank drive
	//COMMENTED BECAUSE IT IS ONLY TANK DRIVE
//	public void drive() {
//		if (driving) {
//			driveTrain.drive(joystick.getRawAxis(ControllerMap.D_LEFT_STICK), joystick.getRawAxis(ControllerMap.D_RIGHT_STICK));
//		}
//	}
	
	//code made by ari that sets the motors to a speed calculated by an equation ari wrote
	public double ariSpeedLeft(int x){
		double result = (0.01257845050366*(double)x)-1.0104688571278;
		if(result > 1) {
			result = 1;
		}
		return result;
	}
	
	public double ariSpeedRight(int x){
		double result = (-0.0125*(double)x)+3;
		if(result < -1) {
			
			result = -1;
		}
		return result;
	}
	
	//drives the robot forward
	public void drive(double left, double right) {
		Motors.driveFL.set(ControlMode.PercentOutput, left);
		Motors.driveFR.set(ControlMode.PercentOutput,- right);
		Motors.driveBL.set(ControlMode.PercentOutput, left);
		Motors.driveBR.set(ControlMode.PercentOutput, -right);
	}
	
	//switches between arcade and tank drive
	public void drive() {
		double left = 0;
		double right = 0;
		//lever();
//		if(joystick.getRawButton(ControllerMap.D_LEFT_DOWNSTICK) || joystick.getRawButton(ControllerMap.D_RIGHT_DOWNSTICK) ) {
//			arcadeDrive = !arcadeDrive;
//		}
//			if(arcadeDrive) {
//				System.out.println("ARCADE");
//				left = joystick.getRawAxis(ControllerMap.D_RIGHT_X_STICK)-joystick.getRawAxis(ControllerMap.D_LEFT_Y_STICK);
//				right =  -joystick.getRawAxis(ControllerMap.D_RIGHT_X_STICK)-joystick.getRawAxis(ControllerMap.D_LEFT_Y_STICK);
//				} else {
					System.out.println("TANK");
					left = -joystick.getRawAxis(ControllerMap.D_LEFT_Y_STICK);
					right = -joystick.getRawAxis(ControllerMap.D_RIGHT_Y_STICK);
//				}
//			drive(left, right);
			drive(-joystick.getRawAxis(ControllerMap.D_LEFT_Y_STICK),-joystick.getRawAxis(ControllerMap.D_RIGHT_Y_STICK));
			lineUp();
	}

	public void lineUp() {
		cam.updateData();
		int x = cam.getX();
		double speed = -0.4;
		if(joystick.getRawButton(ControllerMap.D_RIGHT_BUMPER)) {
			if(x > 0) {
				drive(-ariSpeedLeft(x) * speed, -ariSpeedRight(x) * speed);
			}
		}
	}
	
	public void lever() {
		if (joystick.getRawButton(ControllerMap.D_LEFT_BUMPER)) {
			Motors.lever.set(ControlMode.PercentOutput, 1);
		} else if (joystick.getRawButton(ControllerMap.D_RIGHT_BUMPER)) {
			Motors.lever.set(ControlMode.PercentOutput, -.15);
		} else {
			Motors.lever.set(ControlMode.PercentOutput, 0.0);
		}
	}
}
