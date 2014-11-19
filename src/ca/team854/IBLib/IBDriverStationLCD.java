/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.team854.IBLib;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 *
 * @author Alexander
 */
public class IBDriverStationLCD {
	
	DriverStationLCD lcd;
	
	DigitalInput sidecarLoopBackInput;
	DigitalOutput sidecarLoopBackOutput;
	boolean flipFlop;
	boolean diagnosticMode;
	
	public String  onString = "#";
	public String offString = "X";
	
	public IBDriverStationLCD() {
		this(0, 0);
		diagnosticMode = false;
	}
	
	public IBDriverStationLCD(int loopbackInPort, int loopbackOutPort) {
		lcd = DriverStationLCD.getInstance();
		sidecarLoopBackInput = new DigitalInput(loopbackInPort);
		sidecarLoopBackOutput = new DigitalOutput(loopbackOutPort);
		diagnosticMode = true;
	}
			
	public void display(String s1, String s2, String s3, String s4, String s5, String s6) {
		lcd.clear();
		lcd.println(DriverStationLCD.Line.kUser1, 1, s1);
		lcd.println(DriverStationLCD.Line.kUser2, 1, s2);
		lcd.println(DriverStationLCD.Line.kUser3, 1, s3);
		lcd.println(DriverStationLCD.Line.kUser4, 1, s4);
		lcd.println(DriverStationLCD.Line.kUser5, 1, s5);
		lcd.println(DriverStationLCD.Line.kUser6, 1, s6);
		
		if (diagnosticMode) {
			boolean b = sidecarLoopBackInput.get();
			
			lcd.println(DriverStationLCD.Line.kUser5,
					DriverStationLCD.kLineLength,
					b ? onString : offString);
			
			lcd.println(DriverStationLCD.Line.kUser6,
					DriverStationLCD.kLineLength,
					flipFlop ? onString : offString);
			
			sidecarLoopBackOutput.set(!b);
			flipFlop = !flipFlop;
		}
		
		lcd.updateLCD();
	}
	
	public void updateLCD() {
		lcd.updateLCD();
	}
	
	public void clear() {
		lcd.clear();
	}
	
	public void println(int lineNumber, int startingColumn, String text) {
		DriverStationLCD.Line line;
		switch (lineNumber) {
			case 1: line = DriverStationLCD.Line.kUser1;
				break;
			case 2: line = DriverStationLCD.Line.kUser2;
				break;
			case 3: line = DriverStationLCD.Line.kUser3;
				break;
			case 4: line = DriverStationLCD.Line.kUser4;
				break;
			case 5: line = DriverStationLCD.Line.kUser5;
				break;
			case 6: line = DriverStationLCD.Line.kUser6;
				break;
				
			default:
				System.out.println("Error: calling println with an invalid lineNumber");
				return;
		}
		
		lcd.println(line, startingColumn, text);
	}
}
