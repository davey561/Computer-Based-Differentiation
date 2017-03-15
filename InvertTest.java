package MatrixProjectXDM.MatrixTestClasses;

import MatrixProjectXDM.Matrix;

public class InvertTest {
        public static void main(String[] args) 
        {
                Matrix matt = new Matrix (3,3); //matt is invertible
                
                int counter = 0;
                
                for(int i=0; i<3; i++) //Filling matt
                {
                        for(int j=0; j<3; j++)
                        {
                                counter++;
                                matt.setEntry(i, j, counter);
                        }
                }
                
                matt.setEntry(2, 2, 10);
                System.out.println("Matt: ");
                matt.print(); //printing matt
                
                System.out.println("\n");
                System.out.println("Matt Inverse:");
                matt.invert().print(); //printing the inverse of matt
                
                System.out.println("\n");
                System.out.println("Matt-Inverse Product (If not identity, something is wrong up in here.");
                matt.times(matt.invert()).print(); //printing the product of matt and his inverse
                
                System.out.println("\n");
                
                
                Matrix trixie = new Matrix (4,4); //trixie is invertible
                
                for(int i=0; i<4; i++) //Filling trixie
                {
                        for(int j=0; j<4; j++)
                        {
                                if(i+j==2)
                                        trixie.setEntry(i, j, i+1);
                                else
                                        trixie.setEntry(i, j, 0);
                        }
                }
                
                trixie.setEntry(3,3, 4);
                
                System.out.println("Trixie: ");
                trixie.print(); //printing trixie
                
                System.out.println("\n");
                System.out.println("Trixie Inverse:");
                trixie.invert().print(); //printing the inverse of trixie
                
                System.out.println("\n");
                
                System.out.println("Trixie Inverse Product: ");
                trixie.times(trixie.invert()).print(); //printing the product of trixie and her inverse 
        }
}


