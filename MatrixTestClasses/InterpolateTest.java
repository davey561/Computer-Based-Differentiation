package MatrixProjectXDM.MatrixTestClasses;

import MatrixProjectXDM.InterpolationDM;

public class InterpolateTest {
	public static void main(String[] args) {
		InterpolationDM yo = new InterpolationDM();
		yo.interpolate(new double[][]{{1.0,1.0},{2.0,4.0},{3.0, 9.0}, {45,2}}).print();
	}
}
