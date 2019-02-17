package org.usfirst.frc.team1111.robot;

//import com.ctre.phoenix.motorcontrol.IFollower;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import power.hawks.frc.lib.subsys.DriveTrain;
import subsys.Release;
import vars.ControllerMap;
//import vars.Dimensions;
import vars.Motors;

//import org.omg.CORBA.PUBLIC_MEMBER;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Operator {

	private DoubleSolenoid piston = new DoubleSolenoid(0, 1);
	Joystick joystick = new Joystick(ControllerMap.O_CONTROLLER_PORT);
//	Release release;
	DriveTrain driveTrain;
	
	//constructor
	public Operator(DriveTrain dt) {
		
//		release = r;
		driveTrain = dt;
		
	}

	/*
	 * operates the mechanism to intake or release
	 * buttons desired by the drive team still need to be inputed
	 */
	public void operate() {
		System.out.println("operater");
		intake();
		intakeAngle();
		upDown();
		pistonPush();
	}
	
	public void intake() {
		if(joystick.getRawButton(ControllerMap.O_A_BUTTON)) {
			Motors.intake.set(ControlMode.PercentOutput, 1);
		} else if (joystick.getRawButton(ControllerMap.O_B_BUTTON)){
			Motors.intake.set(ControlMode.PercentOutput, -1);
		} 
		else {
			Motors.intake.set(ControlMode.PercentOutput, 0);
		}
	}
	
	public void upDown() {
		double limit = .8;
		if(joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) > 0) {
			System.out.println("UP");
			Motors.liftLeft.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) * limit);
			Motors.liftRight.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) * -limit);
		} else if(joystick.getRawAxis(ControllerMap.O_RIGHT_TRIGGER) > 0) {
			System.out.println("DOWN");
			Motors.liftRight.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_RIGHT_TRIGGER) * limit);
			Motors.liftLeft.set(ControlMode.PercentOutput, joystick.getRawAxis(ControllerMap.O_RIGHT_TRIGGER) * -limit);	
		} else if (joystick.getRawAxis(ControllerMap.O_RIGHT_TRIGGER) == 0 && joystick.getRawAxis(ControllerMap.O_LEFT_TRIGGER) == 0) {
			Motors.liftLeft.set(ControlMode.PercentOutput, .2);//wont drift down
			Motors.liftRight.set(ControlMode.PercentOutput, -.2);//wont drift down
		}
	}
	
	public void intakeAngle() {
		if (joystick.getRawButton(ControllerMap.O_LEFT_BUMPER)) {
			Motors.intakeAngle.set(ControlMode.PercentOutput, -.3);
		} else if (joystick.getRawButton(ControllerMap.O_RIGHT_BUMPER)) {
			Motors.intakeAngle.set(ControlMode.PercentOutput, .3);
		} else {
			Motors.intakeAngle.set(ControlMode.PercentOutput, 0);
		}
	}
	public void pistonPush() {
		if(joystick.getPOV() == ControllerMap.O_UP_DPAD) {
			piston.set(Value.kForward);
			System.out.println("PISTON HAS BEEN PUSHED");
		} else {
			piston.set(Value.kReverse);
		}
		
		
	}

}
