package MatrixProjectXDM;

import polyfun.Coef;
import polyfun.Polynomial;

public class InterpolationDM {
	/**
	 *  Interpolates a function determined by a few points as inputed
	 * @param coords a double [] of coordinates, each row is a coordinate pair. Length is one more than the degree of the polynomial
	 * @return the interpolated function
	 */
	public Polynomial interpolate (double [][] coords){
		Matrix equations = new Matrix (coords.length, coords.length);
		Matrix resultant = new Matrix (coords.length, 1); //this is the matrix with all of the y coordinates (equivalent values to the polynomials evaluated at the corresponding x values)
		Matrix inverse = new Matrix (coords.length, coords.length); //inverse of equations matrix
		Matrix solutions = new Matrix (coords.length, 1); //the values of the coefficients in the interpolated function
		Polynomial interpol = new Polynomial(); //the interpolated function
		
		//for each equation
		for (int row = 0; row<equations.matt.length; row++){
			//row number equals the equation number. each equation corresponds to a coordinate pair.
			for (int col = 0; col<equations.matt[0].length;col++){
				int degree = col; //polynomial
				equations.matt[row][col] = Math.pow(coords[row][0], degree); //setting this spot in matrix equal to the given x value, raised to the degree 
			}
		}
		//fill resultant matrix with the y coordinates
		for (int row = 0; row<resultant.matt.length; row++){
			resultant.matt[row][0] = coords[row][1]; //sets the corresponding item in the resultant matrix equal to the y value of the coordinate pair in the corresponding spot
		}
		
		//multiplies inverse by y coordinates to get the soluciones
		solutions = equations.invert().times(resultant);
		
		Coef [] coefs= new Coef[coords.length];
		//construct array of coeffs
		//for every coefficient
		for (int i = 0; i<coords.length; i++){
			coefs[i] = new Coef(solutions.matt[i][0]);
		}
		interpol = new Polynomial(coefs);
		interpol.print();
		return interpol;
	}
}
