package vars;

public class Dimensions {

	/*
	 *  ANG = Angle
	 * 
	 * (1st Priority) {
	 * HAB_A1 = Starting point A (Level one)
	 * HAB_A2 = Starting point B (Level two)
	 * HAB_B = Starting point B
	 * HAB_C1 = Starting point C (Level one)
	 * HAB_C2 = Starting point C (Level two) }
	 *
	 * (2nd Priority) {
	 * BL = Baseline
	 * CS_A_1-4 = Cargo Ship A ports 1 to 4 (EX. CS_A_3)
	 * CS_C_1-4 = Cargo Ship C ports 1 to 4 (EX. CS_C_3)
	 * RS_A_1-3 = Rocket Ship A ports 1 to 3 (EX. RS_A_2)
	 * RS_C_1-3 = Rocket Ship C ports 1 to 3 (EX. RS_C_2) }
	 * 
	 * (3rd Priority) {
	 * LVL_1-3 = Level of goal on RS 1 = low goal (EX. LVL_3) }
	 * 
	 * (4th Priority) {
	 * D_A = Depot A
	 * D_C = Depot C
	 * LZ_A = Loading Zone A
	 * LZ_C = Loading Zone C }
	 */
	
// Distance Dimensions (Inches)

	/*
	 * From Starting Position to Baseline
	 */
	public static final double HAB_A1_BL = 120.0;
	public static final double HAB_A2_BL = 167.64;
	public static final double HAB_B_BL = 120.0;
	public static final double HAB_C1_BL = 120.0;
	public static final double HAB_C2_BL = 167.64;
	

	/*
	 * From Starting Position A1 to Cargo Ship A1-4
	 */

	public static final double HAB_A1_CS_A_1 = 149.964;
	public static final double HAB_A1_CS_A_2 = 188.66;
	public static final double HAB_A1_CS_A_3 = 210.41;
	public static final double HAB_A1_CS_A_4 = 232.16;
	
	/*
	 * From Starting Position A2 to Cargo Ship A1-4
	 */
	
	public static final double HAB_A2_CS_A_1 = 195.779;
	public static final double HAB_A2_CS_A_2 = 236.30;
	public static final double HAB_A2_CS_A_3 = 258.05;
	public static final double HAB_A2_CS_A_4 = 279.80;
	
	/*
	 * From Starting Position B to Cargo Ship A1 - C1
	 */
	
	public static final double HAB_B_CS_A_1 = 118.29;
	public static final double HAB_B_CS_C_1 = 118.29;
	
	/*
	 * From Starting Position C1 to Cargo Ship C1-4
	 */
	
	public static final double HAB_C1_CS_C_1 = 149.964;
	public static final double HAB_C1_CS_C_2 = 188.66;
	public static final double HAB_C1_CS_C_3 = 210.41;
	public static final double HAB_C1_CS_C_4 = 232.16;
	
	/*
	 * From Starting Position C2 to Cargo Ship C1-4
	 */
	
	public static final double HAB_C2_CS_C_1 = 195.779;
	public static final double HAB_C2_CS_C_2 = 236.30;
	public static final double HAB_C2_CS_C_3 = 258.05;
	public static final double HAB_C2_CS_C_4 = 279.80;
	
	/*
	 * From Starting Position A1 to Rocket Ship A1-3
	 */
	
	public static final double HAB_A1_RS_A_1 = 176.511;
	public static final double HAB_A1_RS_A_2 = 182.768;
	public static final double HAB_A1_RS_A_3 = 228.963;
	
	/*
	 * From Starting Position C1 to Cargo Ship C1-3
	 */
	
	public static final double HAB_C1_RS_C_1 = 176.511;
	public static final double HAB_C1_RS_C_2 = 182.768;
	public static final double HAB_C1_RS_C_3 = 228.936;
	
	/*
	 * From Cargo Ship A1-4 to Loading Zone A
	 */
	
	public static final double CS_A_1_LZ_A = 222.938;
	public static final double CS_A_2_LZ_A = 275.079;
	public static final double CS_A_3_LZ_A = 295.778;
	public static final double CS_A_4_LZ_A = 316.617;
	
	/*
	 * From Cargo Ship C1-4 to Loading Zone C
	 */
	
	public static final double CS_C_1_LZ_C = 222.938;
	public static final double CS_C_2_LZ_C = 275.079;
	public static final double CS_C_3_LZ_C = 295.778;
	public static final double CS_C_4_LZ_C = 316.617;
	
	/*
	 * From Rocket Ship A1-2 to Loading Zone A
	 */
	
	public static final double RS_A_1_LZ_A = 201.13;
	public static final double RS_A_2_LZ_A = 229.836;
	
	/*
	 * From Rocket Ship C1-2 to Loading Zone C
	 */
	
	public static final double RS_C_1_LZ_C = 201.13;
	public static final double RS_C_2_LZ_C = 229.836;
	
// Angle Dimensions (Degrees)
	
	/*
	 * From Starting Position A1 to Cargo Ship A1-4 (Angle)
	 */
	
	public static final double ANG_HAB_A1_CS_A_1 = 17.837;
//	public static final double ANG_HAB_A1_CS_A_2 = 0.0;
//	public static final double ANG_HAB_A1_CS_A_3 = 0.0;
//	public static final double ANG_HAB_A1_CS_A_4 = 0.0;
	
	/*
	 * From Starting Position A2 to Cargo Ship A1-4 (Angle)
	 */
	
	public static final double ANG_HAB_A2_CS_A_1 = 13.871;
//	public static final double ANG_HAB_A2_CS_A_2 = 0.0;
//	public static final double ANG_HAB_A2_CS_A_3 = 0.0;
//	public static final double ANG_HAB_A2_CS_A_4 = 0.0;
	
	/*
	 * From Starting Position B to Cargo Ship A1 - C1 (Angle)
	 */
	
	public static final double ANG_HAB_B_CS_A_1 = 0.0;
	public static final double ANG_HAB_B_CS_C_1 = 0.0;
	
	/*
	 * From Starting Position C1 to Cargo Ship C1-4 (Angle)
	 */
	
	public static final double ANG_HAB_C1_CS_C_1 = -17.837;
	public static final double ANG_HAB_C1_CS_C_2 = 0.0;
	public static final double ANG_HAB_C1_CS_C_3 = 0.0;
	public static final double ANG_HAB_C1_CS_C_4 = 0.0;
	
	/*
	 * From Starting Position C1 to Cargo Ship C1-4 (Angle)
	 */
	
	public static final double ANG_HAB_C2_CS_C_1 = -13.871;
	public static final double ANG_HAB_C2_CS_C_2 = 0.0;
	public static final double ANG_HAB_C2_CS_C_3 = 0.0;
	public static final double ANG_HAB_C2_CS_C_4 = 0.0;
	
	/*
	 * From Starting Position A1 to Rocket Ship A1-3 (Angle)
	 */
	
	public static final double ANG_HAB_A1_RS_A_1 = 36.785;
	public static final double ANG_HAB_A1_RS_A_2 = 33.329;
	public static final double ANG_HAB_A1_RS_A_3 = 27.491;
	
	/*
	 * From Starting Position C1 to Rocket Ship C1-3 (Angle)
	 */
	
	public static final double ANG_HAB_C1_RS_C_1 = -36.785;
	public static final double ANG_HAB_C1_RS_C_2 = -33.329;
	public static final double ANG_HAB_C1_RS_C_3 = -27.491;
	
	/*
	 * From Cargo Ship A1-4 to Loading Zone A (Angle)
	 */
	
	public static final double ANG_CS_A_1_LZ_A = 37.126;
	public static final double ANG_CS_A_2_LZ_A = 71.425;
	public static final double ANG_CS_A_3_LZ_A = 87.625;
	public static final double ANG_CS_A_4_LZ_A = 73.933;
	
	/*
	 * From Cargo Ship C1-4 to Loading Zone C (Angle)
	 */
	
	public static final double ANG_CS_C_1_LZ_C = -37.126;
	public static final double ANG_CS_C_2_LZ_C = -71.425;
	public static final double ANG_CS_C_3_LZ_C = -87.625;
	public static final double ANG_CS_C_4_LZ_C = -73.933;
	
	/*
	 * From Rocket Ship A1-2 to Loading Zone A (Angle)
	 */
	
	public static final double ANG_RS_A_1_LZ_A = 0.0;
	public static final double ANG_RS_A_2_LZ_A = 4.429;
	
	/*
	 * From Rocket Ship C1-2 to Loading Zone C (Angle)
	 */
	
	public static final double ANG_RS_C_1_LZ_C = 0.0;
	public static final double ANG_RS_C_2_LZ_C = -4.429;
	
// Level Dimensions (Inches)
	
	/*
	 * From Rocket Ship A1 to LVL1-3
	 */
	
	public static final double RS_A_1_LVL_1 = 19;
	public static final double RS_A_1_LVL_2 = 47;
	public static final double RS_A_1_LVL_3 = 75;
	
	/*
	 * From Rocket Ship A2 to LVL1-3
	 */
	
	public static final double RS_A_2_LVL_1 = 27;
	public static final double RS_A_2_LVL_2 = 55;
	public static final double RS_A_2_LVL_3 = 83;
	
	/*
	 * From Rocket Ship A3 to LVL1-3
	 */
	
	public static final double RS_A_3_LVL_1 = 19;
	public static final double RS_A_3_LVL_2 = 47;
	public static final double RS_A_3_LVL_3 = 75;
	
	/*
	 * From Rocket Ship C1 to LVL1-3
	 */
	
	public static final double RS_C_1_LVL_1 = 19;
	public static final double RS_C_1_LVL_2 = 47;
	public static final double RS_C_1_LVL_3 = 75;
	
	/*
	 * From Rocket Ship C2 to LVL1-3
	 */
	
	public static final double RS_C_2_LVL_1 = 27;
	public static final double RS_C_2_LVL_2 = 55;
	public static final double RS_C_2_LVL_3 = 83;
	
	/*
	 * From Rocket Ship C3 to LVL1-3
	 */
	
	public static final double RS_C_3_LVL_1 = 19;
	public static final double RS_C_3_LVL_2 = 47;
	public static final double RS_C_3_LVL_3 = 75;
	
	
	//tester final double
	public static final double TEST = 12;
}
