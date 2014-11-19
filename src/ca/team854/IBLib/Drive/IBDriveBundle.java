/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.team854.IBLib.Drive;

import ca.team854.IBLib.Utils;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Alexander
 */
public class IBDriveBundle {

	static int defaultModule =  SensorBase.getDefaultDigitalModule();
		
	final private int motorModule;
	final private int motorChannel;
	final private SpeedController motor;
	final private String motorClassName;
	
	final private int encoderAModule;
	final private int encoderAChannel;
	final private int encoderBModule;
	final private int encoderBChannel;
	final private Encoder encoder;
	
	final private PIDController pidController;	
	
	IBDriveBundle (int motorModule, int motorChannel, SpeedController s, String name,
				  int encoderAModule, int encoderAChannel,
				  int encoderBModule, int encoderBChannel, Encoder e,
				  PIDController p) {
		this.motorModule  = motorModule;
		this.motorChannel = motorChannel;
		this.motor = s;
		this.motorClassName = name;
				
		this.encoderAModule  = encoderAModule;
		this.encoderAChannel = encoderAChannel;
		this.encoderBModule  = encoderBModule;
		this.encoderBChannel = encoderBChannel;
		this.encoder = e;
		
		this.pidController = p; 
	}
	
	public int getMotorModule() 			{ return motorModule;		}
	public int getMotorChannel()			{ return motorChannel;		}
	public SpeedController getMotor()		{ return motor;				}
	public String getMotorClassName()		{ return motorClassName;	}
	
	public int getEncoderAModule()			{ return encoderAModule;	}
	public int getEncoderAChannel() 		{ return encoderAChannel;	}
	public int getEncoderBModule() 			{ return encoderBModule;	}
	public int getEncoderBChannel() 		{ return encoderBChannel;	}
	public Encoder getEncoder() 			{ return encoder;			}
	
	public PIDController getPidController() { return pidController;		}
	
	public void set(double speed) {
		if (getPidController() != null) { //if the bundle is PID controlled
			getPidController().setSetpoint(speed); //set the setpoint on PID
		}
		else {
			getMotor().set(speed); //otherwise set motor speed directly
		}
	}
	
	public String toString() {
		return toString(0);
	}
	
	public String toString(int indentLevel) {
		String s = Utils.indent(indentLevel)+"[IBDriveBundle:";
		
		if (motor != null) s += "\n" + Utils.pwmOutToString(indentLevel + 1, motorClassName,
					getMotorModule(), getMotorChannel());
			
		if (encoder != null) s += "\n" + Utils.encoderToString(indentLevel + 1,
					getEncoderAModule(), getEncoderAChannel(),
					getEncoderBModule(), getEncoderBChannel());
				
		if (pidController != null) s+= "\n" + Utils.PIDToString(indentLevel + 1,
				pidController.getP(),
				pidController.getI(),
				pidController.getD(),
				pidController.getF());
				
		return s+"\n"+Utils.indent(indentLevel)+"]";
	}
	
	public static class IBDriveBundleFactory {	
		
		private int motorModule;
		private int motorChannel;
		private SpeedController motor;
		private String motorsClassName;

		private int encoderAModule;
		private int encoderAChannel;
		private int encoderBModule;
		private int encoderBChannel;
		private Encoder encoder;
	
		private PIDController pidController;
		
		public IBDriveBundleFactory addMotor(int motorChannel, Class c) {
			return addMotor(defaultModule, motorChannel, c);
		}
		
		public IBDriveBundleFactory addMotor(int motorModule, int motorChannel, Class c) {
			
			SpeedController m;
			if (c.equals(Talon.class))		 m = new  Talon(motorModule,  motorChannel);
			else if (c.equals(Victor.class)) m = new Victor(motorModule, motorChannel);
			else if (c.equals(Jaguar.class)) m = new Jaguar(motorModule, motorChannel);
			else throw new IllegalArgumentException("Class must be a Talon, Victor or Jaguar.");
			
			return addMotor(motorModule, motorChannel, m);
		}
		
		public IBDriveBundleFactory addMotor(int motorModule, int motorChannel, SpeedController motor) {
			this.motorModule = motorModule;
			this.motorChannel = motorChannel;
			this.motor = motor;
			this.motorsClassName = getClassName(motor);
					
			return this;
		}
		
		public static String getClassName(Object o) {
			String s = o.getClass().toString();
			return s.substring(s.lastIndexOf('.') + 1);
		}
		
		public IBDriveBundleFactory addEncoder(int encoderAChannel, int encoderBChannel) {
			return addEncoder(encoderAChannel, encoderBChannel, CounterBase.EncodingType.k1X);
		}
		
		public IBDriveBundleFactory addEncoder(int encoderAChannel, int encoderBChannel, CounterBase.EncodingType encodingType) {
			return addEncoder(defaultModule, encoderAChannel, defaultModule, encoderBChannel, false, encodingType);
		}
		
		public IBDriveBundleFactory addEncoder(int encoderAModule, int encoderAChannel,
											  int encoderBModule, int encoderBChannel,
											  boolean reverseDirection,
											  CounterBase.EncodingType encodingType) {
			Encoder e =  new Encoder(encoderAModule, encoderAChannel,
									 encoderBModule, encoderBChannel,
									 reverseDirection, encodingType);
			
			return addEncoder (encoderAModule, encoderAChannel,
							   encoderBModule, encoderBChannel, e);
		}
		
		public IBDriveBundleFactory addEncoder(int encoderAModule, int encoderAChannel,
											  int encoderBModule, int encoderBChannel,
											  Encoder encoder) {
			this.encoderAModule  = encoderAModule;
			this.encoderAChannel = encoderAChannel;
			this.encoderBModule  = encoderBModule;
			this.encoderBChannel = encoderBChannel;
			this.encoder = encoder;
			return this;
		}
		
		public IBDriveBundleFactory reverseEncoder(boolean reverseDirection) {
			this.encoder.setReverseDirection(reverseDirection);
			return this;
		}
		
		public IBDriveBundleFactory addPIDController(double Kp, double Ki, double Kd) {
			return addPIDController(Kp, Ki, Kd, 0, PIDController.kDefaultPeriod);
		}
		
		public IBDriveBundleFactory addPIDController(double Kp, double Ki, double Kd, double Kf) {
			return addPIDController(Kp, Ki, Kd, Kf, PIDController.kDefaultPeriod);
		}
		
		public IBDriveBundleFactory addPIDController(double Kp, double Ki, double Kd, double Kf, double period) {
			this.pidController = new PIDController(Kp, Ki, Kd, Kf, encoder, motor, period);
			return this;
		}
		
		public IBDriveBundle commit() {
			return new IBDriveBundle(motorModule, motorChannel, motor, motorsClassName,
									encoderAModule, encoderAChannel,
									encoderBModule, encoderBChannel,
									encoder, pidController);
		}
		
		public static IBDriveBundle newBasicBundle(int channel, Class c) {
			return new IBDriveBundleFactory()
					.addMotor(channel, c)
					.commit();
		}
	}
}
