/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.team854.IBLib;

/**
 *
 * @author Alexander
 */
public class Utils {
	public static String indent(int indents) {
		String s = "";
		for (int i = 0; i < indents; i++) s += "    ";
		return s;
	}
	
	public static String pwmOutToString(int indentLevel, String className, int channel) {
		return indent(indentLevel)+"[" + className + ": on PWM output " + channel+ "]";
	}
	
	public static String encoderToString(int indentLevel, int AChannel, int BChannel) {
		String indent = indent(indentLevel);
		return  indent + "[Encoder Channel A on Diginal input " + AChannel + "\n" +
				indent + " Encoder Channel B on Diginal input " + BChannel + "]";
	}
	
	public static String PIDToString(int indentLevel, double Kp, double Ki, double Kd, double Kf) {
		String indent = indent(indentLevel);
		
		String s = indent + "[PIDController: Kp: " + Kp + " Ki: " + Ki + " Kd: " + Kd;
		if (Kf != 0) s += " Kf: " + Kf;
		
		return s + "]";
	}
}
