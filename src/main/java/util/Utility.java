package util;

import java.lang.annotation.Target;

import javax.security.auth.x500.X500Principal;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class Utility {

	public static boolean inRange(double x, double target, double bound) {
		return x < target + bound && x > target - bound;
	}
	
	public static void configurePID(double p, double i, Double d, MiniPID pid) {
		pid.setP(p); pid.setI(i); pid.setD(d);
	}

}
