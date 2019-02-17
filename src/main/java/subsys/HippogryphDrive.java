package subsys;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import power.hawks.frc.lib.subsys.DriveTrain;
import util.MiniPID;
import vars.Motors;
import vars.Pneumatics;

public class HippogryphDrive extends DriveTrain{

	MiniPID pidStrait = new MiniPID(0, 0, 0); //TODO: Calibrate
	AHRS navx = new AHRS(Port.kMXP);
	
	public HippogryphDrive() {
		super(Motors.driveLeft, Motors.driveRight);
	}
		
		//Robot specific drive, probably none since there is
		// no shifting and no Power Take Off

}
