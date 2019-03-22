/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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

public class Autonomus {

    private int currentStepPlace = 0;
    private int currentStepPickup = 0;

    private int autoTimerPlace = 0;
    private int autoTimerPickup = 0;

    Joystick joystick = new Joystick(ControllerMap.D_CONTROLLER_PORT);

    private boolean isDone = false;
    public double pT = .0005, iT = 0.0000045, dT = 0;
    MiniPID pidDist = new MiniPID(pT, iT, dT);
    public int iZone = 400;

    TalonGroup liftGroup = new TalonGroup(Motors.lift, 1);

    private boolean flap = false;
    private int timer = 15;
    private boolean controlMode = true;
	
    public DoubleSolenoid piston;
    
    private static final int LOW_TIER = 2300;

    public Autonomus(DoubleSolenoid piston) {
        this.piston = piston;
    }

    public void operate() {
        autoHatchPannelPickup();
        //autoHatchPannelPlace(LOW_TIER);
        flappyBird();
    }

    public void autoHatchPannelPlace(int levelTicks) {
        if(joystick.getRawButton(ControllerMap.D_B_BUTTON)) {

            if (currentStepPlace == 0) {
                currentStepPlace = 1;
            }

		    if(currentStepPlace == 1 && autoTimerPlace <= 10 ) {
			    flap = true;
			    autoTimerPlace++;
		    } else if (currentStepPlace == 1 && autoTimerPlace > 10) {
			    autoTimerPlace = 0;
			    currentStepPlace = 2;
		    }

		    if(currentStepPlace == 2 && isDone) {
			    isDone = false;
			    currentStepPlace = 3;
		    } else if(currentStepPlace == 2) {
			    isDone = moveToLevel(levelTicks - 500); //Calibrate AnnoyAri
			    System.out.println();
		    }

		    if(currentStepPlace == 3 && autoTimerPlace <= 10) {
			    Motors.driveFL.set(ControlMode.PercentOutput, -.6);
			    Motors.driveFR.set(ControlMode.PercentOutput, .6);
			    Motors.driveBL.set(ControlMode.PercentOutput, -.6);
			    Motors.driveBR.set(ControlMode.PercentOutput, .6);
			    autoTimerPlace++;
			    System.out.println(autoTimerPlace);
		    } else if (currentStepPlace == 3 && autoTimerPlace > 10) {
			    flap = false;
			    Motors.driveFL.set(ControlMode.PercentOutput, 0);
			    Motors.driveFR.set(ControlMode.PercentOutput, 0);
			    Motors.driveBL.set(ControlMode.PercentOutput, 0);
			    Motors.driveBR.set(ControlMode.PercentOutput, 0);
			    currentStepPlace = 0;
                autoTimerPlace = 0;
            }

		} else {
            currentStepPlace = 0;
            autoTimerPlace = 0;
        }
    }
    
    public void autoHatchPannelPickup() {
        if(joystick.getRawButton(ControllerMap.D_X_BUTTON)) {

            System.out.println("curr step: " + currentStepPickup);

            if (currentStepPickup == 0) {
                currentStepPickup = 1;
                flap = true;
            }

		    if(currentStepPickup == 1 && isDone) {
			    isDone = false;
			    currentStepPickup = 2;
			    flap = false;
		    } else if(currentStepPickup == 1) {
			    isDone = moveToLevel(5550); //Calibrate AnnoyAri
		    }

            // if(currentStepPickup == 2 && isDone) {
            //     isDone = false;
            //     currentStepPickup = 3;
            // } else if(currentStepPickup == 2) {
            //     isDone = moveToLevel(LOW_TIER + 3000); //Calibrate AnnoyAri
            // }

            // if(currentStepPickup == 3 && autoTimerPickup <= 10) {
            //     Motors.driveFL.set(ControlMode.PercentOutput, -.4);
            //     Motors.driveFR.set(ControlMode.PercentOutput, .4);
            //     Motors.driveBL.set(ControlMode.PercentOutput, -.4);
            //     Motors.driveBR.set(ControlMode.PercentOutput, .4);
            //     autoTimerPickup++;
            // } else if (currentStepPickup == 3 && autoTimerPickup > 10) {
            //     Motors.driveFL.set(ControlMode.PercentOutput, 0);
            //     Motors.driveFR.set(ControlMode.PercentOutput, 0);
            //     Motors.driveBL.set(ControlMode.PercentOutput, 0);
            //     Motors.driveBR.set(ControlMode.PercentOutput, 0);
            //     currentStepPickup = 4;
            //     autoTimerPickup = 0;
            // }

            // if(currentStepPickup == 4 && isDone) {
            //     isDone = false;
            //     currentStepPickup = 5;
            // } else if(currentStepPickup == 4) {
            //     isDone = moveToLevel(LOW_TIER); //Calibrate AnnoyAri
            // }

        } else {
            currentStepPickup = 0;
            autoTimerPickup = 0;
        }
    }

    public boolean moveToLevel(double desLevel) {
		pidDist.setMaxIOutput(iZone);
		SmartDashboard.putNumber("desLevel ", desLevel);
		SmartDashboard.putNumber("curTicks ", liftGroup.getSensorData());
		int desTicks = (int) ((desLevel));
		System.out.println("desTicks " + desTicks);
		int deadZone = 100;
		pidDist.setSetpoint(desTicks);
		int curTicks = liftGroup.getSensorData();
		if (Utility.inRange(curTicks, desTicks, deadZone)) {
			liftGroup.set(0.2);
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
}
