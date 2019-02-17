package subsys.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.*;
import power.hawks.frc.lib.subsys.DriveTrain;
//import vars.Motors;
import edu.wpi.first.wpilibj.command.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.SerialPort;

import com.ctre.phoenix.motorcontrol.ControlMode;
import java.lang.Object;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.Signature;

import javax.security.auth.x500.X500Principal;

import edu.wpi.first.wpilibj.SerialPort;

public class PixyCam {

	I2C cam = new I2C(Port.kOnboard, 0x69);
	
	int temp;
	int i;
	private int checkSum;
	private int sig;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public PixyCam() {
		//constructor
	}
	public void updateData() {
			if(readByte() == 85) {
				if(readByte() == -86) {
					if(readByte() == 85) {
						if(readByte() == -86) {
							checkSum = readShort();
							sig = readShort();
							x = readShort();
							y = readShort();
							width = readShort();
							height = readShort();

						}
					}	
				}	
			}else {
				x = 0;
			}
		}

	public byte readByte() {
		ByteBuffer buffer = ByteBuffer.allocateDirect(1);
		cam.readOnly(buffer, 1);
		byte myByte = buffer.get();
		return myByte;
	}
	public int getCheckSum() {
		System.out.println("Checksum: " + checkSum);
		return checkSum;
	}
	public int getSig() {
		System.out.println("sig: " + sig);
		return sig;
	}
	public int getX(){
		System.out.println("x: " + x);
		return x;
	}
	public int getWidth(){
		System.out.println("width " + width);
		return width;
	}
	public int getY(){
		System.out.println("Y " + y);
		return y;
	}
	public int getHeight(){
		System.out.println("height " + height);
		return height;
	}
	public short readShort() {
		ByteBuffer buffer = ByteBuffer.allocateDirect(2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		cam.readOnly(buffer, 2);
		short myShort = buffer.getShort();
		return myShort;
	}
	//85
	//-86
	public void pixy() {
		try {
			updateData();
			System.out.println(x);
			
		} catch (Exception e) {
			System.out.println("error");
		}
	}

}
