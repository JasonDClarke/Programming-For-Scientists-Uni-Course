/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        this.coeff=coeff;
        int leadcheck=0;
        int i;
        for (i=coeff.length-1;i>-1; i--)
        {
            if ((leadcheck==0) && 
                (coeff[i].getReal() !=0 || coeff[i].getImag() !=0))
            {
                leadcheck=1;
                Complex newcoeff[] = new Complex[i+1];
                this.coeff = newcoeff;
            }
            if (leadcheck==1)
            {
                this.coeff[i]=coeff[i];
            }
        }
    }
    
    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        Complex newcoeff[] = new Complex[1];
        this.coeff = newcoeff;
        this.coeff[0] = new Complex();
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Create a string representation of the polynomial.
     *
     * For example: (1.0+1.0i)+(1.0+2.0i)X+(1.0+3.0i)X^2
     */
    public String toString() {
        String poly;
        int i;
        poly = "(" + coeff[0] + ")";
        if (coeff.length>1 &&
            (coeff[1].getReal() !=0 || coeff[1].getImag() !=0))
        {
        poly = poly + "+(" + coeff[1] + ")X";
        }
        if (coeff.length>2)
        {
            for(i=2;i<coeff.length;i++) 
            {
                if (coeff[i].getReal() !=0 || coeff[i].getImag() !=0)
                {
                poly = poly + "+(" + coeff[i] + ")X^" +i;
                }
            }
        }
            
		return poly;
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
	return coeff.length-1;
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        Complex eval = coeff[coeff.length-1];
        int i;
        for (i=coeff.length-2;i>-1; i--)
        {
            eval=coeff[i].add(z.multiply(eval));
        }
		return eval;
    }
    
    /**
     * Calculate and returns the derivative of this polynomial.
     *
     * @return The derivative of this polynomial.
     */
    public Polynomial derivative() {
        Complex newcoeff[] = new Complex[this.coeff.length-1];
                int i;
        for (i=0;i<newcoeff.length;i++)
        {
            newcoeff[i]=this.coeff[i+1].multiply(i+1);
        }
        return new Polynomial(newcoeff);
		
    }
    
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        Complex G = new Complex();
        Complex H = new Complex(1.0,2.0);
        Complex I = new Complex(3.0,4.0);
        Complex J = new Complex(1.0,0.0);
        Complex B[] = new Complex[] {I,H,G,H,I,H,H};
        Polynomial A = new Polynomial(B);
      
        System.out.println("Constructor test:");
        System.out.println("G = "+G.toString());
        System.out.println("H = "+H.toString());
        System.out.println("I = "+I.toString());
        System.out.println("A = "+A.toString());
        
        System.out.println("Evaluate test:");
        System.out.println("A(1) = "+A.evaluate(J));
        
        System.out.println("Derivative test:");
        System.out.println("A' = "+A.derivative().toString());
        
        System.out.println("Degree test:");
        System.out.println("Deg(A) = "+A.degree());
        System.out.println("Deg(A') = "+A.derivative().degree());
    }
}
