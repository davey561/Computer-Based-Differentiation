package MatrixProjectXDM.MatrixTestClasses;

import MatrixProjectXDM.Matrix;

public class MatrixTest {
	public static void main(String[] args) {
		Matrix wah = new Matrix(new double[][] {{3,-4, 6},{-1,4, 3}, {5,-5, 6}});
		//wah.print();
		wah.attachIdentity().print();
		wah.rowReduce().attachIdentity().invert().print();
	}
}
