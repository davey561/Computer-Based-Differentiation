# Computer-Based-Differentiation
This repository consists of a set of classes that employ a technique for differentiation called Vertical Difference Method to
find the following for a given function:
 * its derivative function
 * a graph of its derivative function
 * the number value of its derivative at a given point *x=a* of functions
 It also has a function fundamentalTheorem that finds the difference between the original function and the derivative of its integral
 (the integral found using the Riemann class in the 'Computer-Based Integration' repository). The difference is always zero (or
 very very close to it).
 
 The Vertical Difference Method requires the solving of a system of equations, which is executed with matrix algebra and
 inversion. For this, there is a Matrix Class, which includes functions that perform elementary row operations and invert a given
 matrix.
