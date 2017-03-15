package MatrixProjectXDM.MatrixTestClasses;

import MatrixProjectXDM.VerticalDifferenceMethod;
import polyfun.Polynomial;
import sun.applet.Main;
/**
 * Tests the VDM Class
 * @author student
 *
 */
public class VDM_Test {
	public static void main(String[] args) {
		Polynomial bro = new Polynomial(1.0, 5); // one part of polynomial
		Polynomial yo = new Polynomial (-2.0, 3); //second part of polynomial 
		bro = bro.plus(yo); //add the two to yield x^3 + 2x^2
		bro.print(); //print out this polynomial
		VerticalDifferenceMethod jo = new VerticalDifferenceMethod(); //initiates use of Vertical Difference Method
		System.out.println(jo.slopeAtPoint(bro, 2)); //Print out the derivative of bro at x = 2
		jo.plotDerivative(bro); //Plots the derivative
		
		//testing differentiation rules
		jo.diffRules(bro);
	}
}
