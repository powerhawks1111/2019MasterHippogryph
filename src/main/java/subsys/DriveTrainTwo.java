package subsys;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.MiniPID;
import util.Utility;
import vars.Motors;
import vars.Pneumatics;


public final class DriveTrainTwo {
	// NavX PID Variables
		final double pN = 0.05, iN = 0.0, dN = 0.0; // TODO: Calibrate
		public AHRS navx = new AHRS(SPI.Port.kMXP);
		MiniPID pidNavx = new MiniPID(pN, iN, dN);
		final double SPEEDADJUST = .5;
		
		// Encoder PID Variables
		double pT = .055, iT = 0, dT = 0; // TODO: Calibrate
		MiniPID pidDist = new MiniPID(pT, iT, dT);
		final double TPI = 1705;
		
		// Timer
		Timer timer = new Timer();

		// Flags
		boolean low = false;
		boolean takeoff = false;
		public boolean driving = false;
		public boolean turning = false;
		boolean timing = false;

		// Standard Variables
		double speed;
		final double MAX_AUTO_SPEED = .5;

		public DriveTrainTwo() {
			// Configure encoder PID
			Motors.driveEncoderLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Motors.TIMEOUT);
			Motors.driveEncoderRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Motors.TIMEOUT);
			Motors.resetEncoder(Motors.driveEncoderLeft);
			Motors.resetEncoder(Motors.driveEncoderRight);
			Utility.configurePID(pT, iT, dT, pidDist);
			pidDist.setOutputLimits(MAX_AUTO_SPEED);
			
			// Configure NavX PID
			navx.reset();
			Utility.configurePID(pN, iN, dN, pidNavx);
			pidNavx.setOutputLimits(MAX_AUTO_SPEED);
		}

		
		// =====STANDARD METHODS=====

		
		public void drive(double speed) {
			Motors.driveFL.set(ControlMode.PercentOutput, speed);
			Motors.driveFR.set(ControlMode.PercentOutput, speed);
			Motors.driveBL.set(ControlMode.PercentOutput, -speed);
			Motors.driveBR.set(ControlMode.PercentOutput, -speed);
		}

		public void drive(double left, double right) {
			Motors.driveFL.set(ControlMode.PercentOutput, left);
			Motors.driveFR.set(ControlMode.PercentOutput, right);
			Motors.driveBL.set(ControlMode.PercentOutput, -left);
			Motors.driveBR.set(ControlMode.PercentOutput, -right);
		}

		


		
		// =====AUTO METHODS=====
		
		public void driveDistance(double dist, double startingAngle) {
			
			double targetSpeedL = speed;
			double targetSpeedR = speed;
			
			double power = .5;
			//started at .90925 with math
			//double calibrate = .9648093841642229;
			int deadzone = 1000;
			int desTicks = (int) (TPI * dist);
			
			SmartDashboard.putNumber("Desired Ticks:", desTicks);
			pidDist.setSetpoint(desTicks);
			
			int curTicks = Motors.driveEncoderRight.getSelectedSensorPosition(0); // TODO: Verify that the encoder reads properly
			SmartDashboard.putNumber("Ticks", curTicks);
			SmartDashboard.putNumber("right", Motors.driveEncoderRight.getMotorOutputPercent());
			SmartDashboard.putNumber("left", Motors.driveEncoderLeft.getMotorOutputPercent());

			int curAngle = Math.round(navx.getYaw());
			
			SmartDashboard.putNumber("Current Angle:", curAngle);
			System.out.println(curAngle);
			
			SmartDashboard.putNumber("Starting Angle:", startingAngle);
			System.out.println(startingAngle);
			
			if (Utility.inRange(curTicks, desTicks, deadzone)) { // STOPS if in deadzone of distance
				drive(0);
				Motors.resetEncoder(Motors.driveEncoderLeft);
				Motors.resetEncoder(Motors.driveEncoderRight);
				driving = false;
			}
			else { // DRIVES until traveled far enough to be in deadzone
				if (curAngle - startingAngle > 0) { //If the robot is veering to the right
					targetSpeedL -= SPEEDADJUST;
				} else if(curAngle - startingAngle < 0) {//If the robot is veering to the right
					targetSpeedR -= SPEEDADJUST;
				}
				drive(-targetSpeedL, -targetSpeedR);
				speed = pidDist.getOutput(curTicks);
				drive(-power, -power);
				driving = true;
			}
			
			updateDashboard();
		}
		
		// testing navx drive straight
//		public void driveDistance(double distance) {
//			double power = .5;
//			int desTicks = (int) (TPI * distance);
//			pidDist.setSetpoint(desTicks);
//			double compensate = pidDist.getOutput(Math.round(navx.getYaw()));
//			drive(power, power * (1 + compensate));
//		}
		
		public void driveTime(double time, boolean reverse) {
			if (!timing) { // Start the timer and flip the TIMING flag
				timer.start();
				timing = true;
			}
			
			if (timer.get() < time) { // Run the motors at MAX AUTO SPEED for the specified time and flip the DRIVING flag
				if (reverse) {
					drive(-MAX_AUTO_SPEED);
				}
				else {
					drive(MAX_AUTO_SPEED); 
				}
				driving = true;
			}
			else { // Reset the timer and flip the TIMING and DRIVING flag
				timer.stop();
				timer.reset();
				timing = false;
				driving = false;
			}
			
			updateDashboard();
		}
		
		public void turnTo(double desAngle) {
			int deadzone = 2;
			int curAngle = Math.round(navx.getYaw());
			SmartDashboard.putNumber("Current Angle:", curAngle); // Debug
			pidNavx.setSetpoint(desAngle);
			speed = pidNavx.getOutput(curAngle);

			if ((curAngle - desAngle < deadzone) && (curAngle - desAngle > -deadzone)) { //STOPS if in deadzone of angle
				drive(0, 0); // STOPS motors
				turning = false;
			} 
			else { //TURNS until facing a specified angle
				drive(-speed, speed);
				turning = true;
			}
			
			updateDashboard();
		}
		
		
		// =====UTILITY METHODS=====
		
		
		private void updateDashboard() {
			SmartDashboard.putBoolean("Driving:", driving);
			SmartDashboard.putBoolean("Turning:", turning);
			SmartDashboard.putBoolean("Timing:", timing);
		}
		
		public void stop() {
			drive(0);
			Motors.resetEncoder(Motors.driveEncoderLeft);
			Motors.resetEncoder(Motors.driveEncoderRight);
		}
}
