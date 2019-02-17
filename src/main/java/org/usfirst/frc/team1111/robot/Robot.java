/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1111.robot;

import auto.Autonomous;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import power.hawks.frc.lib.auto.cmds.MoveDistCommand;
//import power.hawks.frc.lib.subsys.DriveTrain;
import subsys.Release;
import subsys.HippogryphDrive;
//import vars.Dimensions;
import vars.Pneumatics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	// Subsystem Instantiation 
		HippogryphDrive driveTrain = new HippogryphDrive();
//		Release release = new Release();
		Compressor compressor = new Compressor();
		
		// Drive Team instantiation
		Driver driver = new Driver(driveTrain);
//		Operator operator = new Operator(release, driveTrain);
		Operator operator = new Operator(driveTrain);

		Autonomous autonomous = new Autonomous(driveTrain);
		
		// Auto Chooser Instantiation
		//TODO: Maybe add more later when we actually have a robot FAB
		SendableChooser<String> autoChooser = new SendableChooser<>();
		//private static final String BASELINE = "BS";
		//private static final String CARGOSHIP = "CS";
		//private static final String ROCKETSHIP = "RS";
		public static final String PANIC = "P";
		String autoChoosen;

		//starting position string stuff 
		SendableChooser<String> startChooser = new SendableChooser<>();
		public final static String POSITION_A1 = "PA1";
		public final static String POSITION_A2 = "PA2";
		public final static String POSITION_B = "PB";
		public final static String POSITION_C1 = "PC2";
		public final static String POSITION_C2 = "PC2";
		String startPos;
		
		//possible autonomous destination constants
		SendableChooser<String> destinationChooser = new SendableChooser<>();
		public final static String POS_A1_CARGOSHIP_A1 = "PA1_CS_A1";
		public final static String POS_A2_CARGOSHIP_A1 = "PA2_CS_A1";
		public final static String POS_B_CARGOSHIP_A1 = "PB_CS_A1";
		public final static String POS_B_CARGOSHIP_C1 = "PB_CS_C1";
		public final static String POS_C1_CARGOSHIP_C1 = "PC1_CS_C1";
		public final static String POS_C2_CARGOSHIP_C1 = "PC2_CS_C1";
		
		
		/**
		 * This function is run when the robot is first started up and should be
		 * used for any initialization code.
		 */
		@Override
		public void robotInit() {
			//TODO: ADD THE AUTOS TO THE STDB, LOOK AT PG ROBOT FOR REF
			SmartDashboard.putData("Autonomous Chooser", autoChooser);
			
			startChooser.addObject("Position A1", POSITION_A1);
			startChooser.addObject("Position A2", POSITION_A2);
			startChooser.addDefault("Position B", POSITION_B);
			startChooser.addDefault("Position C1", POSITION_C1);
			startChooser.addDefault("Position C2", POSITION_C2);
			SmartDashboard.putData("Start Position Selector", startChooser);
		}

		/**
		 * This autonomous (along with the chooser code above) shows how to select
		 * between different autonomous modes using the dashboard. The sendable
		 * chooser code works with the Java SmartDashboard. If you prefer the
		 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
		 * getString line to get the auto name from the text box below the Gyro
		 *
		 * <p>You can add additional auto modes by adding additional comparisons to
		 * the switch structure below with additional strings. If using the
		 * SendableChooser make sure to add them to the chooser code above as well.
		 */
		@Override
		public void autonomousInit() {
			
			//Pneumatics.hatPanSuc.set(Value.kForward); //TODO: what does this have to be set to in the beginning 
			
			autoChoosen = autoChooser.getSelected();
			startPos = startChooser.getSelected();
			driveTrain.reset();
			//autonomous.genPathway("Test", "Test");
//			MoveDistCommand test = new MoveDistCommand(driveTrain, Dimensions.TEST * 1883.67, 0);
//			test.execute();
//			System.out.println(test.isComplete());
			//Reset and configure hardware for testing
//			encoderLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 200);
//			encoderRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 200);
//			resetEncoder(encoderLeft);
//			resetEncoder(encoderRight);
//			navx.reset();
		}

		/**
		 * This function is called periodically during autonomous.
		 */
		@Override
		public void autonomousPeriodic() {
////		TODO
////			switch (m_autoSelected) {
////				case kCustomAuto:
////					// Put custom auto code here
////					break;
////				case kDefaultAuto:
////				default:
////					// Put default auto code here
////					break;
//			MoveDistCommand test = new MoveDistCommand(driveTrain, Dimensions.TEST * 1883.67, 0);
//			test.execute();
//			System.out.println(test.isComplete());
//			//autonomous.scheduler.run();

		}
		

		/**
		 * This function is called periodically during operator control.
		 */
		@Override
		public void teleopPeriodic() {
			driver.drive();
			operator.operate();
		}
		
		public void disabledinit() {
			autonomous.reset();
		}
}
