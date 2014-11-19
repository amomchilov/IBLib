/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.team854.IBLib.Enums;

/**
 *
 * @author Alexander
 */
public class IBDirection {
	public static final int N_ordinal = 1;
	public static final int NE_ordinal = 2;
	public static final int E_ordinal = 3;
	public static final int SE_ordinal = 4;
	public static final int S_ordinal = 5;
	public static final int SW_ordinal = 6;
	public static final int W_ordinal = 7;
	public static final int NW_ordinal = 8;
	public static final IBDirection N = new IBDirection(N_ordinal);
	public static final IBDirection NE = new IBDirection(NE_ordinal);
	public static final IBDirection E = new IBDirection(E_ordinal);
	public static final IBDirection SE = new IBDirection(SE_ordinal);
	public static final IBDirection S = new IBDirection(S_ordinal);
	public static final IBDirection SW = new IBDirection(SW_ordinal);
	public static final IBDirection W = new IBDirection(W_ordinal);
	public static final IBDirection NW = new IBDirection(NW_ordinal);

	public final int ordinal;
	IBDirection(int ordinal) {
		this.ordinal = ordinal;
	}
}
