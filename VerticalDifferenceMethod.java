package MatrixProjectXDM;

import org.opensourcephysics.frames.PlotFrame;
import Riemann.PolyPracticeDM;
import polyfun.Atom;
import polyfun.Coef;
import polyfun.Polynomial;
import polyfun.Term;

/**
 * Vertical Difference Method
 * @author student Davey Morse
 * The Vertical Difference Method employs some interesting insights (first synthesized by Dr. Gomp.) about the tangent line to a function at a specific point that ultimately allow one to - with basic algebra - differentiate a given function.
 * The VDM is based on two different perspectives of an equivalent concept: The Vertical Difference between a given function and its tangent line at a given point c.
 * The first perspective is algebraic. The tangent line has equation mx + b = y. The function has the equation f(x) = y. Subtracting the tangent line from the function yields the vertical difference between the two: f(x) - mx - b.
 * The second is more graphic, analyzing the local behavior of the vertical difference at the point of tangency. At the point of tangency, the tangent line and the function intersect with multiplicity two. Therefore, one factor in the VDM function must be (x-c)^2. Call the other factors of the vertical difference Q(x).
 * Now, these two representations can be set equal, and setting equal the coefficients of each x term yields a system of equation of which m is a variable that can be solved for.
 */
public class VerticalDifferenceMethod {
	/**
	 * Both finds the derivative of given polynomial at point x and plots the derivative. This method contains the bulk of the VDM process. 
	 * The matrix is loaded and solved using previously coded functions. These include a function that interpolates a set of points into a function and another function that loads the matrix.
	 * @param paul the given polynomial
	 * @param x the x coordinate at which the derivative will be evaluated
	 * @return the slope of the tangent line of the given polynomial at the specific point indicated.
	 */
	public double slopeAtPoint(Polynomial paul, double c){
		/*
		//define tangent line polynomial
		Atom cero = new Atom('b');
		Atom uno = new Atom('m');
		Coef c0 = new Coef(cero);
		Coef c1 = new Coef(uno);
		Polynomial tang = new Polynomial(new Coef []{c0, c1});
		 */

		//Define graphically derived vertical difference function of form (x-c)^2*Q(x)
		int q_degree = paul.getDegree() - 2;
		//construct q with generic coefficients:
		Polynomial Q = new Polynomial('q', q_degree);
		//now defines new poly (x-c)^2
		Coef second_degree = new Coef(1);
		Coef first_degree = new Coef(-2*c);
		Coef constant = new Coef(c*c);
		Polynomial tangency = new Polynomial (new Coef [] {constant, first_degree, second_degree});

		//now multiples Q and tangency to get the final, graphically derived vertical difference function
		Polynomial gderived = tangency.times(Q);

		//The other form of the vertical difference will be algebraically derived: just f(x) - mx - b. Because the algebraic and graphically derived versions of this function are equivalent, 
		//we can set up an equation where paul - mx - b = gderived --> paul = gderived + mx + b. So, let's find gderived +mx+b:
		gderived = gderived.addTangent();
		//now find equalities by comparing coefficients of the terms on both sides of this equation, and put each of these equalities (the number of which = degree number of paul) into a matrix


		//resultant vector
		Matrix resultant = new Matrix(paul.getDegree()+1, 1);
		for(int col = 0; col<resultant.matt.length; col++){
			resultant.matt[col][0] = paul.getCoefficients()[col].getTerms()[0].getTermDouble();
		}
		return fillMatrix(gderived).invert().times(resultant).matt[1][0]; //second row, first (and only column) is value of m
	}
	public void plotDerivative(Polynomial paul){

		PlotFrame diff = new PlotFrame("Y", "X", "Derivative of given Polynomial");
		diff.setVisible(true);
		diff.setDefaultCloseOperation(3);
		InterpolationDM yo = new InterpolationDM();
		Polynomial derivative = new Polynomial();
		double [][] coords = new double[paul.getDegree()+1][2];
		for (int i = 0; i<paul.getDegree()+ 1; i++){
			int x = -100 + 50*i;
			coords[i][0] = x;
			coords[i][1] = slopeAtPoint(paul, -100 + 50*i);
		}

		this.graph(yo.interpolate(coords), diff, 1, 100);
	}
	public Matrix fillMatrix (Polynomial gderived){
		Matrix vdm_data = new Matrix(gderived.getDegree() + 1, gderived.getDegree()+1); //the matrix that stores the each equation that is formed by setting equal coefficients from gderived and paul
		//order of the 'vector' matrix (with all the values we are looking for): b, m, then all the Q coefs
		Atom [] vector = new Atom [gderived.getDegree()+1];

		//load vector
		vector[0] = new Atom('b');
		vector[1] = new Atom('m');
		for(int i = 2; i<vector.length; i++){
			int s = i-2;
			vector[i] = new Atom('q', s, 1);
		}

		//Fill up vdm_data matrix, keeping track of actual coefficient of each of the variables (b,m,q...).

		boolean nonzero = false; //initially assuming each spot in Matrix is zero, make the analysis actively show us otherwise

		for (int coefnum = 0; coefnum<vdm_data.matt.length; coefnum ++){ 	//for each coef in gderived. coefnum corresponds to degree of variable that this particular coef corresponds to. i.e., if coef is (2a+4b) in (2a+4b)x^2, degree number is 2
			//note that degree = coef number = row number
			//For each of the currently unknown constants (b, m, q1, q2, q3,...):
			for (int vari = 0; vari < vector.length; vari++){
				//note that vari = column number
				//initializes the term [] that represents the given coef
				Term [] coef = gderived.getCoefficients()[coefnum].getTerms();
				//For each term in this coef:
				for (int termnum = 0; termnum<coef.length; termnum++){ 
					//initializes the atom [] that represents the given term
					Atom [] term= coef[termnum].getTermAtoms();
					//For each atom in this term:
					for (int atomnum = 0; atomnum<term.length; atomnum++){ 
						//If the given variable is the atom of the term of the coef of the polynomial that the program is currently examining:
						if(vector[vari].identicalTo(term[atomnum])){
							//Finds the other atoms in this term, which are necessarily the double coefficient of the given variable in this term
							vdm_data.matt[coefnum][vari] = coef[termnum].getTermDouble();
							//Mark that the coef for this variable is indeed nonzero
							nonzero = true; 
						}
					}
				}
				//If this spot in the matrix is has no value:
				if (nonzero == false){
					//Set it to zero
					vdm_data.matt[coefnum][vari] = 0;
				}
			}
		}
		return vdm_data;
	}
	/*
	public Atom [] findOtherAtoms(Atom[] term, int atomnum){
		
		//for each atom in this term (term)
		Atom [] others = new Atom[term.length-1];
		boolean wheresit = false; //it has come already
		//for each of terms
		for(int tomnum = 0; tomnum<others.length; tomnum++){
			//keep track of whether atomnum has passed, is current, or is yet to come
			if (atomnum == tomnum){
				wheresit = true;
			}
			else if(tomnum!= atomnum){
				if (wheresit == false){
					others[tomnum] = term[tomnum];
				}
				else{
					others[tomnum] = term[tomnum+1];
				}
			}
		}
		return others;
	}
	*/
	public void graph (Polynomial poly, PlotFrame meh, double precision, int index){
		PolyPracticeDM okay = new PolyPracticeDM();
		for (int i = 0; i<index; i+=precision){
			meh.append(0, i, okay.eval(poly, i));
		}
	}
	public Polynomial diffRules(Polynomial poly){
		Coef [] coefs = poly.getCoefficients();
		Coef[] derivative_coefs = new Coef[coefs.length-1];
		for (int i  = 1; i<coefs.length-1; i++){
			derivative_coefs[i-1] = coefs[i].plus(new Coef(-1));
		}
		Polynomial derivative = new Polynomial (derivative_coefs);
		try{
			derivative.print();
		}
		catch(Exception e){
			
		}
		return derivative;
	}
}
