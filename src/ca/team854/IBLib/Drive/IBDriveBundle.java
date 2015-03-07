/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.team854.IBLib.Drive;

import ca.team854.IBLib.Utils;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author Alexander
 */
public class IBDriveBundle {
		
	final private int motorChannel;
	final private SpeedController motor;
	final private String motorClassName;
	final private double smallestAllowedChange;
	private double previousSpeed;
	
	final private int encoderAChannel;
	final private int encoderBChannel;
	final private Encoder encoder;
	
	final private PIDController pidController;	
	
	IBDriveBundle (int motorChannel, SpeedController s, String name, double smallestAllowedChange,
				  int encoderAChannel, int encoderBChannel, Encoder e,
				  PIDController p) {
		this.motorChannel = motorChannel;

		this.motor = s;
		this.motorClassName = name;
		this.smallestAllowedChange = smallestAllowedChange;
		previousSpeed = 0;
		set(0);
		
		this.encoderAChannel = encoderAChannel;
		this.encoderBChannel = encoderBChannel;
		this.encoder = e;
		
		this.pidController = p; 
	}
	
	public int getMotorChannel()		{ return motorChannel;		}
	public SpeedController getMotor()	{ return motor;				}
	public String getMotorClassName()	{ return motorClassName;	}
	
	public int getEncoderAChannel() 	{ return encoderAChannel;	}
	public int getEncoderBChannel() 	{ return encoderBChannel;	}
	public Encoder getEncoder() 		{ return encoder;			}
	
	public PIDController getPidController() { return pidController;		}
	
	public void set(double speed) {
		if (Math.abs(speed - previousSpeed) <= smallestAllowedChange) return; //change in speed to small, ignore it
		if (getPidController() != null) { //if the bundle is PID controlled
			getPidController().setSetpoint(speed); //set the setpoint on PID

			System.out.println("Set point: " + getPidController().getSetpoint());
		}
		else getMotor().set(speed); //otherwise set motor speed directly
		
		previousSpeed = speed;
	}
	
	public String toString() {
		return toString(0);
	}
	
	public void get() {
		//TODO: implement
	}
	
	public String toString(int indentLevel) {
		String s = Utils.indent(indentLevel)+"[IBDriveBundle:";
		
		if (motor != null) s += "\n" + Utils.pwmOutToString(indentLevel + 1, motorClassName, getMotorChannel());
			
		if (encoder != null) s += "\n" + Utils.encoderToString(indentLevel + 1, getEncoderAChannel(), getEncoderBChannel());
				
		if (pidController != null) s+= "\n" + Utils.PIDToString(indentLevel + 1,
				pidController.getP(),
				pidController.getI(),
				pidController.getD(),
				pidController.getF());
				
		return s+"\n"+Utils.indent(indentLevel)+"]";
	}
	
	public static class IBDriveBundleFactory {	
		
		private int motorChannel;
		private SpeedController motor;
		private String motorsClassName;
		private double smallestAllowedChange;

		private int encoderAChannel;
		private int encoderBChannel;
		private Encoder encoder;

		private double Kp;
		private double Ki;
		private double Kd;
		private double Kf;
		private double period;
		private boolean addPID;
		
		public IBDriveBundleFactory addMotor(int motorChannel, Class<? extends SpeedController> c) {
			try {
				//TODO: ensure this works with CAN speed controllers
				SpeedController m = c.getConstructor(Integer.TYPE).newInstance(motorChannel);
				return addMotor(motorChannel, m);
			} catch (Exception e) {
				System.err.println("Something went wrong with this fancy reflection stuff. D:");
				e.printStackTrace();
			}
			
			return this;
			
			
//			if (c.equals(Talon.class))		 m = new  Talon(motorChannel);
//			else if (c.equals(Victor.class)) m = new Victor(motorChannel);
//			else if (c.equals(Jaguar.class)) m = new Jaguar(motorChannel);
//			else throw new IllegalArgumentException("Class must be a Talon, Victor or Jaguar.");
			
		}
		
		public IBDriveBundleFactory addMotor(int motorChannel, SpeedController motor) {
			return addMotor(motorChannel, motor, 0.05);
		}
		
		public IBDriveBundleFactory addMotor(int motorChannel, SpeedController motor, double smallestAllowedChange) {
			this.motorChannel = motorChannel;
			this.motor = motor;
			this.motorsClassName = getClassName(motor);
			this.smallestAllowedChange = smallestAllowedChange;
					
			return this;
		}
		
		public static String getClassName(Object o) {
			String s = o.getClass().toString();
			return s.substring(s.lastIndexOf('.') + 1);
		}
		
		public IBDriveBundleFactory addEncoder(int encoderAChannel, int encoderBChannel) {
			return addEncoder(encoderAChannel, encoderBChannel, false, CounterBase.EncodingType.k1X);
		}
		
		public IBDriveBundleFactory addEncoder(int encoderAChannel, int encoderBChannel,
											  boolean reverseDirection,
											  CounterBase.EncodingType encodingType) {
			Encoder e =  new Encoder(encoderAChannel, encoderBChannel,
									 reverseDirection, encodingType);
			
			return addEncoder(encoderAChannel, encoderBChannel, e);
		}
		
		public IBDriveBundleFactory addEncoder(int encoderAChannel, int encoderBChannel,
											  Encoder encoder) {
			this.encoderAChannel = encoderAChannel;
			this.encoderBChannel = encoderBChannel;
			this.encoder = encoder;
			return this;
		}
		
		public IBDriveBundleFactory reverseEncoder(boolean reverseDirection) {
			this.encoder.setReverseDirection(reverseDirection);
			return this;
		}
		
		public IBDriveBundleFactory addPIDController(double Kp, double Ki, double Kd, double Kf) {
			return addPIDController(Kp, Ki, Kd, Kf, PIDController.kDefaultPeriod);
		}
		
		public IBDriveBundleFactory addPIDController(double Kp, double Ki, double Kd, double Kf, double period) {
			this.Kp = Kp;
			this.Ki = Ki;
			this.Kd = Kd;
			this.Kf = Kf;
			this.period = period;
			addPID = true; //the PIDController object will be instantiated in commit()
			
			return this;
		}
		
		public IBDriveBundle commit() {
			
			PIDController pid = null;
			if (addPID) {
				pid = new PIDController(Kp, Ki, Kd, Kf, encoder, motor, period);
				pid.enable();
			}
			
			return new IBDriveBundle(motorChannel, motor, motorsClassName, smallestAllowedChange,
									encoderAChannel, encoderBChannel,
									encoder, pid);
		}
		
		public static IBDriveBundle newBasicBundle(int channel, Class<? extends SpeedController> c) {
			return new IBDriveBundleFactory()
					.addMotor(channel, c)
					.commit();
		}
	}
}
