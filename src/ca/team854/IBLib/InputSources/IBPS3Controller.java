/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.team854.IBLib.InputSources;

/**
 *
 * @author Alexander
 */
public class IBPS3Controller extends IBGameController {

	public static int LeftThumbstickXAxis = 0;
	public static int LeftThumbstickYAxis = 1;
	public static int RightThumbstickXAxis = 2;
	public static int RightThumbstickYAxis = 3;
	
	public static int LeftShoulderButton = 5;
	public static int RightShoulderButton = 6;
	public static int LeftTriggerButton = 7;
	public static int RightTriggerButton = 8;
	public static int SelectButton = 9;
	public static int StartButton = 10;

	public static int TriangleButton = 4;
	public static int CircleButton = 3;
	public static int SquareButton = 2;
	public static int XButton = 1;
	
	public IBPS3Controller() {
		 this(0);
	}

	public IBPS3Controller(int port) {
		super(port);
	}
	
	public boolean getTriangleButton() { return getRawButton(TriangleButton); }
	public boolean getCircleButton() { return getRawButton(CircleButton); }
	public boolean getSquareButton() { return getRawButton(SquareButton); }
	public boolean getXButton() { return getRawButton(XButton); }
}
