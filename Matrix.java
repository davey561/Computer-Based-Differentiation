package MatrixProjectXDM;

import java.text.DecimalFormat;

public class Matrix {
	int r; //number of rows
	int c; //number of columns
	double[][] matt; //stores doubles in the matrix

	//methods

	//Constructors
	/**
	 * Initializes this matrix, given a double [] of values
	 * @param wassup the given double []
	 */
	public Matrix (double [][] wassup){
		matt = setEqual(wassup); 	//sets this matrix's double array equal to the given one
		r = matt.length;			//records corresponding number of rows
		c = matt[0].length;			//records corresponding number of columns
	}
	/**
	 * Initializes a matrix, given its dimensions
	 * @param rownum number of rows
	 * @param colnum number of columns
	 */
	public Matrix (int rownum, int colnum){
		r = rownum;					//records the number of rows
		c = colnum;					//records the number of columns
		matt = new double [r][c];	//initializes the matrix's double array, matt, with the specified dimensions
	}
	
	//Methods
	/**
	 * Prints a 2 by 2 table representation of this matrix's data, using the other print function below.
	 */
	public void print(){
		//uses print function below to print this matrix's double array, 'matt'
		print(this.matt);
	}
	/**
	 * Prints 2 by 2 table representation given double array.
	 * @param printedthang the given double array
	 */
	public void print(double [][] printedthang){
		//Establishes the decimal number format, where all numbers are rounded all numbers to two decimal places:
		DecimalFormat df = new DecimalFormat("#.##");
		//For each row of this double array:
		for (int row = 0; row<printedthang.length; row++){
			//Move to next line:
			System.out.println();
			//For each column in this double array:
			for (int col = 0; col<printedthang[0].length; col++){
				//Print out the number at this index in the double array, formatted with two digits beyond the decimal point
				System.out.print(df.format(printedthang[row][col]) + "  ");
			}
		}
	}
	/**
	 * Streamlines process of setting the particular entry value of a matrix at a given index, to a given value
	 * @param row the specified row of the index
	 * @param col the specified column of the index
	 * @param v	the value to be set
	 */
	public void setEntry(int row, int col, double v){
		matt[row][col] = v;
	}
	/**
	 * Adds this matrix to another one.
	 * @param dawg the given other matrix, to which this one will be added
	 * @return the Sum of the two matrices
	 */
	public Matrix plus(Matrix dawg){
		//Initializes the sum matrix
		Matrix sum = new Matrix(r, c);
		//If it is valid to add these two matrices:
		if ((matt[0].length == dawg.matt[0].length) && (matt.length == dawg.matt.length)){
			//For each row:
			for(int row = 0; row<r; row++){
				//For each column:
				for (int col = 0; col<c; col++){
					//Sets the value of the sum matrix at the given matrix to the sum of the value of this matrix and the value of dawg at the given index
					sum.matt[row][col] = matt[row][col] + dawg.matt[row][col];
				}
			}
		}
		//Returns the Sum Matrix
		return sum;
	}
	/**
	 * Multiplies this matrix by the given matrix.
	 * @param dawg the given matrix
	 * @return the Matrix product of this matrix and the given one
	 */
	public Matrix times(Matrix dawg){
		//Initializes the product matrix with its proper dimensions (the number of rows of the first and the number of columns of the second).
		Matrix prod = new Matrix(r,dawg.matt[0].length);
		//Confirms that the two matrices can be added.
		//If the number of columns of this matrix equals the number of rows of dawg:
		if (matt[0].length == dawg.matt.length){
			//For each row in this matrix
			for(int row = 0; row<r; row++){
				//For each column in dawg
				for (int col = 0; col<dawg.matt[0].length; col++){
					//Initializes the value of the product matrix at index (row of this, col of dawg)
					double value = 0;
					//For each number in this matrix's row (for each of which has a corresponding number in this column of dawg):
					for (int n = 0; n<matt[0].length; n++){
						//Set value equal to this matrix at row,n and dawg at n,col.
						value += matt[row][n]*dawg.matt[n][col];
					}
					//Sets the given index of the product matrix equal to the value just previously calculated
					prod.matt[row][col] = value;
				}
			}
		}
		//Return the product matrix.
		return prod;
	}
	/**
	 * Multiplies a given scalar by a particular row of a copy of this matrix, returning the modified version of this matrix without changing this matrix itself.
	 * @param scalar the given double value
	 * @param rownumber the given row number
	 * @return the modified matrix copy
	 */
	public Matrix scalarTimesRow(double scalar, int rownumber){
		//Initializes product matrix
		Matrix prod = new Matrix (r,c);
		//Sets equal the values stored the Product matrix to those in This matrix
		prod.matt = setEqual(this.matt);
		//For each value in the row
		for (int i = 0; i<matt[0].length; i++){
			//Sets the new value of this matrix at this index to the old value times the given scalar
			prod.matt[rownumber][i] = prod.matt[rownumber][i]*scalar;
		}
		return prod;
	}
	/**
	 * Switches two rows in the matrix. The values of one row go to the previous location of the other row, and vice versa.
	 * @param firstrow the first row indicate
	 * @param secondrow the second row indicated
	 * @return the new Matrix with these two rows switched
	 */
	public Matrix switchRows(int firstrow, int secondrow){
		//Initializes the to-be-returned matrix matrix
		Matrix wtf = new Matrix (r,c);
		//Sets its double array equal to that of This
		wtf.matt = setEqual(this.matt);
		//Temporary double [] that stores the value of a row for reference after the original row is changed
		double[] temprow = setEqual(wtf.matt[firstrow]);
		//if row indexes given are within bounds of the matrix
		if ((firstrow<matt.length)&&(secondrow<matt.length)){
				wtf.matt[firstrow] = setEqual(wtf.matt[secondrow]);
				wtf.matt[secondrow] = setEqual(temprow);
		}
		else{
			System.out.println("Matrix.switchRows didn't perform properly");
		}
		return wtf;
	}
	/**
	 * Add a given first row multiplied by a scalar to another second row of This matrix, using function addRows below.
	 * @param scalar the scalar value
	 * @param firstrow the index of the first row
	 * @param secondrow the index of the second row
	 * @return A clone of this Matrix, modified accordingly
	 */
	public Matrix linearCombRows (double scalar, int firstrow, int secondrow){
		Matrix kobee = new Matrix(r,c);
		kobee.matt = setEqual(this.matt);
		return kobee.addRows(kobee.scalarTimesRow(scalar,firstrow).matt[firstrow], secondrow);
	}
	/**
	 * Adds a given first row to a given second row of this Matrix, replacing the values of this second row with these sums.
	 * Second row is the row being replaced. First row is used in calculation, but its values and placement go untouched in the matrix.
	 * @param firstrow the index of the first row to be added
	 * @param secondrow the index of the second row to be added
	 * return
	 */
	public Matrix addRows (double [] firstrow, int secondrow){
		//Initialize the to-be-returned sum matrix
		Matrix addison = new Matrix(r,c);
		//sets double array of Addison equal to that of This
		addison.matt = setEqual(this.matt);
		//For each index in the row:
		for (int i = 0; i<firstrow.length; i++){
			//set the second row at this index equal to the sum at this index
			addison.matt[secondrow][i] = firstrow[i] + addison.matt[secondrow][i];
		}
		return addison;
	}
	/**
	 * Manipulate a clone of this matrix into Row Reduced Echelon Form
	 * @return the modified row-reduced copy
	 */
	public Matrix rowReduce(){
		//Initialize to-be-returned, row-reduced matrix
		Matrix reddy = new Matrix(r,c);
		//set the double array of reddy equal to that of This
		reddy.matt = setEqual(this.matt);
		//For each column of reddy:
		for (int col = 0; col<reddy.matt.length; col++){
			//For each row that is equal to or greater than the column number in index:
			rowloop: for (int row = col; row<reddy.matt.length; row++){
				//If the value at this index is not zero:
				if(reddy.matt[row][col] != 0){
					//multiply that row by the scalar that makes the value at this index equal one
					reddy.matt[row] = reddy.scalarTimesRow(1/reddy.matt[row][col], row).matt[row];
					//Note that the desired row index number is equal to given column number.
					//Swap this row and the row at index [column]. If this row has index column, will have no effect.
					reddy = reddy.switchRows(row, col);
					//After making the correct row have a value of one
					break rowloop;
				}

			}
			//Make the items in this column of each other row zero.
			//For each row, in decreasing order:
			for (int row = reddy.matt.length-1; row>=0; row--){
				//If the row index is not equal to the column index:
				if (row!= col){
					//If the value at this index is not already zero
					if (reddy.matt[row][col]!= 0){
						//Add this row to a scalar multiplied version of the top row with a 1 that the index of this specific spot also becomes zero
						reddy = reddy.linearCombRows(-reddy.matt[row][col], col, row);
					}
				}
			}
		}
		return reddy;
	}
	public Matrix attachIdentity(){
		Matrix newmatt = new Matrix(matt.length, matt[0].length + matt.length);
		for (int row = 0; row<matt.length; row++){
			for (int col = 0; col<matt[0].length; col ++){
				newmatt.matt[row][col] = matt[row][col];
			}
			for(int col = matt[0].length; col<newmatt.matt[0].length; col++){
				if(col - matt[0].length == row){
					newmatt.matt[row][col] = 1;
				}
				else{
					newmatt.matt[row][col] = 0;
				}
			}
		}
		return newmatt;
	}
	public Matrix invert(){
		Matrix Invbig = new Matrix(matt);
		Invbig = Invbig.attachIdentity();
		//Invbig.print();
		//clear junk on right hand side
		Invbig = Invbig.rowReduce();
		for (int col = matt.length-1; col >0; col--){
			for (int row = col; row>0; row--){
				for(int secrow = row -1; secrow >=0; secrow --){
					linearCombRows(-Invbig.matt[secrow][col], row, secrow);
				}

			}
		}
		//grab back the right hand side
		Matrix Inv_refined = new Matrix(Invbig.matt.length, Invbig.matt.length);
		for (int row = 0; row<Invbig.matt.length; row++){
			for (int col = Invbig.matt.length; col<2*Invbig.matt.length; col++){
				Inv_refined.matt[row][col-Invbig.matt.length] = Invbig.matt[row][col];
			}
		}
		return Inv_refined;
	}
	public double [][] setEqual(double [][] orig){
		double [][] copy = new double [orig.length][orig[0].length];
		for (int i = 0; i<orig.length; i++){
			for(int j = 0; j<orig[0].length; j++){
				copy[i][j] = orig[i][j];
			}
		}
		return copy;
	}
	public double [] setEqual (double [] orig){
		double [] copy = new double [orig.length];
		for(int j = 0; j<orig.length; j++){
			copy[j] = orig[j];
		}
		return copy;
	}
}
