package vars;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import power.hawks.frc.lib.vars.TalonGroup;

public class Motors {

	/*Drive Motors*/
	public static TalonSRX driveFL = new TalonSRX(37); //TODO: Talon Nums 36 is now 37, 36 didnt work
	public static TalonSRX driveFR = new TalonSRX(42); //TODO: Talon Nums
	public static TalonSRX driveBL = new TalonSRX(11); //TODO: Talon Nums
	public static TalonSRX driveBR = new TalonSRX(7); //TODO: Talon Nums
	
	public static TalonSRX liftLeft = new TalonSRX(45);  
	public static TalonSRX liftRight = new TalonSRX(6);  //right elevator
	public static TalonSRX[] lift = {liftLeft, liftRight};
	public static TalonSRX driveEncoderLeft = driveBL;
	public static TalonSRX driveEncoderRight = driveFR;
	
	public static TalonSRX[] leftTalons = {driveFL, driveBL}; //Front/Back
	public static TalonSRX[] rightTalons = {driveFR, driveBR}; //Front/Back
	
	public static TalonGroup driveLeft = new TalonGroup(leftTalons, 1);
	public static TalonGroup driveRight = new TalonGroup(rightTalons, 0);
	
	/*Other Motor*/

	public static TalonSRX intake = new TalonSRX(57); 
	public static TalonSRX intakeAngle = new TalonSRX(24); //24
	
	public static TalonSRX lever = new TalonSRX(1);//54

	public static final int TIMEOUT = 200;
	
	public static void resetEncoder(TalonSRX motor) {
		motor.setSelectedSensorPosition(0,  0,  TIMEOUT);
	}
	

}
